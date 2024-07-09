package com.lgdxcompany.worldchatformats.api;

public class WorldChatFormatterAPIProvider {

    private static WorldChatFormatterAPI api;

    public static void registerAPI(WorldChatFormatterAPI apiInstance) {
        if (api == null) {
            api = apiInstance;
        } else {
            throw new IllegalStateException("API is already registered.");
        }
    }

    public static WorldChatFormatterAPI getAPI() {
        if (api == null) {
            throw new IllegalStateException("API is not registered yet.");
        }
        return api;
    }
}
