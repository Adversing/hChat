package me.adversing.hchat.utils;

import me.adversing.hchat.HChat;

import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class PlayerUtils {

    private static final Pattern URL_REGEX = Pattern.compile("^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.][a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$");
    private static final Pattern IP_REGEX = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])([.,])){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

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

        String pureMessage = blockedMessage.toLowerCase().replace("(dot)", ".").replace("[dot]", ".").replace("3", "e").replace("1", "i").replace("!", "i").replace("@", "a").replace("7", "t").replace("0", "o").replace("5", "s").replace("8", "b").replaceAll("\\p{Punct}|\\d", "");

        if (blockedMessage.contains("school shooting") && HChat.getInstance().getConfig().getStringList("blockedWords").contains("school shooting")) {
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            player.sendMessage(CC.translate("&cWe blocked your comment \"" + blockedMessage + "\" as it is breaking our rules because it contains inappropriate content with adult themes. " + "&b" + HChat.getInstance().getConfig().getString("rulesLink")));
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            return;
        } else if (containsAdvertisement(blockedMessage)) {
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            player.sendMessage(CC.translate("&cAdvertising is against the rules. You will be permanently banned from the server if you attempt to advertise."));
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            return;
        } else {
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            player.sendMessage(CC.translate("&cWe blocked your comment \"" + blockedMessage + "\" as it is breaking our rules because it involves encouraging violence or hatred towards other players."));
            player.sendMessage(CC.translate("&b" + HChat.getInstance().getConfig().getString("rulesLink")));
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
        }
    }

    /**
     * Used to check if a message contains IPs or generic advertising
     * @param message The message to check
     * @return true if the message contains IPs
     */
    public static boolean containsAdvertisement(String message) {
        String pureMessage = message.toLowerCase().replace("(dot)", ".").replace("[dot]", ".").replace("3", "e").replace("1", "i").replace("!", "i").replace("@", "a").replace("7", "t").replace("0", "o").replace("5", "s").replace("8", "b").replaceAll("\\p{Punct}|\\d", "");
        if (URL_REGEX.matcher(pureMessage).matches() || URL_REGEX.matcher(message).matches()) return true;
        if (IP_REGEX.matcher(pureMessage).matches() || IP_REGEX.matcher(message).matches()) return true;
        return false;
    }

}
