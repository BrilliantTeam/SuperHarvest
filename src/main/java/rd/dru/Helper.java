package rd.dru;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import rd.dru.PlayerManager.OptionType;
import rd.dru.config.Config;

public class Helper {

	/**
	 * 
	 * @param center block
	 * @return near blocks, represent to 3*3*3-1 blocks
	 */
	public static Collection<Block> getNear(Block b) {
		HashSet<Block> bs = new HashSet<>();
		for(int x=-1;x<=1;x++)
			for(int y=-1;y<=1;y++)
				for(int z=-1;z<=1;z++) {
					if(x==0&&y==0&&z==0)
						continue;
					bs.add(new Location(b.getWorld(), x+b.getX(), y+b.getY(), z+b.getZ()).getBlock());			
				}
		return bs;
	}
	
	public static String trans(Player p, OptionType type) {
		switch (type) {
		case Farming:
			return PlayerManager.getLang(p).farm;
		case Logging:
			return PlayerManager.getLang(p).log;
		case Mining:
			return PlayerManager.getLang(p).mine;
		default:
			return "";
		}
	}
	
	public static String tranEnable(Player p,boolean enabled) {
		return enabled ? PlayerManager.getLang(p).enable : PlayerManager.getLang(p).disable;
	}
	
	private final List<String> stones =  Arrays.asList("STONE","DEEPSLATE","NETHERRACK","COBBLED_DEEPSLATE","GRAVEL","BLACKSTONE","GRANITE",
			"DIORITE", "ANDESITE", "CALCITE","TUFF","DRIPSTONE_BLOCK","DIRT","MAGMA_BLOCK","BASALT","BEDROCK");
	
    public boolean isstone(Block block){
    	return stones.contains(block.getType().name());
    	
    }


}
