package com.lgdxcompany.worldchatformats.api;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WorldChatFormatterAPIImpl implements WorldChatFormatterAPI {

    private final Map<Player, String> playerChatFormats = new HashMap<>();

    @Override
    public String getChatFormat(Player player) {
        return playerChatFormats.getOrDefault(player, "default format");
    }

    @Override
    public void setChatFormat(Player player, String format) {
        playerChatFormats.put(player, format);
    }
}
