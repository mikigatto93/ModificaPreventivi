package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import gui.events.ModificaIntestazioneButtonAL;
import gui.events.ModificaRigaButtonAL;
import model.PreventivoManager;
import model.entity.ClientData;
import model.entity.ProductData;
import utilities.MyStringUtilities;
import utilities.SpringUtilities;

public class PreventivoGUIBuilder {
	private Container panel;
	private PreventivoManager model;
	private HashMap<String, JTextField> textFieldHeaderMap;
	private ArrayList<Map<String, JComponent>> textFieldsProduct;
	private JScrollPane scrollPane;
	private JLabel ptotfinal;
	private JPanel header;

	public PreventivoGUIBuilder(Container panel, PreventivoManager model) {
		this.panel = panel;
		this.model = model;
		this.textFieldHeaderMap = new HashMap<>();
		this.textFieldsProduct = new ArrayList<>();
	}
	
	

	public HashMap<String, JTextField> getTextFieldHeaderMap() {
		return textFieldHeaderMap;
	}

	public void buildGUI(boolean buildBoth) {
		if (buildBoth) 
			buildHeaderGUI();
		
		
		try {
			buildProductListGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollPane.getVerticalScrollBar().setValue(0);
	
	}
	
	public void buildGUI() {
		buildGUI(true);
	}
	
	public void rebuildGUI() {
		panel.remove(scrollPane);
		panel.remove(ptotfinal);
		panel.remove(header);
		buildGUI(true); 
		panel.revalidate();
		panel.repaint();
		
	}
	
	public void rebuildProdListGUI() {
		panel.remove(scrollPane);
		panel.remove(ptotfinal);
		buildGUI(false); // no rebuild of the header
		header.revalidate();
		header.repaint();
	}

	private void buildProductListGUI() throws IOException {
		JPanel prodListPanel = new JPanel();

		prodListPanel.setLayout(new BoxLayout(prodListPanel , BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(prodListPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		ptotfinal = new JLabel();
		ptotfinal.setFont(new Font("", Font.PLAIN, 20));
		ptotfinal.setBorder(new EmptyBorder(10,10,10,10));
		ptotfinal.setAlignmentX(Component.LEFT_ALIGNMENT); //left aligned
		float total = 0.0f;
		int numriga = 0;
		
		ArrayList<ProductData> productDataList = model.getProductDataList();
		for (ProductData pdata : productDataList) {
			numriga++;  // !!starts from 1!!
			JPanel prodItem = new JPanel();
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			prodItem.setLayout(layout);

			JTextArea desc = new JTextArea(pdata.desc);

			JTextField trasm = new JTextField(MyStringUtilities.prettifyNumber(Float.toString(pdata.transmittance)));
			JTextField punit = new JTextField(MyStringUtilities.prettifyNumber(Float.toString(pdata.unitPrice)));
			JTextField qta = new JTextField(Integer.toString(pdata.quantity));
			JLabel ptot = new JLabel(MyStringUtilities.prettifyNumber(Float.toString(pdata.unitPrice * pdata.quantity)));  //label so it's read-only
			
			trasm.setPreferredSize(new Dimension(60, 20));
			punit.setPreferredSize(new Dimension(60, 20));
			qta.setPreferredSize(new Dimension(60, 20));
			
			//update total
			total += pdata.unitPrice * pdata.quantity;

			JLabel ltrasm = new JLabel("Trasmittanza termica: ");
			JLabel lpunit = new JLabel("Prezzo unit.: ");
			JLabel lqta = new JLabel("Q.tà: ");
			JLabel lptot = new JLabel("Prezzo tot.: ");
			JLabel lpos = new JLabel("Posizione: " + pdata.position);
			
			//populate arraylist
			Map<String, JComponent> textFieldMap = Map.of(
					"punit", punit,
					"qta", qta,
					"desc", desc,
					"ptotfinal", ptotfinal,
					"ptot", ptot,
					"trasm", trasm
			);
			
			textFieldsProduct.add(textFieldMap);
			
			//padding
			c.insets = new Insets(10,20,10,20);

			//immagine e trasmittanza
			//System.out.println(pdata.imagePath);
			BufferedImage image = ImageIO.read(new File(pdata.imagePath));
			ImageIcon icon = new ImageIcon(image);
			JLabel pimage = new JLabel(icon);
			
			//add edit button
			JButton modificaRigaButton = new JButton("Modifica");
			modificaRigaButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			//modificaButton
			modificaRigaButton.addActionListener(new ModificaRigaButtonAL(numriga, textFieldMap, model));
			
			c.gridx = 0;
			c.gridy = 0;
			prodItem.add(modificaRigaButton, c);
			
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 2;
			c.gridwidth = 2;
			prodItem.add(pimage, c);

			//reset colspan and rowspan
			c.gridwidth = 1;
			c.gridheight = 1;
			
			//skip trasmittance if it's 0
			if (pdata.transmittance > 0) {
				c.gridx = 1;
				c.gridy = 2;
				prodItem.add(ltrasm, c);
	
				c.gridx = 2;
				c.gridy = 2;
				prodItem.add(trasm, c);
			}
			//desc

			c.gridx = 3;
			c.gridy = 0;
			c.gridheight = 3;
			c.gridwidth = 2;
			prodItem.add(desc, c);

			//prezzi e q.tà

			//reset colspan and rowspan
			c.gridheight = 1;
			c.gridwidth = 1;

			c.gridx = 5;
			c.gridy = 0;
			prodItem.add(lpunit, c);

			c.gridx = 6;
			c.gridy = 0;
			prodItem.add(punit, c);

			c.gridx = 5;
			c.gridy = 1;
			prodItem.add(lqta, c);

			c.gridx = 6;
			c.gridy = 1;
			prodItem.add(qta, c);

			c.gridx = 5;
			c.gridy = 2;
			prodItem.add(lptot, c);

			c.gridx = 6;
			c.gridy = 2;
			prodItem.add(ptot, c);
			
			//position
			c.gridx = 7;
			c.gridy = 0;
			prodItem.add(lpos, c);
			

			prodItem.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			//scrollPane.getVerticalScrollBar().setValue(0);
			prodListPanel.add(prodItem);
			
			
		}
		//prodListPanel.revalidate();
		//prodListPanel.repaint();
		
		scrollPane.revalidate();
		scrollPane.repaint();
		JScrollBar vertical = scrollPane.getVerticalScrollBar();
		//vertical.setValue(vertical.getMinimum());
		//vertical.setValues(0, 32, vertical.getMinimum(), vertical.getMinimum());
		vertical.setUnitIncrement(32);
		//vertical.addAdjustmentListener((event) -> System.out.println(vertical.getValue()));
		
		float totalrounded = (float) (Math.round(total * 100.0) / 100.0);
		
		ptotfinal.setText("TOTALE: " + MyStringUtilities.prettifyNumber(Float.toString(totalrounded)));
		panel.add(ptotfinal);
		panel.add(scrollPane);
		
		//vertical.setValue(vertical.getMinimum());
		
		
	}

	private void buildHeaderGUI() {
		ClientData client = model.getClientData();
		header = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		header.setAlignmentX(0.0f);
		textFieldHeaderMap.put("Title", new JTextField(client.title, 50));
		textFieldHeaderMap.put("Customer", new JTextField(client.name, 50));
		textFieldHeaderMap.put("Commission", new JTextField(client.commission, 50));

		JLabel labels[] = {new JLabel("Titolo: "), new JLabel("Cliente: "), new JLabel("Commissione: ")};

		/*int i = 0;
		for (String key : textFieldHeaderMap.keySet()) {
			labels[i].setLabelFor(textFieldHeaderMap.get(key));
			header.add(labels[i]);
			header.add(textFieldHeaderMap.get(key));
			i++;
		}*/
		
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.LINE_START;
		//labels[0].setLabelFor(textFieldHeaderMap.get("Title"));
		c.gridx = 0;
		c.gridy = 0;
		header.add(labels[0], c);
		
		c.gridx = 1;
		c.gridy = 0;
		header.add(textFieldHeaderMap.get("Title"), c);
		

		//labels[1].setLabelFor(textFieldHeaderMap.get("Customer"));
		c.gridx = 0;
		c.gridy = 1;
		header.add(labels[1], c);
		
		c.gridx = 1;
		c.gridy = 1;
		header.add(textFieldHeaderMap.get("Customer"), c);
		

		//labels[2].setLabelFor(textFieldHeaderMap.get("Commission"));
		c.gridx = 0;
		c.gridy = 2;
		header.add(labels[2], c);
		
		c.gridx = 1;
		c.gridy = 2;
		header.add(textFieldHeaderMap.get("Commission"), c);
		
		//modifica intestazione button
		JButton modificaIntestazioneButton = new JButton("Modifica intestazione");
		modificaIntestazioneButton.addActionListener(new ModificaIntestazioneButtonAL(textFieldHeaderMap, model));
		
		c.gridx = 0;
		c.gridy = 3;
		header.add(modificaIntestazioneButton, c);
		
		panel.add(header);
		
		header.revalidate();
		header.repaint();
	}
	
	public ArrayList<Map<String, JComponent>> getTextFieldsProductList() {
		return textFieldsProduct;
	}




	


}
