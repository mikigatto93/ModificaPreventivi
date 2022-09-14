package gui.events;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import gui.PreventivoGUIBuilder;
import model.PreventivoManager;
import utilities.MyStringUtilities;

public class VariationMenuItemAL implements ActionListener {
	public enum Mode {
		PERCENTUAL,
		NUMERICAL
	} 
	private PreventivoManager model;
	private Mode mode;
	private PreventivoGUIBuilder gui;

	public VariationMenuItemAL(PreventivoGUIBuilder gui, PreventivoManager model, Mode mode) {
		this.model = model;
		this.mode = mode;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JTextField amountTextField = new JTextField(10);
		JLabel lab = new JLabel("Variazione:");
		JRadioButton isIncrement = new JRadioButton("Incremento", true);
		JRadioButton isDecrement = new JRadioButton("Decremento", false);
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(isIncrement);
		radioGroup.add(isDecrement);
	
		panel.add(lab);
		panel.add(amountTextField);
		panel.add(isIncrement);
		panel.add(isDecrement);
		
		if (mode == Mode.NUMERICAL) {
			int result = JOptionPane.showConfirmDialog(null, panel, "Variazione numerica", JOptionPane.OK_CANCEL_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				float amount = Float.parseFloat(MyStringUtilities.deprettifyNumber(amountTextField.getText()));
				int i = 0;
				float tot = 0.0f;
				for (Map<String, JComponent> map : gui.getTextFieldsProductList()) {
					JTextField punit = (JTextField) map.get("punit");
					float newPunit = 0;
					
					if (isIncrement.isSelected())
						newPunit = Float.parseFloat(MyStringUtilities.deprettifyNumber(punit.getText())) + amount; 
					else if (isDecrement.isSelected())
						newPunit = Float.parseFloat(MyStringUtilities.deprettifyNumber(punit.getText())) - amount; 
					
					newPunit = (float) (Math.round(newPunit * 100.0) / 100.0);
					punit.setText(MyStringUtilities.prettifyNumber(Float.toString(newPunit)));
					
					float newPartialTot = newPunit * Integer.parseInt(((JTextField) map.get("qta")).getText());
					((JLabel)(map.get("ptot"))).setText(
							MyStringUtilities.prettifyNumber(Float.toString(newPartialTot))
					);
					
					model.changeProductProperty(i, "UnitPrice", Float.toString(newPunit));
					model.changeProductProperty(i, "QuantityPrice", Float.toString(newPartialTot));
					tot += newPartialTot;
					//!! this updates the total every time, as it's the same reference for every row (there are better solutions)
					((JLabel)(map.get("ptotfinal"))).setText(
							"TOTALE: " + MyStringUtilities.prettifyNumber(Float.toString(tot))
					);
					i++;
				}
				
				//update the final total on the xml doc
				model.changeFooterProperty("TotalPrice", Float.toString(tot));
				
			}
			
		} else if (mode == Mode.PERCENTUAL) {
			int result = JOptionPane.showConfirmDialog(null, panel, "Variazione percentuale", JOptionPane.OK_CANCEL_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				float amount = Float.parseFloat(MyStringUtilities.deprettifyNumber(amountTextField.getText()));
				int i = 0;
				float tot = 0.0f;
				for (Map<String, JComponent> map : gui.getTextFieldsProductList()) {
					JTextField punit = (JTextField) map.get("punit");
					float punitAmount = Float.parseFloat(MyStringUtilities.deprettifyNumber(punit.getText()));
					float newPunit = 0;
					
					if (isIncrement.isSelected())
						newPunit = punitAmount + (amount / 100 * punitAmount); 
					else if (isDecrement.isSelected())
						newPunit = punitAmount - (amount / 100 * punitAmount);
					
					newPunit = (float) (Math.round(newPunit * 100.0) / 100.0);
					punit.setText(MyStringUtilities.prettifyNumber(Float.toString(newPunit)));
					
					float newPartialTot = newPunit * Integer.parseInt(((JTextField) map.get("qta")).getText());
					((JLabel)(map.get("ptot"))).setText(
							MyStringUtilities.prettifyNumber(Float.toString(newPartialTot))
					);
					
					model.changeProductProperty(i, "UnitPrice", Float.toString(newPunit));
					model.changeProductProperty(i, "QuantityPrice", Float.toString(newPartialTot));
					tot += newPartialTot;
					//!!update every time, it's the same reference for every row (there are better solutions)
					((JLabel)(map.get("ptotfinal"))).setText(
							"TOTALE: " + MyStringUtilities.prettifyNumber(Float.toString(tot))
					);
					i++;
				}
				
				//update the final total on the xml doc
				model.changeFooterProperty("TotalPrice", Float.toString(tot));
			}
		
		}
	}

}
