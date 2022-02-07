package me.adversing.hchat;

import lombok.Getter;
import me.adversing.hchat.listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HChat extends JavaPlugin {

    @Getter
    private static HChat instance;

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }
}
