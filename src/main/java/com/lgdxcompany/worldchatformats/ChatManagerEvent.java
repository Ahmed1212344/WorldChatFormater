package com.lgdxcompany.worldchatformats;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatManagerEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String message;

    public ChatManagerEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
