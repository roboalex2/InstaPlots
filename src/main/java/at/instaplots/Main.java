package at.instaplots;

import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        super.onEnable();
        MainBinderModule module = new MainBinderModule(this);
        injector = module.createInjector();
        injector.injectMembers(this);
        PluginManager mgnt = Bukkit.getPluginManager();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public Injector getInjector() {
        return injector;
    }
}
