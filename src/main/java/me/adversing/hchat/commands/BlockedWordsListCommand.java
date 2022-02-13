package me.adversing.hchat.commands;

import me.adversing.hchat.HChat;
import me.adversing.hchat.command.Command;
import me.adversing.hchat.command.CommandArgs;
import me.adversing.hchat.utils.CC;

import org.bukkit.entity.Player;

public class BlockedWordsListCommand {

    @Command(name = "blockedwordslist", permission = "hchat.commands.blockedwordslist", inGameOnly = true, noPerm = "§cNo permission.")
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();

        player.sendMessage(CC.translate("&eFetching..."));
        player.sendMessage(CC.translate("&6&m-----------------------------------"));
        player.sendMessage(CC.translate("&c&lList of Blocked Words: "));
        HChat.getInstance().reloadConfig();
        try {
            for (String word :
                    HChat.getInstance().getConfig().getStringList("blockedWords")) {
                player.sendMessage(CC.translate(" &7&l* &f" + word + "\n"));
            }
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
        } catch (NullPointerException | IllegalArgumentException e) {
            player.sendMessage(CC.translate("\n&cAn error occurred whilst trying to get the list from " + HChat.getInstance().getConfig().getName()));
            player.sendMessage(CC.translate("&cCheck your server console for more details."));
            player.sendMessage(CC.translate("&6&m-----------------------------------"));
            e.printStackTrace();
        }

    }

}
