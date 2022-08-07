package model.entity;

public class ClientData {
	public String title;
	public String name;
	public String commission;
	
	public ClientData(String title, String name, String commission) {
		this.title = title;
		this.name = name;
		this.commission = commission;
	}

	@Override
	public String toString() {
		return "ClientData [name=" + name + ", commission=" + commission + "]";
	}
	
	//TODO: aggiungi gli altri dati estratti da csv
	
}
