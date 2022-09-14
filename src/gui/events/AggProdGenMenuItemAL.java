package gui.events;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.PreventivoGUIBuilder;
import gui.events.VariationMenuItemAL.Mode;
import model.PreventivoManager;

public class AggProdGenMenuItemAL implements ActionListener {

	private PreventivoManager model;
	private PreventivoGUIBuilder gui;

	public AggProdGenMenuItemAL(PreventivoGUIBuilder gui,  PreventivoManager model) {
		this.model = model;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panel = new JPanel(layout);
		JTextField qtaTextField = new JTextField(5);
		JTextField punitTextField = new JTextField(10);
		JLabel labQta = new JLabel("Q.tà:");
		JLabel labPunit = new JLabel("Prez. Unit.:");
		JLabel pimage = new JLabel();
		JTextArea desc = new JTextArea(10, 50); 
		
		//padding
		c.insets = new Insets(10,30,10,30);
		
		//immagine 
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("F:/wnd-images/images//custom/prodotto.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(image);
		pimage.setIcon(icon);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(labQta, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panel.add(qtaTextField, c);
		
		c.gridx = 2;
		c.gridy = 0;
		panel.add(labPunit, c);
		
		c.gridx = 3;
		c.gridy = 0;
		panel.add(punitTextField, c);
		
		c.gridheight = 2;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(pimage, c);
		
		c.gridx = 2;
		c.gridy = 1;
		panel.add(desc, c);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Aggiungi nuovo prodotto generico", JOptionPane.OK_CANCEL_OPTION);
		//TODO: validate...
		if (result == JOptionPane.OK_OPTION) {
			String id = "CUSTOM.PRODOTTO";
			model.addGenericProduct(id, qtaTextField.getText(), punitTextField.getText(), desc.getText());
			gui.rebuildProdListGUI();
		}
		
		
		
	}

}
