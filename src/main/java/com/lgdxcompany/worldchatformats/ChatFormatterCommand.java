package com.lgdxcompany.worldchatformats;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatFormatterCommand implements CommandExecutor {

    private final chat_formater_addon_for_chatmanger plugin;

    public ChatFormatterCommand(chat_formater_addon_for_chatmanger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("wcf") || command.getName().equalsIgnoreCase("worldchatformat")) {
            if (args.length == 0) {
                // Show command list if no arguments are provided
                showCommandList(sender);
                return true;
            }

            String prefix = plugin.getMessagesConfig().getString("plugin-prefix");

            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("worldchatformat.reload")) {
                    plugin.reloadConfig();
                    plugin.loadChatFormats();
                    plugin.reloadMessages();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.reload")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.no-permission")));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("toggleDefaultChatFormat")) {
                if (sender.hasPermission("worldchatformat.toggle")) {
                    plugin.toggleDefaultChatFormat();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("toggle-default-chat-format")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.no-permission")));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("toggleWorldChatFormat")) {
                if (sender.hasPermission("worldchatformat.toggle")) {
                    plugin.toggleWorldChatFormat();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("toggle-world-chat-format")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.no-permission")));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("toggleGroupRankFormat")) {
                if (sender.hasPermission("worldchatformat.toggle")) {
                    plugin.toggleGroupRankFormat();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("toggle-group-rank-format")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.no-permission")));
                }
                return true;
            }
        }

        return false;
    }

    private void showCommandList(CommandSender sender) {
        String prefix = plugin.getMessagesConfig().getString("plugin-prefix");

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.header")));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.reload")));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleDefaultChatFormat")));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleWorldChatFormat")));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleGroupRankFormat")));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Available Commands:"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wcf reload &7- Reloads the chat formats"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wcf toggleDefaultChatFormat &7- Toggles the default chat format"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wcf toggleWorldChatFormat &7- Toggles the world chat format"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/wcf toggleGroupRankFormat &7- Toggles the group rank chat format"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/worldchatformat reload &7- Reloads the chat formats"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/worldchatformat toggleDefaultChatFormat &7- Toggles the default chat format"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/worldchatformat toggleWorldChatFormat &7- Toggles the world chat format"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6/worldchatformat toggleGroupRankFormat &7- Toggles the group rank chat format"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.header")));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.reload")));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleDefaultChatFormat")));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleWorldChatFormat")));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("command.list.toggleGroupRankFormat")));
            sender.sendMessage("Available Commands:");
            sender.sendMessage("/wcf reload - Reloads the chat formats");
            sender.sendMessage("/wcf toggleDefaultChatFormat - Toggles the default chat format");
            sender.sendMessage("/wcf toggleWorldChatFormat - Toggles the world chat format");
            sender.sendMessage("/wcf toggleGroupRankFormat - Toggles the group rank chat format");
            sender.sendMessage("/worldchatformat reload - Reloads the chat formats");
            sender.sendMessage("/worldchatformat toggleDefaultChatFormat - Toggles the default chat format");
            sender.sendMessage("/worldchatformat toggleWorldChatFormat - Toggles the world chat format");
            sender.sendMessage("/worldchatformat toggleGroupRankFormat - Toggles the group rank chat format");
        }
    }
}
