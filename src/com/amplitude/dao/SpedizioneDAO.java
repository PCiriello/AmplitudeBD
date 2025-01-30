package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Spedizione;

public class SpedizioneDAO implements DAO<Spedizione> {

    @Override
    public void insert(Spedizione spedizione) {

        String sql = "INSERT INTO Spedizione (ID, data_consegna, data_spedizione, indirizzo, stato) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, spedizione.getID());
            statement.setDate(2, spedizione.getData_consegna());
            statement.setDate(3, spedizione.getData_spedizione());
            statement.setString(4, spedizione.getIndirizzo());
            statement.setString(5, spedizione.getStato());

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Spedizione aggiunta con successo.");
            } else {
                System.out.println("Errore durante l'aggiunta della spedizione.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Spedizione spedizione) throws SQLException {
        String updateSQL = "UPDATE Spedizione SET data_consegna=?, data_spedizione=?, indirizzo=?, stato=? WHERE ID=?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDate(1, spedizione.getData_consegna());
            preparedStatement.setDate(2, spedizione.getData_spedizione());
            preparedStatement.setString(3, spedizione.getIndirizzo());
            preparedStatement.setString(4, spedizione.getStato());
            preparedStatement.setInt(5, spedizione.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Spedizione aggiornata con successo.");
            } else {
                System.out.println("Errore durante l'aggiornamento della spedizione.");
            }
        }
    }

    @Override
    public void delete(Spedizione spedizione) throws SQLException {
        String deleteSQL = "DELETE FROM Spedizione WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, spedizione.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Spedizione eliminata con successo.");
            } else {
                System.out.println("Errore durante l'eliminazione della spedizione.");
            }
        }
    }

    @Override
    public List<Spedizione> read() {
        String sql = "SELECT * FROM Spedizione";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            ArrayList<Spedizione> spedizioni = new ArrayList<>();
            while (rs.next()) {
                Spedizione spedizione = new Spedizione(rs.getInt("ID"), rs.getDate("data_consegna"),
                        rs.getDate("data_spedizione"), rs.getString("indirizzo"), rs.getString("stato"));
                spedizioni.add(spedizione);
            }
            return spedizioni;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Spedizione read(String ID) {
        String sql = "SELECT * FROM Spedizione WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Spedizione spedizione = new Spedizione(rs.getInt("ID"), rs.getDate("data_consegna"),
                            rs.getDate("data_spedizione"), rs.getString("indirizzo"), rs.getString("stato"));
                    return spedizione;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
