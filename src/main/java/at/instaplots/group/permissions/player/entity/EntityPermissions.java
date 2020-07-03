package at.instaplots.group.permissions.player.entity;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntityPermissions {

    private ConcurrentHashMap<Material, SpecificPermission> permsPerEntityType = new ConcurrentHashMap<>();
    private Set<EntityPermissions.Permission> basePermissionEntity = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public boolean canInteractEntity(Entity ent) {
        SpecificPermission blPerms = permsPerEntityType.get(ent.getType());
        if(blPerms != null && blPerms.isPermissionSet(EntityPermissions.Permission.INTERACT)){
            return blPerms.hasPermission(EntityPermissions.Permission.INTERACT);
        }

        return basePermissionEntity.contains(EntityPermissions.Permission.INTERACT);
    }


    public boolean canHitEntity(Entity ent) {
        SpecificPermission blPerms = permsPerEntityType.get(ent.getType());
        if(blPerms != null && blPerms.isPermissionSet(EntityPermissions.Permission.HIT_MOBS)){
            return blPerms.hasPermission(EntityPermissions.Permission.HIT_MOBS);
        }

        return basePermissionEntity.contains(EntityPermissions.Permission.HIT_MOBS);
    }


    public boolean canEntityHitPlayer(Entity ent) {
        SpecificPermission blPerms = permsPerEntityType.get(ent.getType());
        if(blPerms != null && blPerms.isPermissionSet(Permission.HIT_BY_MOBS)){
            return blPerms.hasPermission(EntityPermissions.Permission.HIT_BY_MOBS);
        }

        return basePermissionEntity.contains(EntityPermissions.Permission.HIT_BY_MOBS);
    }


    public enum Permission {
        HIT_MOBS("HIT_MOBS"),
        INTERACT("INTERACT"),
        HIT_BY_MOBS("HIT_BY_MOBS");


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
