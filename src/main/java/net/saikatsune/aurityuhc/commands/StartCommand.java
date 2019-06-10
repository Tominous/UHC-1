package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import net.saikatsune.aurityuhc.gamestate.GameState;
import net.saikatsune.aurityuhc.gamestate.states.LobbyState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String prefix = aurityUHC.getPrefix();

    private String mColor = aurityUHC.getmColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("start")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(aurityUHC.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
                    if (Bukkit.getWorld("uhc_world") != null) {
                        if (Bukkit.getWorld("uhc_nether") != null) {
                            if(!aurityUHC.isArenaEnabled()) {
                                player.sendMessage(prefix + mColor + "You have started the game!");
                                aurityUHC.getGameStateManager().setGameState(GameState.SCATTERING);
                                aurityUHC.getScatterTask().runTask();
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "Please disable practice before you start the game!");
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "The UHC nether doesn't exist!");
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "The UHC world doesn't exist!");
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "The game has already started!");
                }
            }
        }
        return false;
    }
}
