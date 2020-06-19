package at.instaplots.world.region;

import at.instaplots.world.region.area.AreaManager;


public class Region {

    private int x;
    private int z;
    private AreaManager areaMgnt;


    Region(long regkey) {
        this.x = (int)(regkey >> 32);
        this.z = (int)regkey;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public AreaManager getAreaManager() {
        if(this.areaMgnt == null) {
            this.areaMgnt = new AreaManager();
        }
        return this.areaMgnt;
    }
}
