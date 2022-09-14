package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.entity.ClientData;
import model.entity.CompanyData;
import model.entity.ProductData;
import utilities.MyStringUtilities;

public class PreventivoManager {
	private Document preventivo = null;
	private Document config;

	public void setPreventivo(Document parsedDoc) {
		preventivo = parsedDoc;
		try {
			this.config = new XMLParser(new File("config.xml")).parse();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void convertToPDF(String path) {
		if (preventivo != null) {
			PDFConverter PDFPrinter = new PDFConverter(preventivo, config);
			PDFPrinter.createPDF(getProductDataList(), path);
		}
	}

	public CompanyData getCompanyData() {
		if (preventivo != null) {

			return new CompanyData(
						preventivo.getElementsByTagName("CompanyName").item(0).getTextContent(),
					    preventivo.getElementsByTagName("VatNumber").item(0).getTextContent(),
					    preventivo.getElementsByTagName("Address").item(0).getTextContent(),
					    preventivo.getElementsByTagName("Phone").item(0).getTextContent(),
					    preventivo.getElementsByTagName("Email").item(0).getTextContent()
				    );

		}
		return null;
	}

	public ClientData getClientData() {
		if (preventivo != null) {
			//getClientCompleteData
			return new ClientData(
						preventivo.getElementsByTagName("Title").item(0).getTextContent(),
						preventivo.getElementsByTagName("Customer").item(0).getTextContent(),
					    preventivo.getElementsByTagName("Commission").item(0).getTextContent()
					);
		}
		return null;
	}

	public ArrayList<ProductData> getProductDataList() {
		if (preventivo != null) {
			NodeList prodList = preventivo.getElementsByTagName("Row");
			ArrayList<ProductData> listToReturn = new ArrayList<>();

			for (int i = 0; i < prodList.getLength(); i++) {
				if (prodList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element prod = (Element) prodList.item(i);

					NodeList trasm = prod.getElementsByTagName("Trasmittance");


					listToReturn.add(new ProductData(
								prod.getElementsByTagName("ProductId").item(0).getTextContent(),
								(trasm.getLength() != 0) ? Float.parseFloat(
										MyStringUtilities.deprettifyNumber(trasm.item(0).getTextContent())
										) : 0.0f,
								prod.getElementsByTagName("Description").item(0).getTextContent(),
								Float.parseFloat(MyStringUtilities.deprettifyNumber(prod.getElementsByTagName("UnitPrice").item(0).getTextContent())),
								Integer.parseInt(prod.getElementsByTagName("Quantity").item(0).getTextContent()),
								Integer.parseInt(prod.getElementsByTagName("Position").item(0).getTextContent()),
								config.getElementsByTagName("ImageFolder").item(0).getTextContent()
							));
				}

			}

			return listToReturn;
		}
		return null;
	}

	public void changeProductProperty(int index, String name, String newContent) {
		Element rowsElem = (Element) preventivo.getElementsByTagName("Rows").item(0);
		NodeList row = rowsElem.getElementsByTagName("Row");
		NodeList prodElements = ((Element) row.item(index)).getElementsByTagName(name);
		
		if (prodElements.getLength() > 0) 
			prodElements.item(0).setTextContent(newContent);
		
	}
	
	public void changeHeaderProperty(String name, String newContent) {
		
		NodeList header = preventivo.getElementsByTagName("Header");
		((Element) header.item(0)).getElementsByTagName(name).item(0).setTextContent(newContent);
		
	}
	
	public void changeFooterProperty(String name, String newContent) {
		
		NodeList footer = preventivo.getElementsByTagName("Footer");
		((Element) footer.item(0)).getElementsByTagName(name).item(0).setTextContent(newContent);
		
	}
	
	public float calculateTotal() {
		float tot = 0.0f;
		
		NodeList partialTotals = preventivo.getElementsByTagName("QuantityPrice");
		for (int i = 0; i < partialTotals.getLength(); i++) {
			if (partialTotals.item(i).getNodeType() == Node.ELEMENT_NODE) 
				tot += Float.parseFloat(partialTotals.item(i).getTextContent());
		}
		
		return (float) (Math.round(tot * 100.0) / 100.0);
	}
	
	public Document getPreventivo() {
		return preventivo;
	}

	public String getNote() {
		Element footer = (Element) preventivo.getElementsByTagName("Footer").item(0);
		return footer.getElementsByTagName("Note").item(0).getTextContent();
	}

	public void addGenericProduct(String id, String qta, String punit, String desc) {
		Element rowsElem = (Element) preventivo.getElementsByTagName("Rows").item(0);
		NodeList rowList = rowsElem.getElementsByTagName("Row");
		String ptot = Float.toString(
							Math.round(
									Integer.parseInt(qta) * Float.parseFloat(MyStringUtilities.deprettifyNumber(punit)) * 100
							) / 100
				);
		Element posEl = preventivo.createElement("Position");
		posEl.appendChild(preventivo.createTextNode(Integer.toString(rowList.getLength()+1)));
		Element	idEl =  preventivo.createElement("ProductId");
		idEl.appendChild(preventivo.createTextNode(id));
		Element descEl =  preventivo.createElement("Description");
		descEl.appendChild(preventivo.createTextNode(desc));
		Element descEstEl =  preventivo.createElement("DescriptionExtended");
		descEstEl.appendChild(preventivo.createTextNode(""));  //it's used in the conversion to html
		Element punitEl =  preventivo.createElement("UnitPrice");
		punitEl.appendChild(preventivo.createTextNode(punit));
		Element qtaPriceEl =  preventivo.createElement("QuantityPrice");
		qtaPriceEl.appendChild(preventivo.createTextNode(ptot));
		Element qtaEl =  preventivo.createElement("Quantity");
		qtaEl.appendChild(preventivo.createTextNode(qta));
				
		
		//new row
		Element newRow = preventivo.createElement("Row");
		newRow.appendChild(posEl);
		newRow.appendChild(idEl);
		newRow.appendChild(descEl);
		newRow.appendChild(descEstEl);
	    newRow.appendChild(punitEl);
	    newRow.appendChild(qtaPriceEl);
	    newRow.appendChild(qtaEl);
	    
	    rowsElem.appendChild(newRow);
	    
	    //change total
	    changeFooterProperty("TotalPrice", Float.toString(calculateTotal()));
	    
	}


}
