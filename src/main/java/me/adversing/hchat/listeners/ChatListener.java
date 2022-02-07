package me.adversing.hchat.listeners;

import me.adversing.hchat.HChat;
import me.adversing.hchat.utils.CC;
import me.adversing.hchat.utils.MessageUtils;
import me.adversing.hchat.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This class is used to Listen for the chat. Everytime a player tries to swear, it gets detected from here.
 * @implNote {@link Listener}
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        String[] blockedWords = HChat.getInstance().getConfig().getStringList("blockedWords").toArray(new String[0]);

        if(MessageUtils.stringContainsItemFromList(message, blockedWords)) {
            if (player.hasPermission("hchat.bypass") || player.isOp()) {
                player.sendMessage(CC.translate("&c&l[FILTER] &7» Your message would have been filtered. \n&7(" + message + ")"));
                PlayerUtils.sendStaffNotify("&c&l[FILTER] &7» " + player.getDisplayName() + " &7bypassed the chat filter.");
                return;
            }

            PlayerUtils.sendChatViolationNotify(player, message);
            event.setCancelled(true);
        }
    }
}
