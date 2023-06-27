package rd.dru.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
public class Lang {
	public String language;
	public boolean enableFarming,enableMining,enableLogging, actionBarNotify, titleBarNotify, defaultSneaking;
	public String toggle, toggleAll,toggleLang, farm, mine, log, enable, disable, about, perms, notify, notifyStatus,classicMode,sneakingMode;
	public Lang(String lang) {
		this.language = lang;
		File langF = new File("plugins/SuperHarvest/Langs/"+lang+".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(langF);
		switch(lang) {
		case "zh_tw":
			config.options().header("- 相關聲明\r\n"
					+ "\r\n"
					+ "請不要出售輝煌團隊開發之插件，或者出售插件功能，\r\n"
					+ "我們的插件都是無償提供給各伺服器使用，\r\n"
					+ "故我們開發這些插件並沒有任何的實質收益，\r\n"
					+ "\r\n"
					+ "故這類行為是會一定程度上降低我們的開發意願，\r\n"
					+ "也有可能會使我們有將免費開源插件變為付費閉源插件的想法，望理解。\r\n"
					+ "\r\n"
					+ "如有盈利需求，請聯絡我們：https://discord.gg/9c287zPpUZ\r\n"
					+ "\r\n"
					+ "- SuperHarvest 繁體中文語言設定檔案 - by. 小千、RiceChen_");
			
			enable = Config.color(config.getString("enable","§a開啟§r"));
			disable = Config.color(config.getString("disable","§c關閉§r"));
			notify = Config.color(config.getString("notify", "§e快速{0} §a已開啟§7。"));
			notifyStatus = Config.color(config.getString("toggleNotify", "§7狀態提示已 §r{0}§7！"));
			perms = Config.color(config.getString("permission","§c您沒有權限使用此指令！"));
			toggle = Config.color(config.getString("toggle", "§e快速{0} §7已§r{1}§7。"));
			toggleAll = Config.color(config.getString("toggleall", "§7SuperHarvest 已§e{0}§7。"));
			toggleLang = Config.color(config.getString("togglelang", "§7您已將 SuperHarvest 的語言設定為：§e{0} §7。"));
			farm = Config.color(config.getString("farming", "§e收割§r"));
			mine = Config.color(config.getString("mining", "§e挖礦§r"));
			log = Config.color(config.getString("logging", "§e伐木§r"));
			about = Config.color(config.getString("about","§7此插件由 §6{0} §7製作。"));
			
			classicMode = Config.color(config.getString("classic-mode","§7SuperHarvest 已開啟§6經典模式§7。"));
			sneakingMode = Config.color(config.getString("sneaking-mode","§7SuperHarvest 已開啟§e蹲下模式§7。"));
			break;
		case "zh_cn":
			config.options().header("# - 相关声明\r\n"
					+ "\r\n"
					+ "请不要出售辉煌团队开发之插件，或者出售插件功能，\r\n"
					+ "我们的插件都是无偿提供给各伺服器使用，\r\n"
					+ "故我们开发这些插件并没有任何的实质收益，\r\n"
					+ "\r\n"
					+ "故这类行为是会一定程度上降低我们的开发意愿，\r\n"
					+ "也有可能会使我们有将免费开源插件变为付费闭源插件的想法，望理解。\r\n"
					+ "\r\n"
					+ "如有盈利需求，请联络我们：https://discord.gg/9c287zPpUZ\r\n"
					+ "\r\n"
					+ "- SuperHarvest 简体中文语言设定档案 - by. 小千、RiceChen_\r\n"
					+ "");
			enable = Config.color(config.getString("enable","§a启用§r"));
			disable = Config.color(config.getString("disable","§c禁用§r"));
			notify = Config.color(config.getString("notify", "§e快速{0} §a已启用§7。"));
			notifyStatus = Config.color(config.getString("toggleNotify", "§7状态提示已 §r{0}§7！"));
			perms = Config.color(config.getString("permission","&c你没有权限使用此指令"));
			toggle = Config.color(config.getString("toggle", "§e快速{0} §7已§r{1}§7。"));
			toggleAll = Config.color(config.getString("toggleall", "§7SuperHarvest 已§e{0}§7。"));
			toggleLang = Config.color(config.getString("togglelang", "§7您已将 SuperHarvest 的语言设置为：§e{0} §7。"));
			farm = Config.color(config.getString("farming", "§e收割§r"));
			mine = Config.color(config.getString("mining", "§e挖矿§r"));
			log = Config.color(config.getString("logging", "§e伐木§r"));
			about = Config.color(config.getString("about","§7此插件由 §6{0} §7製作。"));
			
			classicMode = Config.color(config.getString("classic-mode","§7SuperHarvest 已启用§6经典模式§7。"));
			sneakingMode = Config.color(config.getString("sneaking-mode","§7SuperHarvest 已启用§e蹲下模式§7。"));
			break;
		default:

			enable = Config.color(config.getString("enable","&aenable§r"));
			disable = Config.color(config.getString("disable","&cdisable§r"));
			notify = Config.color(config.getString("notify", "§eFast {0} is now on"));
			notifyStatus = Config.color(config.getString("toggleNotify", "§7Status notify is now {0}!"));
			perms = Config.color(config.getString("permission","&cYou don't have enough permission to execute this command."));
			toggle = Config.color(config.getString("toggle", "§eFast {0} §ris now {1}"));
			toggleAll = Config.color(config.getString("toggleall", "§7SuperHarvest is now {0}"));
			toggleLang = Config.color(config.getString("togglelang", "§7The language is now {0}"));
			farm = Config.color(config.getString("farming", "§efarming§r"));
			mine = Config.color(config.getString("mining", "§emining§r"));
			log = Config.color(config.getString("logging", "§elogging§r"));
			about = Config.color(config.getString("about","§7This plugin is made by {0}"));
			
			classicMode = Config.color(config.getString("classic-mode","§7SuperHarvest is now on Classic mode."));
			sneakingMode = Config.color(config.getString("sneaking-mode","§7SuperHarvest is now on Sneaking mode."));
			break;
		}
		
		config.set("enable",enable);
		config.set("disable",disable);
		config.set("notify", notify);
		config.set("notifyStatus", notifyStatus);
		config.set("permission",perms);
		config.set("toggle", toggle);
		config.set("toggleall", toggleAll);
		config.set("togglelang", toggleLang);
		config.set("farming", farm);
		config.set("mining", mine);
		config.set("logging", log);
		config.set("about", about);
		config.set("classic-mode", classicMode);
		config.set("sneaking-mode", sneakingMode);
		
		try {
			config.save(langF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
