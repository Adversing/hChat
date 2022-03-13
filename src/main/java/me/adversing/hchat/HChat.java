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
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§6[hChat] §8loading...");
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getConsoleSender().sendMessage("§6[hChat] §eRegistering listeners...");
        registerListeners();

        Bukkit.getConsoleSender().sendMessage("§6[hChat] §eRegistering commands...");
        registerCommands();

        Bukkit.getConsoleSender().sendMessage("§6[hChat] §eReading config.yml...");
        saveDefaultConfig();
        reloadConfig();

        getConfig().getStringList("blockedWords").forEach(
                str -> Bukkit.getConsoleSender().sendMessage("§6[hChat] §7successfully registered \"" + str + "\" as a blocked word.")
        );

        Bukkit.getConsoleSender().sendMessage("§6[hChat] §asuccessfully enabled.");
    }

    @Override
    public void onDisable() {
        cf = null;
        instance = null;

        Bukkit.getConsoleSender().sendMessage("§6[hChat] §csuccessfully disabled.");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getConsoleSender().sendMessage("§6[hChat] §aListeners successfully registered");
    }

    private void registerCommands() {
        cf = new CommandFramework(this);
        cf.registerCommands(new ToggleChatAlertsCommand());
        cf.registerCommands(new BlockedWordsListCommand());
        cf.registerCommands(new CheckWordCommand());
        cf.registerCommands(new AddBlockedWordCommand());
        cf.registerCommands(new DeleteBlockedWordCommand());
        Bukkit.getConsoleSender().sendMessage("§6[hChat] §aCommands successfully registered");
    }
}
