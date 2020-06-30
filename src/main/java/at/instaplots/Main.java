package at.instaplots;

import at.instaplots.area.AreaManager;
import at.instaplots.block.BlockManager;
import at.instaplots.border.BorderManager;
import at.instaplots.commands.CmdPlot;
import at.instaplots.listeners.BlockBreakListener;
import at.instaplots.plot.PlotManager;
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

    @Inject
    private BlockManager blockMgnt;

    @Inject
    private PlotManager plotMgnt;

    @Inject
    private AreaManager areaMgnt;

    @Override
    public void onEnable() {
        super.onEnable();
        MainBinderModule module = new MainBinderModule(this);
        injector = module.createInjector();
        injector.injectMembers(this);

        PluginManager mgnt = Bukkit.getPluginManager();
        mgnt.registerEvents(injector.getInstance(BlockBreakListener.class), this);
        this.getCommand("plot").setExecutor(injector.getInstance(CmdPlot.class));

        plotMgnt.initDatabase();
        areaMgnt.initDatabase();
        blockMgnt.initDatabase();
        borderMgnt.runBorderBlockLoop();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Injector getInjector() {
        return injector;
    }
}
