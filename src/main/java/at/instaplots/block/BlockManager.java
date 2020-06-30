package at.instaplots.block;

import at.instabase.sql.ConnectionManager;
import at.instaplots.Main;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class BlockManager {

    private Main plugin;

    @Inject
    public BlockManager(Main plugin) {
        this.plugin = plugin;
    }


    public long getBalance(UUID uuid) {
        if(Bukkit.isPrimaryThread()) throw new RuntimeException("Only Async calls allowed.");
        try {
            ResultSet rs = ConnectionManager.query("select blocks" +
                    "    from PlayerBlocks" +
                    "   where uuid = '" + uuid.toString() + "';");
            if(rs.next()) {
                return rs.getLong(1);
            } else {
                ConnectionManager.update("insert into PlayerBlocks(uuid, blocks) " +
                        "values ('" + uuid.toString() + "', 0);");
                return 0L;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void makeTransaction(UUID uuid, long change) {
        getBalance(uuid);
        ConnectionManager.update("update PlayerBlocks set blocks = blocks + " + change + " " +
                "where uuid = '" + uuid.toString() + "';");
    }


    public void initDatabase() {
        try {
            ResultSet rs = ConnectionManager.query("select TABLE_NAME " +
                    "from INFORMATION_SCHEMA.TABLES " +
                    "where TABLE_NAME='PlayerBlocks';");
            if (rs.next()) {
                return;
            }
            ConnectionManager.update("create table PlayerBlocks (\n" +
                    "    uuid   varchar(36)   not null primary key\n" +
                    ",   blocks bigint not null\n" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
