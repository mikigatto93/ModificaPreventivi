package model.entity;

public class CompanyData {
	public String name;
	public String vatNumber;
	public String address;
	public String phone;
	public String email;

	public CompanyData(String name, String vatNumber, String address, String phone, String email) {
		this.name = name;
		this.vatNumber = vatNumber;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	@Override
	public String toString() {
		return "CompanyData [name=" + name + ", vatNumber=" + vatNumber + ", address=" + address + ", phone=" + phone
				+ ", email=" + email + "]";
	}



}
