package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import model.PreventivoManager;
import model.XMLParser;

public class ApriMenuItemAL implements ActionListener {
	private final JFileChooser fc = new JFileChooser();
	private JMenu parent;
	private PreventivoManager model;
	private JPanel preventivoPanel;
	public ApriMenuItemAL(JMenu parent, JPanel productPanel, PreventivoManager model) {
		this.parent = parent;
		this.model = model;
		this.preventivoPanel = productPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int result = fc.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    loadFileIntoGUI(selectedFile);
		}
		
	}

	private void loadFileIntoGUI(File f) {
		XMLParser parser = null;
		
		try {
			parser = new XMLParser(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Document parsedDoc = parser.parse();
			model.setPreventivo(parsedDoc);
			PreventivoGUIBuilder GUIBuilder = new PreventivoGUIBuilder(preventivoPanel, model);
			GUIBuilder.buildGUI();
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

}

