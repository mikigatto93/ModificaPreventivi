package model.entity;

public class ProductData {
	public String id;
	public float transmittance;
	public String desc;
	public float unitPrice;
	public int quantity;
	public int position;
	public String imagePath;

	public ProductData(String id, float transmittance, String desc, float unitPrice, int quantity, int position, String baseImagePath) {
		this.id = id;
		this.transmittance = transmittance;
		this.desc = desc;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.position = position;
		this.imagePath = getImageFromId(baseImagePath);
	}

	private String getImageFromId(String basePath) {
		
		String path = basePath + "/images/catalog/";

		String[] pathFrags = id.toLowerCase().split("\\.");
		
		if (pathFrags[0].equals("custom"))
			path = basePath + "/images/";
		
		//cartella
		path += pathFrags[0] + "/";

		//tento di ricostruire il path
		if (pathFrags[1].startsWith("cas")) {
			path += "sfusi/";
			for (int i = 1; i < pathFrags.length; i++)
				path += pathFrags[i] + ".";

		} else {
			if (pathFrags[1].startsWith("c"))
				path += "sfusi/";

			for (int i = 1; i < pathFrags.length; i++)
				path += pathFrags[i] + "_";
		}
		path = path.substring(0, path.length() - 1);
		path += ".png";
		//System.out.println(path);
		return path;
	}

	@Override
	public String toString() {
		return "ProductData [id=" + id + ", transmittance=" + transmittance + ", desc=" + desc + ", unitPrice="
				+ unitPrice + ", quantity=" + quantity + ", position=" + position + "]";
	}


}
