package at.instaplots.group;

import at.instaplots.group.permissions.Permissions;
import at.instaplots.group.permissions.PermissionsManager;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class Group {

    private HashSet<UUID> players = new HashSet<>();
    private PermissionsManager permMgnt;


    public boolean contains(UUID uuid) {
        return players.contains(uuid);
    }

    public void addPlayer(UUID uuid) {
        players.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }
}
