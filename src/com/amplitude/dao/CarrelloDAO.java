package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Carrello;

public class CarrelloDAO implements DAO<Carrello> {

    @Override
    public void insert(Carrello carrello) {
        String sql = "INSERT INTO Carrello (ID, quantita, Account_username, Prodotto_ID) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, carrello.getID());
            statement.setInt(2, carrello.getQuantità());
            statement.setString(3, carrello.getAccount_username());
            statement.setInt(4, carrello.getProdotto_ID());

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Carrello aggiunto con successo.");
            } else {
                System.out.println("Errore durante l'aggiunta del carrello.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Carrello carrello) throws SQLException {
        String updateSQL = "UPDATE Carrello SET quantita=?, Account_username=?, Prodotto_ID=? WHERE ID=?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, carrello.getQuantità());
            preparedStatement.setString(2, carrello.getAccount_username());
            preparedStatement.setInt(3, carrello.getProdotto_ID());
            preparedStatement.setInt(4, carrello.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Carrello aggiornato con successo.");
            } else {
                System.out.println("Errore durante l'aggiornamento del carrello.");
            }
        }
    }

    @Override
    public void delete(Carrello carrello) throws SQLException {
        String deleteSQL = "DELETE FROM Carrello WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, carrello.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Carrello eliminato con successo.");
            } else {
                System.out.println("Errore durante l'eliminazione del carrello.");
            }
        }
    }

    @Override
    public List<Carrello> read() {
        String sql = "SELECT * FROM Carrello";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            ArrayList<Carrello> carrelli = new ArrayList<>();
            while (rs.next()) {
                Carrello carrello = new Carrello(rs.getInt("ID"), rs.getInt("quantita"),
                        rs.getString("Account_username"), rs.getInt("Prodotto_ID"));
                carrelli.add(carrello);
            }
            return carrelli;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Carrello read(String ID) {
        String sql = "SELECT * FROM Carrello WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Carrello carrello = new Carrello(rs.getInt("ID"), rs.getInt("quantita"),
                            rs.getString("Account_username"), rs.getInt("Prodotto_ID"));
                    return carrello;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getCartItemCount(String username) {
        String sql = "SELECT SUM(quantita) AS numero_prodotti_nel_carrello FROM Carrello WHERE Account_username = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numero_prodotti_nel_carrello");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Nessun prodotto nel carrello o errore
    }
}
