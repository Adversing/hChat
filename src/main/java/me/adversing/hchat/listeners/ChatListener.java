package me.adversing.hchat.listeners;

import me.adversing.hchat.HChat;
import me.adversing.hchat.utils.CC;
import me.adversing.hchat.utils.MessageUtils;
import me.adversing.hchat.utils.PlayerUtils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * This class is used to listen for the chat. Everytime a player tries to swear, it gets detected from here.
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

                if (HChat.getInstance().getConfig().getBoolean("ALERTS.NOTIFY_WHENEVER_A_STAFF_MEMBER_TRIES_TO_SWEAR")) {
                    PlayerUtils.sendStaffNotify("&c&l[FILTER] &7» " + player.getDisplayName() + " &7bypassed the chat filter.");
                    return;
                }
                return;
            }

            ComponentBuilder alert = new ComponentBuilder(CC.translate("&c&l[FILTER] &7» " + player.getDisplayName() + ": " + message))
                    .append(CC.translate(" &e&l[MUTE]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mute 1h " + player.getName() + " Swearing"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &emute &7" + player.getDisplayName() + " for Swearing."))))
                    .append(CC.translate(" &c&l[KICK]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/kick " + player.getDisplayName() + " Swearing"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &ckick &7" + player.getDisplayName() + " for Swearing."))))
                    .append(CC.translate(" &4&l[BAN]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ban 1d " + player.getDisplayName() + " Swearing"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &4ban &7" + player.getDisplayName() + " for Swearing."))));

            if (HChat.getInstance().getConfig().getBoolean("ALERTS.NOTIFY_WHENEVER_A_PLAYER_TRIES_TO_SWEAR")) {
                PlayerUtils.sendStaffNotify(alert.create());
            }

            PlayerUtils.sendChatViolationNotify(player, message);
            event.setCancelled(true);
        }
    }
}
