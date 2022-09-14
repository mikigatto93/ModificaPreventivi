package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
