package rd.dru.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
	public long bufferMs;
	public boolean enableFarming,enableMining,enableLogging, actionBarNotify, titleBarNotify, defaultSneaking, autoLang;
//	public String toggle, toggleAll, farm, mine, log, enable, disable, about, perms, notify, notifyStatus,classicMode,sneakingMode;
	
	public static String language;
	private static HashMap<String, Lang> langs = new HashMap<>();
	
	public Config(File f) { 
		this.f = f;
		load();
		save();
	}

	public Lang getLang(String lang) {
		if(langs.containsKey(lang))
			return langs.get(lang);
		else 
			return langs.get(language);
//		return langs.getOrDefault(lang, langs.get(language));
	}
	
	public Set<String> getLangs() {
		return langs.keySet();
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
		if(!f.exists()||!new File("plugins/SuperHarvest/Langs").exists()) {
			try {
				new File("plugins/SuperHarvest").mkdirs();
				new File("plugins/SuperHarvest/Langs").mkdirs();
				f.createNewFile();
				loadLang("zh_tw");
				loadLang("zh_cn");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		config = YamlConfiguration.loadConfiguration(f);
		disableWorlds = config.getStringList("disable-worlds");
		enableFarming = config.getBoolean("enable-farming",true);
		enableMining = config.getBoolean("enable-mining",true);
		enableLogging = config.getBoolean("enable-logging",true);
		actionBarNotify = config.getBoolean("notify-actionbar", true);
		titleBarNotify = config.getBoolean("notify-titlebar", false);
		defaultSneaking = config.getBoolean("default-sneaking-mode", false);
		bufferMs = config.getLong("cpu-mileseconds-limitation", 3);

		language = config.getString("default-language","en_us");
		loadLang(language);
		autoLang = config.getBoolean("auto-locale",true);
		Arrays.stream(new File("plugins/SuperHarvest/Langs").list()).forEach(s->loadLang(s.replace(".yml", "")));
	}
	
	public void save() {
		try {
			config.set("disable-worlds", disableWorlds);
			config.set("enable-farming",enableFarming);
			config.set("enable-mining", enableMining);
			config.set("enable-logging", enableLogging);
			
			config.set("messages", null);
			if(config.contains("eanble-farming")) {
				config.set("eanble-farming",null);
				config.set("eanble-mining", null);
				config.set("eanble-logging", null);
			}
			config.set("default-sneaking-mode", defaultSneaking);
			config.set("cpu-mileseconds-limitation", bufferMs);
			config.set("notify-actionbar", actionBarNotify);
			config.set("notify-titlebar", titleBarNotify);
			config.set("default-language", language);
			config.set("auto-locale", autoLang);
			config.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void loadLang(String dlang) {
		langs.put(dlang, new Lang(dlang));
	}

}