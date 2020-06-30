package at.instaplots.selection;

import org.bukkit.Location;

public class Selection {

    protected Location topLocation;
    protected Location bottomLocation;


    protected Selection() {super();}


    public Selection(Location pos1, Location pos2) {
        topLocation = new Location(pos1.getWorld(),
                Math.max(pos1.getBlockX(), pos2.getBlockX()), 0, Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
        bottomLocation = new Location(pos1.getWorld(),
                Math.min(pos1.getBlockX(), pos2.getBlockX()), 0, Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
    }


    public boolean isOverlapping(Selection other) {
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


    public boolean isTouching(Selection other) {
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


    public Location getCenter() {
        int dX2 = (topLocation.getBlockX() - bottomLocation.getBlockX()) / 2;
        int dZ2 = (topLocation.getBlockZ() - bottomLocation.getBlockZ()) / 2;
        return new Location(topLocation.getWorld(), bottomLocation.getBlockX() + dX2,
                254, bottomLocation.getBlockZ() + dZ2);
    }

    public long getSize() {
        Vector3D vec1 = new Vector3D(topLocation.getBlockX(), topLocation.getBlockY(), topLocation.getBlockZ());
        Vector3D vec2 = new Vector3D(bottomLocation.getBlockX(), bottomLocation.getBlockY(), bottomLocation.getBlockZ());
        vec1.sub(vec2);
        return (long)(Math.abs(vec1.getX()) + 1) * (long)(Math.abs(vec1.getZ()) + 1);
    }

    public Location getTopLocation() {
        return topLocation;
    }

    public Location getBottomLocation() {
        return bottomLocation;
    }
}
