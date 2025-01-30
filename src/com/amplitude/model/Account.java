package com.amplitude.model;

public class Account {

	private String username;
	private String email;
	private String nome;
	private String cognome;
	private String telefono;
	private String indirizzo;
	private String dataNascita;
	private Boolean isAdmin;
	private String nazionalita;

	public Account(String username, String email, String nome, String cognome, String telefono, String indirizzo,
			String dataNascita, Boolean isAdmin, String nazionalita) {
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.indirizzo = indirizzo;
		this.dataNascita = dataNascita;
		this.isAdmin = isAdmin;
		this.nazionalita = nazionalita;
	}

	public Account() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Boolean getisAdmin() {
		return isAdmin;
	}

	public void setisAdmin(Boolean tipo) {
		this.isAdmin = tipo;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", email=" + email + ", nome=" + nome + ", cognome=" + cognome
				+ ", telefono=" + telefono + ", indirizzo=" + indirizzo + ", dataNascita=" + dataNascita + ", isAdmin="
				+ isAdmin + ", nazionalita=" + nazionalita + "]";
	}

}
