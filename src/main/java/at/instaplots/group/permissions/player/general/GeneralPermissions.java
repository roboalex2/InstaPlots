package at.instaplots.group.permissions.player.general;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GeneralPermissions {
    private Set<Permission> permissions = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public boolean canEnter() {
        return permissions.contains(Permission.ENTER);
    }


    public boolean canHitOthers() {
        return permissions.contains(Permission.HIT_PLAYERS);
    }


    public boolean canBeHitByOthers() {
        return permissions.contains(Permission.HIT_BY_PLAYERS);
    }


    public boolean canDropItems() {
        return permissions.contains(Permission.DROP_ITEMS);
    }


    public boolean canPickupItems() {
        return permissions.contains(Permission.PICKUP_ITEMS);
    }


    public boolean canFly() {
        return permissions.contains(Permission.FLY);
    }


    public boolean canSetHome() {
        return permissions.contains(Permission.SETHOME);
    }


    public boolean canCreateBorder() {
        return permissions.contains(Permission.CREATE_BORDER);
    }


    public boolean canRemoveBorder() {
        return permissions.contains(Permission.REMOVE_BORDER);
    }


    public boolean canEditAreas() {
        return permissions.contains(Permission.EDIT_AREAS);
    }


    public boolean canDeletePlot() {
        return permissions.contains(Permission.DELETE_PLOT);
    }


    public boolean canMergePlot() {
        return permissions.contains(Permission.MERGE_PLOT);
    }


    public boolean canChangePermissionsOwner() {
        return permissions.contains(Permission.CHANGE_PERMISSIONS_OWNER);
    }


    public boolean canChangePermissionsTrusted() {
        return permissions.contains(Permission.CHANGE_PERMISSIONS_TRUSTED);
    }


    public boolean canChangePermissionsWorker() {
        return permissions.contains(Permission.CHANGE_PERMISSIONS_WORKER);
    }


    public boolean canChangePermissionsEveryone() {
        return permissions.contains(Permission.CHANGE_PERMISSIONS_EVERYONE);
    }


    public boolean canChangePermissionsBanned() {
        return permissions.contains(Permission.CHANGE_PERMISSIONS_BANNED);
    }


    public boolean canEditMembersOwner() {
        return permissions.contains(Permission.EDIT_MEMBERS_OWNER);
    }


    public boolean canEditMembersTrusted() {
        return permissions.contains(Permission.EDIT_MEMBERS_TRUSTED);
    }


    public boolean canEditMembersWorker() {
        return permissions.contains(Permission.EDIT_MEMBERS_WORKER);
    }


    public boolean canEditMembersBanned() {
        return permissions.contains(Permission.EDIT_MEMBERS_BANNED);
    }


    public boolean hasPermission(Permission perm) {
        return permissions.contains(perm);
    }


    public void grantPermission(Permission perm) {
        permissions.add(perm);
    }


    public void revokePermission(Permission perm) {
        permissions.remove(perm);
    }


    public enum Permission {
        ENTER("ENTER"),
        HIT_PLAYERS("HIT_PLAYERS"),
        HIT_BY_PLAYERS("HIT_BY_PLAYERS"),
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
