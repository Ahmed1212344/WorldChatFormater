package com.lgdxcompany.worldchatformats;

import net.luckperms.api.LuckPerms;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class LegacyChatFormatterHandler extends ChatFormatterHandler {

    public LegacyChatFormatterHandler( JavaPlugin plugin,
                                       LuckPerms luckPerms,
                                       boolean placeholderAPIAvailable,
                                       boolean defaultChatFormatEnabled,
                                       boolean worldChatFormatsEnabled,
                                       boolean groupRankFormatsEnabled,
                                       String defaultChatFormat,
                                       Map<String, String> worldChatFormats,
                                       Map<String, String> groupRankFormats) {
        super(plugin, luckPerms, placeholderAPIAvailable, defaultChatFormatEnabled, worldChatFormatsEnabled, groupRankFormatsEnabled, defaultChatFormat, worldChatFormats, groupRankFormats);
    }

    @Override
    public void handleChatEvent(AsyncPlayerChatEvent event) {
        super.handleChatEvent(event);
        String format = handleLegacySupport(event.getFormat());
        event.setFormat(format);
    }

    @Override
    protected String handleLegacySupport(String format) {
        // Add legacy-specific format handling here
        // For example, handling placeholders differently or using different format patterns
        return format;
    }
}
