package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.dao.ProdottoDAO;
import com.amplitude.model.Prodotto;

public class ProdottoDAO implements DAO<Prodotto> {

	@Override
	public void insert(Prodotto p) {

		String sql = "INSERT INTO Prodotto (ID, nome, descrizione, quantità, prezzo, Categoria_ID) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, p.getID());
			statement.setString(2, p.getNome());
			statement.setString(3, p.getDescrizione());
			statement.setInt(4, p.getQuantità());
			statement.setDouble(5, p.getPrezzo());
			statement.setInt(6, p.getCategoria_ID());

			int rowsAffected = statement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Prodotto aggiunto con successo.");
			} else {
				System.out.println("Errore durante l'aggiunta del prodotto.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Prodotto p) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String updateSQL = "UPDATE prodotto SET nome=?, descrizione=?, prezzo=?, quantita=?,categoria=?"
				+ " WHERE ID=?";
		try {
			connection = DBConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, p.getNome());
			preparedStatement.setString(2, p.getDescrizione());
			preparedStatement.setDouble(3, p.getPrezzo());
			preparedStatement.setInt(4, p.getQuantità());
			preparedStatement.setInt(5, p.getCategoria_ID());
			preparedStatement.setInt(6, p.getID());
			System.out.println("doUpdate: " + preparedStatement.toString());
			preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public void delete(Prodotto p) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM Prodotto WHERE ID = ?";

		try {
			connection = DBConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, p.getID());

			System.out.println("deleteProdotto: " + preparedStatement.toString());
			preparedStatement.executeUpdate();

			connection.commit();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DBConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public List<Prodotto> read() {

		String sql = "SELECT * FROM Prodotto";

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = DBConnectionPool.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			ArrayList<Prodotto> prodotti = new ArrayList<>();
			while (rs.next()) {
				Prodotto prodotto = new Prodotto();
				prodotto.setID(rs.getInt("id"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setQuantità(rs.getInt("quantità"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setCategoria_ID(rs.getInt("Categoria_ID"));
				prodotti.add(prodotto);
			}
			return prodotti;
		} catch (SQLException s) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				DBConnectionPool.releaseConnection(con);
			} catch (SQLException s) {
			}
		}
		return null;

	}

	@Override
	public Prodotto read(String id) {
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;
		Prodotto prodotto = new Prodotto();

		String sql = "SELECT * FROM Prodotto WHERE ID = ?";
		try {
			connection = DBConnectionPool.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setInt(1, Integer.parseInt(id));
			System.out.println("doRetriveByKey :" + statement.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				prodotto.setID(rs.getInt("id"));
				prodotto.setNome(rs.getString("nome"));
				prodotto.setDescrizione(rs.getString("descrizione"));
				prodotto.setQuantità(rs.getInt("quantità"));
				prodotto.setPrezzo(rs.getDouble("prezzo"));
				prodotto.setCategoria_ID(rs.getInt("Categoria_ID"));
			}

		} catch (SQLException s) {
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				DBConnectionPool.releaseConnection(connection);
			} catch (SQLException s) {
			}
		}
		return prodotto;
	}

	public List<Prodotto> getProductsByCategory(int categoryId) {
		String sql = "SELECT * FROM Prodotto WHERE Categoria_ID = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, categoryId);

			try (ResultSet rs = statement.executeQuery()) {
				List<Prodotto> products = new ArrayList<>();
				while (rs.next()) {
					Prodotto prodotto = new Prodotto(rs.getInt("ID"), rs.getString("nome"), rs.getString("descrizione"),
							rs.getInt("quantità"), rs.getDouble("prezzo"), rs.getInt("Categoria_ID"));
					products.add(prodotto);
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun prodotto trovato o errore
	}

	public List<Prodotto> getProductsByRating(double minRating) {
		String sql = "SELECT Prodotto.nome, Prodotto.descrizione " + "FROM Prodotto "
				+ "JOIN Recensione ON Prodotto.ID = Recensione.Prodotto_ID " + "GROUP BY Prodotto.ID "
				+ "HAVING AVG(Recensione.valutazione) >= ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDouble(1, minRating);

			try (ResultSet rs = statement.executeQuery()) {
				List<Prodotto> products = new ArrayList<>();
				while (rs.next()) {
					String nome = rs.getString("nome");
					String descrizione = rs.getString("descrizione");

					Prodotto prodotto = new Prodotto();
					prodotto.setNome(nome);
					prodotto.setDescrizione(descrizione);

					products.add(prodotto);
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun prodotto trovato o errore
	}

	public List<Prodotto> getProductsByReviewsAndRating(int minReviews, double minRating) {
		String sql = "SELECT Prodotto.* " + "FROM Prodotto "
				+ "JOIN Recensione ON Prodotto.ID = Recensione.Prodotto_ID " + "GROUP BY Prodotto.ID "
				+ "HAVING COUNT(Recensione.ID) >= ? AND AVG(Recensione.valutazione) >= ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, minReviews);
			statement.setDouble(2, minRating);

			try (ResultSet rs = statement.executeQuery()) {
				List<Prodotto> products = new ArrayList<>();
				while (rs.next()) {
					int id = rs.getInt("ID");
					String nome = rs.getString("nome");
					String descrizione = rs.getString("descrizione");
					int quantita = rs.getInt("quantità");
					double prezzo = rs.getDouble("prezzo");
					int categoriaID = rs.getInt("Categoria_ID");

					Prodotto product = new Prodotto(id, nome, descrizione, quantita, prezzo, categoriaID);
					products.add(product);
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun prodotto trovato o errore
	}

	public List<Prodotto> getProductsByNationalityInCategory(int categoryID, String nazionalita) {
		String sql = "SELECT DISTINCT Prodotto.* " + "FROM Prodotto "
				+ "JOIN OrdineItem ON Prodotto.ID = OrdineItem.Prodotto_ID "
				+ "JOIN Ordine ON OrdineItem.Ordine_ID = Ordine.ID "
				+ "JOIN Account ON Ordine.Account_username = Account.username " + "WHERE Prodotto.Categoria_ID = ? "
				+ "AND Account.nazionalita = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, categoryID);
			statement.setString(2, nazionalita);

			try (ResultSet rs = statement.executeQuery()) {
				List<Prodotto> products = new ArrayList<>();
				while (rs.next()) {
					int id = rs.getInt("ID");
					String nome = rs.getString("nome");
					String descrizione = rs.getString("descrizione");
					int quantita = rs.getInt("quantità");
					double prezzo = rs.getDouble("prezzo");
					int categoriaID = rs.getInt("Categoria_ID");

					Prodotto product = new Prodotto(id, nome, descrizione, quantita, prezzo, categoriaID);
					products.add(product);
				}
				return products;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun prodotto trovato o errore
	}

	public List<Prodotto> getProductsLessThan(double importoMassimo) {
		String sql = "SELECT * FROM Prodotto WHERE prezzo < ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setDouble(1, importoMassimo);

			try (ResultSet rs = statement.executeQuery()) {
				List<Prodotto> prodotti = new ArrayList<>();
				while (rs.next()) {
					int ID = rs.getInt("ID");
					String nome = rs.getString("nome");
					String descrizione = rs.getString("descrizione");
					int quantita = rs.getInt("quantità");
					double prezzo = rs.getDouble("prezzo");
					int categoriaID = rs.getInt("Categoria_ID");

					Prodotto prodotto = new Prodotto(ID, nome, descrizione, quantita, prezzo, categoriaID);
					prodotti.add(prodotto);
				}
				return prodotti;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Collections.emptyList(); // Nessun prodotto trovato o errore
	}

	public List<Prodotto> getProductsAVGHigherThan() {
		String sql = "SELECT P.* " + "FROM Prodotto P " + "WHERE P.ID IN ( " + "    SELECT R.Prodotto_ID "
				+ "    FROM Recensione R " + "    WHERE R.valutazione > ( " + "        SELECT AVG(valutazione) "
				+ "        FROM Recensione " + "    ) " + ")";

		List<Prodotto> prodotti = new ArrayList<>();

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("ID");
				String nome = rs.getString("nome");
				String descrizione = rs.getString("descrizione");
				int quantita = rs.getInt("quantità");
				double prezzo = rs.getDouble("prezzo");
				int categoriaID = rs.getInt("Categoria_ID");

				Prodotto prodotto = new Prodotto(id, nome, descrizione, quantita, prezzo, categoriaID);
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prodotti;
	}
}
