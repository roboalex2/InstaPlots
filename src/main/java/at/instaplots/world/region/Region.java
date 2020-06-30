package at.instaplots.world.region;

import at.instaplots.area.AreaSet;


public class Region {

    private int x;
    private int z;
    private AreaSet areaMgnt;


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

    public AreaSet getAreaSet() {
        if(this.areaMgnt == null) {
            this.areaMgnt = new AreaSet();
        }
        return this.areaMgnt;
    }
}
