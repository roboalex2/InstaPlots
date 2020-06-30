package at.instaplots.border;

import at.instaplots.Main;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@Singleton
public class BorderManager {

    private Main plugin;

    @Inject
    public BorderManager(Main plugin) {
        this.plugin = plugin;
    }

    private Queue<Location> newBorderBlocks = new ConcurrentLinkedQueue<>();
    private Queue<Location> removeBorderBlocks = new ConcurrentLinkedQueue<>();


    public void runBorderBlockLoop() {
        (new BukkitRunnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                while(!newBorderBlocks.isEmpty() && ((System.currentTimeMillis() - time) < 15)) {
                    Location loc = newBorderBlocks.poll();
                    int y = loc.getWorld().getHighestBlockYAt(loc);
                    for(int i = y; i > 1; i--) {
                        loc.setY(i);
                        Block bl = loc.getBlock();
                        if((!(bl.isPassable() || bl.isEmpty()
                                || bl.getType().toString().endsWith("LEAVES"))) || bl.isLiquid()) break;
                    }
                    loc.setY(loc.getBlockY() + 1);
                    loc.getBlock().setType(Material.TORCH);
                }

                time = System.currentTimeMillis();
                while(!removeBorderBlocks.isEmpty() && ((System.currentTimeMillis() - time) < 15)) {
                    Location loc = removeBorderBlocks.poll();
                    int y = 254;
                    for(int i = y; i > 1; i--) {
                        loc.setY(i);
                        Block bl = loc.getBlock();
                        if(bl.getType() == Material.TORCH) break;
                    }
                    loc.getBlock().setType(Material.AIR);
                }
            }
        }).runTaskTimer(plugin, 1, 1);
    }


    public void addBorderBlock(Location loc) {
        newBorderBlocks.add(loc);
    }

    public void addBorderBlocks(Collection<Location> locs) {
        newBorderBlocks.addAll(locs);
    }

    public void removeBorderBlock(Location loc) {
        removeBorderBlocks.add(loc);
    }

    public void removeBorderBlocks(Collection<Location> locs) {
        removeBorderBlocks.addAll(locs);
    }
}
