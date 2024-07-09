package com.lgdxcompany.worldchatformats;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class ChatFormatterTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("wcf") || command.getName().equalsIgnoreCase("worldchatformat")) {
            if (args.length == 1) {
                return Arrays.asList("reload", "toggleDefaultChatFormat", "toggleWorldChatFormat", "toggleGroupRankFormat");
            }
        }
        return null;
    }
}
