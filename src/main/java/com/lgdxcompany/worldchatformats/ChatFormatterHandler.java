package com.lgdxcompany.worldchatformats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ChatFormatterHandler {

    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;
    private final boolean placeholderAPIAvailable;
    private final boolean defaultChatFormatEnabled;
    private final boolean worldChatFormatsEnabled;
    private final boolean groupRankFormatsEnabled;
    private final String defaultChatFormat;
    private final Map<String, String> worldChatFormats;
    private final Map<String, String> groupRankFormats;

    public ChatFormatterHandler(JavaPlugin plugin,
                                LuckPerms luckPerms,
                                boolean placeholderAPIAvailable,
                                boolean defaultChatFormatEnabled,
                                boolean worldChatFormatsEnabled,
                                boolean groupRankFormatsEnabled,
                                String defaultChatFormat,
                                Map<String, String> worldChatFormats,
                                Map<String, String> groupRankFormats) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
        this.placeholderAPIAvailable = placeholderAPIAvailable;
        this.defaultChatFormatEnabled = defaultChatFormatEnabled;
        this.worldChatFormatsEnabled = worldChatFormatsEnabled;
        this.groupRankFormatsEnabled = groupRankFormatsEnabled;
        this.defaultChatFormat = defaultChatFormat;
        this.worldChatFormats = worldChatFormats;
        this.groupRankFormats = groupRankFormats;
    }

    public void handleChatEvent(AsyncPlayerChatEvent event) {
        String worldName = event.getPlayer().getWorld().getName();
        FileConfiguration config = plugin.getConfig();

        // Determine the default chat format
        String format = defaultChatFormatEnabled ? defaultChatFormat : "%player_displayname%: %message%";

        // Check if a specific format is defined for the current world
        if (worldChatFormatsEnabled && worldChatFormats.containsKey(worldName)) {
            format = worldChatFormats.get(worldName);
        }

        // Determine the player's group and apply group-specific format if available
        User user = luckPerms.getUserManager().getUser(event.getPlayer().getUniqueId());
        if (groupRankFormatsEnabled && user != null && groupRankFormats.containsKey(user.getPrimaryGroup())) {
            format = groupRankFormats.get(user.getPrimaryGroup());
        }

        // Apply placeholders if PlaceholderAPI is available
        if (placeholderAPIAvailable && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            format = PlaceholderAPI.setPlaceholders(event.getPlayer(), format);
        }

        // Replace placeholders manually
        format = format.replace("%player_displayname%", event.getPlayer().getDisplayName())
                .replace("%player_name%", event.getPlayer().getName())
                .replace("%message%", event.getMessage());

        // Log the selected chat format before and after replacement for debugging purposes
        plugin.getLogger().info("Selected chat format (before replacement): " + format);
        format = ChatColor.translateAlternateColorCodes('&', format);
        plugin.getLogger().info("Selected chat format (after replacement): " + format);

        // Escape single '%' characters to avoid format issues
        format = format.replace("%", "%%");

        // Set the chat format
        event.setFormat(format);

        // Call custom ChatManagerEvent synchronously if needed
        Bukkit.getScheduler().runTask(plugin, () -> {
            ChatManagerEvent chatManagerEvent = new ChatManagerEvent(event.getMessage());
            Bukkit.getServer().getPluginManager().callEvent(chatManagerEvent);
        });
    }

    protected String handleLegacySupport(String format) {
        return format;
    }
}
