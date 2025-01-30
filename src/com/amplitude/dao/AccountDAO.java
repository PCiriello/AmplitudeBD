package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Account;

public class AccountDAO implements DAO<Account> {

	@Override
	public void insert(Account account) {
		String sql = "INSERT INTO Account (username, email, nome, cognome, telefono, indirizzo, isAdmin, dataNascita, nazionalita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, account.getUsername());
			statement.setString(2, account.getEmail());
			statement.setString(3, account.getNome());
			statement.setString(4, account.getCognome());
			statement.setString(5, account.getTelefono());
			statement.setString(6, account.getIndirizzo());
			statement.setBoolean(7, account.getisAdmin());
			statement.setString(8, account.getDataNascita());
			statement.setString(9, account.getNazionalita());

			int rowsAffected = statement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Account aggiunto con successo.");
			} else {
				System.out.println("Errore durante l'aggiunta dell'account.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Account account) throws SQLException {
		String updateSQL = "UPDATE Account SET email=?, nome=?, cognome=?, telefono=?, indirizzo=?, isAdmin=?, dataNascita=?, nazionalita=? WHERE username=?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, account.getEmail());
			preparedStatement.setString(2, account.getNome());
			preparedStatement.setString(3, account.getCognome());
			preparedStatement.setString(4, account.getTelefono());
			preparedStatement.setString(5, account.getIndirizzo());
			preparedStatement.setBoolean(6, account.getisAdmin());
			preparedStatement.setString(7, account.getDataNascita());
			preparedStatement.setString(8, account.getNazionalita());
			preparedStatement.setString(9, account.getUsername());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Account aggiornato con successo.");
			} else {
				System.out.println("Errore durante l'aggiornamento dell'account.");
			}
		}
	}

	@Override
	public List<Account> read() {
		String sql = "SELECT * FROM Account";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {

			ArrayList<Account> accounts = new ArrayList<>();
			while (rs.next()) {
				Account account = new Account(rs.getString("username"), rs.getString("email"), rs.getString("nome"),
						rs.getString("cognome"), rs.getString("telefono"), rs.getString("indirizzo"),
						rs.getString("dataNascita"), rs.getBoolean("isAdmin"), rs.getString("nazionalita"));
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account read(String username) {
		String sql = "SELECT * FROM Account WHERE username = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, username);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					Account account = new Account(rs.getString("username"), rs.getString("email"), rs.getString("nome"),
							rs.getString("cognome"), rs.getString("telefono"), rs.getString("indirizzo"),
							rs.getString("dataNascita"), rs.getBoolean("isAdmin"), rs.getString("nazionalita"));
					return account;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete(Account account) throws SQLException {
		String deleteSQL = "DELETE FROM Account WHERE username = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

			preparedStatement.setString(1, account.getUsername());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Account eliminato con successo.");
			} else {
				System.out.println("Errore durante l'eliminazione dell'account.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Account> getCustomersBySpending(double minSpending) {
		String sql = "SELECT Account.username, Account.nome, Account.cognome, Account.email, Account.telefono, "
				+ "Account.indirizzo, Account.isAdmin, Account.dataNascita, Account.nazionalita " + "FROM Account "
				+ "JOIN Ordine ON Account.username = Ordine.Account_username " + "GROUP BY Account.username "
				+ "HAVING SUM(Ordine.totale) >= ? " + "ORDER BY SUM(Ordine.totale) ASC";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDouble(1, minSpending);

			try (ResultSet rs = statement.executeQuery()) {
				List<Account> customers = new ArrayList<>();
				while (rs.next()) {
					String username = rs.getString("username");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String email = rs.getString("email");
					String telefono = rs.getString("telefono");
					String indirizzo = rs.getString("indirizzo");
					String dataNascita = rs.getString("dataNascita");
					String nazionalita = rs.getString("nazionalita");
					Boolean isAdmin = rs.getBoolean("isAdmin");

					Account account = new Account(username, email, nome, cognome, telefono, indirizzo, dataNascita,
							isAdmin, nazionalita);
					customers.add(account);
				}
				return customers;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun cliente trovato o errore
	}
	
	public List<Account> getCustomersWithHighRatingInCategory(String categoryName, double minRating) {
	    String sql = "SELECT DISTINCT Account.* " +
	                 "FROM Account " +
	                 "JOIN Ordine ON Account.username = Ordine.Account_username " +
	                 "JOIN OrdineItem ON Ordine.ID = OrdineItem.Ordine_ID " +
	                 "JOIN Prodotto ON OrdineItem.Prodotto_ID = Prodotto.ID " +
	                 "JOIN Categoria ON Prodotto.Categoria_ID = Categoria.ID " +
	                 "JOIN Recensione ON Prodotto.ID = Recensione.Prodotto_ID " +
	                 "WHERE Categoria.nome = ? " +
	                 "AND Recensione.valutazione > ?";

	    try (Connection connection = DBConnectionPool.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setString(1, categoryName);
	        statement.setDouble(2, minRating);

	        try (ResultSet rs = statement.executeQuery()) {
	            List<Account> customers = new ArrayList<>();
	            while (rs.next()) {
	                String username = rs.getString("username");
	                String email = rs.getString("email");
	                String nome = rs.getString("nome");
	                String cognome = rs.getString("cognome");
	                String telefono = rs.getString("telefono");
	                String indirizzo = rs.getString("indirizzo");
	                String dataNascita = rs.getString("dataNascita");
	                boolean isAdmin = rs.getBoolean("isAdmin");
	                String nazionalita = rs.getString("nazionalita");

	                Account customer = new Account(username, email, nome, cognome, telefono, indirizzo, dataNascita, isAdmin, nazionalita);
	                customers.add(customer);
	            }
	            return customers;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return Collections.emptyList(); // Nessun cliente trovato o errore
	}
	 public void registerAdmin(Account admin) {
	        String insertAdminSQL = "INSERT INTO Account (username, email, nome, cognome, telefono, indirizzo, dataNascita, isAdmin, nazionalita) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (Connection connection = DBConnectionPool.getConnection();
	             PreparedStatement insertAdminStatement = connection.prepareStatement(insertAdminSQL)) {

	            insertAdminStatement.setString(1, admin.getUsername());
	            insertAdminStatement.setString(2, admin.getEmail());
	            insertAdminStatement.setString(3, admin.getNome());
	            insertAdminStatement.setString(4, admin.getCognome());
	            insertAdminStatement.setString(5, admin.getTelefono());
	            insertAdminStatement.setString(6, admin.getIndirizzo());
	            insertAdminStatement.setString(7, admin.getDataNascita());
	            insertAdminStatement.setBoolean(8, admin.getisAdmin());
	            insertAdminStatement.setString(9, admin.getNazionalita());

	            int rowsAffected = insertAdminStatement.executeUpdate();
	            connection.commit();

	            if (rowsAffected == 0) {
	                throw new SQLException("Errore durante la registrazione dell'amministratore.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public List<Account> getCustomersWithOrdersInAllCategories() {
         String sql = "SELECT DISTINCT A.* " +
                 "FROM Account A " +
                 "WHERE EXISTS (" +
                 "    SELECT C.ID " +
                 "    FROM Categoria C " +
                 "    WHERE NOT EXISTS (" +
                 "        SELECT P.ID " +
                 "        FROM Prodotto P " +
                 "        WHERE NOT EXISTS (" +
                 "            SELECT OI.Ordine_ID " +
                 "            FROM OrdineItem OI " +
                 "            WHERE OI.Prodotto_ID = P.ID " +
                 "                AND OI.Ordine_ID IN (" +
                 "                    SELECT O.ID " +
                 "                    FROM Ordine O " +
                 "                    WHERE O.Account_username = A.username" +
                 "                )" +
                 "        )" +
                 "        AND P.Categoria_ID = C.ID" +
                 "    )" +
                 ")";

			try (Connection connection = DBConnectionPool.getConnection();
					PreparedStatement statement = connection.prepareStatement(sql);
					ResultSet rs = statement.executeQuery()) {

				List<Account> customers = new ArrayList<>();
				while (rs.next()) {
					String username = rs.getString("username");
					String email = rs.getString("email");
					String nome = rs.getString("nome");
					String cognome = rs.getString("cognome");
					String telefono = rs.getString("telefono");
					String indirizzo = rs.getString("indirizzo");
					String dataNascita = rs.getString("dataNascita");
					boolean isAdmin = rs.getBoolean("isAdmin");
					String nazionalita = rs.getString("nazionalita");

					Account customer = new Account(username, email, nome, cognome, telefono, indirizzo, dataNascita,
							isAdmin, nazionalita);
					customers.add(customer);
				}
				return customers;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return Collections.emptyList(); // Nessun cliente trovato o errore
		}

}