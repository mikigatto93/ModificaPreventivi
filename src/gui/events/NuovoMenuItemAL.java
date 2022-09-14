package gui.events;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.PreventivoGUIBuilder;
import model.PreventivoManager;

public class NuovoMenuItemAL implements ActionListener {

	private PreventivoGUIBuilder gui;
	private PreventivoManager model;

	public NuovoMenuItemAL(PreventivoGUIBuilder guiPreventivo, PreventivoManager model) {
		this.gui = guiPreventivo;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel panel = new JPanel(layout);
		JTextField titoloTextField = new JTextField(50);
		JTextField clienteTextField = new JTextField(50);
		JTextField commessaTextField = new JTextField(50);
		JLabel labTitolo = new JLabel("Titolo");
		JLabel labCliente = new JLabel("Cliente");
		JLabel  labCommessa = new JLabel("Commessa");
		
		//padding
		c.insets = new Insets(5,10,5,10);
		
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(labTitolo, c);
		
		c.gridx = 1;
		c.gridy = 0;
		panel.add(titoloTextField, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panel.add(labCliente, c);
		
		c.gridx = 1;
		c.gridy = 1;
		panel.add(clienteTextField, c);
		
		c.gridx = 0;
		c.gridy = 2;
		panel.add(labCommessa, c);
		
		c.gridx = 1;
		c.gridy = 2;
		panel.add(commessaTextField, c);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Crea nuovo preventivo", JOptionPane.OK_CANCEL_OPTION);
		//TODO: validate...
		if (result == JOptionPane.OK_OPTION) {
			
		}

	}

}
