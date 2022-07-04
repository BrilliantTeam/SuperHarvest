package rd.dru;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import rd.dru.nms.NMSHandler;
import rd.dru.nms.VersionChecker;
import rd.dru.thread.SafeThread;

/**
 * 
 * @author Dru_TNT
 *
 */
public class SuperHarvest extends JavaPlugin{
	private static SuperHarvest inst;
	public static SafeThread thread;
	private static Config config;
	public static NMSHandler nms;
	@Override
	public void onEnable() {
		inst = this;
		config = new Config(new File("plugins/SuperHarvest/config.yml"));
		nms = VersionChecker.getCurrentVersion().getNMS();
		thread = new SafeThread();
		thread.start();
		new EventManager();
		getCommand("superharvest").setExecutor(new Commands());
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("Disable ");
	}
	
	public static SuperHarvest getInstance() {
		return inst;
	}
	
	public static Config getSuperConfig() {
		return config;
	}
}
