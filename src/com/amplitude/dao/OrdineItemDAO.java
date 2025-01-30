package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.OrdineItem;

public class OrdineItemDAO implements DAO<OrdineItem> {

	@Override
	public void insert(OrdineItem ordineItem) {

		String sql = "INSERT INTO OrdineItem (ID, prezzo_unitario, quantità, Ordine_ID, Prodotto_ID) VALUES (?, ?, ?, ?, ?)";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, ordineItem.getID());
			statement.setDouble(2, ordineItem.getPrezzo_unitario());
			statement.setInt(3, ordineItem.getQuantità());
			statement.setInt(4, ordineItem.getOrdine_ID());
			statement.setInt(5, ordineItem.getProdotto_ID());

			int rowsAffected = statement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Elemento dell'ordine aggiunto con successo.");
			} else {
				System.out.println("Errore durante l'aggiunta dell'elemento dell'ordine.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(OrdineItem ordineItem) throws SQLException {
		String updateSQL = "UPDATE OrdineItem SET prezzo_unitario=?, quantità=?, Ordine_ID=?, Prodotto_ID=? WHERE ID=?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

			preparedStatement.setDouble(1, ordineItem.getPrezzo_unitario());
			preparedStatement.setInt(2, ordineItem.getQuantità());
			preparedStatement.setInt(3, ordineItem.getOrdine_ID());
			preparedStatement.setInt(4, ordineItem.getProdotto_ID());
			preparedStatement.setInt(5, ordineItem.getID());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Elemento dell'ordine aggiornato con successo.");
			} else {
				System.out.println("Errore durante l'aggiornamento dell'elemento dell'ordine.");
			}
		}
	}

	@Override
	public void delete(OrdineItem ordineItem) throws SQLException {
		String deleteSQL = "DELETE FROM OrdineItem WHERE ID = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

			preparedStatement.setInt(1, ordineItem.getID());

			int rowsAffected = preparedStatement.executeUpdate();
			connection.commit();
			if (rowsAffected > 0) {
				System.out.println("Elemento dell'ordine eliminato con successo.");
			} else {
				System.out.println("Errore durante l'eliminazione dell'elemento dell'ordine.");
			}
		}
	}

	@Override
	public List<OrdineItem> read() {
		String sql = "SELECT * FROM OrdineItem";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery()) {

			ArrayList<OrdineItem> ordineItems = new ArrayList<>();
			while (rs.next()) {
				OrdineItem ordineItem = new OrdineItem(rs.getInt("ID"), rs.getDouble("prezzo_unitario"),
						rs.getInt("quantità"), rs.getInt("Ordine_ID"), rs.getInt("Prodotto_ID"));
				ordineItems.add(ordineItem);
			}
			return ordineItems;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OrdineItem read(String ID) {
		String sql = "SELECT * FROM OrdineItem WHERE ID = ?";

		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, ID);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					OrdineItem ordineItem = new OrdineItem(rs.getInt("ID"), rs.getDouble("prezzo_unitario"),
							rs.getInt("quantità"), rs.getInt("Ordine_ID"), rs.getInt("Prodotto_ID"));
					return ordineItem;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getProductSales(int productId) {

		String sql = "SELECT SUM(OrdineItem.quantità) AS vendite FROM OrdineItem WHERE OrdineItem.Prodotto_ID = ?";
		int sales = 0;
		try (Connection connection = DBConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, productId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					sales = resultSet.getInt("vendite");
				} else {
					System.out.println("Nessuna vendita trovata per il prodotto " + productId);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sales;

	}
	
	
}
