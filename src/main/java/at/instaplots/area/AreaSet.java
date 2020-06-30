package at.instaplots.area;

import org.bukkit.Location;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class AreaSet implements Iterable<Area> {

    private Set<Area> areas = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public Area inArea(Location loc) {
        for(Area area : areas) {
            if(area.isInside(loc)) return area;
        }
        return null;
    }

    public boolean add(Area area) {
        return areas.add(area);
    }

    public boolean remove(Area area) {
        return areas.remove(area);
    }

    @Override
    public Iterator<Area> iterator() {
        return areas.iterator();
    }

    @Override
    public void forEach(Consumer<? super Area> action) {
        areas.forEach(action);
    }

    @Override
    public Spliterator<Area> spliterator() {
        return areas.spliterator();
    }
}
