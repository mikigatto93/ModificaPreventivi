package main;

import gui.MainGUI;
import model.PreventivoManager;

public class Main {
	public static void main(String[] args) {
		PreventivoManager model = new PreventivoManager();
		MainGUI gui = new MainGUI("Modifica Preventivi", model);
		gui.show();
		
	}
}
