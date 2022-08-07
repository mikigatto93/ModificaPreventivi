package gui;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import model.PreventivoManager;
import model.entity.ClientData;
import utilities.SpringUtilities;

public class PreventivoGUIBuilder {
	private JPanel panel;
	private PreventivoManager model;
	private HashMap<JTextField, String> textFieldMap;
	
	public PreventivoGUIBuilder(JPanel panel, PreventivoManager model) {
		this.panel = panel;
		this.model = model;
		this.textFieldMap = new HashMap<JTextField, String>();
		
	}
	
	public void buildGUI() {
		buildHeaderGUI();
	}

	private void buildHeaderGUI() {
		ClientData client = model.getClientData();
		SpringLayout layout = new SpringLayout();
		JPanel header = new JPanel();
		header.setLayout(layout);
		
		
		textFieldMap.put(new JTextField(client.title), "Title");
		textFieldMap.put(new JTextField(client.name), "Customer");
		textFieldMap.put(new JTextField(client.commission), "Commission");
		
		JLabel labels[] = {new JLabel("Titolo: "), new JLabel("Cliente: "), new JLabel("Commissione: ")};
		
		int i = 0;
		for (JTextField field : textFieldMap.keySet()) {
			labels[i].setLabelFor(field);
			header.add(labels[i]);
			header.add(field);
			i++;
		}
		SpringUtilities.makeCompactGrid(header, labels.length, 2, 5, 5, 5, 5);
		panel.add(header);
		header.revalidate();
		header.repaint();
	}
	
}
