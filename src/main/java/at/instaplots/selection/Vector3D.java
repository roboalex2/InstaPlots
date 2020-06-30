package at.instaplots.selection;

import org.bukkit.Location;

public class Vector3D {
    private int x;
    private int y;
    private int z;

    public Vector3D(int dx, int dy, int dz) {
        this.x = dx;
        this.y = dy;
        this.z = dz;
    }


    public int getX() {
        return this.x;
    }


    public int getY() {
        return this.y;
    }


    public int getZ() {
        return this.z;
    }


    public void setX(int dx) {
        this.x = dx;
    }


    public void setY(int dy) {
        this.y = dy;
    }


    public void setZ(int dz) {
        this.z = dz;
    }


    public Vector3D add(Vector3D vec) {
        this.setX(vec.getX() + this.x);
        this.setY(vec.getY() + this.y);
        this.setZ(vec.getZ() + this.z);
        return this;
    }


    public Vector3D sub(Vector3D vec) {
        this.setX(this.x - vec.getX());
        this.setY(this.y - vec.getY());
        this.setZ(this.z - vec.getZ());
        return this;
    }
}
