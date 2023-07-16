package ch.chunkclaim;

import ch.chunkclaim.commands.ChunkCommand;
import ch.chunkclaim.manager.BuildListener;
import ch.chunkclaim.manager.ChunkManager;
import ch.chunkclaim.provider.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ChunkClaim extends JavaPlugin {
    private static ChunkClaim instance;
    private MySQL mySQL;
    private ChunkManager chunkManager;

    @Override
    public void onLoad() {
        instance = this;
        this.mySQL = MySQL.newBuilder()
                .withUrl(getConfig().getString("host"))
                .withPort(getConfig().getInt("port"))
                .withDatabase(getConfig().getString("database"))
                .withUser(getConfig().getString("user"))
                .withPassword(getConfig().getString("password"))
                .create();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BuildListener(),this);
        loadconfig();
        getCommand("chunk").setExecutor(new ChunkCommand());
        chunkManager = new ChunkManager();
        chunkManager.createTables();
    }
        // Plugin startup logic


    public String getPrefix(){
        return getConfig().getString("prefix").replace("&", "§");
    }
    public String getWorld(){
        return getConfig().getString("world");
    }

    public void loadconfig(){
        getConfig().addDefault("host","host");
        getConfig().addDefault("port","port");
        getConfig().addDefault("database","database");
        getConfig().addDefault("user","user");
        getConfig().addDefault("password","password");
        getConfig().addDefault("prefix", "&8[&6ChunkClaim&8]");
        getConfig().addDefault("world","world");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    @Override
    public @NotNull FileConfiguration getConfig() {
        return super.getConfig();
    }

    public static ChunkClaim getInstance(){
        return getPlugin(ChunkClaim.class);
    }
    public void getInstances(){
        getPlugin(ChunkClaim.class);
    }
    public MySQL getMySQL(){
        return mySQL;
    }
    public ChunkManager getChunkManager(){
        return chunkManager;
    }

}