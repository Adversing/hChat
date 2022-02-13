package me.adversing.hchat;

import lombok.Getter;

import me.adversing.hchat.command.CommandFramework;
import me.adversing.hchat.commands.*;
import me.adversing.hchat.listeners.ChatListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HChat extends JavaPlugin {

    @Getter
    private static HChat instance;

    private CommandFramework cf;

    @Override
    public void onEnable() {
        instance = this;
        registerListeners();
        registerCommands();
        saveDefaultConfig();
        reloadConfig();
    }

    @Override
    public void onDisable() {
        cf = null;
        instance = null;
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }
    public void registerCommands() {
        cf = new CommandFramework(this);
        cf.registerCommands(new ToggleChatAlertsCommand());
        cf.registerCommands(new BlockedWordsListCommand());
        cf.registerCommands(new CheckWordCommand());
        cf.registerCommands(new AddBlockedWordCommand());
        cf.registerCommands(new DeleteBlockedWordCommand());
    }
}
