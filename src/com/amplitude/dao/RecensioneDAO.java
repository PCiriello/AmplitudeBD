package com.amplitude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.amplitude.control.DBConnectionPool;
import com.amplitude.model.Recensione;

public class RecensioneDAO implements DAO<Recensione> {

    @Override
    public void insert(Recensione recensione) {

        String sql = "INSERT INTO Recensione (ID, titolo, testo, valutazione, data, Account_username, Prodotto_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, recensione.getID());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getTesto());
            statement.setInt(4, recensione.getValutazione());
            statement.setString(5, recensione.getData());
            statement.setString(6, recensione.getAccount_username());
            statement.setInt(7, recensione.getProdotto_ID());

            int rowsAffected = statement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Recensione aggiunta con successo.");
            } else {
                System.out.println("Errore durante l'aggiunta della recensione.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Recensione recensione) throws SQLException {
        String updateSQL = "UPDATE Recensione SET titolo=?, testo=?, valutazione=?, data=?, Account_username=?, Prodotto_ID=? WHERE ID=?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, recensione.getTitolo());
            preparedStatement.setString(2, recensione.getTesto());
            preparedStatement.setInt(3, recensione.getValutazione());
            preparedStatement.setString(4, recensione.getData());
            preparedStatement.setString(5, recensione.getAccount_username());
            preparedStatement.setInt(6, recensione.getProdotto_ID());
            preparedStatement.setInt(7, recensione.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Recensione aggiornata con successo.");
            } else {
                System.out.println("Errore durante l'aggiornamento della recensione.");
            }
        }
    }

    @Override
    public void delete(Recensione recensione) throws SQLException {
        String deleteSQL = "DELETE FROM Recensione WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, recensione.getID());

            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected > 0) {
                System.out.println("Recensione eliminata con successo.");
            } else {
                System.out.println("Errore durante l'eliminazione della recensione.");
            }
        }
    }

    @Override
    public List<Recensione> read() {
        String sql = "SELECT * FROM Recensione";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            ArrayList<Recensione> recensioni = new ArrayList<>();
            while (rs.next()) {
                Recensione recensione = new Recensione(rs.getInt("ID"), rs.getString("titolo"),
                        rs.getString("testo"), rs.getInt("valutazione"), rs.getString("data"),
                        rs.getString("Account_username"), rs.getInt("Prodotto_ID"));
                recensioni.add(recensione);
            }
            return recensioni;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Recensione read(String ID) {
        String sql = "SELECT * FROM Recensione WHERE ID = ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Recensione recensione = new Recensione(rs.getInt("ID"), rs.getString("titolo"),
                            rs.getString("testo"), rs.getInt("valutazione"), rs.getString("data"),
                            rs.getString("Account_username"), rs.getInt("Prodotto_ID"));
                    return recensione;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Recensione> getReviewByNumber(int minimoRecensioni) {
    	   String sql = "SELECT r.* " +
                   "FROM Recensione r " +
                   "JOIN Account a ON r.Account_username = a.username " +
                   "WHERE a.username IN " +
                   "(SELECT Account_username " +
                   " FROM Ordine " +
                   " GROUP BY Account_username " +
                   " HAVING COUNT(ID) >= ?)";

      try (Connection connection = DBConnectionPool.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {

          statement.setInt(1, minimoRecensioni);

          try (ResultSet rs = statement.executeQuery()) {
              List<Recensione> recensioni = new ArrayList<>();
              while (rs.next()) {
                  int ID = rs.getInt("ID");
                  String titolo = rs.getString("titolo");
                  String testo = rs.getString("testo");
                  int valutazione = rs.getInt("valutazione");
                  String data = rs.getString("data");
                  String accountUsername = rs.getString("Account_username");
                  int prodottoID = rs.getInt("Prodotto_ID");

                  Recensione recensione = new Recensione(ID, titolo, testo, valutazione, data, accountUsername, prodottoID);
                  recensioni.add(recensione);
              }
              return recensioni;
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return Collections.emptyList(); // Nessuna recensione trovata o errore
  }
    
    public List<Recensione> getReviewsWithKeyword(String keyword) {
        String sql = "SELECT * FROM Recensione WHERE testo LIKE ?";

        try (Connection connection = DBConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet rs = statement.executeQuery()) {
                List<Recensione> reviews = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String titolo = rs.getString("titolo");
                    String testo = rs.getString("testo");
                    int valutazione = rs.getInt("valutazione");
                    String data = rs.getString("data");
                    String username = rs.getString("Account_username");
                    int prodottoID = rs.getInt("Prodotto_ID");

                    Recensione review = new Recensione(id, titolo, testo, valutazione, data, username, prodottoID);
                    reviews.add(review);
                }
                return reviews;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // Nessuna recensione trovata o errore
    }

}
