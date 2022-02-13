package me.adversing.hchat.commands;

import me.adversing.hchat.HChat;
import me.adversing.hchat.command.Command;
import me.adversing.hchat.command.CommandArgs;
import me.adversing.hchat.utils.CC;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ToggleChatAlertsCommand {

    @Command(name = "togglechatalerts", permission = "hchat.commands.togglechatalerts", inGameOnly = true, noPerm = "§cNo permission.")
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length != 1) {
            player.sendMessage(CC.translate("&c/togglechatalerts <on/off>"));
            return;
        }

        String value = args[0];

        if (value.equalsIgnoreCase("on")) {
            if (player.hasMetadata("chatAlertsToggled")) {
                player.sendMessage(CC.translate("&cYou have already toggled your chat alerts."));
                return;
            }

            MetadataValue metadataValue = new FixedMetadataValue(HChat.getInstance(), "chatAlertsToggled");
            player.setMetadata("chatAlertsToggled", metadataValue);
            player.sendMessage(CC.translate("&c&l[FILTER] &7» &aYou can now see the chat alerts."));
            return;
        } else if (value.equalsIgnoreCase("off")) {
            if (!player.hasMetadata("chatAlertsToggled")) {
                player.sendMessage(CC.translate("&cYou haven't toggled your chat alerts."));
                return;
            }

            player.removeMetadata("chatAlertsToggled" , HChat.getInstance());
            player.sendMessage(CC.translate("&c&l[FILTER] &7» &4You can no longer see the chat alerts."));
            return;
        } else {
            player.sendMessage(CC.translate("&c/togglechatalerts (on/off)"));
            return;
        }
    }


}
