package gui.events;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import model.PreventivoManager;

public class EsportaPDFMenuItemAL implements ActionListener {
	private PreventivoManager model;

	public EsportaPDFMenuItemAL(PreventivoManager model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  model.convertToPDF(file.getAbsolutePath());
		}

	}

}
