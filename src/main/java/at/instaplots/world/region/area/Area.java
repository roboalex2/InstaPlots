package at.instaplots.world.region.area;

import at.instaplots.plot.Plot;
import at.instaplots.plot.PlotManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Location;

import java.util.UUID;

public class Area {

    private long id;
    private long plotId;

    private Location topLocation;
    private Location bottomLocation;

    @Inject
    private PlotManager plotMgnt;


    Area(Location pos1, Location pos2, Plot plot) {
        topLocation = new Location(pos1.getWorld(),
                Math.max(pos1.getBlockX(), pos2.getBlockX()), 0, Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
        bottomLocation = new Location(pos1.getWorld(),
                Math.min(pos1.getBlockX(), pos2.getBlockX()), 0, Math.min(pos1.getBlockZ(), pos2.getBlockZ()));

        id = UUID.randomUUID().getMostSignificantBits();
        plotId = plot.getId();
    }

    // Only used by DAO when loading from Database
    public Area(long id, long plotId, Location topLocation, Location bottomLocation) {
        this.id = id;
        this.plotId = plotId;
        this.topLocation = topLocation;
        this.bottomLocation = bottomLocation;
    }


    public boolean isOverlapping(Area other) {
        if (topLocation.getBlockZ() < other.bottomLocation.getBlockZ()
                || this.bottomLocation.getBlockZ() > other.topLocation.getBlockZ()) {
            return false;
        }
        if (this.topLocation.getBlockX() < other.bottomLocation.getBlockX()
                || this.bottomLocation.getBlockX() > other.topLocation.getBlockX()) {
            return false;
        }
        return true;
    }


    public boolean isTouching(Area other) {
        if(isOverlapping(other)) return false;
        if (topLocation.getBlockZ() < (other.bottomLocation.getBlockZ() - 1)
                || this.bottomLocation.getBlockZ() > (other.topLocation.getBlockZ() + 1)) {
            return false;
        }
        if (this.topLocation.getBlockX() < (other.bottomLocation.getBlockX() - 1)
                || this.bottomLocation.getBlockX() > (other.topLocation.getBlockX() + 1)) {
            return false;
        }
        return true;
    }


    public boolean isInside(Location loc) {
        int x = loc.getBlockX();
        int z = loc.getBlockZ();

        return (x <= topLocation.getBlockX() && x >= bottomLocation.getBlockX()
                && z <= topLocation.getBlockZ() && z >= bottomLocation.getBlockZ());
    }


    public void injectAll(Injector injector) {
        injector.injectMembers(this);
    }

    public long getId() {
        return id;
    }

    public long getPlotId() {
        return plotId;
    }
}
