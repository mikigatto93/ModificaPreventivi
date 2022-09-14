package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.entity.ProductData;
import utilities.MyStringUtilities;

public class PDFConverter {
	private Document preventivo;
	private Document config;

	public PDFConverter(Document preventivo, Document config) {
		this.preventivo = preventivo;
		this.config = config;
	}
	
	private void prettifyNumbersPreventivo() {
		Element rowsElem = (Element) preventivo.getElementsByTagName("Rows").item(0);
		NodeList rowsList = rowsElem.getElementsByTagName("Row");
		
		for (int i = 0; i < rowsList.getLength(); i++) {
			Element row = (Element) rowsList.item(i);
			Node punit = row.getElementsByTagName("UnitPrice").item(0);
			Node ptot = row.getElementsByTagName("QuantityPrice").item(0);
			
			punit.setTextContent(MyStringUtilities.prettifyNumber(punit.getTextContent()));
			ptot.setTextContent(MyStringUtilities.prettifyNumber(ptot.getTextContent()));
		}
		
		NodeList trasmList = rowsElem.getElementsByTagName("Trasmittance");
		for (int i = 0; i < trasmList.getLength(); i++) {
			trasmList.item(i).setTextContent(MyStringUtilities.prettifyNumber(trasmList.item(i).getTextContent()));
		}
		
		Node totPrice = preventivo.getElementsByTagName("TotalPrice").item(0);
		totPrice.setTextContent(MyStringUtilities.prettifyNumber(totPrice.getTextContent()));
		
	}
	
	private void deprettifyNumbersPreventivo() {
		Element rowsElem = (Element) preventivo.getElementsByTagName("Rows").item(0);
		NodeList rowsList = rowsElem.getElementsByTagName("Row");
		
		for (int i = 0; i < rowsList.getLength(); i++) {
			Element row = (Element) rowsList.item(i);
			Node punit = row.getElementsByTagName("UnitPrice").item(0);
			Node ptot = row.getElementsByTagName("QuantityPrice").item(0);
			
			punit.setTextContent(MyStringUtilities.deprettifyNumber(punit.getTextContent()));
			ptot.setTextContent(MyStringUtilities.deprettifyNumber(ptot.getTextContent()));
		}
		
		NodeList trasmList = rowsElem.getElementsByTagName("Trasmittance");
		for (int i = 0; i < trasmList.getLength(); i++) {
			trasmList.item(i).setTextContent(MyStringUtilities.deprettifyNumber(trasmList.item(i).getTextContent()));
		}
		
		Node totPrice = preventivo.getElementsByTagName("TotalPrice").item(0);
		totPrice.setTextContent(MyStringUtilities.deprettifyNumber(totPrice.getTextContent()));
	}
	
	private String convertXMLToHTML(ArrayList<ProductData> pdata) {
		//convert document to an xml string
		
		//prettify all numbers
		prettifyNumbersPreventivo();
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;

		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}

		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();

		try {
			transformer.transform(new DOMSource(preventivo), new StreamResult(writer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		String output = writer.getBuffer().toString();
		
		//reset the document original numbers (the string output is already prettified)
		deprettifyNumbersPreventivo();
		
		//replace xml nods wit divs
		output = output.replaceAll("\\<[^>]*?\\/>", "");
		output = output.replaceAll("<", "<div class=\"");
		output = output.replaceAll(">", "\">");
		output = output.replaceAll("\\<div class=\\\"\\/.*?\\\">", "</div>");

		//add end html tags
		StringBuilder strBuilder = new StringBuilder(output);
		strBuilder.append("</body></html>");

		//add logo
		strBuilder.insert(output.indexOf("<div class=\"Reseller\">") + 22,
				"<img src=\"file:///" + config.getElementsByTagName("Logo").item(0).getTextContent() + "\" />");

		//add doctype, starting html tags and stylesheet
		strBuilder.insert(0, 
				"<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><link rel=\"stylesheet\"href=\"file:///"+ 
				 config.getElementsByTagName("PdfCssStyle").item(0).getTextContent()
				+ "\"/></head><body>");

		output = strBuilder.toString();

		//add lists on header

		//add li elements
		Pattern pattern =  Pattern.compile("<div");
        Matcher matcher = pattern.matcher(output);
        output = MyStringUtilities.replaceFromTo(3, 14, matcher,"<li");

        pattern =  Pattern.compile("</div>");

        matcher = pattern.matcher(output);
        output = MyStringUtilities.replaceFromTo(0, 5, matcher,"</li>");

        matcher = pattern.matcher(output);
        output = MyStringUtilities.replaceFromTo(1, 6, matcher,"</li>");

        //add the ul tags
        output = output.replace("<li class=\"BrandId\">", "<ul><li class=\"BrandId\">");
        output = output.replace("</div><ul><li class=\"BrandId\">", "</ul></div><ul><li class=\"BrandId\">");
        output = output.replace("</div><div class=\"Rows\">", "</ul></div><div class=\"Rows\">");
        output = output.replace("<li class=\"CompanyName\">", "<ul><li class=\"CompanyName\">");

        //add addictional container div (.ImgContainer, .PriceDetails)
        output = output.replaceAll("<div class=\"Row\">", "<div class=\"Row\"><div class=\"ImgContainer\">");
        output = output.replaceAll("<div class=\"ProductId\">.*?</div>", "$0 </div>");
        output = output.replaceAll("<div class=\"DescriptionExtended\">", "<div class=\"PriceDetails\"><div class=\"DescriptionExtended\">");
        output = output.replaceAll("<div class=\"Quantity\">.*?</div>", "$0 </div>");

        // add all the product images
 		pattern = Pattern.compile("<div class=\"ProductId\">.*?</div>");
 		matcher = pattern.matcher(output);

         //build src list
         ArrayList<String> imgSrcList = new ArrayList<>();

         for (int i = 0; i < pdata.size(); i++) {
         	imgSrcList.add("<img src=\"file:///" + pdata.get(i).imagePath + "\" />");
         }

 		output = MyStringUtilities.replaceAllList(matcher, imgSrcList);

		return output;
	}

	public void createPDF(ArrayList<ProductData> pdata, String path) {
		//create html file for conversion with Wkhtmltopdf
		String htmlpath = path + ".html";
		try (OutputStreamWriter writer =
	             new OutputStreamWriter(new FileOutputStream(htmlpath), StandardCharsets.UTF_8)) {
			writer.write(convertXMLToHTML(pdata));
		} catch (IOException e) {
		      e.printStackTrace();
		}

		// call external process
		String command = config.getElementsByTagName("WkhtmltopdfExecutable").item(0).getTextContent() +
				" --print-media-type" + " --enable-local-file-access " + htmlpath + " " + path + ".pdf";
		System.out.println(command);
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		BufferedReader stdInput = new BufferedReader(new
			     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new
		     InputStreamReader(proc.getErrorStream()));

		// Read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		try {
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		try {
			while ((s = stdError.readLine()) != null) {
			    System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			proc.waitFor();
			System.out.println("Deleting temporary html files...");
			File file = new File(htmlpath);
			file.delete();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

			
	}
}
