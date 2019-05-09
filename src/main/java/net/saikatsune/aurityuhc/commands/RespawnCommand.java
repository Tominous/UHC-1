package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.enums.PlayerState;
import net.saikatsune.aurityuhc.gamestate.states.IngameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RespawnCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("respawn")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof IngameState) {
                            if(aurityUHC.isDatabaseActive()) {
                                if(aurityUHC.getSpectators().contains(target)) {
                                    if(aurityUHC.isDeathRegistered(target)) {
                                        aurityUHC.getGameManager().setPlayerState(target, PlayerState.PLAYER);
                                        aurityUHC.getDatabaseManager().removeDeaths(target, 1);
                                        target.teleport(aurityUHC.getDeathLocation().get(target.getUniqueId()));
                                        target.getInventory().setContents(aurityUHC.getDeathInventory().get(target.getUniqueId()));
                                        target.getInventory().setArmorContents(aurityUHC.getDeathArmor().get(target.getUniqueId()));
                                        target.setLevel(aurityUHC.getDeathLevels().get(target.getUniqueId()));

                                        aurityUHC.getLoggedPlayers().add(target.getUniqueId());
                                        aurityUHC.getWhitelisted().add(target.getUniqueId());

                                        if(aurityUHC.getGameManager().isTeamGame()) {
                                            if(aurityUHC.getTeamManager().getTeams().containsKey(aurityUHC.getDeathTeamNumber().get(target.getUniqueId()))) {
                                                aurityUHC.getTeamManager().addPlayerToTeam(aurityUHC.getDeathTeamNumber().get(target.getUniqueId()), target.getUniqueId());
                                            } else {
                                                aurityUHC.getTeamManager().createTeam(target.getUniqueId());
                                            }
                                        }
                                        Bukkit.broadcastMessage(prefix + mColor + target.getName() + sColor + " has been re-spawned!");
                                    } else {
                                        player.sendMessage(prefix + ChatColor.RED + target.getName() + " hasn't died in this game!");
                                    }
                                } else {
                                    player.sendMessage(prefix + ChatColor.RED + target.getName() + " is already in game!");
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "Stats are currently disabled!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "You cannot respawn players right now!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + args[0] + " is currently offline!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /respawn (player)");
                }
            }
        }
        return false;
    }
}
