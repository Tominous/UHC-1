package net.saikatsune.uhc;

import net.saikatsune.uhc.board.SimpleBoardManager;
import net.saikatsune.uhc.board.UHCBoardProvider;
import net.saikatsune.uhc.commands.*;
import net.saikatsune.uhc.commands.editor.BorderEditorCommand;
import net.saikatsune.uhc.commands.editor.ConfigEditorCommand;
import net.saikatsune.uhc.commands.editor.ScenariosEditorCommand;
import net.saikatsune.uhc.commands.editor.WorldEditorCommand;
import net.saikatsune.uhc.enums.GameType;
import net.saikatsune.uhc.enums.PlayerState;
import net.saikatsune.uhc.gamestate.GameState;
import net.saikatsune.uhc.gamestate.manager.GameStateManager;
import net.saikatsune.uhc.handler.FileHandler;
import net.saikatsune.uhc.handler.InventoryHandler;
import net.saikatsune.uhc.support.EnchantmentSupport;
import net.saikatsune.uhc.support.GlassBorderSupport;
import net.saikatsune.uhc.support.LegacySupport;
import net.saikatsune.uhc.tasks.ButcherTask;
import net.saikatsune.uhc.tasks.RelogTask;
import net.saikatsune.uhc.tasks.ScatterTask;
import net.saikatsune.uhc.tasks.TimeTask;
import net.saikatsune.uhc.listener.*;
import net.saikatsune.uhc.listener.config.*;
import net.saikatsune.uhc.listener.scenarios.*;
import net.saikatsune.uhc.manager.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Game extends JavaPlugin {

    private static Game instance;

    private String prefix;

    private String mColor;
    private String sColor;

    private String teamSizeInString;

    private FileHandler fileHandler;
    private InventoryHandler inventoryHandler;

    private WorldManager worldManager;
    private GameStateManager gameStateManager;
    private LocationManager locationManager;
    private ConfigManager configManager;
    private GameManager gameManager;
    private TeamManager teamManager;
    private DatabaseManager databaseManager;
    private ScoreboardManager scoreboardManager;
    private SimpleBoardManager simpleBoardManager;

    private HashMap<String, GameType> gameType;
    private HashMap<Player, PlayerState> playerState;
    private HashMap<UUID, Integer> teamNumber;
    private HashMap<Player, Player> teamInvitation;
    private HashMap<UUID, Integer> playerKills;

    private HashMap<UUID, UUID> combatVillagerUUID;

    private HashMap<Player, Location> scatterLocation;

    private HashMap<UUID, Location> deathLocation;
    private HashMap<UUID, ItemStack[]> deathInventory;
    private HashMap<UUID, ItemStack[]> deathArmor;
    private HashMap<UUID, Integer> deathLevels;
    private HashMap<UUID, Integer> deathTeamNumber;

    private HashMap<UUID, Integer> logoutTimer;

    private ArrayList<UUID> players;
    private ArrayList<Player> spectators;

    private ArrayList<UUID> whitelisted;
    private ArrayList<UUID> loggedPlayers;
    private ArrayList<UUID> arenaPlayers;

    private ArrayList<UUID> loggedOutPlayers;

    private ArrayList<UUID> receivePvpAlerts;
    private ArrayList<UUID> receiveDiamondAlerts;
    private ArrayList<UUID> receiveGoldAlerts;

    private ArrayList<UUID> helpopMuted;

    private boolean inGrace;
    private boolean chatMuted;
    private boolean databaseActive;
    private boolean arenaEnabled;

    private int relogTimeInMinutes;

    private ScatterTask scatterTask;
    private TimeTask timeTask;
    private ButcherTask butcherTask;
    private RelogTask relogTask;

    private LimitationsListener limitationsListener;

    @Override
    public void onEnable() {
        this.createConfigFile();

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
        databaseManager = new DatabaseManager();
        scoreboardManager = new ScoreboardManager();

        if(!scoreboardManager.getScoreboardsFile().exists()) {
            saveResource("scoreboards.yml", false);
        }

        gameStateManager.setGameState(GameState.LOBBY);

        gameType = new HashMap<>();
        playerState = new HashMap<>();
        teamNumber = new HashMap<>();
        teamInvitation = new HashMap<>();
        playerKills = new HashMap<>();

        combatVillagerUUID = new HashMap<>();

        scatterLocation = new HashMap<>();

        deathLocation = new HashMap<>();
        deathTeamNumber = new HashMap<>();
        deathInventory = new HashMap<>();
        deathArmor = new HashMap<>();
        deathLevels = new HashMap<>();

        logoutTimer = new HashMap<>();

        players = new ArrayList<>();
        spectators = new ArrayList<>();

        whitelisted = new ArrayList<>();
        loggedPlayers = new ArrayList<>();
        arenaPlayers = new ArrayList<>();

        loggedOutPlayers = new ArrayList<>();

        receivePvpAlerts = new ArrayList<>();
        receiveDiamondAlerts = new ArrayList<>();
        receiveGoldAlerts = new ArrayList<>();

        helpopMuted = new ArrayList<>();

        scatterTask = new ScatterTask();
        timeTask = new TimeTask();
        butcherTask = new ButcherTask();
        relogTask = new RelogTask();

        inGrace = true;
        chatMuted = false;
        arenaEnabled = false;

        teamSizeInString = "FFA";

        relogTimeInMinutes = getConfig().getInt("SETUP.LOGOUT-TIMER");

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

        this.init(Bukkit.getPluginManager());

        gameManager.setWhitelisted(true);

        worldManager.createWorld("uhc_world", World.Environment.NORMAL, WorldType.NORMAL);
        worldManager.createWorld("uhc_nether", World.Environment.NETHER, WorldType.NORMAL);
        worldManager.createWorld("uhc_practice", World.Environment.NORMAL, WorldType.FLAT);

        worldManager.createBorderLayer("uhc_practice", 50, 4, null);

        new BukkitRunnable() {
            @Override
            public void run() {
                worldManager.loadWorld("uhc_practice", 50, 1000);
            }
        }.runTaskLater(this, 20);

        for (Entity entity : Bukkit.getWorld("uhc_world").getEntities()) {
            if(entity instanceof Villager) {
                if(entity.getCustomName().contains("[CombatLogger] ")) {
                    entity.remove();
                }
            }
        }

        for (Entity entity : Bukkit.getWorld("uhc_nether").getEntities()) {
            if(entity instanceof Villager) {
                if(entity.getCustomName().contains("[CombatLogger] ")) {
                    entity.remove();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        instance = null;

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

        this.simpleBoardManager = new SimpleBoardManager(this, new UHCBoardProvider(this));
        pluginManager.registerEvents(this.simpleBoardManager, this);

        pluginManager.registerEvents(new LegacySupport(), this);
        pluginManager.registerEvents(new GlassBorderSupport(), this);
        pluginManager.registerEvents(new EnchantmentSupport(), this);

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
        config.addDefault("SETUP.LOGOUT-TIMER", 10);

        config.addDefault("MYSQL.ENABLED", false);
        config.addDefault("MYSQL.HOST", "localhost");
        config.addDefault("MYSQL.DATABASE", "aurityuhc");
        config.addDefault("MYSQL.USERNAME", "root");
        config.addDefault("MYSQL.PASSWORD", "password");
        config.addDefault("MYSQL.PORT", 3306);

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

    public void setTeamSizeInString(String teamSizeInString) {
        this.teamSizeInString = teamSizeInString;
    }

    public String getTeamSizeInString() {
        return teamSizeInString;
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

    public static Game getInstance() {
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

    public ArrayList<UUID> getPlayers() {
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

    public HashMap<UUID, Integer> getPlayerKills() {
        return playerKills;
    }

    public TimeTask getTimeTask() {
        return timeTask;
    }

    public ArrayList<UUID> getLoggedPlayers() {
        return loggedPlayers;
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

    public HashMap<Player, Location> getScatterLocation() {
        return scatterLocation;
    }

    public HashMap<UUID, UUID> getCombatVillagerUUID() {
        return combatVillagerUUID;
    }

    public ButcherTask getButcherTask() {
        return butcherTask;
    }

    public HashMap<UUID, Integer> getLogoutTimer() {
        return logoutTimer;
    }

    public int getRelogTimeInMinutes() {
        return relogTimeInMinutes;
    }

    public ArrayList<UUID> getLoggedOutPlayers() {
        return loggedOutPlayers;
    }

    public RelogTask getRelogTask() {
        return relogTask;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}

