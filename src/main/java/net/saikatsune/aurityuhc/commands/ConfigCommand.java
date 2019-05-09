package net.saikatsune.aurityuhc.commands;

import net.saikatsune.aurityuhc.AurityUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCommand implements CommandExecutor {

    private AurityUHC aurityUHC = AurityUHC.getInstance();

    private String mColor = aurityUHC.getmColor();
    private String sColor = aurityUHC.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("config")) {
            player.sendMessage("§8§m-----------------------");
            player.sendMessage(mColor + "UHC GAME CONFIGURATION");
            player.sendMessage("");
            if(!aurityUHC.getGameManager().isTeamGame()) {
                player.sendMessage(sColor + "Game Type: " + mColor + "FFA");
            } else {
                player.sendMessage(sColor + "Game Type: " + mColor + "To" + aurityUHC.getTeamManager().getTeamSize());
            }
            player.sendMessage(sColor + "Nether: " + mColor + aurityUHC.getConfigManager().isNether());
            player.sendMessage(sColor + "Speed I: " + mColor + aurityUHC.getConfigManager().isSpeed1());
            player.sendMessage(sColor + "Speed II: " + mColor + aurityUHC.getConfigManager().isSpeed2());
            player.sendMessage(sColor + "Strength I: " + mColor + aurityUHC.getConfigManager().isStrength1());
            player.sendMessage(sColor + "Strength II: " + mColor + aurityUHC.getConfigManager().isStrength2());
            player.sendMessage(sColor + "Enderpearl Damage: " + mColor + aurityUHC.getConfigManager().isEnderpearlDamage());
            player.sendMessage(sColor + "God Apples: " + mColor + "false");
            player.sendMessage(sColor + "Horses: " + mColor + "true");
            player.sendMessage(sColor + "Horse Healing: " + mColor + "true");
            player.sendMessage(sColor + "Apple Rate: " + mColor + aurityUHC.getConfigManager().getAppleRate() + "%");
            player.sendMessage(sColor + "Flint Rate: " + mColor + aurityUHC.getConfigManager().getFlintRate() + "%");
            player.sendMessage(sColor + "Shears: " + mColor + aurityUHC.getConfigManager().isShears());
            player.sendMessage(sColor + "Starter Food: " + mColor + aurityUHC.getConfigManager().getStarterFood() + " Steaks");
            player.sendMessage(sColor + "Final Heal: " + mColor + aurityUHC.getConfigManager().getFinalHeal() + " minute(s)");
            player.sendMessage(sColor + "Grace Period: " + mColor + aurityUHC.getConfigManager().getGraceTime() + " minute(s)");
            player.sendMessage(sColor + "First Shrink: " + mColor + aurityUHC.getConfigManager().getBorderTime() + " minute(s)");
            player.sendMessage(sColor + "Current Border: " + mColor + aurityUHC.getConfigManager().getBorderSize());
            player.sendMessage("§8§m-----------------------");
        }
        return false;
    }
}
