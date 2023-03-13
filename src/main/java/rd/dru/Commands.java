package rd.dru;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import rd.dru.PlayerManager.OptionType;

/**
 * 
 * @author Dru_TNT
 *
 */
public class Commands implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player))
			return true;
		Player p = (Player) sender;
		if(args.length<1) {
			PlayerManager.toggle(p);
			return true;
		}
		if(!p.hasPermission("superharvest.use")) {
			p.sendMessage(Config.color(SuperHarvest.getSuperConfig().perms));
		}
		switch(args[0].toLowerCase()) {
		case "toggle":
			PlayerManager.toggle(p);
			break;
		case "farming":
			PlayerManager.toggle(p, OptionType.Farming);
			break;
		case "mining":
			PlayerManager.toggle(p, OptionType.Mining);
			break;
		case "logging":
			PlayerManager.toggle(p, OptionType.Logging);
			break; 
		case "about":
			p.sendMessage(ChatColor.YELLOW+SuperHarvest.getSuperConfig().about.replace("{0}", "Dru_TNT"));
			p.sendMessage(ChatColor.AQUA+"DC: 小千#3422");
			break;
		case "notify":
			PlayerManager.toggleNotify(p);
			break;		

		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return Arrays.asList("toggle","farming","mining","logging","about","notify");
	}

}
