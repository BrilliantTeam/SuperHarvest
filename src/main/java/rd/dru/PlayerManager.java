package rd.dru;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import rd.dru.config.Config;
import rd.dru.config.Lang;

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
	
	public static boolean isSneakingMode(Player p) {
		return p.hasMetadata("enable-sneaking") ? p.getMetadata("enable-sneaking").get(0).asBoolean() :
			SuperHarvest.getSuperConfig().defaultSneaking;
	}
	
	public static boolean shouldNotify(Player p) {
		return !p.hasMetadata("disable-notify");
	}
	
	public static boolean toggle(Player p) {
		boolean on = p.hasMetadata("disable-"+OptionType.Farming);
		p.sendMessage(""+ChatColor.GREEN+getLang(p).toggleAll.replace("{0}", Helper.tranEnable(p, on)));
		
		
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
		p.sendMessage(""+ChatColor.GREEN+getLang(p).toggle.replace("{1}", Helper.tranEnable(p, on)).replace("{0}", Helper.trans(p, type)));

		set(p, type, on);
	
		return on;
	}

	public static void toggleNotify(Player p) {
		boolean on = p.hasMetadata("disable-notify");
		p.sendMessage(getLang(p).notifyStatus.replace("{0}", Helper.tranEnable(p, on)));
		if(!on)
			p.setMetadata("disable-notify",  new FixedMetadataValue(SuperHarvest.getInstance(), "disable"));
		else 
			p.removeMetadata("disable-notify", SuperHarvest.getInstance());
	}
	
	public static void toggleMode(Player p) {
		Config c = SuperHarvest.getSuperConfig();
		boolean on = p.hasMetadata("enable-sneaking") ? p.getMetadata("enable-sneaking").get(0).asBoolean() :
			c.defaultSneaking;
		
		p.sendMessage(!on ? getLang(p).sneakingMode : getLang(p).classicMode);
		p.setMetadata("enable-sneaking",  new FixedMetadataValue(SuperHarvest.getInstance(), !on));
	}
	
	public static void setLang(Player p, String lang) {
		if(lang.equals("default"))
			p.removeMetadata("language", SuperHarvest.getInstance());
		else
			p.setMetadata("language", new FixedMetadataValue(SuperHarvest.getInstance(), lang));
		p.sendMessage(getLang(p).toggleLang.replace("{0}", lang));		
	}
	
	public static Lang getLang(Player p) {
		return SuperHarvest.getSuperConfig().getLang(getLanguage(p));
	}
	
	private static String getLanguage(Player p) {
		if(p.hasMetadata("language"))
			return p.getMetadata("language").stream().filter(m->m.getOwningPlugin().equals(SuperHarvest.getInstance())).findFirst().get().asString();
		else if(SuperHarvest.getSuperConfig().autoLang)
			return p.getLocale();
		else
			return Config.language;
		}
	
	

	
}
