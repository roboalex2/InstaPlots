package at.instaplots.plot.group;

import at.instaplots.plot.group.Group;

import java.util.UUID;

public class GroupManager {

    private Group owner;
    private Group trusted;
    private Group worker;
    private Group everyone;
    private Group banned;

    public GroupManager() {
        this.owner = owner;
        this.trusted = trusted;
        this.worker = worker;
        this.everyone = everyone;
        this.banned = banned;
    }

    public GroupManager(Group owner, Group trusted, Group worker, Group everyone, Group banned) {
        this.owner = owner;
        this.trusted = trusted;
        this.worker = worker;
        this.everyone = everyone;
        this.banned = banned;
    }

    public Group getPlayersGroup(UUID uuid) {
        if(owner.contains(uuid)) return owner;
        if(trusted.contains(uuid)) return trusted;
        if(banned.contains(uuid)) return banned;
        if(worker.contains(uuid)) return worker;
        return everyone;
    }
}
