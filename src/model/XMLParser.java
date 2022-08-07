package model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class XMLParser {
	private File file;
	public XMLParser(File file) throws IOException {
		/*String originalPath = file.getPath();
		System.out.println(originalPath);
		String[] splittedPath = originalPath.split("\\.");
		System.out.println(splittedPath[0]);
		splittedPath[0] += "_copy";
		String newPath = String.join(".", originalPath);
		System.out.println(newPath);
		File newFile = new File(newPath);
		Files.copy(file.toPath(), newFile.toPath(), 
				StandardCopyOption.REPLACE_EXISTING);
		
		this.file = newFile;
		System.out.println(this.file.getAbsolutePath());*/
		
		this.file=file;
	}
	
	public Document parse() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(file);
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		return doc;
		
	}
}
