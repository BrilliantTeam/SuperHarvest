package rd.dru;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 
 * @author Dru_TNT
 *
 */
public class Config {
	File f;
	FileConfiguration config;
	List<String> disableWorlds;
	List<Material> customBreaks;
	public boolean enableFarming,enableMining,enableLogging;
	public String toggle, toggleAll, farm, mine, log, enable, disable, about, perms;
	public Config(File f) { 
		this.f = f;
		load();
		save();
	}
	
	/**
	 * Currently not done 
	 * @param world
	 * @return true if the world is vaild to run SuperHarvest
	 */
	public boolean isVaildWorld(World world) {
		return !disableWorlds.contains(world.getName());
	}
	
	/**
	 * Currently not done
	 * @param matertal id
	 * @return true if it can trigger SuperHarvest
	 */
	public boolean isCustomBreak(Material id) {
		return customBreaks.contains(id);
	}
	
	public static String color(String mes) {
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	
	public void load() {
		if(f.isDirectory())
			f.delete();
		if(!f.exists()) {
			try {
				new File("plugins/SuperHarvest").mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		config = YamlConfiguration.loadConfiguration(f);
		disableWorlds = config.getStringList("disable-worlds");
		enableFarming = config.getBoolean("eanble-farming",true);
		enableMining = config.getBoolean("eanble-mining",true);
		enableLogging = config.getBoolean("eanble-logging",true);
		
		
//		enable = config.getString("messages.eanble",ChatColor.GREEN+"開啟");
//		disable = config.getString("messages.disable",ChatColor.RED+"關閉");
//		perms = config.getString("messages.permission",ChatColor.RED+"你沒有足夠權限使用此指令");
//		toggle = config.getString("messages.toggle", "快速{0}已{1}");
//		toggleAll = config.getString("messages.toggleall", "快速收成已{0}");
//		farm = config.getString("messages.farming", "收割");
//		mine = config.getString("messages.mining", "挖礦");
//		log = config.getString("messages.logging", "伐木");
//		about = config.getString("messages.about"," 此插件由{0}製作");

		enable = config.getString("messages.eanble","&aenable");
		disable = config.getString("messages.disable","&cdisable");
		perms = config.getString("messages.permission","&cYou don't have enough permissino do execute this command");
		toggle = config.getString("messages.toggle", "Fast {0} is now {1}");
		toggleAll = config.getString("messages.toggleall", "SuperHarvest is now {0}");
		farm = config.getString("messages.farming", "farming");
		mine = config.getString("messages.mining", "mining");
		log = config.getString("messages.logging", "logging");
		about = config.getString("messages.about"," This plugin is made by {0}");
	
		
	}
	
	public void save() {
		try {
			config.set("disable-worlds", disableWorlds);
			config.set("eanble-farming",enableFarming);
			config.set("eanble-mining", enableMining);
			config.set("eanble-logging", enableLogging);
			
			
			config.set("messages.eanble",enable);
			config.set("messages.disable",disable);
			config.set("messages.permission",perms);
			config.set("messages.toggle", toggle);
			config.set("messages.toggleall", toggleAll);
			config.set("messages.farming", farm);
			config.set("messages.mining", mine);
			config.set("messages.logging", log);
			config.set("messages.about", about);
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}