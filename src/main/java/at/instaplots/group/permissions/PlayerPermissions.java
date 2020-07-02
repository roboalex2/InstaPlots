package at.instaplots.group.permissions;

import java.util.HashSet;

public class PlayerPermissions {

    private HashSet<Permission> permission = new HashSet<>();

    public enum Permission {
        BLOCK_BREAK("BLOCK_BREAK"),
        BLOCK_PLACE("BLOCK_PLACE"),
        HIT_PLAYERS("HIT_PLAYERS"),
        HIT_MOBS("HIT_MOBS"),
        INTERACT("INTERACT"),
        ENTER("ENTER"),
        HIT_BY_PLAYERS("HIT_BY_PLAYERS"),
        HIT_BY_MOBS("HIT_BY_MOBS"),
        DROP_ITEMS("DROP_ITEMS"),
        PICKUP_ITEMS("PICKUP_ITEMS"),
        FLY("FLY"),
        SETHOME("SETHOME"),
        CREATE_BORDER("CREATE_BORDER"),
        REMOVE_BORDER("REMOVE_BORDER"),
        EDIT_AREAS("EDIT_AREAS"),
        DELETE_PLOT("DELETE_PLOT"),
        MERGE_PLOT("MERGE_PLOT"),
        CHANGE_PERMISSIONS_OWNER("CHANGE_PERMISSIONS_OWNER"),
        CHANGE_PERMISSIONS_TRUSTED("CHANGE_PERMISSIONS_TRUSTED"),
        CHANGE_PERMISSIONS_WORKER("CHANGE_PERMISSIONS_WORKER"),
        CHANGE_PERMISSIONS_EVERYONE("CHANGE_PERMISSIONS_EVERYONE"),
        CHANGE_PERMISSIONS_BANNED("CHANGE_PERMISSIONS_BANNED"),
        EDIT_MEMBERS_OWNER("EDIT_MEMBERS_OWNER"),
        EDIT_MEMBERS_TRUSTED("EDIT_MEMBERS_TRUSTED"),
        EDIT_MEMBERS_WORKER("EDIT_MEMBERS_WORKER"),
        EDIT_MEMBERS_BANNED("EDIT_MEMBERS_BANNED");


        public final String label;

        private Permission(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

}
