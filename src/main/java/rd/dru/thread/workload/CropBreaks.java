package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
public class CropBreaks implements Workload{

	HashSet<Block> lands = new HashSet<>();
	Deque<Block> going = new ArrayDeque<>();
	Player player;
	
	public CropBreaks(Player player, Block loc) {
		this.player = player;
//		Block farm = loc.getRelative(BlockFace.DOWN);
		lands.add(loc);
		going.add(loc);
		chains(loc);
	}
	
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null||going.isEmpty())
			return true;
			
			Block b = going.poll();
		
			if(canHarvest(b)) {
				SuperHarvest.nms.crackCrop(b, b.getType());
				SuperHarvest.nms.playSound(b, NSound.Farm);
				if(b.getWorld().equals(player.getWorld())&&SuperHarvest.nms.breakBlock(player, b)&&isHoe(SuperHarvest.nms.getItemInHand(player))) {
					chains(b);	
				} else 
					return cancel();
			
			}
		return going.isEmpty();
	}
	
	private boolean cancel() {
		SuperHarvest.thread.cach.removeAll(going.stream().map(bb->bb).collect(Collectors.toSet()));
		return true;
	}
	
	private void chains(Block b) {
		SuperHarvest.thread.cach.remove(b);
		List<Block> sels = Helper.getNear(b).stream().map(w->{
			if(SuperHarvest.nms.isFarmLnad(w)) {
				return w.getRelative(BlockFace.UP);
			} else 
				return w;
		}).filter(w->SuperHarvest.nms.isCrop(w)&&!lands.contains(w)&&!going.contains(w)&&!w.getType().equals(Material.SUGAR_CANE)||
				(w.getType().equals(Material.SUGAR_CANE)&&w.getY()==b.getY()&&!going.contains(w)))
				.collect(Collectors.toList());
		going.addAll(sels);
		lands.addAll(sels);
		SuperHarvest.thread.cach.addAll(sels);
	
	}
	
	private boolean canHarvest(Block b) {
		return SuperHarvest.nms.canCropHarvest(b)||b.getType().equals(Material.SUGAR_CANE);
	}
	
	private boolean isHoe(ItemStack item) {
		return item.getType().toString().contains("_HOE");
	}

	@Override
	public Player player() {
		// TODO Auto-generated method stub
		return player;
	}
	
}
