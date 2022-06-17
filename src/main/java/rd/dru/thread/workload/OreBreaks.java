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
import rd.dru.thread.Workload;

/**
 * 
 * @author Dru_TNT
 *
 */
public class OreBreaks implements Workload {
	

	Deque<Block> going = new ArrayDeque<>();
	Material type;
	Player player;
	
	public OreBreaks(Player player, Block loc) {
		this.player = player;
		this.type = loc.getType();
		chains(loc);
	}
	
	private static int max = 12;
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null||going.isEmpty())
			return true;
		
			Block b = going.poll();
			if(b.getType().equals(type)&&player.breakBlock(b)&&isPickaxe(player.getInventory().getItemInMainHand())) {
				chains(b);
				b.getWorld().playSound(b.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);		
				b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 25, 1, 0.1,
						0.1, 0.1, type.createBlockData());
			} 
		
		return going.isEmpty();
	}
	
	private void chains(Block b) {
		List<Block> sels = Helper.getNear(b).stream().filter(w->w.getType().equals(type)&&!going.contains(w)).collect(Collectors.toList());
		going.addAll(sels);

		SuperHarvest.thread.cach.addAll(sels);
	}
	
	private static boolean isPickaxe(ItemStack item) {
		return item.getType().toString().contains("_PICKAXE");
	}

}
