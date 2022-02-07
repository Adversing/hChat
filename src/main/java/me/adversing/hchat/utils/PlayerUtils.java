package me.adversing.hchat.utils;

import me.adversing.hchat.HChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PlayerUtils {
    /**
     * Sends a message to the Staff.
     * The message is already formatted with {@link CC} utils.
     * @param message The message you want to send to the Staff
     */
    public static void sendStaffNotify(String message) {
        for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
            if (staff.isOp() || staff.hasPermission("hchat.staff")) staff.sendMessage(CC.translate(message));
        }
    }

    /**
     * Sends a warning each time a player tries to send a blocked word.
     * @param player The message's target.
     * @param blockedMessage The blocked message (you should get it from {@link org.bukkit.event.player.AsyncPlayerChatEvent})
     */
    public static void sendChatViolationNotify(Player player, String blockedMessage) {
        player.sendMessage(CC.translate("&6&m-----------------------------------"));
        player.sendMessage(CC.translate("&cWe blocked your comment \"" + blockedMessage + "\" as it is breaking our rules because it involves encouraging violence or hatred towards other players."));
        player.sendMessage(CC.translate("&b" + HChat.getInstance().getConfig().getString("rulesLink")));
        player.sendMessage(CC.translate("&6&m-----------------------------------"));
    }


}
