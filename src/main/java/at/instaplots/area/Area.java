package at.instaplots.area;

import at.instaplots.plot.Plot;
import at.instaplots.selection.Selection;
import com.google.inject.Injector;
import org.bukkit.Location;

import java.util.UUID;

public class Area extends Selection {

    private long id;
    private long plotId;


    /*public Area(Location pos1, Location pos2, Plot plot) {
        super(pos1, pos2);
        id = UUID.randomUUID().getMostSignificantBits();
        plotId = plot.getId();
    }*/

    Area(Selection selection, Plot plot) {
        //this(selection.getBottomLocation(), selection.getTopLocation(), plot);
        super(selection.getBottomLocation(), selection.getTopLocation());
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
