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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

@Singleton
public class PlotManager {

    private Main plugin;
    private BlockManager blockMgnt;
    private AreaManager areaMgnt;

    private HashMap<Long, Plot> plots = new HashMap<>();

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

        ConnectionManager.update("insert into Plots(id, server, home_world, home_x, home_y, home_z) " +
                "values (" + id + ", '" + HazelcastManager.getServerName() + "', '" + loc.getWorld().getName() + "', " +
                loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ");");

        Plot plot = new Plot(id, loc, plugin.getInjector());
        areaMgnt.createArea(selection, plot);
        plots.put(id, plot);
        plot.createBorder();
    }


    public void loadPlotsFromDB() {
        
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
                    "    home_x     integer      not null,\n" +
                    "    home_y     integer      not null,\n" +
                    "    home_z     integer      not null\n" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
