package at.instaplots.world;

import at.instaplots.Main;
import at.instaplots.world.region.RegionManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.World;

import java.util.HashMap;

@Singleton
public class WorldManager {

    private HashMap<String, RegionManager> worldRegions = new HashMap<>();

    private Main plugin;

    @Inject
    public WorldManager(Main plugin) {
        this.plugin = plugin;
    }

    public RegionManager getRegionManager(World world) {
        RegionManager regMgnt = worldRegions.get(world.getName());
        if(regMgnt == null) {
            regMgnt = plugin.getInjector().getInstance(RegionManager.class);
            worldRegions.put(world.getName(), regMgnt);
        }
        return regMgnt;
    }
}
