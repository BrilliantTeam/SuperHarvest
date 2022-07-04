package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
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
		chains(loc);
	}
	
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null||going.isEmpty())
			return true;

			Block b = going.poll().getRelative(BlockFace.UP);
			SuperHarvest.nms.crackCrop(b, b.getType());
//			b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 100, 0.1, 0.1,
//					0.1, 0.1, b.getType().createBlockData());
			
		
			if(canHarvest(b)) {
				if(SuperHarvest.nms.breakBlock(player, b)&&isHoe(SuperHarvest.nms.getItemInHand(player))) {
					SuperHarvest.nms.playSound(b, NSound.Farm);
					
//					b.getWorld().playSound(b.getLocation(), Sound.BLOCK_CROP_BREAK, 1, 1);		
					chains(b.getRelative(BlockFace.DOWN));
				} else 
					return cancel();
			
			}
		return going.isEmpty();
	}
	
	private boolean cancel() {
		SuperHarvest.thread.cach.removeAll(going);
		return true;
	}
	
	private void chains(Block b) {
		SuperHarvest.thread.cach.remove(b);
		List<Block> sels = Helper.getNear(b).stream().filter(w->{
			if(SuperHarvest.nms.isFarmLnad(w)&&!lands.contains(w)) {
				lands.add(w);
				if(lands.size()<64)
					return true;
				
			}
			return false;
		}).collect(Collectors.toList());
		going.addAll(sels);
		SuperHarvest.thread.cach.addAll(sels);
		
	}
	
	private boolean canHarvest(Block b) {
//		if(!(SuperHarvest.nms.isCrop(b)))
//			return false;
		return SuperHarvest.nms.canCropHarvest(b);
	}
	
	private boolean isHoe(ItemStack item) {
		return item.getType().toString().contains("_HOE");
	}
	
}
