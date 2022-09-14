package gui.events;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import gui.PreventivoGUIBuilder;
import model.PreventivoManager;
import model.XMLParser;

public class ApriMenuItemAL implements ActionListener {
	private final JFileChooser fc = new JFileChooser();
	private PreventivoManager model;
	private PreventivoGUIBuilder gui;

	public ApriMenuItemAL(PreventivoGUIBuilder gui, PreventivoManager model) {
		this.model = model;
		this.gui = gui;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    loadFileIntoGUI(selectedFile, model.getPreventivo() != null);
		}
		
		//loadFileIntoGUI(new File("src\\test.xml"), model.getPreventivo() != null);
			
			
	}

	private void loadFileIntoGUI(File f, boolean redraw) {
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
			if (!redraw)
				gui.buildGUI();
			else 
				gui.rebuildGUI();
			//model.setTextFieldsMaps(gui.getTextFieldHeaderMap(), gui.getTextFieldsProduct());
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

