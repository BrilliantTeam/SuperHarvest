package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rd.dru.Helper;
import rd.dru.SuperHarvest;
import rd.dru.nms.NMSHandler.NSound;
import rd.dru.thread.Workload;
import rd.dru.utils.MaterialGroup;

/**
 * 
 * @author Dru_TNT
 *
 */
public class TreeBreaks implements Workload {

	Deque<Block> going = new ArrayDeque<>(), leaves = new ArrayDeque<>();
	MaterialGroup type;
	Player player;
	
	public TreeBreaks(Player player, Block loc) {
		this.player = player;
		this.type = new MaterialGroup(loc.getType());
		chains(loc);	
	}
	
	public TreeBreaks(Player player, Block loc, MaterialGroup mats) {
		this.player = player;
		this.type = mats;
		chains(loc);
		
	}
	
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null)
			return true;
		Block b;
		if(!going.isEmpty()) {
			b = going.poll();
			Material t = b.getType();
			if(type.contains(t)&&SuperHarvest.nms.breakBlock(player, b)&&isAxe(player.getItemInHand())) {
				chains(b);
				SuperHarvest.nms.crackBlock(b, t);
				SuperHarvest.nms.playSound(b, NSound.Tree);
			} else 
				return cancel();
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
	
	private boolean cancel() {
		SuperHarvest.thread.cach.removeAll(going);
		return true;
	}
	
	private void chains(Block b) {
		SuperHarvest.thread.cach.remove(b);
		Helper.getNear(b).stream().forEach(nb->{
			if(isLeaves(nb)&&!leaves.contains(nb)) {
				leaves.add(nb);
			} else if(type.contains(nb.getType())&&nb.getY()>=b.getY()&&!going.contains(nb)) {
				SuperHarvest.thread.cach.add(nb);
				going.add(nb);		
			}
		});
	}
	
	
	private boolean isLeaves(Block b) {
		return b.getType().toString().contains("LEAVES");
	}
	
	private static boolean isAxe(ItemStack item) {
		return item.getType().toString().contains("_AXE");
	}
}
