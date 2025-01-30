package com.amplitude.model;

public class Categoria {
    private int ID;
    private String nome;
    private String descrizione;
    
    public Categoria() {
  	}

    public Categoria(int ID, String nome, String descrizione) {
        this.ID = ID;
        this.nome = nome;
        this.descrizione = descrizione;
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


}
