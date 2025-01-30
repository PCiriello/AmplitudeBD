package com.amplitude.model;

import java.sql.Date;

public class Spedizione {
   private int ID; 
   private Date data_consegna; 
   private Date data_spedizione; 
   private String indirizzo; 
   private String stato; 

   public Spedizione(int ID, Date data_consegna, Date data_spedizione,String indirizzo,String stato) { 
      this.ID=ID; 
      this.data_consegna=data_consegna; 
      this.data_spedizione=data_spedizione; 
      this.indirizzo=indirizzo; 
      this.stato=stato; 
   }

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public Date getData_consegna() {
	return data_consegna;
}

public void setData_consegna(Date data_consegna) {
	this.data_consegna = data_consegna;
}

public Date getData_spedizione() {
	return data_spedizione;
}

public void setData_spedizione(Date data_spedizione) {
	this.data_spedizione = data_spedizione;
}

public String getIndirizzo() {
	return indirizzo;
}

public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}

public String getStato() {
	return stato;
}

public void setStato(String stato) {
	this.stato = stato;
} 


}