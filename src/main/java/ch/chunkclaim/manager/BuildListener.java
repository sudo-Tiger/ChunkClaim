package ch.chunkclaim.manager;

import ch.chunkclaim.ChunkClaim;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuildListener implements Listener {
    @EventHandler
    public void onBuild(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(event.getPlayer().hasPermission("chunkclaim.build")) {
            event.setCancelled(false);
        }else {
            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
            if(chunkManager.isClaimed(player.getChunk().toString())){
                if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                    event.setCancelled(false);
                }else {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onBuild(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(event.getPlayer().hasPermission("chunkclaim.build")) {
            event.setCancelled(false);
        }else {
            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
            if(chunkManager.isClaimed(player.getChunk().toString())){
                if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                    event.setCancelled(false);
                }else {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getClickedBlock() == null) {
            return;
        }
        if(event.getPlayer().hasPermission("chunkclaim.build")) {
            event.setCancelled(false);
        }else {
            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
            if(chunkManager.isClaimed(player.getChunk().toString())){
                if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                    event.setCancelled(false);
                }else {
                    event.setCancelled(true);
                }
            }
        }
    }

}
