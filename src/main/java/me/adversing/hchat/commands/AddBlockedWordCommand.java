package me.adversing.hchat.commands;

import me.adversing.hchat.HChat;
import me.adversing.hchat.command.Command;
import me.adversing.hchat.command.CommandArgs;
import me.adversing.hchat.utils.CC;

import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;
import java.util.List;

public class AddBlockedWordCommand {

    @Command(name = "addblockedword", permission = "hchat.commands.addblockedword", inGameOnly = true, noPerm = "§cNo permission.")
    public void onCommand(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length != 1) {
            player.sendMessage(CC.translate("&c/addblockedword (word)"));
            return;
        }

        String blockedWord = args[0].replace("_", " ");

        try {
            if(HChat.getInstance().getConfig().getStringList("blockedWords").contains(blockedWord)) {
                player.sendMessage(CC.translate("&c&l\"" + blockedWord + "\" &cis already blocked."));
                return;
            }

            List<String> blockedWords = HChat.getInstance().getConfig().getStringList("blockedWords");
            blockedWords.add(blockedWord);
            HChat.getInstance().getConfig().set("blockedWords", blockedWords);
            HChat.getInstance().saveConfig();
            HChat.getInstance().reloadConfig();
            player.sendMessage(CC.translate("&aSuccessfully added &a&l\"" + blockedWord + "\" &aas blocked word."));
        } catch (ConcurrentModificationException | IllegalAccessError | ArrayStoreException | ArrayIndexOutOfBoundsException e) {
            player.sendMessage(CC.translate("&cError. Check your server console for details."));
            e.printStackTrace();
        }

    }
}
