package me.adversing.hchat.commands;

import me.adversing.hchat.HChat;
import me.adversing.hchat.command.Command;
import me.adversing.hchat.command.CommandArgs;
import me.adversing.hchat.utils.CC;

import org.bukkit.entity.Player;

public class CheckWordCommand {

    @Command(name = "checkword", permission = "hchat.commands.checkword", inGameOnly = true, noPerm = "§cNo permission.")
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length != 1) {
            player.sendMessage(CC.translate("&c/checkword <word>"));
            return;
        }

        String word = args[0].replace("_", " ");
        player.sendMessage(HChat.getInstance().getConfig().getStringList("blockedWords").contains(word)
                ? CC.translate("&a&l\"" + word + "\" &ais a blocked word.")
                : CC.translate("&c&l\"" + word + "\" &cisn't a blocked word.")
        );
    }
}
