package rd.dru;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import rd.dru.PlayerManager.OptionType;

public class Helper {

	static Config c = SuperHarvest.getSuperConfig();
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
	
	public static String trans(OptionType type) {
		switch (type) {
		case Farming:
			return c.farm;
		case Logging:
			return c.log;
		case Mining:
			return c.mine;
		default:
			return "";
		}
	}
	
	public static String tranEnable(boolean enabled) {
		return enabled ? c.enable : c.disable;
	}
	
	private final List<String> stones =  Arrays.asList("STONE","DEEPSLATE","NETHERRACK","COBBLED_DEEPSLATE","GRAVEL","BLACKSTONE","GRANITE",
			"DIORITE", "ANDESITE", "CALCITE","TUFF","DRIPSTONE_BLOCK","DIRT","MAGMA_BLOCK","BASALT","BEDROCK");
	
    public boolean isstone(Block block){
    	return stones.contains(block.getType().name());
    	
    }


}
