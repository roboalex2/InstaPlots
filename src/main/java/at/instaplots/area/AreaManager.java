package at.instaplots.area;

import at.instabase.sql.ConnectionManager;
import at.instaplots.Main;
import at.instaplots.plot.Plot;
import at.instaplots.selection.Selection;
import at.instaplots.world.WorldManager;
import at.instaplots.world.region.Region;
import at.instaplots.world.region.RegionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class AreaManager {

    private Main plugin;
    private WorldManager worldMgnt;

    @Inject
    public AreaManager(Main plugin, WorldManager worldMgnt) {
        this.plugin = plugin;
        this.worldMgnt = worldMgnt;
    }


    public boolean isSelectionCreatable(Selection selection) {
        RegionManager regMgnt = worldMgnt.getRegionManager(selection.getBottomLocation().getWorld());
        Region region = regMgnt.getRegion(selection.getCenter());
        for(Area area : region.getAreaSet()) {
            if(area.isOverlapping(selection)) return false;
        }
        for(Region reg : regMgnt.getSurroundingRegions(region)) {
            for(Area area : reg.getAreaSet()) {
                if(area.isOverlapping(selection)) return false;
            }
        }
        return true;
    }


    public Area isInArea(Location loc) {
        RegionManager regMgnt = worldMgnt.getRegionManager(loc.getWorld());
        Region region = regMgnt.getRegion(loc);
        Area area = region.getAreaSet().inArea(loc);
        if(area != null) return area;

        for(Region reg : regMgnt.getSurroundingRegions(region)) {
            area = reg.getAreaSet().inArea(loc);
            if(area != null) return area;
        }
        return null;
    }


    public void createArea(Selection selection, Plot plot) {
        Area area = new Area(selection, plot);

        ConnectionManager.update(String.format(
                "insert into Areas (id, plot_id, world, bottom_x, bottom_z, top_x, top_z) " +
                        "values (%d, %d, '%s', %d, %d, %d, %d);",
                area.getId(), area.getPlotId(), area.getTopLocation().getWorld().getName(),
                area.getBottomLocation().getBlockX(), area.getBottomLocation().getBlockZ(),
                area.getTopLocation().getBlockX(), area.getTopLocation().getBlockZ()));

        plot.getAreaSet().add(area);
        worldMgnt.getRegionManager(selection.getBottomLocation().getWorld())
                .getRegion(selection.getCenter()).getAreaSet().add(area);
    }


    public void loadAreas(Plot plot) throws SQLException {


        ResultSet rs = ConnectionManager.query("select * from Areas where plot_id = " + plot.getId() + ";");
        boolean areaFound = false;

        while(rs.next()) {
            areaFound = true;
            long id = rs.getLong("id");
            long plotId = rs.getLong("plot_id");
            World world = Bukkit.getWorld(rs.getString("world"));
            Location top = new Location(world,
                    rs.getInt("top_x"), 254, rs.getInt("top_z"));
            Location bottom = new Location(world,
                    rs.getInt("bottom_x"), 0, rs.getInt("bottom_z"));
            Area area = new Area(id, plotId, top, bottom);
            plot.getAreaSet().add(area);
            worldMgnt.getRegionManager(world)
                    .getRegion(area.getCenter()).getAreaSet().add(area);
        }

        if(!areaFound) throw new RuntimeException("Keine Area zu dem Plot gefunden! PlotId: " + plot.getId());
    }


    public void initDatabase() {
        try {
            ResultSet rs = ConnectionManager.query("select TABLE_NAME " +
                    "from INFORMATION_SCHEMA.TABLES " +
                    "where TABLE_NAME='Areas';");
            if (rs.next()) {
                return;
            }
            ConnectionManager.update("create table Areas (\n" +
                    "    id       bigint       not null primary key,\n" +
                    "    plot_id  bigint       not null,\n" +
                    "    world    varchar(128) not null,\n" +
                    "    bottom_x integer      not null,\n" +
                    "    bottom_z integer      not null,\n" +
                    "    top_x    integer      not null,\n" +
                    "    top_z    integer      not null,\n" +
                    "    foreign key(plot_id) references Plots(id)\n" +
                    ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
