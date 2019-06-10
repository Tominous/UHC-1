package net.saikatsune.aurityuhc;

import net.saikatsune.aurityuhc.commands.*;
import net.saikatsune.aurityuhc.commands.editor.BorderEditorCommand;
import net.saikatsune.aurityuhc.commands.editor.ConfigEditorCommand;
import net.saikatsune.aurityuhc.commands.editor.ScenariosEditorCommand;
import net.saikatsune.aurityuhc.commands.editor.WorldEditorCommand;
import net.saikatsune.aurityuhc.enums.GameType;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.GameState;
import net.saikatsune.aurityuhc.gamestate.manager.GameStateManager;
import net.saikatsune.aurityuhc.handler.FileHandler;
import net.saikatsune.aurityuhc.handler.InventoryHandler;
import net.saikatsune.aurityuhc.license.AdvancedLicense;
import net.saikatsune.aurityuhc.listener.*;
import net.saikatsune.aurityuhc.listener.config.*;
import net.saikatsune.aurityuhc.listener.scenarios.*;
import net.saikatsune.aurityuhc.manager.*;
import net.saikatsune.aurityuhc.tasks.ScatterTask;
import net.saikatsune.aurityuhc.tasks.TimeTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AurityUHC extends JavaPlugin {

    private static AurityUHC instance;

    private String prefix;

    private String mColor;
    private String sColor;

    private FileHandler fileHandler;
    private InventoryHandler inventoryHandler;

    private WorldManager worldManager;
    private GameStateManager gameStateManager;
    private LocationManager locationManager;
    private ConfigManager configManager;
    private GameManager gameManager;
    private TeamManager teamManager;
    private ScoreboardManager scoreboardManager;
    private DatabaseManager databaseManager;

    private HashMap<String, GameType> gameType;
    private HashMap<Player, PlayerState> playerState;
    private HashMap<UUID, Integer> teamNumber;
    private HashMap<Player, Player> teamInvitation;
    private HashMap<UUID, Integer> playerKills;

    private HashMap<UUID, Location> deathLocation;
    private HashMap<UUID, ItemStack[]> deathInventory;
    private HashMap<UUID, ItemStack[]> deathArmor;
    private HashMap<UUID, Integer> deathLevels;
    private HashMap<UUID, Integer> deathTeamNumber;

    private ArrayList<Player> players;
    private ArrayList<Player> spectators;

    private ArrayList<UUID> whitelisted;
    private ArrayList<UUID> loggedPlayers;
    private ArrayList<UUID> combatLoggedPlayers;
    private ArrayList<UUID> arenaPlayers;

    private ArrayList<UUID> receivePvpAlerts;
    private ArrayList<UUID> receiveDiamondAlerts;
    private ArrayList<UUID> receiveGoldAlerts;

    private ArrayList<UUID> helpopMuted;

    private boolean inGrace;
    private boolean chatMuted;
    private boolean databaseActive;
    private boolean arenaEnabled;

    private ScatterTask scatterTask;
    private TimeTask timeTask;

    private LimitationsListener limitationsListener;

    @Override
    public void onEnable() {
        createConfigFile();

        /*
        if(new AdvancedLicense(getConfig().getString("SECURITY.LICENSE-KEY"), "http://54.37.166.10/license/verify.php", this).register()) {

        }
         */

        instance = this;

        prefix = getConfig().getString("SETTINGS.PREFIX").replace("&", "§")
            .replace(">>", "»");

        mColor = getConfig().getString("SETTINGS.MAIN-COLOR").replace("&", "§");
        sColor = getConfig().getString("SETTINGS.SECONDARY-COLOR").replace("&", "§");

        fileHandler = new FileHandler();
        inventoryHandler = new InventoryHandler();

        worldManager = new WorldManager();
        gameStateManager = new GameStateManager();
        locationManager = new LocationManager();
        configManager = new ConfigManager();
        gameManager = new GameManager();
        teamManager = new TeamManager();
        scoreboardManager = new ScoreboardManager();
        databaseManager = new DatabaseManager();

        gameStateManager.setGameState(GameState.LOBBY);

        gameType = new HashMap<>();
        playerState = new HashMap<>();
        teamNumber = new HashMap<>();
        teamInvitation = new HashMap<>();
        playerKills = new HashMap<>();

        deathLocation = new HashMap<>();
        deathTeamNumber = new HashMap<>();
        deathInventory = new HashMap<>();
        deathArmor = new HashMap<>();
        deathLevels = new HashMap<>();

        players = new ArrayList<>();
        spectators = new ArrayList<>();

        whitelisted = new ArrayList<>();
        loggedPlayers = new ArrayList<>();
        combatLoggedPlayers = new ArrayList<>();
        arenaPlayers = new ArrayList<>();

        receivePvpAlerts = new ArrayList<>();
        receiveDiamondAlerts = new ArrayList<>();
        receiveGoldAlerts = new ArrayList<>();

        helpopMuted = new ArrayList<>();

        scatterTask = new ScatterTask();
        timeTask = new TimeTask();

        inGrace = true;
        chatMuted = false;
        arenaEnabled = false;

        databaseActive = getConfig().getBoolean("MYSQL.ENABLED");

        limitationsListener = new LimitationsListener();

        worldManager.deleteWorld("uhc_practice");

        gameType.put("GameType", GameType.SOLO);

        if(databaseActive) {
            try {
                databaseManager.connectToDatabase();
                getLogger().info("[MySQL] Connection to database succeeded!");
            } catch (ClassNotFoundException | SQLException e) {
                getLogger().info("[MySQL] Connection to database failed!");
            }

            try {
                databaseManager.createTable();
            } catch (SQLException e) {
                e.printStackTrace();
                getLogger().info("[MySQL] Table creation succeeded!");
            }
        }

        init(Bukkit.getPluginManager());

        gameManager.setWhitelisted(true);

        worldManager.createWorld("uhc_world", World.Environment.NORMAL, WorldType.NORMAL);
        worldManager.createWorld("uhc_nether", World.Environment.NETHER, WorldType.NORMAL);
        worldManager.createWorld("uhc_practice", World.Environment.NORMAL, WorldType.FLAT);

        worldManager.createBorderLayer("uhc_practice", 100, 4, null);

        scoreboardManager.updateScoreboard();
    }

    @Override
    public void onDisable() {
        this.players.clear();
        this.spectators.clear();
        this.whitelisted.clear();
        this.helpopMuted.clear();

        if(databaseActive) {
            try {
                databaseManager.disconnectFromDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void init(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("whitelist").setExecutor(new WhitelistCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("mutechat").setExecutor(new MuteChatCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("time").setExecutor(new TimeCommand());
        getCommand("health").setExecutor(new HealthCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("helpop").setExecutor(new HelpopCommand());
        getCommand("rates").setExecutor(new RatesCommand());
        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("staff").setExecutor(new StaffCommand());
        getCommand("scatter").setExecutor(new ScatterCommand());
        getCommand("teamchat").setExecutor(new TeamChatCommand());
        getCommand("scenarios").setExecutor(new ScenariosCommand());
        getCommand("sendcoords").setExecutor(new SendCoordsCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("respawn").setExecutor(new RespawnCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("alerts").setExecutor(new AlertsCommand());
        getCommand("killstop").setExecutor(new KillsTopCommand());
        getCommand("starterfood").setExecutor(new StarterFoodCommand());
        getCommand("giveall").setExecutor(new GiveAllCommand());
        getCommand("helpopmute").setExecutor(new HelpopMuteCommand());
        getCommand("helpopunmute").setExecutor(new HelpopMuteCommand());
        getCommand("practice").setExecutor(new PracticeCommand());

        getCommand("worldeditor").setExecutor(new WorldEditorCommand());
        getCommand("configeditor").setExecutor(new ConfigEditorCommand());
        getCommand("bordereditor").setExecutor(new BorderEditorCommand());
        getCommand("scenarioseditor").setExecutor(new ScenariosEditorCommand());

        pluginManager.registerEvents(new SetupCommand(), this);
        pluginManager.registerEvents(new WorldEditorCommand(), this);
        pluginManager.registerEvents(new ConfigEditorCommand(), this);
        pluginManager.registerEvents(new BorderEditorCommand(), this);
        pluginManager.registerEvents(new ScenariosEditorCommand(), this);

        pluginManager.registerEvents(new AlertsCommand(), this);

        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new BlockChangeListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);

        pluginManager.registerEvents(new PlayerPortalListener(), this);
        pluginManager.registerEvents(new PlayerTeleportListener(), this);
        pluginManager.registerEvents(new PlayerCraftListener(), this);
        pluginManager.registerEvents(new PlayerConsumeListener(), this);
        pluginManager.registerEvents(new PlayerDecayListener(), this);
        pluginManager.registerEvents(new PlayerExitVehicleListener(), this);

        pluginManager.registerEvents(new CutCleanListener(), this);
        pluginManager.registerEvents(new TimebombListener(), this);
        pluginManager.registerEvents(new TimberListener(), this);
        pluginManager.registerEvents(new SoupListener(), this);
        pluginManager.registerEvents(new BowlessListener(), this);
        pluginManager.registerEvents(new FirelessListener(), this);
        pluginManager.registerEvents(new NoFallListener(), this);
        pluginManager.registerEvents(new RodlessListener(), this);
        pluginManager.registerEvents(new BloodDiamondsListener(), this);
        pluginManager.registerEvents(new LongShotsListener(), this);
        pluginManager.registerEvents(new DiamondlessListener(), this);
        pluginManager.registerEvents(new GoldlessListener(), this);
        pluginManager.registerEvents(new HasteyBoysListener(), this);
        pluginManager.registerEvents(new LimitationsListener(), this);
        pluginManager.registerEvents(new BarebonesListener(), this);
        pluginManager.registerEvents(new FlowerPowerListener(), this);
        pluginManager.registerEvents(new DoubleXPListener(), this);
        pluginManager.registerEvents(new WebCageListener(), this);
        pluginManager.registerEvents(new NoCleanListener(), this);
        pluginManager.registerEvents(new DoubleOresListener(), this);
        pluginManager.registerEvents(new OreFrenzyListener(), this);

        configManager.setBorderSize(getConfig().getInt("SETUP.MAP-RADIUS"));
        configManager.setAppleRate(2);
        configManager.setFlintRate(50);
        configManager.setEnderpearlDamage(true);
        configManager.setBorderTime(getConfig().getInt("SETUP.FIRST-SHRINK"));
        configManager.setFinalHeal(getConfig().getInt("SETUP.HEAL-TIME"));
        configManager.setGraceTime(getConfig().getInt("SETUP.GRACE-TIME"));
        configManager.setGoldenHeads(true);
        configManager.setNether(false);
        configManager.setShears(false);
        configManager.setSpeed1(false);
        configManager.setSpeed2(false);
        configManager.setStrength1(false);
        configManager.setStrength2(false);
        configManager.setStarterFood(16);

        teamManager.setTeamSize(2);
    }

    private void createConfigFile() {
        FileConfiguration config = getConfig();

        config.addDefault("SETTINGS.PREFIX", "&7[&aUHC&7] ");
        config.addDefault("SETTINGS.MAIN-COLOR", "&a");
        config.addDefault("SETTINGS.SECONDARY-COLOR", "&f");

        config.addDefault("SETUP.MAP-RADIUS", 1500);
        config.addDefault("SETUP.HEAL-TIME", 10);
        config.addDefault("SETUP.GRACE-TIME", 20);
        config.addDefault("SETUP.FIRST-SHRINK", 45);

        config.addDefault("MYSQL.ENABLED", true);
        config.addDefault("MYSQL.HOST", "localhost");
        config.addDefault("MYSQL.DATABASE", "aurityuhc");
        config.addDefault("MYSQL.USERNAME", "root");
        config.addDefault("MYSQL.PASSWORD", "password");
        config.addDefault("MYSQL.PORT", 3306);

        config.addDefault("SCOREBOARD.HEADER", "&a&lAurity &7:|: &f&lUHC");

        config.addDefault("SECURITY.LICENSE-KEY", "XXXX-XXXX-XXXX-XXXX");

        config.options().copyDefaults(true);
        saveConfig();
    }

    public void registerPlayerDeath(Player player) {
        deathLocation.put(player.getUniqueId(), player.getLocation());
        deathInventory.put(player.getUniqueId(), player.getInventory().getContents());
        deathArmor.put(player.getUniqueId(), player.getInventory().getArmorContents());
        deathLevels.put(player.getUniqueId(), player.getLevel());
        deathTeamNumber.put(player.getUniqueId(), this.getTeamNumber().get(player.getUniqueId()));
    }

    public void setDatabaseActive(boolean databaseActive) {
        this.databaseActive = databaseActive;

        if(databaseActive) {
            getConfig().set("MYSQL.ENABLED", false);
        } else {
            getConfig().set("MYSQL.ENABLED", true);
        }
    }

    public boolean isDeathRegistered(Player player) {
        return deathLocation.get(player.getUniqueId()) != null;
    }

    public void setInGrace(boolean inGrace) {
        this.inGrace = inGrace;
    }

    public void setChatMuted(boolean chatMuted) {
        this.chatMuted = chatMuted;
    }

    public static AurityUHC getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getmColor() {
        return mColor;
    }

    public String getsColor() {
        return sColor;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public HashMap<Player, PlayerState> getPlayerState() {
        return playerState;
    }

    public HashMap<String, GameType> getGameType() {
        return gameType;
    }

    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ArrayList<Player> getSpectators() {
        return spectators;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<UUID> getWhitelisted() {
        return whitelisted;
    }

    public HashMap<UUID, Integer> getTeamNumber() {
        return teamNumber;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public HashMap<Player, Player> getTeamInvitation() {
        return teamInvitation;
    }

    public boolean isInGrace() {
        return inGrace;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }

    public ScatterTask getScatterTask() {
        return scatterTask;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public HashMap<UUID, Integer> getPlayerKills() {
        return playerKills;
    }

    public TimeTask getTimeTask() {
        return timeTask;
    }

    public ArrayList<UUID> getLoggedPlayers() {
        return loggedPlayers;
    }

    public ArrayList<UUID> getCombatLoggedPlayers() {
        return combatLoggedPlayers;
    }

    public HashMap<UUID, Integer> getDeathLevels() {
        return deathLevels;
    }

    public HashMap<UUID, Integer> getDeathTeamNumber() {
        return deathTeamNumber;
    }

    public HashMap<UUID, ItemStack[]> getDeathArmor() {
        return deathArmor;
    }

    public HashMap<UUID, ItemStack[]> getDeathInventory() {
        return deathInventory;
    }

    public HashMap<UUID, Location> getDeathLocation() {
        return deathLocation;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public LimitationsListener getLimitationsListener() {
        return limitationsListener;
    }

    public ArrayList<UUID> getReceivePvpAlerts() {
        return receivePvpAlerts;
    }

    public ArrayList<UUID> getReceiveDiamondAlerts() {
        return receiveDiamondAlerts;
    }

    public ArrayList<UUID> getReceiveGoldAlerts() {
        return receiveGoldAlerts;
    }

    public boolean isDatabaseActive() {
        return databaseActive;
    }

    public ArrayList<UUID> getHelpopMuted() {
        return helpopMuted;
    }

    public ArrayList<UUID> getArenaPlayers() {
        return arenaPlayers;
    }

    public boolean isArenaEnabled() {
        return arenaEnabled;
    }

    public void setArenaEnabled(boolean arenaEnabled) {
        this.arenaEnabled = arenaEnabled;
    }
}
