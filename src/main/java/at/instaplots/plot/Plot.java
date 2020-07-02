package at.instaplots.plot;

import at.instaplots.area.Area;
import at.instaplots.border.BorderManager;
import at.instaplots.group.GroupManager;
import at.instaplots.permission.GeneralPermissionManager;
import at.instaplots.area.AreaSet;
import com.google.inject.Injector;
import org.bukkit.Location;

import java.util.LinkedList;

public class Plot {

    private long id;

    private AreaSet areaSet;
    private GroupManager groupMgnt;
    private GeneralPermissionManager genPermMgnt;
    private BorderManager borderMgnt;

    Plot(long id, Injector injector) {
        this.id = id;
        this.areaSet = injector.getInstance(AreaSet.class);
        this.groupMgnt = injector.getInstance(GroupManager.class);
        this.genPermMgnt = injector.getInstance(GeneralPermissionManager.class);
        this.borderMgnt = injector.getInstance(BorderManager.class);
    }


    public void createBorder() {
        borderMgnt.addBorderBlocks(getBorderBlocks());
    }


    public void removeBorder() {
        borderMgnt.removeBorderBlocks(getBorderBlocks());
    }


    public LinkedList<Location> getBorderBlocks() {
        LinkedList<Location> locs = new  LinkedList<>();
        for(Area area : areaSet) {
            Location bottom = area.getBottomLocation().clone();
            bottom.setX(bottom.getBlockX() - 1);
            bottom.setZ(bottom.getBlockZ() - 1);

            Location top = area.getTopLocation().clone();
            top.setX(top.getBlockX() + 1);
            top.setZ(top.getBlockZ() + 1);

            Location temp = bottom.clone();
            for(int x = bottom.getBlockX() + 1; x < top.getBlockX(); x += 2) {
                temp.setX(x);
                if(areaSet.inArea(temp) == null) {
                    locs.add(new Location(temp.getWorld(), temp.getX(), 0, temp.getZ() + 1));
                }
            }

            temp = bottom.clone();
            for(int z = bottom.getBlockZ() + 1; z < top.getBlockZ(); z += 2) {
                temp.setZ(z);
                if(areaSet.inArea(temp) == null) {
                    locs.add(new Location(temp.getWorld(), temp.getX() + 1, 0, temp.getZ()));
                }
            }

            temp = top.clone();
            for(int z = top.getBlockZ() - 1; z > bottom.getBlockZ(); z -= 2) {
                temp.setZ(z);
                if(areaSet.inArea(temp) == null) {
                    locs.add(new Location(temp.getWorld(), temp.getX() - 1, 0, temp.getZ()));
                }
            }

            temp = top.clone();
            for(int x = top.getBlockX() - 1; x > bottom.getBlockX(); x -= 2) {
                temp.setX(x);
                if(areaSet.inArea(temp) == null) {
                    locs.add(new Location(temp.getWorld(), temp.getX(), 0, temp.getZ() - 1));
                }
            }
        }
        return locs;
    }


    public long getId() {
        return this.id;
    }


    public AreaSet getAreaSet() {
        return this.areaSet;
    }

}
