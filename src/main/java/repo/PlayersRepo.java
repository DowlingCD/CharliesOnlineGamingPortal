package repo;

import util.DB;
import java.sql.*;

public class PlayersRepo {

    public boolean gamerTagExists(String tag) throws SQLException {
        String sql = "SELECT 1 FROM players WHERE gamer_tag = ?";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tag);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void createPlayer(String tag, String password) throws SQLException {
        String sql = "INSERT INTO players (gamer_tag, password, credits) VALUES (?, ?, 500)";
        try (Connection c = DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tag);
            ps.setString(2, password);
            ps.executeUpdate();
        }
    }

    public Integer findIdByCredentials(String tag, String password) throws SQLException {
        String sql = "SELECT id FROM players WHERE gamer_tag = ? AND password = ?";
        try (Connection c = util.DB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tag);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : null;
            }
        }
    }
    public int getCredits(int playerId) throws java.sql.SQLException {
        String sql = "SELECT credits FROM players WHERE id = ?";
        try (java.sql.Connection c = util.DB.getConnection();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, playerId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
    public boolean changeCredits(int playerId, int delta) throws java.sql.SQLException {
        // only updates if the new balance would be greater than 0
        final String sql = "UPDATE players SET credits = credits + ? " +
                "WHERE id = ? AND credits + ? >= 0";
        try (java.sql.Connection c = util.DB.getConnection();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, delta);      // add or subtract
            ps.setInt(2, playerId);   // which player
            ps.setInt(3, delta);      // guard: new balance must be >= 0
            int updated = ps.executeUpdate();
            return updated == 1;      // true if applied, false if it would've gone negative
        }
    }

}
