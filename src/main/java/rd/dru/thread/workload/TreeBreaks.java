package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
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
public class TreeBreaks implements Workload {

	Deque<Block> going = new ArrayDeque<>(), leaves = new ArrayDeque<>();
	Material type;
	Player player;
	
	public TreeBreaks(Player player, Block loc) {
		this.player = player;
		this.type = loc.getType();
		chains(loc);
	}
	
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null)
			return true;
		Block b;
		if(!going.isEmpty()) {
			b= going.poll();

			if(b.getType().equals(type)&&player.breakBlock(b)&&isAxe(player.getInventory().getItemInMainHand())) {
				chains(b);
				b.getWorld().playSound(b.getLocation(), Sound.BLOCK_WOOL_BREAK, 1, 1);		
				b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 25, 1, 0.1,
						0.1, 0.1, type.createBlockData());
			} 			
		} else for(int i=0;!leaves.isEmpty()&&i<6;i++) {
			b = leaves.poll();
			b.breakNaturally();
			chainsLeave(b);
		}
		return going.isEmpty()&&leaves.isEmpty();
	}
	
	int cl=0;
	private void chainsLeave(Block b) {
		cl++;
		if(cl>64) return;
		leaves.addAll(Helper.getNear(b).stream().filter(nb->isLeaves(nb)&&!leaves.contains(nb)).collect(Collectors.toList()));
	}
	
	private void chains(Block b) {
		
		Helper.getNear(b).stream().forEach(nb->{
			if(isLeaves(nb)&&!leaves.contains(nb)) {
				leaves.add(nb);
			} else if(nb.getType().equals(type)&&nb.getY()>=b.getY()&&!going.contains(nb)) {
				SuperHarvest.thread.cach.add(nb);
				going.add(nb);		
			}
		});
	}
	
	
	private boolean isLeaves(Block b) {
		return b.getType().toString().contains("_LEAVES");
	}
	
	private static boolean isAxe(ItemStack item) {
		return item.getType().toString().contains("_AXE");
	}
}
