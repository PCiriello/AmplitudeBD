package com.amplitude.model;

public class Carrello {
	private int ID;
	private int quantita;
	private String Account_username;
	private int Prodotto_ID;

	public Carrello(int ID, int quantita, String Account_username, int Prodotto_ID) {
		this.ID = ID;
		this.quantita = quantita;
		this.Account_username = Account_username;
		this.Prodotto_ID = Prodotto_ID;
	}

	public Carrello() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getQuantità() {
		return quantita;
	}

	public void setQuantità(int quantità) {
		this.quantita = quantità;
	}

	public String getAccount_username() {
		return Account_username;
	}

	public void setAccount_username(String account_username) {
		Account_username = account_username;
	}

	public int getProdotto_ID() {
		return Prodotto_ID;
	}

	public void setProdotto_ID(int prodotto_ID) {
		Prodotto_ID = prodotto_ID;
	}

	@Override
	public String toString() {
		return "Carrello [ID=" + ID + ", quantità=" + quantita + ", Account_username=" + Account_username
				+ ", Prodotto_ID=" + Prodotto_ID + "]";
	}

}
