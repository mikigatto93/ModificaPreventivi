package gui;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import gui.events.AggProdGenMenuItemAL;
import gui.events.ApriMenuItemAL;
import gui.events.EsportaPDFMenuItemAL;
import gui.events.ModificaNoteMenuItemAL;
import gui.events.NuovoMenuItemAL;
import gui.events.SalvaMenuItemAL;
import gui.events.VariationMenuItemAL;
import gui.events.VariationMenuItemAL.Mode;
import model.PreventivoManager;

public class MainGUI{
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu fileMenu, esportaMenu, modificaMenu, aggiungiMenu;
	private JMenuItem apriMenuItem, nuovoMenuItem, salvaMenuItem, esportaPDFMenuItem, 
					  varPercMenuItem, varNumMenuItem, modificaNoteMenuItem,
					  aggProdGenMenuItem;
	private Container  panel;
	private PreventivoManager model;
	private PreventivoGUIBuilder guiPreventivo;

	public MainGUI(String title, PreventivoManager model) {
		this.model = model;
		window = new JFrame();
		window.setTitle(title);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		window.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//window.setSize(500,500);
		panel = window.getContentPane();
		buildMenuGUI();
		guiPreventivo = new PreventivoGUIBuilder(panel, model);
		addEventListenersToMenu();
		
	}

	public void show() {
		window.pack();
		window.setVisible(true);
	}

	private void buildMenuGUI() {
		
		((JComponent) panel).setBorder(new EmptyBorder(10,30,10,30));
		
		menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        esportaMenu = new JMenu("Esporta");
        modificaMenu = new JMenu("Modifica");
        aggiungiMenu = new JMenu("Aggiungi");

        apriMenuItem = new JMenuItem("Apri");
		fileMenu.add(apriMenuItem);
		nuovoMenuItem = new JMenuItem("Nuovo");
		fileMenu.add(nuovoMenuItem);
        salvaMenuItem = new JMenuItem("Salva");
		fileMenu.add(salvaMenuItem);
		

		esportaPDFMenuItem = new JMenuItem("Esporta in PDF");
		esportaMenu.add(esportaPDFMenuItem);
		
		varPercMenuItem = new JMenuItem("Variazione in %");
		varNumMenuItem = new JMenuItem("Variazione numerica");
		modificaNoteMenuItem = new JMenuItem("Modifica note");
		modificaMenu.add(varNumMenuItem);
		modificaMenu.add(varPercMenuItem);
		modificaMenu.add(modificaNoteMenuItem);
		
		aggProdGenMenuItem = new JMenuItem("Aggiungi prodotto generico");
		aggiungiMenu.add(aggProdGenMenuItem);
		
		
        menuBar.add(fileMenu);
        menuBar.add(esportaMenu);
        menuBar.add(modificaMenu);
        menuBar.add(aggiungiMenu);
        
        window.setJMenuBar(menuBar);

        BoxLayout mainLayout = new BoxLayout(panel , BoxLayout.Y_AXIS);
        panel.setLayout(mainLayout);

	}


	private void addEventListenersToMenu() {
		apriMenuItem.addActionListener(new ApriMenuItemAL(guiPreventivo, model));
		esportaPDFMenuItem.addActionListener(new EsportaPDFMenuItemAL(model));
		salvaMenuItem.addActionListener(new SalvaMenuItemAL(model));
		varNumMenuItem.addActionListener(new VariationMenuItemAL(guiPreventivo, model, Mode.NUMERICAL));
		varPercMenuItem.addActionListener(new VariationMenuItemAL(guiPreventivo, model, Mode.PERCENTUAL));
		modificaNoteMenuItem.addActionListener(new ModificaNoteMenuItemAL(model));
		aggProdGenMenuItem.addActionListener(new AggProdGenMenuItemAL(guiPreventivo, model));
		nuovoMenuItem.addActionListener(new NuovoMenuItemAL(guiPreventivo, model));
	}





}
