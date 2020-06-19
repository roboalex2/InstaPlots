package at.instaplots;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MainBinderModule extends AbstractModule {

    private final Main plugin;

    public MainBinderModule(Main plugin) {
        this.plugin = plugin;
    }


    public Injector createInjector() {
        return Guice.createInjector(this);
    }


    @Override
    protected void configure() {
        this.bind(Main.class).toInstance(this.plugin);
    }
}
