package at.instaplots.group;

import at.instaplots.group.permissions.PlayerPermissions;

import java.util.HashSet;
import java.util.UUID;

public class Group {

    private HashSet<UUID> players = new HashSet<>();
    private PlayerPermissions playerPerms;


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
