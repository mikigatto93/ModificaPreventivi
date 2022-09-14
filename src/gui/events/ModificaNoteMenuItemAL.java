package gui.events;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.events.VariationMenuItemAL.Mode;
import model.PreventivoManager;

public class ModificaNoteMenuItemAL implements ActionListener {

	private PreventivoManager model;

	public ModificaNoteMenuItemAL(PreventivoManager model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextArea textArea = new JTextArea(5, 30);
		textArea.setText(model.getNote());
		
		int result = JOptionPane.showConfirmDialog(null, textArea, "Modifica note", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			model.changeFooterProperty("Note", textArea.getText());
		}
	}

}
