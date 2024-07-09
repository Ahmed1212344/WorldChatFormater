package com.lgdxcompany.worldchatformats.api;

import org.bukkit.entity.Player;

public interface WorldChatFormatterAPI {

    String getChatFormat(Player player);

    void setChatFormat(Player player, String format);
}
