package rd.dru.nms;

import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Crops;

public class V1_8Handler implements NMSHandler{
	
	@Override
	public boolean breakBlock(Player p, Block b) {
		// TODO Auto-generated method stub
		return NMSHandler.super.breakBlock(p, b);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void crackBlock(Block b, Material type) {
		b.getWorld().playEffect(b.getLocation(), Effect.valueOf("TILE_BREAK"),  type.getId(),25);
	}
	
	@Override
	public void crackCrop(Block b, Material type) {
		crackBlock(b, Material.matchMaterial("CROPS"));
	}
	
	@Override
	public void playSound(Block b,NSound sound) {
		Sound e;
		switch(sound) {
		case Farm:
			e = Sound.valueOf("DIG_GRASS");
			break;
		case Tree:
			e = Sound.valueOf("DIG_WOOD");
			break;
		default:
			e = Sound.valueOf("DIG_STONE");
			break;
		}
		b.getWorld().playSound(b.getLocation(), e, 1, 1);
	}
	
	@Override
	public boolean canCropHarvest(Block b) {
		if(!(isCrop(b)))
			return false;
		return ((Crops)b.getState().getData()).getState().equals(CropState.RIPE);
	}
	
	@Override
	public boolean isCrop(Block b) {
		// TODO Auto-generated method stub
		return b.getState().getData() instanceof Crops;
	}
	
	@Override
	public boolean isFarmLnad(Block b) {
		// TODO Auto-generated method stub
		return b.getType().equals(Material.matchMaterial("SOIL"));
	}
	

}
