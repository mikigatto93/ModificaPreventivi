package gui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JFileChooser;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import model.PreventivoManager;

public class SalvaMenuItemAL implements ActionListener {
	private PreventivoManager model;

	public SalvaMenuItemAL(PreventivoManager model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getAbsolutePath() + ".xml";
			
			//convert document to an xml string
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = null;
			
			try {
				transformer = tf.newTransformer();
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			}
			
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			
			try {
				transformer.transform(new DOMSource(model.getPreventivo()), new StreamResult(new File(path)));
			} catch (TransformerException e2) {
				e2.printStackTrace();
			}
			
		}

	}
}
