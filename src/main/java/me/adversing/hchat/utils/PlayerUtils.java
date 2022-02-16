package me.adversing.hchat.utils;

import me.adversing.hchat.HChat;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {
    /**
     * Sends a message to the Staff.
     * The message is already formatted with {@link CC} utils.
     * @param message The message you want to send to the Staff
     */
    public static void sendStaffNotify(String message) {
        for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
            if (message == null || staff == null) throw new NullPointerException("\"message\" or \"staff\" cannot be null.");
            if (staff.hasMetadata("chatAlertsToggled")) { //I know, this is messed up.
                if (staff.isOp() || staff.hasPermission("hchat.staff")) staff.sendMessage(CC.translate(message));
            }
        }
    }
    /**
     * Sends a message to the Staff.
     * The message is NOT formatted with {@link CC} utils due to its component, which is a {@link BaseComponent}.
     * @param message The message you want to send to the Staff (made using {@link BaseComponent})
     */
    public static void sendStaffNotify(BaseComponent[] message) {
        for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
            if (message == null) throw new NullPointerException("\"message\" cannot be null.");
            if  (staff.hasMetadata("chatAlertsToggled")) { //This one too
                if (staff.isOp() || staff.hasPermission("hchat.staff")) staff.spigot().sendMessage(message);
            }
        }
    }

    /**
     * Sends a warning each time a player tries to send a blocked word.
     * @param player The message's target.
     * @param blockedMessage The blocked message (you should get it from {@link org.bukkit.event.player.AsyncPlayerChatEvent})
     */
    public static void sendChatViolationNotify(Player player, String blockedMessage) {
        if (player == null) throw new NullPointerException("\"player\" cannot be null.");
        if (blockedMessage == null) throw new NullPointerException("\"blockedMessage\" cannot be null.");

        player.sendMessage(CC.translate("&6&m-----------------------------------"));
        player.sendMessage(CC.translate("&cWe blocked your comment \"" + blockedMessage + "\" as it is breaking our rules because it involves encouraging violence or hatred towards other players."));
        player.sendMessage(CC.translate("&b" + HChat.getInstance().getConfig().getString("rulesLink")));
        player.sendMessage(CC.translate("&6&m-----------------------------------"));
    }


}
