package at.instaplots.world.region.area;

import org.bukkit.Location;

import java.util.HashSet;

public class AreaManager {

    private HashSet<Area> areas = new HashSet<>();

    public Area inArea(Location loc) {
        for(Area area : getAreas()) {
            if(area.isInside(loc)) return area;
        }
        return null;
    }


    public HashSet<Area> getAreas() {
        return this.areas;
    }
}
