package com.lgdxcompany.worldchatformats;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class chat_formater_addon_for_chatmanger extends JavaPlugin implements Listener {

    private LuckPerms luckPerms;
    private boolean placeholderAPIAvailable;
    private boolean chatManagerAvailable;
    private boolean deluxeMenusAvailable;
    private boolean vaultAvailable;
    private boolean bedwars1058Available;
    private boolean perWorldPluginsAvailable;
    private boolean holographicDisplaysAvailable;
    private boolean superVanishAvailable;
    private boolean viaVersionAvailable;
    private boolean viaBackwardsAvailable;
    private boolean viaRewindAvailable;
    private boolean lilyPadConnectAvailable;

    private Map<String, String> worldChatFormats;
    private Map<String, String> groupRankFormats;
    private String defaultChatFormat;
    private boolean defaultChatFormatEnabled;
    private boolean worldChatFormatsEnabled;
    private boolean groupRankFormatsEnabled;

    private FileConfiguration messagesConfig;
    private File messagesFile;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();
        saveDefaultMessagesConfig();

        // Load messages
        loadMessages();


        // Register event listener
        getServer().getPluginManager().registerEvents(this, this);

        // Register commands and their tab completer
        ChatFormatterCommand commandExecutor = new ChatFormatterCommand(this);
        getCommand("worldchatformat").setExecutor(commandExecutor);
        getCommand("wcf").setExecutor(commandExecutor);

        ChatFormatterTabCompleter tabCompleter = new ChatFormatterTabCompleter();
        getCommand("worldchatformat").setTabCompleter(tabCompleter);
        getCommand("wcf").setTabCompleter(tabCompleter);

        getCommand("toggleDefaultChatFormat").setExecutor(new ToggleFormatCommand("defaultChatFormat"));
        getCommand("toggleWorldChatFormat").setExecutor(new ToggleFormatCommand("worldChatFormat"));
        getCommand("toggleGroupRankFormat").setExecutor(new ToggleFormatCommand("groupRankFormat"));

        // Check if each plugin is available
        placeholderAPIAvailable = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        chatManagerAvailable = Bukkit.getPluginManager().getPlugin("ChatManager") != null;
        deluxeMenusAvailable = Bukkit.getPluginManager().getPlugin("DeluxeMenus") != null;
        vaultAvailable = Bukkit.getPluginManager().getPlugin("Vault") != null;
        bedwars1058Available = Bukkit.getPluginManager().getPlugin("Bedwars1058") != null;
        perWorldPluginsAvailable = Bukkit.getPluginManager().getPlugin("PerWorldPlugins") != null;
        holographicDisplaysAvailable = Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null;
        superVanishAvailable = Bukkit.getPluginManager().getPlugin("SuperVanish") != null;
        viaVersionAvailable = Bukkit.getPluginManager().getPlugin("ViaVersion") != null;
        viaBackwardsAvailable = Bukkit.getPluginManager().getPlugin("ViaBackwards") != null;
        viaRewindAvailable = Bukkit.getPluginManager().getPlugin("ViaRewind") != null;
        lilyPadConnectAvailable = Bukkit.getPluginManager().getPlugin("LilyPad-Connect") != null;

        // Log warnings for unavailable plugins
        if (!placeholderAPIAvailable) getLogger().warning("PlaceholderAPI not found! Placeholder support will be disabled.");
        if (!chatManagerAvailable) getLogger().warning("ChatManager not found!");
        if (!deluxeMenusAvailable) getLogger().warning("DeluxeMenus not found!");
        if (!vaultAvailable) getLogger().warning("Vault not found!");
        if (!bedwars1058Available) getLogger().warning("Bedwars1058 not found!");
        if (!perWorldPluginsAvailable) getLogger().warning("PerWorldPlugins not found!");
        if (!holographicDisplaysAvailable) getLogger().warning("HolographicDisplays not found!");
        if (!superVanishAvailable) getLogger().warning("SuperVanish not found!");
        if (!viaVersionAvailable) getLogger().warning("ViaVersion not found!");
        if (!viaBackwardsAvailable) getLogger().warning("ViaBackwards not found!");
        if (!viaRewindAvailable) getLogger().warning("ViaRewind not found!");
        if (!lilyPadConnectAvailable) getLogger().warning("LilyPad-Connect not found!");

        // Setup LuckPerms
        setupLuckPerms();

        // Load chat formats from config
        loadChatFormats();
    }

    private void saveDefaultMessagesConfig() {
        if (messagesFile == null) {
            messagesFile = new File(getDataFolder(), "messages.yml");
        }
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void loadMessages() {
        if (messagesFile == null) {
            messagesFile = new File(getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadMessages() {
        if (messagesFile == null) {
            messagesFile = new File(getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        getLogger().info("Messages reloaded: " + messagesConfig.saveToString());
    }


    public FileConfiguration getMessagesConfig() {
        if (messagesConfig == null) {
            loadMessages();
        }
        return messagesConfig;
    }


    public String getMessage(String key) {

        return ChatColor.translateAlternateColorCodes('&', messagesConfig.getString(key, ""));

    }

    public void toggleGroupRankFormat() {
        groupRankFormatsEnabled = !groupRankFormatsEnabled;
        getConfig().set("group-rank-formats.enabled", groupRankFormatsEnabled);
        saveConfig();
        getLogger().info(getMessage("toggle-group-rank-format") + (groupRankFormatsEnabled ? "enabled" : "disabled") + ".");
    }

    public void toggleWorldChatFormat() {
        worldChatFormatsEnabled = !worldChatFormatsEnabled;
        getConfig().set("world-chat-formats.enabled", worldChatFormatsEnabled);
        saveConfig();
        getLogger().info(getMessage("toggle-world-chat-format") + (worldChatFormatsEnabled ? "enabled" : "disabled") + ".");
    }

    public void toggleDefaultChatFormat() {
        defaultChatFormatEnabled = !defaultChatFormatEnabled;
        getConfig().set("default-chat-format.enabled", defaultChatFormatEnabled);
        saveConfig();
        getLogger().info(getMessage("toggle-default-chat-format") + (defaultChatFormatEnabled ? "enabled" : "disabled") + ".");
    }

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public void loadChatFormats() {
        FileConfiguration config = getConfig();
        worldChatFormats = new HashMap<>();
        groupRankFormats = new HashMap<>();

        // Load default chat format
        defaultChatFormatEnabled = config.getBoolean("default-chat-format.enabled", false);
        defaultChatFormat = config.getString("default-chat-format.format", "%player_displayname%: %message%");

        // Load world chat formats
        worldChatFormatsEnabled = config.getBoolean("world-chat-formats.enabled", true);
        if (worldChatFormatsEnabled && config.isConfigurationSection("world-chat-formats.formats")) {
            for (String world : config.getConfigurationSection("world-chat-formats.formats").getKeys(false)) {
                String format = config.getString("world-chat-formats.formats." + world);
                worldChatFormats.put(world, format);
            }
        }

        // Load group rank formats
        groupRankFormatsEnabled = config.getBoolean("group-rank-formats.enabled", false);
        if (groupRankFormatsEnabled && config.isConfigurationSection("group-rank-formats.formats")) {
            for (String group : config.getConfigurationSection("group-rank-formats.formats").getKeys(false)) {
                String format = config.getString("group-rank-formats.formats." + group);
                groupRankFormats.put(group, format);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Worldchatformat has been disabled");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String worldName = event.getPlayer().getWorld().getName();
        FileConfiguration config = getConfig();

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
        getLogger().info("Selected chat format (before replacement): " + format);
        format = ChatColor.translateAlternateColorCodes('&', format);
        getLogger().info("Selected chat format (after replacement): " + format);

        // Escape single '%' characters to avoid format issues
        format = format.replace("%", "%%");


        // Set the chat format
        event.setFormat(format);

        // Call custom ChatManagerEvent synchronously if needed
        Bukkit.getScheduler().runTask(this, () -> {
            ChatManagerEvent chatManagerEvent = new ChatManagerEvent(event.getMessage());
            Bukkit.getServer().getPluginManager().callEvent(chatManagerEvent);
        });
    }

    // Getter and Setter methods for chat format settings
    public boolean isDefaultChatFormatEnabled() {
        return defaultChatFormatEnabled;
    }

    public void setDefaultChatFormatEnabled(boolean enabled) {
        this.defaultChatFormatEnabled = enabled;
    }

    public boolean isWorldChatFormatsEnabled() {
        return worldChatFormatsEnabled;
    }

    public void setWorldChatFormatsEnabled(boolean enabled) {
        this.worldChatFormatsEnabled = enabled;
    }

    public boolean isGroupRankFormatsEnabled() {
        return groupRankFormatsEnabled;
    }

    public void setGroupRankFormatsEnabled(boolean enabled) {
        this.groupRankFormatsEnabled = enabled;
    }

    private class ChatManagerListener implements Listener {
        @EventHandler
        public void onChatManagerEvent(ChatManagerEvent event) {
            String message = event.getMessage();
            getLogger().info("ChatManagerListener received message: " + message);
        }
    }

    private class ToggleFormatCommand implements CommandExecutor {
        private final String formatType;

        public ToggleFormatCommand(String formatType) {
            this.formatType = formatType;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            switch (formatType) {
                case "defaultChatFormat":
                    toggleDefaultChatFormat();
                    break;
                case "worldChatFormat":
                    toggleWorldChatFormat();
                    break;
                case "groupRankFormat":
                    toggleGroupRankFormat();
                    break;
            }
            return true;
        }
    }
}