package rd.dru;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import rd.dru.PlayerManager.OptionType;
import rd.dru.nms.NMSHandler;
import rd.dru.thread.workload.CropBreaks;
import rd.dru.thread.workload.OreBreaks;
import rd.dru.thread.workload.TreeBreaks;

/**
 * 
 * @author Dru_TNT
 *
 */
public class EventManager implements Listener {
//	HashMap<Supplier<BlockBreakEvent>, Consumer<BlockBreakEvent>> events = new HashMap<>();
	
	public EventManager() {
		Bukkit.getPluginManager().registerEvents(this, SuperHarvest.getInstance());
		
	}
	
	/**
	 * Notify whether player has turn SuperHarvest on
	 * @param e of the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemHeld(PlayerItemHeldEvent e) {
		ItemStack i = e.getPlayer().getInventory().getItem(e.getNewSlot());
		if(i==null)
			return;
		String tool = i.getType().toString();
		if(!tool.contains("_"))
			return;
		Player p = e.getPlayer();
		OptionType type = null;
		switch(tool.substring(tool.indexOf("_")).toLowerCase()) {
		case "_hoe":
			type = OptionType.Farming;
			break;
		case "_pickaxe":
			type = OptionType.Mining;
			break;
		case "_axe":
			type = OptionType.Logging;
			break;
		}
		
		if(type!=null&&PlayerManager.isEnable(p, type)) {
			Config c = SuperHarvest.getSuperConfig();
			String message = ""+ChatColor.GREEN+c.notify.replace("{0}", Helper.trans(type));
			if(c.actionBarNotify)
				SuperHarvest.nms.actionBarMes(p, message);
			if(c.titleBarNotify) 
				SuperHarvest.nms.titleBarMes(p, message);
		}
			
	}
	
	/**
	 *	Active super break chains. 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.isCancelled())
			return;
		if(SuperHarvest.thread.cach.contains(e.getBlock())) {
			SuperHarvest.thread.cach.remove(e.getBlock());
			
			return;		
		}
		
		String type = e.getBlock().getType().toString(), tool = SuperHarvest.nms.getItemInHand(e.getPlayer()).getType().toString();
			//FARM
		Player p = e.getPlayer();
		if(SuperHarvest.getSuperConfig().enableFarming&&PlayerManager.isEnable(p, OptionType.Farming)
				&&tool.contains("_HOE")&&SuperHarvest.nms.isCrop(e.getBlock())) {
 
			SuperHarvest.thread.poll(new CropBreaks(e.getPlayer(), e.getBlock()));
		} else 

		//ORE
		if(SuperHarvest.getSuperConfig().enableMining&&PlayerManager.isEnable(p, OptionType.Mining)
				&&tool.contains("_PICKAXE")&&type.contains("_ORE")) {
			SuperHarvest.thread.poll(new OreBreaks(e.getPlayer(), e.getBlock()));
		} else 
			
		//AXE
		if(SuperHarvest.getSuperConfig().enableLogging&&PlayerManager.isEnable(p, OptionType.Logging)
				&&tool.contains("_AXE")&&
				(type.contains("LOG")||type.equals("WARPED_STEM")||type.equals("CRIMSON_STEM"))) 
		{
			SuperHarvest.thread.poll(new TreeBreaks(e.getPlayer(), e.getBlock()));	
		}
			
	
	}
}
