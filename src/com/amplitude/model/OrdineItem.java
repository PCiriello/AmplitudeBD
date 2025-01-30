package com.amplitude.model;

public class OrdineItem {
	private int ID;
	private double prezzo_unitario;
	private int quantità;
	private int Ordine_ID;
	private int Prodotto_ID;

	public OrdineItem(int ID, double prezzo_unitario, int quantità, int Ordine_ID, int Prodotto_ID) {
		this.ID = ID;
		this.prezzo_unitario = prezzo_unitario;
		this.quantità = quantità;
		this.Ordine_ID = Ordine_ID;
		this.Prodotto_ID = Prodotto_ID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getPrezzo_unitario() {
		return prezzo_unitario;
	}

	public void setPrezzo_unitario(double prezzo_unitario) {
		this.prezzo_unitario = prezzo_unitario;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	public int getOrdine_ID() {
		return Ordine_ID;
	}

	public void setOrdine_ID(int ordine_ID) {
		Ordine_ID = ordine_ID;
	}

	public int getProdotto_ID() {
		return Prodotto_ID;
	}

	public void setProdotto_ID(int prodotto_ID) {
		Prodotto_ID = prodotto_ID;
	}


}