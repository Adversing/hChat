package me.adversing.hchat.managers;

import lombok.Getter;
import lombok.Setter;
import me.adversing.hchat.HChat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager {

    @Getter @Setter
    private HashMap<UUID, Long> lastMessage;

    public ChatManager() {
        lastMessage = new HashMap<UUID, Long>();
    }

    public long getPlayerCooldown(Player player) {
        if (player.hasPermission(HChat.getInstance().getConfig().getString("COOLDOWN.VIP.BYPASS_PERMISSION"))) return HChat.getInstance().getConfig().getInt("COOLDOWN.VIP.VALUE");
        return HChat.getInstance().getConfig().getInt("COOLDOWN.DEFAULT.VALUE");
    }
}
