package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Categoria;

public class CategoriaDAO implements DAO<Categoria> {

    @Override
    public void insert(Categoria categoria) {

        String sql = "INSERT INTO Categoria (ID, nome, descrizione) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoria.getID());
            statement.setString(2, categoria.getNome());
            statement.setString(3, categoria.getDescrizione());

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Categoria aggiunta con successo.");
            } else {
                System.out.println("Errore durante l'aggiunta della categoria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Categoria categoria) throws SQLException {
        String updateSQL = "UPDATE Categoria SET nome=?, descrizione=? WHERE ID=?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, categoria.getNome());
            preparedStatement.setString(2, categoria.getDescrizione());
            preparedStatement.setInt(3, categoria.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Categoria aggiornata con successo.");
            } else {
                System.out.println("Errore durante l'aggiornamento della categoria.");
            }
        }
    }

    @Override
    public void delete(Categoria categoria) throws SQLException {
        String deleteSQL = "DELETE FROM Categoria WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, categoria.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Categoria eliminata con successo.");
            } else {
                System.out.println("Errore durante l'eliminazione della categoria.");
            }
        }
    }

    @Override
    public List<Categoria> read() {
        String sql = "SELECT * FROM Categoria";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            ArrayList<Categoria> categorie = new ArrayList<>();
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("ID"), rs.getString("nome"), rs.getString("descrizione"));
                categorie.add(categoria);
            }
            return categorie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Categoria read(String ID) {
        String sql = "SELECT * FROM Categoria WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt("ID"), rs.getString("nome"), rs.getString("descrizione"));
                    return categoria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
