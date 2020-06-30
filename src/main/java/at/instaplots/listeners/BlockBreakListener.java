package at.instaplots.listeners;

import at.instaplots.Main;
import at.instaplots.area.AreaManager;
import at.instaplots.world.WorldManager;
import at.instaplots.area.Area;
import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private AreaManager areaMgnt;
    private WorldManager worldMgnt;
    private Main plugin;


    @Inject
    public BlockBreakListener(AreaManager areaMgnt, WorldManager worldMgnt, Main plugin) {
        this.areaMgnt = areaMgnt;
        this.worldMgnt = worldMgnt;
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        Area area = areaMgnt.isInArea(loc);
        if(area != null) {
            event.setCancelled(true);
        }
    }
}
