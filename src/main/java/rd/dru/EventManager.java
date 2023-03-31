package rd.dru;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import rd.dru.PlayerManager.OptionType;
import rd.dru.thread.workload.CropBreaks;
import rd.dru.thread.workload.OreBreaks;
import rd.dru.thread.workload.TreeBreaks;
import rd.dru.utils.MaterialGroup;

/**
 * 
 * @author Dru_TNT
 *
 */
public class EventManager implements Listener {

	public EventManager() {
		Bukkit.getPluginManager().registerEvents(this, SuperHarvest.getInstance());
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSneaking(PlayerToggleSneakEvent e) {
		if(e.isSneaking()&&PlayerManager.isSneakingMode(e.getPlayer()))
			notify(e.getPlayer());
	}
	
	/**
	 * Notify whether player has turn SuperHarvest on
	 * @param e of the event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemHeld(PlayerItemHeldEvent e) {
		if(!PlayerManager.isSneakingMode(e.getPlayer()))
			notify(e.getPlayer(), e.getNewSlot());
	}
	private boolean notify(Player p) {
		return notify(p, p.getInventory().getHeldItemSlot());
	}
	
	private boolean notify(Player p, int slot) {
		ItemStack i = p.getInventory().getItem(slot);
		if(i==null||!PlayerManager.shouldNotify(p))
			return false;
		String tool = i.getType().toString();
		if(!tool.contains("_"))
			return false;
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
			return true;
		}
		return false;
	}
	
	private static MaterialGroup speicalTrees = new MaterialGroup("MANGROVE_WOOD","MANGROVE_LOG","MANGROVE_ROOTS","WARPED_STEM","CRIMSON_STEM");
	
	/**
	 *	Active super break chains. 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.isCancelled())
			return;
		if(SuperHarvest.thread.cach.contains(e.getBlock())||(PlayerManager.isSneakingMode(e.getPlayer())&&!e.getPlayer().isSneaking())) {
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
				&&tool.contains("_AXE")) {
			if(type.contains("LOG")) {
				SuperHarvest.thread.poll(new TreeBreaks(e.getPlayer(), e.getBlock()));			
			} else if(speicalTrees.contains(e.getBlock().getType())) {
				SuperHarvest.thread.poll(new TreeBreaks(e.getPlayer(), e.getBlock(), speicalTrees));				
			}
		}
			
			
	
	}
}
