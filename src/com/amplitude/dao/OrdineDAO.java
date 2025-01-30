package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Ordine;
import com.amplitude.model.Prodotto;

public class OrdineDAO implements DAO<Ordine> {

	@Override
	public void insert(Ordine ordine) {

		String sql = "INSERT INTO Ordine (ID, data, totale, Account_username, Spedizione_ID) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, ordine.getID());
			statement.setString(2, ordine.getData());
			statement.setDouble(3, ordine.getTotale());
			statement.setString(4, ordine.getAccount_username());
			statement.setInt(5, ordine.getSpedizione_ID());

			int rowsAffected = statement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Ordine aggiunto con successo.");
			} else {
				System.out.println("Errore durante l'aggiunta dell'ordine.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Ordine ordine) throws SQLException {
		String updateSQL = "UPDATE Ordine SET data=?, totale=?, Account_username=?, Spedizione_ID=? WHERE ID=?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, ordine.getData());
			preparedStatement.setDouble(2, ordine.getTotale());
			preparedStatement.setString(3, ordine.getAccount_username());
			preparedStatement.setInt(4, ordine.getSpedizione_ID());
			preparedStatement.setInt(5, ordine.getID());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Ordine aggiornato con successo.");
			} else {
				System.out.println("Errore durante l'aggiornamento dell'ordine.");
			}
		}
	}

	@Override
	public void delete(Ordine ordine) throws SQLException {
		String deleteSQL = "DELETE FROM Ordine WHERE ID = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

			preparedStatement.setInt(1, ordine.getID());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Ordine eliminato con successo.");
			} else {
				System.out.println("Errore durante l'eliminazione dell'ordine.");
			}
		}
	}

	@Override
	public List<Ordine> read() {
		String sql = "SELECT * FROM Ordine";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {

			ArrayList<Ordine> ordini = new ArrayList<>();
			while (rs.next()) {
				Ordine ordine = new Ordine(rs.getInt("ID"), rs.getString("data"), rs.getDouble("totale"),
						rs.getString("Account_username"), rs.getInt("Spedizione_ID"));
				ordini.add(ordine);
			}
			return ordini;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Ordine read(String ID) {
		String sql = "SELECT * FROM Ordine WHERE ID = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, ID);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					Ordine ordine = new Ordine(rs.getInt("ID"), rs.getString("data"), rs.getDouble("totale"),
							rs.getString("Account_username"), rs.getInt("Spedizione_ID"));
					return ordine;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int countOrdersByCustomer(String username) {
		String sql = "SELECT COUNT(*) AS numero_ordini FROM Ordine WHERE Account_username = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, username);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("numero_ordini");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0; // Nessun ordine trovato o errore
	}

	public List<Ordine> getNonDeliveredOrdersInCategory(String category) {
		String sql = "SELECT o.*, c.nome AS nome_categoria " + "FROM Ordine o "
				+ "JOIN OrdineItem oi ON o.ID = oi.Ordine_ID " + "JOIN Spedizione s ON o.Spedizione_ID = s.ID "
				+ "JOIN Prodotto p ON oi.Prodotto_ID = p.ID " + "JOIN Categoria c ON p.Categoria_ID = c.ID "
				+ "WHERE s.stato != 'Consegnato' " + "AND c.nome = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, category);

			try (ResultSet rs = statement.executeQuery()) {
				List<Ordine> orders = new ArrayList<>();
				while (rs.next()) {
					int ID = rs.getInt("ID");
					String data = rs.getString("data");
					double totale = rs.getDouble("totale");
					String Account_username = rs.getString("Account_username");
					Integer Spedizione_ID = rs.getInt("Spedizione_ID");

					Ordine order = new Ordine(ID, data, totale, Account_username, Spedizione_ID);
					orders.add(order);
				}
				return orders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun ordine trovato o errore
	}

	public List<Ordine> getOrdersByCustomer(String customerUsername) {
		String sql = "SELECT * FROM Ordine " + "WHERE Account_username = ? " + "ORDER BY data DESC";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, customerUsername);

			try (ResultSet rs = statement.executeQuery()) {
				List<Ordine> orders = new ArrayList<>();
				while (rs.next()) {
					int ID = rs.getInt("ID");
					String data = rs.getString("data");
					double totale = rs.getDouble("totale");
					int spedizioneID = rs.getInt("Spedizione_ID");

					Ordine order = new Ordine(ID, data, totale, customerUsername, spedizioneID);
					orders.add(order);
				}
				return orders;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun ordine trovato o errore
	}

	public void insertOrderForAccount(String username, List<Prodotto> prodotti, int IDSpedizione) {
	    try (Connection connection = DBConnectionPool.getConnection()) {
	        try {
	            connection.setAutoCommit(false);

	            String insertOrdineSQL = "INSERT INTO Ordine (data, totale, Account_username, Spedizione_ID) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement insertOrdineStatement = connection.prepareStatement(insertOrdineSQL, Statement.RETURN_GENERATED_KEYS)) {
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                String dataCorrente = dateFormat.format(new Date(System.currentTimeMillis()));
	                double totalCost = calculateTotalCost(prodotti, connection);
	                insertOrdineStatement.setString(1, dataCorrente);
	                insertOrdineStatement.setDouble(2, totalCost);
	                insertOrdineStatement.setString(3, username);
	                insertOrdineStatement.setInt(4, IDSpedizione);

	                int rowsAffected = insertOrdineStatement.executeUpdate();

	                if (rowsAffected == 0) {
	                    throw new SQLException("Errore durante l'inserimento dell'ordine.");
	                }

	                // Ottieni l'ID generato per l'ordine
	                ResultSet generatedKeys = insertOrdineStatement.getGeneratedKeys();
	                int orderId = -1;
	                if (generatedKeys.next()) {
	                    orderId = generatedKeys.getInt(1);
	                } else {
	                    throw new SQLException("Errore durante l'ottenimento dell'ID generato per l'ordine.");
	                }

	                // Inserisci i prodotti nell'ordine nella tabella OrdineItem
	                String insertOrdineItemSQL = "INSERT INTO OrdineItem (prezzo_unitario, quantità, Ordine_ID, Prodotto_ID) VALUES (?, ?, ?, ?)";
	                try (PreparedStatement insertOrdineItemStatement = connection.prepareStatement(insertOrdineItemSQL)) {
	                    for (Prodotto prodotto : prodotti) {
	                        insertOrdineItemStatement.setDouble(1, getProductPriceFromDatabase(prodotto.getID()));
	                        insertOrdineItemStatement.setInt(2, prodotto.getQuantità());
	                        insertOrdineItemStatement.setInt(3, orderId);
	                        insertOrdineItemStatement.setInt(4, prodotto.getID());

	                        rowsAffected = insertOrdineItemStatement.executeUpdate();

	                        if (rowsAffected == 0) {
	                            throw new SQLException("Errore durante l'inserimento di un prodotto nell'ordine.");
	                        }
	                    }
	                }

	                // Aggiorna la quantità dei prodotti nella tabella Prodotto
	                for (Prodotto prodotto : prodotti) {
	                    String updateProdottoSQL = "UPDATE Prodotto SET quantità = (quantità - ?) WHERE ID = ?";
	                    try (PreparedStatement updateProdottoStatement = connection.prepareStatement(updateProdottoSQL)) {
	                        updateProdottoStatement.setInt(1, prodotto.getQuantità());
	                        updateProdottoStatement.setInt(2, prodotto.getID());
	                        rowsAffected = updateProdottoStatement.executeUpdate();

	                        if (rowsAffected == 0) {
	                            throw new SQLException("Errore durante l'aggiornamento della quantità del prodotto.");
	                        }
	                    }
	                }

	                connection.commit();
	            } catch (SQLException e) {
	                connection.rollback();
	                e.printStackTrace();
	            } finally {
	                connection.setAutoCommit(true);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public double getProductPriceFromDatabase(int productID) {
	    double prezzo = 0.0;

	    try (Connection connection = DBConnectionPool.getConnection()) {
	        String query = "SELECT prezzo FROM Prodotto WHERE ID = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, productID);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    prezzo = resultSet.getDouble("prezzo");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prezzo;
	}
	
	public double calculateTotalCost(List<Prodotto> prodotti, Connection connection) {
		double totalCost = 0.0;

		try {
			for (Prodotto prodotto : prodotti) {
				int productId = prodotto.getID();
				int quantity = prodotto.getQuantità();

				String query = "SELECT prezzo FROM Prodotto WHERE ID = ?";
				try (PreparedStatement statement = connection.prepareStatement(query)) {
					statement.setInt(1, productId);
					ResultSet resultSet = statement.executeQuery();

					if (resultSet.next()) {
						double price = resultSet.getDouble("prezzo");
						totalCost += price * quantity;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalCost;
	}

}
