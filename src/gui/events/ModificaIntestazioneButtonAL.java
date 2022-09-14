package gui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTextField;

import model.PreventivoManager;

public class ModificaIntestazioneButtonAL implements ActionListener {

	private HashMap<String, JTextField> textFieldHeaderMap;
	private PreventivoManager model;

	public ModificaIntestazioneButtonAL(HashMap<String, JTextField> textFieldHeaderMap2, PreventivoManager model) {
		this.textFieldHeaderMap = textFieldHeaderMap2;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		model.changeHeaderProperty("Title", textFieldHeaderMap.get("Title").getText());
		model.changeHeaderProperty("Commission", textFieldHeaderMap.get("Commission").getText());
		model.changeHeaderProperty("Customer", textFieldHeaderMap.get("Customer").getText());
	}

}
