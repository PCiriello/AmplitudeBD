package com.amplitude.model;

public class Prodotto {
	private int ID;
	private String nome;
	private String descrizione;
	private int quantità;
	private double prezzo;
	private int Categoria_ID;

	public Prodotto() {
	}

	public Prodotto(int iD, int quantità) {
		super();
		this.ID = iD;
		this.quantità = quantità;
	}

	public Prodotto(int ID, String nome, String descrizione, int quantità, double prezzo, int Categoria_ID) {
		this.ID = ID;
		this.nome = nome;
		this.descrizione = descrizione;
		this.quantità = quantità;
		this.prezzo = prezzo;
		this.Categoria_ID = Categoria_ID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getCategoria_ID() {
		return Categoria_ID;
	}

	public void setCategoria_ID(int categoria_ID) {
		Categoria_ID = categoria_ID;
	}

	@Override
	public String toString() {
		return "Prodotto [ID=" + ID + ", nome=" + nome + ", descrizione=" + descrizione + ", quantità=" + quantità
				+ ", prezzo=" + prezzo + ", Categoria_ID=" + Categoria_ID + "]";
	}

}
