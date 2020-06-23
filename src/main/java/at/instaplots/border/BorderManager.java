package at.instaplots.border;

import at.instaplots.Main;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

@Singleton
public class BorderManager {

    private Main plugin;

    @Inject
    public BorderManager(Main plugin) {
        this.plugin = plugin;
    }

    private Queue<Location> newBorderBlocks = new LinkedList<>();


    public void runSetBorderBlockLoop() {
        (new BukkitRunnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                while(!newBorderBlocks.isEmpty() && ((System.currentTimeMillis() - time) < 30)) {
                    Location loc = newBorderBlocks.poll();
                    int y = loc.getWorld().getHighestBlockYAt(loc);
                    for(int i = y; i > 1; i--) {
                        loc.setY(i);
                        Block bl = loc.getBlock();
                        if(!(bl.isPassable() || bl.isEmpty() || bl.getType().toString().endsWith("LEAVES"))) break;
                    }
                    loc.getBlock().setType(Material.STONE_SLAB);
                }
            }
        }).runTaskTimer(plugin, 1, 1);
    }


    public void addBorderBlock(Location loc) {
        newBorderBlocks.add(loc);
    }
}
