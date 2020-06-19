package at.instaplots.listeners;

import at.instaplots.Main;
import at.instaplots.world.WorldManager;
import at.instaplots.world.region.area.Area;
import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private WorldManager worldMgnt;
    private Main plugin;

    @Inject
    public BlockBreakListener(WorldManager worldMgnt, Main plugin) {
        this.worldMgnt = worldMgnt;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        Area area = worldMgnt.getRegionManager(loc.getWorld()).isInArea(loc);
    }
}
