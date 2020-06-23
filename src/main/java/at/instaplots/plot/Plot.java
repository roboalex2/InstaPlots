package at.instaplots.plot;

import at.instaplots.group.GroupManager;
import at.instaplots.permission.GeneralPermissionManager;
import at.instaplots.world.region.area.AreaManager;
import com.google.inject.Injector;
import org.bukkit.Location;

public class Plot {

    private long id;

    private AreaManager areaMgnt;
    private GroupManager groupMgnt;
    private GeneralPermissionManager genPermMgnt;
    private Location home;

    Plot(long id, Location home, Injector injector) {
        this.id = id;
        this.home = home;
        this.areaMgnt = injector.getInstance(AreaManager.class);
        this.groupMgnt = injector.getInstance(GroupManager.class);
        this.genPermMgnt = injector.getInstance(GeneralPermissionManager.class);
    }


    public long getId() {
        return this.getId();
    }

}
