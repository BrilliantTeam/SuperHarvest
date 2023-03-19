package rd.dru;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

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
		p.sendMessage(""+ChatColor.GREEN+c.toggleAll.replace("{0}", Helper.tranEnable(on)));
		
		
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
		p.sendMessage(""+ChatColor.GREEN+SuperHarvest.getSuperConfig().toggle.replace("{1}", Helper.tranEnable(on)).replace("{0}", Helper.trans(type)));

		set(p, type, on);
	
		return on;
	}

	public static void toggleNotify(Player p) {
		boolean on = p.hasMetadata("disable-notify");
		p.sendMessage(SuperHarvest.getSuperConfig().notifyStatus.replace("{0}", Helper.tranEnable(on)));
		if(!on)
			p.setMetadata("disable-notify",  new FixedMetadataValue(SuperHarvest.getInstance(), "disable"));
		else 
			p.removeMetadata("disable-notify", SuperHarvest.getInstance());
	}
	

	
}
