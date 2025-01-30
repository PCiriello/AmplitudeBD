package com.amplitude.model;

public class Ordine {
	private int ID;
	private String data;
	private double totale;
	private String Account_username;
	private Integer Spedizione_ID;

	public Ordine(int ID, String data, double totale, String Account_username, Integer Spedizione_ID) {
		this.ID = ID;
		this.data = data;
		this.totale = totale;
		this.Account_username = Account_username;
		this.Spedizione_ID = Spedizione_ID;
	}

	public Ordine(String date, double totale, String username, int shippingID) {
		this.data = date;
		this.totale = totale;
		this.Account_username = username;
		this.Spedizione_ID = shippingID;
	}

	public Ordine() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public String getAccount_username() {
		return Account_username;
	}

	public void setAccount_username(String account_username) {
		Account_username = account_username;
	}

	public Integer getSpedizione_ID() {
		return Spedizione_ID;
	}

	public void setSpedizione_ID(Integer spedizione_ID) {
		Spedizione_ID = spedizione_ID;
	}

	@Override
	public String toString() {
		return "Ordine [ID=" + ID + ", data=" + data + ", totale=" + totale + ", Account_username=" + Account_username
				+ ", Spedizione_ID=" + Spedizione_ID + "]";
	}

}
