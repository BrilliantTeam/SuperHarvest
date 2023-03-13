package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rd.dru.Helper;
import rd.dru.SuperHarvest;
import rd.dru.nms.NMSHandler.NSound;
import rd.dru.thread.Workload;

/**
 * 
 * @author Dru_TNT
 *
 */
public class CustomChainBreak implements Workload {
	

	Deque<Block> going = new ArrayDeque<>();
//	Array<Block> logs = new ArrayDeque<>();
	Material type;
	Player player;
	public CustomChainBreak(Player player, Block loc) {
		this.player = player;
		this.type = loc.getType();
		chains(loc);
	}
	
	private static int max = 12;
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null||going.isEmpty())
			return cancel();
			
			Block b = going.poll();
//			if(b.getType().equals(type)&&SuperHarvest.nms.breakBlock(player, b)&&isPickaxe()) {
//				chains(b);
//				SuperHarvest.nms.playSound(b, type);
//				SuperHarvest.nms.crackBlock(b, type);
//			} else
//				return cancel();
			
				
		
		return going.isEmpty();
	}

	private boolean cancel() {
		SuperHarvest.thread.cach.removeAll(going);
		return true;
	}
	
	private void chains(Block b) {
		SuperHarvest.thread.cach.remove(b);
		List<Block> sels = Helper.getNear(b).stream().filter(w->w.getType().equals(type)&&!going.contains(w)).collect(Collectors.toList());
		going.addAll(sels);
		
		SuperHarvest.thread.cach.addAll(sels);
		
	}
	
	private static boolean isPickaxe(ItemStack item) {
		return item.getType().toString().contains("_PICKAXE");
	}

}
