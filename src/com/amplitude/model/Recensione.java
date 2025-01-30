package com.amplitude.model;


public class Recensione {
    private int ID;
    private String titolo;
    private String testo;
    private int valutazione;
    private String data;
    private String Account_username;
    private int Prodotto_ID;
    
    public Recensione() {
    }

    public Recensione(int ID, String titolo, String testo, int valutazione, String data, String Account_username, int Prodotto_ID) {
        this.ID = ID;
        this.titolo = titolo;
        this.testo = testo;
        this.valutazione = valutazione;
        this.data = data;
        this.Account_username = Account_username; 
        this.Prodotto_ID = Prodotto_ID; 
     }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getValutazione() {
		return valutazione;
	}

	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}

	public String getData() {
		return data;
	}

	public void setData(String date) {
		this.data = date;
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
		return "Recensione [ID=" + ID + ", titolo=" + titolo + ", testo=" + testo + ", valutazione=" + valutazione
				+ ", data=" + data + ", Account_username=" + Account_username + ", Prodotto_ID=" + Prodotto_ID + "]";
	}


}
