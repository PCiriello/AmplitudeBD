package com.amplitude.view;

import java.sql.*;

import com.amplitude.control.*;
import com.amplitude.dao.*;
import com.amplitude.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AmplitudeApp {

	static Prodotto prodotto = new Prodotto();
	static ProdottoDAO p = new ProdottoDAO();
	static List<Prodotto> prodotti = new ArrayList<>();

	static OrdineDAO ordine = new OrdineDAO();
	static List<Ordine> ordini = new ArrayList<>();

	static OrdineItemDAO ordineitem = new OrdineItemDAO();

	static Recensione r = new Recensione();
	static RecensioneDAO recensione = new RecensioneDAO();
	static List<Recensione> recensioni = new ArrayList<>();

	static Account a = new Account();
	static AccountDAO account = new AccountDAO();
	static List<Account> accounts = new ArrayList<>();

	static CarrelloDAO carrello = new CarrelloDAO();

	static String id;
	static String name;
	static String description;
	static int quantity;
	static double price;
	static int categoryId;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Inserisci l'username per accedere al database: ");
		String username = scanner.nextLine();

		System.out.print("Inserisci la password per accedere al database: ");
		String password = scanner.nextLine();

		DatabaseConfig config = new DatabaseConfig(username, password);

		new DBConnectionPool(config);

		while (true) {
			System.out.println("Seleziona un'operazione:");
			System.out.println("1. Aggiungi un nuovo prodotto al catalogo");
			System.out.println("2. Registrare un nuovo amministratore");
			System.out.println("3. Effettua un ordine per un cliente");
			System.out.println("4. Inserisci una nuova recensione di un cliente");
			System.out.println("5. Selezionare il numero di vendite effettuate per un dato prodotto");
			System.out.println("6. Selezionare quanti ordini ha effettuato un cliente");
			System.out.println("7. Selezionare tutti i prodotti di una data categoria");
			System.out.println(
					"8. Selezionare l’username dei clienti che hanno speso almeno un determinato importo in ordine crescente");
			System.out.println("9. Selezionare nome e descrizione di tutti prodotti con valutazione superiore a ?");
			System.out.println(
					"10. Selezionare i prodotti comprati da clienti di nazionalità italiana che fanno parte della categoria 'Chitarre Elettriche'");
			System.out.println("11. Visualizzare gli ordini che non sono stati ancora consegnati");
			System.out.println(
					"12. Visualizzare tutti gli ordini effettuati da un dato cliente in ordine cronologico decrescente");
			System.out.println("13. Visualizza le recensioni dei clienti che hanno effettuato almeno due ordini");
			System.out.println("14. Visualizza tutti i prodotti con costo inferiore a 300€");
			System.out.println("15. Visualizza i prodotti con almeno due recensioni con una valutazione superiore a ?");
			System.out.println("16. Visualizza quanti prodotti ha un cliente nel carrello");
			System.out.println("17. Visualizza le recensioni che contengono una determinata parola all'interno");
			System.out.println(
					"18. Visualizza quali clienti hanno lasciato una valutazione superiore a ? per i prodotti di una determinata categoria");
			System.out.println("19. Seleziona i clienti che hanno effettuato ordini in tutte le categorie");
			System.out.println("20. Elenca i prodotti con valutazione superiore alla media delle valutazioni");

			System.out.println("0. Esci");

			int selection = scanner.nextInt();
			scanner.nextLine();

			if (selection == 0) {
				break;
			}

			switch (selection) {
			case 1: // Insert Prodotto
				Query1(scanner);
				break;
			case 2: // addAdmin
				Query2(scanner); // registerAdmin
				break;
			case 3:// addOrder
				Query3(scanner); // addOrder
				break;
			case 4:
				Query4(scanner); // AddReview
				break;
			case 5:
				Query5(scanner); // getSalesByProduct
				break;
			case 6:
				Query6(scanner);
				break;
			case 7:
				Query7(scanner);
				break;
			case 8:
				Query8(scanner);
				break;
			case 9:
				Query9(scanner); // getProductsByRating
				break;
			case 10:
				Query10(scanner); // getProductsByNationalityInCategory
				break;
			case 11:
				Query11(scanner); // getNonDeliveredOrdersInCategory
				break;
			case 12:
				Query12(scanner);
				break;
			case 13:
				Query13(scanner);
				break;
			case 14:
				Query14(scanner); // getProductsLessThan
				break;
			case 15:
				Query15(scanner); // getProductsByReviewsAndRating
				break;
			case 16:
				Query16(scanner); // getCartItemCount
				break;
			case 17:
				Query17(scanner); // getReviewsByKeyword
				break;
			case 18:
				Query18(scanner);// getCustomersWithHighRatingInCategory
				break;
			case 19:
				Query19(); // getCustomersWithOrdersInAllCategories
				break;
			case 20:
				Query20(); //
				break;
			case 21: // getProducts
				prodotti = p.read();
				for (Prodotto product : prodotti) {
					System.out.println(product);
				}
				break;
			case 22: // getProductById
				System.out.println("Inserisci ID del prodotto da ricercare: ");
				id = scanner.nextLine();
				prodotto = p.read(id);
				System.out.println(prodotto.getNome());
				break;
			case 23: // DELETE Prodotto

				System.out.print("Inserisci l' ID del prodotto: ");
				id = scanner.nextLine();
				System.out.print("Inserisci il nome del prodotto: ");
				name = scanner.nextLine();
				System.out.print("Inserisci la descrizione del prodotto: ");
				description = scanner.nextLine();
				System.out.print("Inserisci la quantità del prodotto: ");
				quantity = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Inserisci il prezzo del prodotto: ");
				price = scanner.nextDouble();
				scanner.nextLine();
				System.out.print("Inserisci l'ID della categoria del prodotto: ");
				categoryId = scanner.nextInt();
				scanner.nextLine();

				prodotto.setID(Integer.parseInt(id));
				prodotto.setNome(name);
				prodotto.setDescrizione(description);
				prodotto.setQuantità(quantity);
				prodotto.setPrezzo(price);
				prodotto.setCategoria_ID(categoryId);

				try {
					p.delete(prodotto);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Selezione non valida.");
			}
		}
	}

	static private void Query1(Scanner scanner) {// INSERT Prodotto
		System.out.print("Inserisci l' ID del prodotto: ");
		id = scanner.nextLine();
		System.out.print("Inserisci il nome del prodotto: ");
		name = scanner.nextLine();
		System.out.print("Inserisci la descrizione del prodotto: ");
		description = scanner.nextLine();
		System.out.print("Inserisci la quantità del prodotto: ");
		quantity = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Inserisci il prezzo del prodotto: ");
		price = scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Inserisci l'ID della categoria del prodotto: ");
		categoryId = scanner.nextInt();
		scanner.nextLine();

		prodotto.setID(Integer.parseInt(id));
		prodotto.setNome(name);
		prodotto.setDescrizione(description);
		prodotto.setQuantità(quantity);
		prodotto.setPrezzo(price);
		prodotto.setCategoria_ID(categoryId);

		p.insert(prodotto);
	}

	private static void Query2(Scanner scanner) { // INSERT Ordine

		System.out.print("Inserisci il nome dell'amministratore: ");
		String nome = scanner.nextLine();
		System.out.print("Inserisci il cognome dell'amministratore: ");
		String cognome = scanner.nextLine();
		System.out.print("Inserisci l'username dell'amministratore: ");
		String username = scanner.nextLine();
		System.out.print("Inserisci l'email dell'amministratore: ");
		String email = scanner.nextLine();
		System.out.print("Inserisci il telefono dell'amministratore: ");
		String telefono = scanner.nextLine();
		System.out.print("Inserisci l'indirizzo dell'amministratore: ");
		String indirizzo = scanner.nextLine();
		System.out.print("Inserisci la data di nascita dell'amministratore: ");
		String dataNascita = scanner.nextLine();
		System.out.print("L'utente è un amministratore? (true/false): ");
		boolean isAdmin = scanner.nextBoolean();
		scanner.nextLine();
		System.out.print("Inserisci la nazionalità dell'amministratore: ");
		String nazionalita = scanner.nextLine();

		Account newAdmin = new Account(username, email, nome, cognome, telefono, indirizzo, dataNascita, isAdmin,
				nazionalita);

		AccountDAO accountDAO = new AccountDAO();
		accountDAO.registerAdmin(newAdmin);
	}

	private static void Query3(Scanner scanner) { // INSERT Ordine

		System.out.print("Inserisci l'username del cliente: ");
		String username = scanner.nextLine();

		while (true) {
			System.out.print("Inserisci l'ID del prodotto da acquistare (0 per uscire): ");
			int idProdotto = scanner.nextInt();

			if (idProdotto == 0) {
				break;
			}

			System.out.print("Inserisci la quantità da comprare: ");
			int quantita = scanner.nextInt();

			Prodotto prodotto = new Prodotto();

			prodotto.setID(idProdotto);
			prodotto.setQuantità(quantita);

			prodotti.add(prodotto);
		}
		System.out.print("Inserisci l' ID della Spedizione: ");
		int IDSpedizione = scanner.nextInt();

		ordine.insertOrderForAccount(username, prodotti, IDSpedizione);
	}

	static private void Query4(Scanner scanner) { // INSERT Recensione
		System.out.print("Inserisci il titolo della recensione: ");
		String title = scanner.nextLine();
		System.out.print("Inserisci il testo della recensione: ");
		String text = scanner.nextLine();
		System.out.print("Inserisci la valutazione della recensione (1-5): ");
		int rating = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Inserisci la data della recensione (YYYY-MM-DD): ");
		String date = scanner.nextLine();
		System.out.print("Inserisci l'username del cliente: ");
		String username = scanner.nextLine();
		System.out.print("Inserisci l'ID del prodotto: ");
		int productId = scanner.nextInt();
		scanner.nextLine();

		r.setTitolo(title);
		r.setTesto(text);
		r.setValutazione(rating);
		r.setData(date);
		r.setAccount_username(username);
		r.setProdotto_ID(productId);

		recensione.insert(r);
	}

	static private void Query5(Scanner scanner) { // getProductSales
		System.out.print("Inserisci l'ID del prodotto: ");
		int productId = scanner.nextInt();
		scanner.nextLine();

		int vendite = ordineitem.getProductSales(productId);
		System.out.println("Il prodotto é stato venduto " + vendite + " volte\n");

	}

	private static void Query6(Scanner scanner) { // countOrdersByCustomer
		System.out.print("Inserisci l'username del cliente: ");
		String username = scanner.nextLine();

		int ordinieffettuati = ordine.countOrdersByCustomer(username);
		System.out.println(username + " ha effettuato " + ordinieffettuati + " ordini\n");
	}

	static private void Query7(Scanner scanner) { // getProductsByCategory
		System.out.print("Inserisci l'ID della categoria: ");
		int categoryId = scanner.nextInt();
		scanner.nextLine();

		prodotti = p.getProductsByCategory(categoryId);
		for (Prodotto prodotto : prodotti) {
			System.out.println(prodotto.getNome());
		}
	}

	static private void Query8(Scanner scanner) { // getCustomersBySpending
		System.out.print("Inserisci l'importo minimo speso: ");
		double minSpending = scanner.nextDouble();
		scanner.nextLine();

		accounts = account.getCustomersBySpending(minSpending);

		for (Account account : accounts) {
			if (account.getisAdmin() == false) // stampo solo i clienti
				System.out.println(account.getUsername());
		}
	}

	static private void Query9(Scanner scanner) { // getProductsByRating
		System.out.print("Inserisci la valutazione minima: ");
		int minRating = scanner.nextInt();
		scanner.nextLine();

		prodotti = p.getProductsByRating(minRating);

		for (Prodotto prodotto : prodotti) {
			System.out.println("Nome : " + prodotto.getNome() + "\n Descrizione: " + prodotto.getDescrizione());

		}
	}

	static private void Query10(Scanner scanner) { // getProductsByNationalityInCategory
		System.out.println("Inserisci ID della categoria da ricercare: ");
		int idcategoria = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Inserisci la nazione del cliente: ");
		String nazionalita = scanner.nextLine();

		prodotti = p.getProductsByNationalityInCategory(idcategoria, nazionalita);
		for (Prodotto prodotto : prodotti) {
			System.out.println(prodotto.getNome());

		}
	}

	static private void Query11(Scanner scanner) { // getNonDeliveredOrdersInCategory
		System.out.println("Inserisci la categoria da ricercare: ");
		String categoria = scanner.nextLine();

		ordini = ordine.getNonDeliveredOrdersInCategory(categoria);

		for (Ordine ordine : ordini) {
			System.out.println("Ordine effettuato da " + ordine.getAccount_username() + " in data " + ordine.getData());
		}
	}

	static private void Query12(Scanner scanner) { // getOrdersByCustomer
		System.out.print("Inserisci l'username del cliente: ");
		String username = scanner.nextLine();

		ordini = ordine.getOrdersByCustomer(username);

		for (Ordine ordine : ordini) {
			System.out.println("Ordine effettuato da " + ordine.getAccount_username() + " in data " + ordine.getData());
		}

	}

	static private void Query13(Scanner scanner) { // getReviewsByMultipleOrders
		System.out.print("Inserisci il numero minimo di recensioni da ricercare: ");
		int nRecensioni = scanner.nextInt();

		recensioni = recensione.getReviewByNumber(nRecensioni);

		for (Recensione recensione : recensioni) {
			System.out.println("Titolo: " + recensione.getTitolo() + "\nTesto:" + recensione.getTesto() + "\n");
		}
	}

	static private void Query14(Scanner scanner) { // getProductsLessThan
		System.out.print("Inserisci il costo da ricercare: ");
		double costo = scanner.nextDouble();

		prodotti = p.getProductsLessThan(costo);

		for (Prodotto prodotto : prodotti) {
			System.out.println(prodotto.getNome() + " al costo di " + prodotto.getPrezzo());
		}
	}

	static private void Query15(Scanner scanner) { // getProductsByReviewsAndRating
		System.out.print("Inserisci il numero minimo di recensioni da ricercare: ");
		int nRecensioni = scanner.nextInt();
		System.out.print("Inserisci il voto della recensione da ricercare: ");
		int voto = scanner.nextInt();

		prodotti = p.getProductsByReviewsAndRating(nRecensioni, voto);

		for (Prodotto prodotto : prodotti) {
			System.out.println(prodotto.getNome());
		}
	}

	static private void Query16(Scanner scanner) { // getCartItemCount
		System.out.print("Inserisci l'username del cliente: ");
		String username = scanner.nextLine();

		int items = carrello.getCartItemCount(username);
		System.out.println(
				username + " possiede " + items + ((items == 1 ? " prodotto" : " prodotti") + " nel carrello"));

	}

	static private void Query17(Scanner scanner) { // getReviewsByKeyword
		System.out.print("Inserisci la parola da cercare nelle recensioni: ");
		String keyword = scanner.nextLine();

		recensioni = recensione.getReviewsWithKeyword(keyword);

		for (Recensione recensione : recensioni) {
			System.out.println("Titolo: " + recensione.getTitolo() + "\nTesto:" + recensione.getTesto() + "\n");
		}
	}

	static private void Query18(Scanner scanner) { // getCustomersWithHighRatingInCategory

		System.out.print("Inserisci la categoria da ricercare: ");
		String categoryName = scanner.nextLine();
		System.out.print("Inserisci la valutazione minima: ");
		int minRating = scanner.nextInt();
		scanner.nextLine();

		accounts = account.getCustomersWithHighRatingInCategory(categoryName, minRating);
		for (Account account : accounts) {
			if (account.getisAdmin() == false)
				System.out.println(account.getUsername());
		}
	}

	static private void Query19() { // getCustomersWithOrdersInAllCategories
		accounts = account.getCustomersWithOrdersInAllCategories();

		for (Account account : accounts) {
			if (account.getisAdmin() == false)
				System.out.println(account.getUsername());
		}
	}

	static private void Query20() {
		prodotti = p.getProductsAVGHigherThan();

		for (Prodotto prodotto : prodotti) {
			System.out.println(prodotto.getNome());
		}
	}
}
