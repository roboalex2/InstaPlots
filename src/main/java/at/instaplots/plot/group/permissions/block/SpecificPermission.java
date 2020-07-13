package at.instaplots.plot.group.permissions.block;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SpecificPermission {

    private Set<BlockPermissions.Permission> allowed = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private Set<BlockPermissions.Permission> denied = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public void setPermission(BlockPermissions.Permission perm, boolean grant) {
        if(grant) {
            allowed.add(perm);
            denied.remove(perm);
        } else {
            denied.add(perm);
            allowed.remove(perm);
        }
    }

    public void unsetPermission(BlockPermissions.Permission perm) {
        allowed.remove(perm);
        denied.remove(perm);
    }

    public boolean hasPermission(BlockPermissions.Permission perm) {
        if(denied.contains(perm)) return false;
        if(allowed.contains(perm)) return true;
        throw new RuntimeException("Permission not set!");
    }

    public boolean isPermissionSet(BlockPermissions.Permission perm) {
        if(denied.contains(perm) || allowed.contains(perm)) return true;
        return false;
    }
}
