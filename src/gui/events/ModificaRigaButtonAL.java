package gui.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.PreventivoManager;
import utilities.MyStringUtilities;

public class ModificaRigaButtonAL implements ActionListener {
	private int numriga;
	private Map<String, JComponent> textFieldMap;
	private PreventivoManager model;

	public ModificaRigaButtonAL(int numriga, Map<String, JComponent> textFieldMap, PreventivoManager model) {
		this.numriga = numriga;
		this.textFieldMap = textFieldMap;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String punit = ((JTextField) textFieldMap.get("punit")).getText();
		String qta = ((JTextField) textFieldMap.get("qta")).getText();
		String desc = ((JTextArea) textFieldMap.get("desc")).getText();
		String trasm = ((JTextField) textFieldMap.get("trasm")).getText();
		
		String partialTot = Float.toString(
				(float) (Math.round(
							Float.parseFloat(MyStringUtilities.deprettifyNumber(punit)) * Integer.parseInt(qta) * 100.0) / 100.0
						)
		);
		
		model.changeProductProperty(numriga-1, "UnitPrice", punit);
		model.changeProductProperty(numriga-1, "QuantityPrice", partialTot);
		model.changeProductProperty(numriga-1, "Quantity", qta);
		model.changeProductProperty(numriga-1, "Description", desc);
		model.changeProductProperty(numriga-1, "Transmittance", trasm);
		
		// update partial total
		((JLabel) textFieldMap.get("ptot")).setText(MyStringUtilities.prettifyNumber(partialTot));
		
		//update total
		float tot = model.calculateTotal();
		((JLabel) textFieldMap.get("ptotfinal")).setText("TOTALE: " + MyStringUtilities.prettifyNumber(Float.toString(tot)));
		
		model.changeFooterProperty("TotalPrice", Float.toString(tot));
		
		
	}

}
