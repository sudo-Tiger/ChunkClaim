package ch.chunkclaim.commands;

import ch.chunkclaim.ChunkClaim;
import ch.chunkclaim.manager.ChunkManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChunkCommand implements CommandExecutor, TabCompleter {
    ChunkClaim plugin = ChunkClaim.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player)sender;
        World world = Bukkit.getWorld(plugin.getWorld());
        if(args.length == 0){
            player.sendMessage(plugin.getPrefix());
            player.sendMessage(plugin.getPrefix() + " §7/chunk claim §8- §7Claimt ein Chunk");
            player.sendMessage(plugin.getPrefix() + " §7/chunk unclaim §8- §7Unclaimt ein Chunk");
            player.sendMessage(plugin.getPrefix() + " §7/chunk info §8- §7Zeigt dir Informationen über das Chunk");
            player.sendMessage(plugin.getPrefix() + " §7/chunk add §8- §7Fügt einen Spieler zu deinem Chunk hinzu");
            player.sendMessage(plugin.getPrefix() + " §7/chunk remove §8- §7Entfernt einen Spieler von deinem Chunk");
            player.sendMessage(plugin.getPrefix() + " §7/chunk list §8- §7Zeigt dir alle Spieler in deinem Chunk");
            player.sendMessage(plugin.getPrefix());
            return false;
        }
                switch (args[0]){
                    case "claim":
                        if(player.getWorld().equals(world)){
                            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
                            if(!(chunkManager.doesPlayerExist(player.getUniqueId()))){
                                if(chunkManager.isClaimed(player.getChunk().toString())) {
                                    player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört bereits §b" + chunkManager.getChunkOwner(player.getChunk().toString()));
                                }else {
                                    chunkManager.iniplayerandchunk(player.getUniqueId().toString(),player.getChunk().toString(),player.getName());
                                    player.sendMessage(plugin.getPrefix() + " §7Du hast erfolgreich ein Chunk geclaimt!");
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + " §7Du hast bereits ein Chunk geclaimt!");
                            }
                        }
                        break;
                    case "reload":
                        if(player.hasPermission("chunk.reload")){
                            plugin.reloadConfig();
                            player.sendMessage(plugin.getPrefix() + " §7Du hast die Config §6erfolgreich §7reloaded!");
                        }else {
                            player.sendMessage(plugin.getPrefix() + " §7Du hast keine Rechte um diesen Befehl auszuführen!");
                        }
                        break;
                    case "info":
                        if(player.getWorld().equals(world)){
                            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
                            if(!(chunkManager.isClaimed(player.getChunk().toString()))){
                                player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört §bniemandem");
                            }else {
                                if(!(chunkManager.getTrusted(player.getName()) == null)){
                                    player.sendMessage(plugin.getPrefix());
                                    player.sendMessage(plugin.getPrefix() + " §bOwner§7 " + chunkManager.getChunkOwner(player.getChunk().toString()));
                                    player.sendMessage(plugin.getPrefix() + " §bChunk§7 " + player.getChunk().toString());
                                    player.sendMessage(plugin.getPrefix()+ " §bTrusted§7 " + "Niemand");
                                    player.sendMessage(plugin.getPrefix());
                                }else {
                                    player.sendMessage(plugin.getPrefix());
                                    player.sendMessage(plugin.getPrefix() + " §bOwner§7 " + chunkManager.getChunkOwner(player.getChunk().toString()));
                                    player.sendMessage(plugin.getPrefix() + " §bChunk§7 " + player.getChunk().toString());
                                    player.sendMessage(plugin.getPrefix()+ " §bTrusted§7 " + chunkManager.getTrusted(player.getChunk().toString()));
                                    player.sendMessage(plugin.getPrefix());
                                }
                            }
                        }else {
                            player.sendMessage(plugin.getPrefix() + " §7Du befindest dich nicht in der richtigen Welt!");
                        }
                        break;
                    case "trust":
                        if(player.getWorld().equals(world)){
                            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
                            if(!(chunkManager.doesPlayerExist(player.getUniqueId()))){
                                player.sendMessage(plugin.getPrefix() + " §7Du hast noch kein Chunk geclaimt!");
                            }else {
                                if(args.length == 2){
                                    try {
                                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                        if(target.getName().equals(player.getName())){
                                            player.sendMessage(plugin.getPrefix() + " §7Du kannst dich nicht selbst Trusten!");
                                        }else {
                                            if(chunkManager.isClaimed(player.getChunk().toString())){
                                                if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                                                    if(chunkManager.getTrusted(player.getChunk().toString()) == null){
                                                        chunkManager.setTrusted(player.getUniqueId().toString(), target.getName());
                                                        player.sendMessage(plugin.getPrefix() + " §7Du hast §b" + target.getName() + " §7erfolgreich als Trusted gesetzt!");
                                                    }else {
                                                        player.sendMessage(plugin.getPrefix() + " §7Du hast bereits einen Spieler getrusted!");
                                                    }
                                                }else {
                                                    player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört nicht dir!");
                                                }
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört niemandem!");
                                            }
                                        }
                                    }catch (Exception e){
                                        player.sendMessage(plugin.getPrefix() + " §7nutze §b/chunk trust <Spieler>");
                                    }}}}
                        break;
                    case "untrust":
                        if(player.getWorld().equals(world)){
                            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
                            if(!(chunkManager.doesPlayerExist(player.getUniqueId()))){
                                player.sendMessage(plugin.getPrefix() + " §7Du hast noch kein Chunk geclaimt!");
                            }else {
                                if(args.length == 2){
                                    try{
                                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                                        if(chunkManager.isClaimed(player.getChunk().toString())){
                                            if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                                                if(chunkManager.getTrusted(player.getChunk().toString()) == "niemand"){
                                                    player.sendMessage(plugin.getPrefix() + " §7Es ist kein Spieler Trusted!");
                                                }else {
                                                    chunkManager.removetrust(player.getChunk().toString());
                                                    player.sendMessage(plugin.getPrefix() + " §7Du hast §b" + target.getName() + " §7erfolgreich von deinem Chunk entfernt!");
                                                }
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört nicht dir!");
                                            }
                                        }else {
                                            player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört niemandem!");
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }else {
                                    player.sendMessage(plugin.getPrefix() + " §7Bitte benutze §b/chunk untrust <Spieler>!");
                                }
                            }
                            return false;
                        }
                        break;
                    case "unclaim":
                        if(player.getWorld().equals(world)){
                            ChunkManager chunkManager = ChunkClaim.getInstance().getChunkManager();
                            if(!(chunkManager.doesPlayerExist(player.getUniqueId()))){
                                player.sendMessage(plugin.getPrefix() + " §7Du hast noch kein Chunk geclaimt!");
                            }else {
                                if(chunkManager.isClaimed(player.getChunk().toString())){
                                    if(chunkManager.getChunkOwner(player.getChunk().toString()).equals(player.getName())){
                                        chunkManager.unclaimChunk(player.getChunk().toString());
                                        player.sendMessage(plugin.getPrefix() + " §7Du hast erfolgreich dein Chunk unclaimed!");
                                    }else {
                                        player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört nicht dir!");
                                    }
                                }else {
                                    player.sendMessage(plugin.getPrefix() + " §7Dieser Chunk gehört niemandem!");
                                }
                            }
                        }else {
                            player.sendMessage(plugin.getPrefix() + " §7Du befindest dich nicht in der richtigen Welt!");
                        }
                        break;
    }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            Player player = (Player) sender;
            List<String> commands = new ArrayList<>();
            commands.add("claim");
            commands.add("unclaim");
            commands.add("info");
            commands.add("trust");
            commands.add("untrust");
            return commands;
    }
        return null;
    }
}
