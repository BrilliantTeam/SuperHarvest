package rd.dru.nms;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


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
//		net.minecraft.server.v1_8_R3.Block;
		event.setExpToDrop(BlockExpDropTable.getExpDrop(b, getItemInHand(p)));
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			ItemStack hand = p.getItemInHand();
			hand.setDurability((short) (hand.getDurability()+(short)1));
			if(hand.getDurability()>=hand.getType().getMaxDurability())
				return false;
			b.breakNaturally(hand);
			if(event.getExpToDrop()>0) {
				ExperienceOrb exp = (ExperienceOrb) b.getWorld().spawnEntity(b.getLocation().clone().add(0.5,0.5,0.5), EntityType.EXPERIENCE_ORB);
				exp.setVelocity(new Vector(0,0.2f,0));
				exp.setExperience(exp.getExperience() + event.getExpToDrop());		
			}
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
	public void playSound(Block b,Material sound);
	
	public boolean canCropHarvest(Block b);
	public boolean isFarmLnad(Block b);
	public boolean isCrop(Block b);
	public void actionBarMes(Player p, String mes);
	public void titleBarMes(Player p, String mes);
	

}
