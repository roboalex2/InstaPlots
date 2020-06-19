package at.instaplots.world.region;

import at.instaplots.world.region.area.Area;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegionManager {


    private final HashMap<Long, Region> regions = new HashMap<>();


    public Region getRegion(long key) {
        Region region = regions.get(key);
        if(region == null) {
            region = new Region(key);
            regions.put(key, region);
        }
        return region;
    }


    public Region getRegion(Location loc) {
        return getRegion(getRegionKey(loc));
    }


    public Area isInArea(Location loc) {
        Region region = getRegion(loc);
        Area area = region.getAreaManager().inArea(loc);
        if(area != null) return area;

        for(Region reg : getSurroundingRegions(region)) {
            area = reg.getAreaManager().inArea(loc);
            if(area != null) return area;
        }
        return null;
    }


    private ArrayList<Region> getSurroundingRegions(Region region) {
        int x = region.getX();
        int z = region.getZ();
        ArrayList<Region> regs = new ArrayList<>();
        for(int xI = -1; xI <= 1; xI++) {
            for(int zI = -1; zI <= 1; zI++) {
                regs.add(getRegion(getRegionKey(x + xI, z + zI)));
            }
        }
        return regs;
    }


    private long getRegionKey(Location loc) {
        int xReg = loc.getBlockX() >> 11;
        int zReg = loc.getBlockZ() >> 11;
        return getRegionKey(xReg, zReg);
    }

    private long getRegionKey(int xReg, int zReg) {
        return (((long)xReg) << 32) | (zReg & 0xffffffffL);
    }
}
