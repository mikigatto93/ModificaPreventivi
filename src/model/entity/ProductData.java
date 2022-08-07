package model.entity;

public class ProductData {
	public String id;
	public float transmittance;
	public String desc;
	public float unitPrice;
	public float quantity;
	public int position;
	
	public ProductData(String id, float transmittance, String desc, float unitPrice, float quantity, int position) {
		this.id = id;
		this.transmittance = transmittance;
		this.desc = desc;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.position = position;
	}

	@Override
	public String toString() {
		return "ProductData [id=" + id + ", transmittance=" + transmittance + ", desc=" + desc + ", unitPrice="
				+ unitPrice + ", quantity=" + quantity + ", position=" + position + "]";
	}
	
	
}
