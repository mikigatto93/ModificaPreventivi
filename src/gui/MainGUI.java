package gui;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.*;

import model.PreventivoManager;

public class MainGUI{
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem apriMenuItem;
	private JMenuItem salvaMenuItem;
	private JPanel panel;
	private ArrayList<ProductElem> productList;
	private PreventivoManager model;
	
	public MainGUI(String title, PreventivoManager model) {
		this.model = model;
		window = new JFrame();
		window.setTitle(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		//window.setSize(500,500);
		buildGUI();
	}
	
	public void show() {
		window.pack();
		window.setVisible(true);
	}
	
	private void buildGUI() {
		menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        
        
        apriMenuItem = new JMenuItem("Apri");
		fileMenu.add(apriMenuItem);
        salvaMenuItem = new JMenuItem("Salva");
		fileMenu.add(salvaMenuItem);

        menuBar.add(fileMenu);

        window.setJMenuBar(menuBar);
        
        
        
        panel = new JPanel();
        BoxLayout mainLayout = new BoxLayout(panel , BoxLayout.Y_AXIS);
        
        window.add(panel);
        
        addEventListenersToMenu();
		
	}
	
	
	private void addEventListenersToMenu() {
		apriMenuItem.addActionListener(new ApriMenuItemAL(fileMenu, panel, model));
	}
	
	
	
	
	
}
