package at.instaplots.plot.group.permissions.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BlockPermissions {

    private ConcurrentHashMap<Material, SpecificPermission> permsPerMaterial = new ConcurrentHashMap<>();
    private Set<BlockPermissions.Permission> basePermissionBlock = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public boolean canBreakBlock(Block bl) {
        SpecificPermission blPerms = permsPerMaterial.get(bl.getType());
        if(blPerms != null && blPerms.isPermissionSet(BlockPermissions.Permission.BLOCK_BREAK)){
            return blPerms.hasPermission(BlockPermissions.Permission.BLOCK_BREAK);
        }
        return basePermissionBlock.contains(BlockPermissions.Permission.BLOCK_BREAK);
    }


    public boolean canPlaceBlock(Block bl) {
        SpecificPermission blPerms = permsPerMaterial.get(bl.getType());
        if(blPerms != null && blPerms.isPermissionSet(BlockPermissions.Permission.BLOCK_PLACE)){
            return blPerms.hasPermission(BlockPermissions.Permission.BLOCK_PLACE);
        }

        return basePermissionBlock.contains(BlockPermissions.Permission.BLOCK_PLACE);
    }


    public boolean canInteractBlock(Block bl) {
        SpecificPermission blPerms = permsPerMaterial.get(bl.getType());
        if(blPerms != null && blPerms.isPermissionSet(BlockPermissions.Permission.INTERACT)){
            return blPerms.hasPermission(BlockPermissions.Permission.INTERACT);
        }

        return basePermissionBlock.contains(BlockPermissions.Permission.INTERACT);
    }



    public void grantPermission(Permission perm) {
        basePermissionBlock.add(perm);
    }


    public void revokePermission(Permission perm) {
        basePermissionBlock.remove(perm);
    }


    public void grantPermission(Permission perm, Material type) {
        SpecificPermission matPerms = permsPerMaterial.get(type);
        if(matPerms == null) {
            matPerms = new SpecificPermission();
            permsPerMaterial.put(type, matPerms);
        }
        matPerms.setPermission(perm, true);
    }


    public void revokePermission(Permission perm, Material type) {
        SpecificPermission matPerms = permsPerMaterial.get(type);
        if(matPerms == null) {
            matPerms = new SpecificPermission();
            permsPerMaterial.put(type, matPerms);
        }
        matPerms.setPermission(perm, false);
    }


    public void unsetPermission(Permission perm, Material type) {
        SpecificPermission matPerms = permsPerMaterial.get(type);
        if(matPerms != null) {
            matPerms.unsetPermission(perm);
        }
    }


    public enum Permission {
        BLOCK_BREAK("BLOCK_BREAK"),
        BLOCK_PLACE("BLOCK_PLACE"),
        INTERACT("INTERACT");


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
