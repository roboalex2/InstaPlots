package at.instaplots.group;

import java.util.UUID;

public class GroupManager {

    private Group owner;
    private Group trusted;
    private Group worker;
    private Group everyone;
    private Group banned;



    public Group getPlayersGroup(UUID uuid) {
        if(owner.contains(uuid)) return owner;
        if(trusted.contains(uuid)) return trusted;
        if(banned.contains(uuid)) return banned;
        if(worker.contains(uuid)) return worker;
        return everyone;
    }
}
