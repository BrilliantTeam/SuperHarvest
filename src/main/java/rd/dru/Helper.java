package rd.dru;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
}
