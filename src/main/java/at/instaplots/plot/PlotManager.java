package at.instaplots.plot;

import at.instaplots.Main;
import at.instaplots.world.region.RegionManager;
import at.instaplots.world.region.area.Area;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

@Singleton
public class PlotManager {

    private Main plugin;

    private HashMap<Long, Plot> plots = new HashMap<>();

    @Inject
    public PlotManager(Main plugin) {
        this.plugin = plugin;
    }


    public void createPlot(UUID uuid, Location loc, int size) {
        // TODO createPlot
    }






    public Plot getPlot(long id) {
        return plots.get(id);
    }

    public Plot getPlot(Area area) {
        return getPlot(area.getPlotId());
    }

}
