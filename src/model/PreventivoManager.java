package model;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.entity.*;

public class PreventivoManager {
	private Document preventivo = null;

	public void setPreventivo(Document parsedDoc) {
		preventivo = parsedDoc;
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
			ArrayList<ProductData> listToReturn = new ArrayList<ProductData>();  
			
			for (int i = 0; i < prodList.getLength(); i++) {
				if (prodList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element prod = (Element) prodList.item(i);
					
					NodeList trasm = prod.getElementsByTagName("Trasmittance");
					
					
					listToReturn.add(new ProductData(
								prod.getElementsByTagName("ProductId").item(0).getTextContent(),
								(trasm.getLength() != 0) ? Float.parseFloat(trasm.item(0).getTextContent()) : 0.0f,
								prod.getElementsByTagName("Description").item(0).getTextContent(),
								Float.parseFloat(prod.getElementsByTagName("UnitPrice").item(0).getTextContent()),
								Float.parseFloat(prod.getElementsByTagName("Quantity").item(0).getTextContent()),
								Integer.parseInt(prod.getElementsByTagName("Position").item(0).getTextContent())
							));
				}
			}
			return listToReturn;
		}
		return null;
	}
	
	
	
	
	
	
}
