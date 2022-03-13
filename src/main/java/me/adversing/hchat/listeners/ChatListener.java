package me.adversing.hchat.listeners;

import me.adversing.hchat.HChat;
import me.adversing.hchat.utils.CC;
import me.adversing.hchat.utils.MessageUtils;
import me.adversing.hchat.utils.PlayerUtils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.Random;

/**
 * This class is used to listen for the chat. Everytime a player tries to swear, it gets detected from here.
 */

public class ChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        Random random = new Random();

        /* This variable could be cleaned up a bit, but nvm */
        String[] toxicityReplacement = new String[]{
                "Hey Helper, how play game?",
                "You’re a great person! Do you want to play some games with me?",
                "Your personality shines brighter than the sun!",
                "Welcome to the zoo!",
                "Maybe we can have a rematch?",
                "In my free time I like to watch cat videos on youtube",
                "I heard you like minecraft, so I built a computer so you can minecraft, while minecrafting in your minecraft.",
                "I like pineapple on my pizza",
                "I had something to say, then I forgot it.",
                "Hello everyone! I’m an innocent player who loves everything about this network.",
                "I like Minecraft pvp but you are truly better than me!",
                "Behold, the great and powerful, my magnificent and almighty nemesis!",
                "When nothing is right, go left.",
                "Let’s be friends instead of fighting okay?",
                "Your Clicks per second are godly. :O",
                "If the world in Minecraft is infinite…how can the sun revolve around it?",
                "Pls give me doggo memes!",
                "Blue is greenier than purple for sure",
                "I sometimes try to say bad things and then this happens :/",
                "I have really enjoyed playing with you! <3",
                "What can’t the Ender Dragon read a book? Because he always starts at the End.",
                "You are very good at this game friend.",
                "I like to eat pasta, do you prefer nachos?",
                "Sometimes I sing soppy, love songs in the car.",
                "I love the way your hair glistens in the light",
                "In my free time I like to watch cat videos on youtube",
                "When I saw the guy with a potion I knew there was trouble brewing.",
                "I enjoy long walks on the beach and playing this server",
                "Doin a bamboozle fren.",
                "I need help, teach me how to play!",
                "Can you paint with all the colors of the wind"};

        String[] blockedWords = HChat.getInstance().getConfig().getStringList("blockedWords").toArray(new String[0]);

        if (!(player.isOp() || player.hasPermission("hchat.staff")) && (message.contains("ez") || message.contains("kys") || message.contains("go die"))) {

            /*
            This code must be cleaned up
             */
            for (Player playerCase:
                    Bukkit.getServer().getOnlinePlayers()) {
                if ((playerCase.getName().contains("ez") || playerCase.getName().contains("kys")) && message.contains(playerCase.getName())) return;
            }
            event.setMessage(toxicityReplacement[random.nextInt(toxicityReplacement.length)]);
            return;
        }

        if(MessageUtils.stringContainsItemFromList(message, blockedWords) || PlayerUtils.containsAdvertisement(message)) {

            /*
            This one too
             */
            for (Player playerCase1:
                    Bukkit.getServer().getOnlinePlayers()) {
                if ((MessageUtils.stringContainsItemFromList(playerCase1.getName(), blockedWords)) && message.contains(playerCase1.getName())) return;
            }

            if (player.hasPermission("hchat.bypass") || player.isOp()) {
                player.sendMessage(CC.translate("&c&l[FILTER] &7» Your message would have been filtered. \n&7(" + message + ")"));

                if (HChat.getInstance().getConfig().getBoolean("ALERTS.NOTIFY_WHENEVER_A_STAFF_MEMBER_TRIES_TO_SWEAR")) {
                    PlayerUtils.sendStaffNotify("&c&l[FILTER] &7» " + player.getDisplayName() + " &7bypassed the chat filter.");
                    return;
                }
                return;
            }

            ComponentBuilder alert = new ComponentBuilder(CC.translate("&c&l[FILTER] &7» " + player.getDisplayName() + ": " + message))
                    .append(CC.translate(" &e&l[MUTE]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mute 1h " + player.getName() + (PlayerUtils.containsAdvertisement(message) ? " Advertising" : " Swearing")))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &emute &7" + player.getDisplayName() + " for " + (PlayerUtils.containsAdvertisement(message) ? "Advertising." : "Swearing.")))))
                    .append(CC.translate(" &c&l[KICK]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/kick " + player.getDisplayName() + (PlayerUtils.containsAdvertisement(message) ? " Advertising" : " Swearing")))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &ckick &7" + player.getDisplayName() + " for " + (PlayerUtils.containsAdvertisement(message) ? "Advertising." : "Swearing.")))))
                    .append(CC.translate(" &4&l[BAN]")).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/ban 1d " + player.getDisplayName() + (PlayerUtils.containsAdvertisement(message) ? " Advertising" : " Swearing")))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(CC.translate("&7Click here to &4ban &7" + player.getDisplayName() + " for " + (PlayerUtils.containsAdvertisement(message) ? "Advertising." : "Swearing.")))));

            if (HChat.getInstance().getConfig().getBoolean("ALERTS.NOTIFY_WHENEVER_A_PLAYER_TRIES_TO_SWEAR")) {
                PlayerUtils.sendStaffNotify(alert.create());
            }

            PlayerUtils.sendChatViolationNotify(player, message);
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (HChat.getInstance().getConfig().getBoolean("onJoinNotification") && (player.hasPermission("hchat.staff") || player.isOp())) {
            player.sendMessage(CC.translate("&7Your chat alerts are toggled " + (player.hasMetadata("chatAlertsToggled") ? "&a&lon" : "&c&loff")));
            player.sendMessage(CC.translate("&7Type \"&7&o/togglechatalerts " + (player.hasMetadata("chatAlertsToggled") ? "&7&ooff\" &7to &cdisable &7them" : "&7&oon\" &7to &aenable &7them")));
        }
    }

}
