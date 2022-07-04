package rd.dru;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * 
 * @author Dru_TNT
 *
 */
public class PlayerManager {
	
	public enum OptionType {
		Farming,
		Mining,
		Logging
	}
	
	/**
	 * 
	 * @param player
	 * @param harvest type
	 * @return true if the player should trigger this harvest
	 */
	public static boolean isEnable(Player p,OptionType type) {
//		return true;
		return !p.hasMetadata("disable-"+type.toString())&&p.hasPermission("superharvest."+type.toString().toLowerCase());
	}
	
	public static boolean toggle(Player p) {
		boolean on = p.hasMetadata("disable-"+OptionType.Farming);
		Config c = SuperHarvest.getSuperConfig();
		p.sendMessage(""+ChatColor.GREEN+Config.color(c.toggleAll.replace("{0}", (on ?  c.enable: c.disable))));
		
		
		set(p, OptionType.Farming, on);
		set(p, OptionType.Mining, on);
		set(p, OptionType.Logging, on);
		
		return on;
	}
	
	public static void set(Player p, OptionType type, boolean on) {
		if(!on)
			p.setMetadata("disable-"+type.toString(), new FixedMetadataValue(SuperHarvest.getInstance(), "disable"));
		else 
			p.removeMetadata("disable-"+type.toString(), SuperHarvest.getInstance());
	}
	
	public static boolean toggle(Player p, OptionType type) {
		boolean on = p.hasMetadata("disable-"+type.toString());
		Config c = SuperHarvest.getSuperConfig();
		p.sendMessage(""+ChatColor.GREEN+Config.color(c.toggle.replace("{1}", (on ?  c.enable: c.disable)).replace("{0}", trans(type))));

		set(p, type, on);
	
		return on;
	}
	
	public static String trans(OptionType type) {
		Config c = SuperHarvest.getSuperConfig();
		switch (type) {
		case Farming:
			return c.farm;
		case Logging:
			return c.log;
		case Mining:
			return c.mine;
		default:
			return "";
		}
	}
	
}
