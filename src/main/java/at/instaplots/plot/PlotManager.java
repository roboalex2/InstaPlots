package at.instaplots.plot;

import at.instabase.hazel.HazelcastManager;
import at.instabase.sql.ConnectionManager;
import at.instaplots.Main;
import at.instaplots.area.AreaManager;
import at.instaplots.block.BlockManager;
import at.instaplots.world.WorldManager;
import at.instaplots.area.Area;
import at.instaplots.selection.Selection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.security.Policy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@Singleton
public class PlotManager {

    private Main plugin;
    private BlockManager blockMgnt;
    private AreaManager areaMgnt;

    private ConcurrentHashMap<Long, Plot> plots = new ConcurrentHashMap<>();

    @Inject
    public PlotManager(Main plugin, AreaManager areaMgnt, BlockManager blockMgnt) {
        this.plugin = plugin;
        this.areaMgnt = areaMgnt;
        this.blockMgnt = blockMgnt;
    }



    public void createPlot(UUID uuid, Location loc, int size) throws SQLException {
        createPlot(uuid, loc, size, true);
    }


    public void createPlot(UUID uuid, Location loc, int size, boolean payForIt) throws SQLException {
        if(Bukkit.isPrimaryThread()) throw new RuntimeException("Only Async calls allowed.");
        size = size - 1;
        int halfSize = size / 2;
        int rest = size % 2;
        Selection selection = new Selection(
                new Location(loc.getWorld(), loc.getBlockX() - halfSize, 0, loc.getBlockZ() - halfSize),
                new Location(loc.getWorld(), loc.getBlockX() + halfSize + rest, 0, loc.getBlockZ() + halfSize + rest));
        if(!areaMgnt.isSelectionCreatable(selection)) {
            throw new RuntimeException("Ein anderes Grundstück verhindert das erstellen des Grundstücks.");
        }
        if(payForIt) {
            long blocks = blockMgnt.getBalance(uuid);
            if(blocks < selection.getSize()) {
                throw new RuntimeException("§7Du hast nur §9" + blocks + " §7von §9"
                        + selection.getSize() + " §7benötigten Blöcken für dein Grundstück.");
            }
        }

        ResultSet rs = ConnectionManager.query("select max(id) as num from Plots;");
        long id = 0;
        if(rs.next()) {
            id = rs.getLong(1) + 1L;
        }

        ConnectionManager.update("insert into Plots(id, server, home_world, home_x, home_y, home_z, home_yaw, home_pitch) " +
                "values (" + id + ", '" + HazelcastManager.getServerName() + "', '" + loc.getWorld().getName() + "', " +
                loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch() + ");");

        Plot plot = new Plot(id, plugin.getInjector());
        areaMgnt.createArea(selection, plot);
        plots.put(id, plot);
        plot.createBorder();
    }


    public void loadPlotsFromDB() {
        ResultSet rs = ConnectionManager.query("select id from Plots where server = '"
                + HazelcastManager.getServerName() + "';");
        try {
            while (rs.next()) {
                try {
                    long id = rs.getLong("id");
                   /* Location home = new Location(Bukkit.getWorld(rs.getString("home_world")),
                            rs.getDouble("home_x"), rs.getDouble("home_y"), rs.getDouble("home_z"),
                            (float) rs.getDouble("home_yaw"), (float) rs.getDouble("home_pitch"));*/

                    Plot plot = new Plot(id, plugin.getInjector());
                    areaMgnt.loadAreas(plot);
                    plots.put(id, plot);

                } catch (SQLException | RuntimeException e) {
                    plugin.getLogger().log(Level.WARNING, "Error on Plot load: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on loading all Plots. Disable Plugin.");
        }
    }


    public Plot getPlot(long id) {
        return plots.get(id);
    }


    public Plot getPlot(Area area) {
        return getPlot(area.getPlotId());
    }


    public void initDatabase() {
        try {
            ResultSet rs = ConnectionManager.query("select TABLE_NAME " +
                    "from INFORMATION_SCHEMA.TABLES " +
                    "where TABLE_NAME='Plots';");
            if (rs.next()) {
                return;
            }
            ConnectionManager.update("create table Plots (\n" +
                    "    id         bigint       not null primary key,\n" +
                    "    server     varchar(128) not null,\n" +
                    "    home_world varchar(128) not null,\n" +
                    "    home_x     double       not null,\n" +
                    "    home_y     double       not null,\n" +
                    "    home_z     double       not null,\n" +
                    "    home_yaw   double       not null,\n" +
                    "    home_pitch double       not null\n" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
