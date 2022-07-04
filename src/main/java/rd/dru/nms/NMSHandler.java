package rd.dru.nms;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public interface NMSHandler {
		
	public enum NSound {
		Ore,
		Farm,
		Tree;
	}
	
	public static NMSHandler getNMSHandler() {
		return VersionChecker.getCurrentVersion().getNMS();
	}
	
	public default boolean breakBlock(Player p,Block b) {
		BlockBreakEvent event = new BlockBreakEvent(b, p);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			ItemStack hand = p.getItemInHand();
			hand.setDurability((short) (hand.getDurability()+(short)1));
			if(hand.getDurability()>=hand.getType().getMaxDurability())
				return false;
			b.breakNaturally(hand);
			
		}
		return !event.isCancelled();
	}
	
	public default ItemStack getItemInHand(Player p) {
		return p.getItemInHand();
	}
	
//	@SuppressWarnings("deprecation")
	public void crackBlock(Block b, Material type);
	public void crackCrop(Block b, Material type);
	
//	@SuppressWarnings("deprecation")
	public void playSound(Block b,NSound sound);
	
	public boolean canCropHarvest(Block b);
	public boolean isFarmLnad(Block b);
	public boolean isCrop(Block b);

	

}
