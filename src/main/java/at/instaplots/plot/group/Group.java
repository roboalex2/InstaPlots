package at.instaplots.plot.group;

import at.instaplots.plot.group.permissions.block.BlockPermissions;
import at.instaplots.plot.group.permissions.entity.EntityPermissions;
import at.instaplots.plot.group.permissions.general.PlayerGeneralPermissions;

import java.util.HashSet;
import java.util.UUID;

public class Group {

    private HashSet<UUID> players = new HashSet<>();
    private EntityPermissions playerEntityPerms;
    private BlockPermissions playerBlockPerms;
    private PlayerGeneralPermissions playerPerms;


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
