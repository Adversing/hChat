package me.adversing.hchat.listeners;

import me.adversing.hchat.HChat;
import me.adversing.hchat.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatFloodListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (HChat.getInstance().getChatManager().getLastMessage().containsKey(player.getUniqueId())) {
            long lastMessageTime = HChat.getInstance().getChatManager().getLastMessage().get(player.getUniqueId());
            long difference = System.currentTimeMillis() - lastMessageTime;
            if (difference < HChat.getInstance().getChatManager().getPlayerCooldown(player)) {
                int seconds;
                if (player.hasPermission(HChat.getInstance().getConfig().getString("COOLDOWN.STAFF.BYPASS_PERMISSION")) || player.isOp())
                    seconds = Math.round(HChat.getInstance().getConfig().getInt("COOLDOWN.STAFF.VALUE") / 100) / 10;
                else seconds = Math.round(HChat.getInstance().getChatManager().getPlayerCooldown(player) / 100) / 10;

                List<String> message = HChat.getInstance().getConfig().getStringList("COOLDOWN.MESSAGE");
                for (String s : message) {
                    event.getPlayer().sendMessage(CC.translate(s.replace("%time%", String.valueOf(seconds))));
                }
                event.setCancelled(true);
                return;
            }
        }
        HChat.getInstance().getChatManager().getLastMessage().put(player.getUniqueId(), System.currentTimeMillis());
    }


}
