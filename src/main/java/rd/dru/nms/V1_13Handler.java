package rd.dru.nms;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import rd.dru.SuperHarvest;
import rd.dru.nms.NMSHandler.NSound;

public class V1_13Handler implements NMSHandler{
	@Override
	public boolean breakBlock(Player p, Block b) {
		// TODO Auto-generated method stub
		return NMSHandler.super.breakBlock(p, b);
	}
	@Override
	public ItemStack getItemInHand(Player p) {
		// TODO Auto-generated method stub
		return p.getInventory().getItemInMainHand();
	}
	
	@Override
	public void crackBlock(Block b, Material type) {
		// TODO Auto-generated method stub
//		type.data;
//		MaterialData d = new MaterialData(type);
		b.getWorld().spawnParticle(Particle.BLOCK_CRACK, b.getLocation().add(0.5,0.5,0.5), 25, 1, 0.1,
				0.1, 0.1, type.createBlockData());
	} 
	
	@Override
	public void crackCrop(Block b, Material type) {
		if(type.equals(Material.NETHER_WART))
			crackBlock(b, Material.NETHER_WART);
		else
			crackBlock(b, Material.LEGACY_CROPS);
	}
	
	@Override
	public void playSound(Block b, NSound sound) {
		// TODO Auto-generated method stub
		Sound e;
		switch(sound) {
		case Farm:
			if(b.getType().equals(Material.NETHER_WART))
				e = Sound.BLOCK_STONE_BREAK;
			else
				e = Sound.BLOCK_GRASS_BREAK;
			break;
		case Ore:
			e = Sound.BLOCK_STONE_BREAK;
			break;
		case Tree:
			e = Sound.BLOCK_WOOD_BREAK;	
			break;
		default:
			e = Sound.BLOCK_GRASS_BREAK;
			break;
			
		}
		b.getWorld().playSound(b.getLocation(), e, 1, 1);	
	}
	
	@Override
	public void playSound(Block b, Material sound) {
		playSound(b, NSound.Ore);
	}
	
	@Override
	public boolean isCrop(Block b) {
		// TODO Auto-generated method stub
		return b.getBlockData() instanceof Ageable;
	}
	
	@Override
	public boolean isFarmLnad(Block b) {
		// TODO Auto-generated method stub
		return b.getType().equals(Material.FARMLAND)||b.getType().equals(Material.SOUL_SAND);
	}
	
	@Override
	public boolean canCropHarvest(Block b) {
		if(!(SuperHarvest.nms.isCrop(b)))
			return false;
		Ageable age = (Ageable)b.getBlockData();
		return age.getAge()== age.getMaximumAge();
		
	}
	@Override
	public void actionBarMes(Player p, String mes) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(mes));
	}

	@Override
	public void titleBarMes(Player p, String mes) {
		p.sendTitle(" ", mes, 0, 20, 10);
	}
}
