package ch.chunkclaim.manager;

import ch.chunkclaim.ChunkClaim;
import ch.chunkclaim.provider.MySQL;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ChunkManager {
    private MySQL mySQL;
    private static ChunkManager instance;
    public void createTables() {
        this.mySQL = ChunkClaim.getInstance().getMySQL();
        mySQL.update("CREATE TABLE IF NOT EXISTS chunks (uuid VARCHAR(36), chunk VARCHAR(35), name VARCHAR(16), trusted VARCHAR(36))");
    }
    public static ChunkManager getInstance(){
        return instance;
    }
    public void iniplayerandchunk(String uuid, String chunk, String name){
        mySQL.update("INSERT INTO chunks (uuid, chunk, name) VALUES ('" + uuid + "', '" + chunk + "', '" + name + "')");
    }
    public void unclaimChunk(String chunk){
        mySQL.update("DELETE FROM chunks WHERE chunk=?", chunk);
    }
    public void removetrust(String chunk){
        mySQL.update("UPDATE chunks SET trusted=? WHERE chunk=?", "keiner", chunk);
    }
    public String getChunkOwnerUUID(String chunk) {
        String uuid = null;
        String query = "SELECT name FROM chunks WHERE chunk = ?";

        try {
            ResultSet resultSet = mySQL.query(query, chunk);
            if (resultSet.next()) {
                uuid = resultSet.getString("uuid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uuid;
    }
    public boolean doesPlayerExist(UUID uuid) {
        String qry = "SELECT count(*) AS count FROM chunks WHERE uuid=?";
        try (ResultSet rs = mySQL.query(qry, uuid.toString())) {
            if (rs.next()) {
                return rs.getInt("count") != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public String getTrusted(String chunk) {
        String trusted = null;
        String query = "SELECT trusted FROM chunks WHERE chunk = ?";
        try {
            ResultSet resultSet = mySQL.query(query, chunk);
            if (resultSet.next()) {
                trusted = resultSet.getString("trusted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trusted;
    }
    public String getChunkOwner(String chunk) {
        String name = null;
        String query = "SELECT name FROM chunks WHERE chunk = ?";
        try {
            ResultSet resultSet = mySQL.query(query, chunk);
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }
    public void setTrusted(String uuid, String trusted){
        mySQL.update("UPDATE chunks SET trusted=? WHERE uuid=?", trusted, uuid.toString());
    }
    public boolean isClaimed(String chunk){
        String qry = "SELECT count(*) AS count FROM chunks WHERE chunk=?";
        try (ResultSet rs = mySQL.query(qry, chunk)) {
            if (rs.next()) {
                return rs.getInt("count") != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
