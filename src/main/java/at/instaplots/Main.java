package at.instaplots;

import at.instaplots.border.BorderManager;
import at.instaplots.commands.CmdPlot;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

public class Main extends JavaPlugin {

    private Injector injector;

    @Inject
    private BorderManager borderMgnt;

    @Override
    public void onEnable() {
        super.onEnable();
        MainBinderModule module = new MainBinderModule(this);
        injector = module.createInjector();
        injector.injectMembers(this);

        PluginManager mgnt = Bukkit.getPluginManager();
        getCommand("plot").setExecutor(injector.getInstance(CmdPlot.class));

        borderMgnt.runSetBorderBlockLoop();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Injector getInjector() {
        return injector;
    }
}
