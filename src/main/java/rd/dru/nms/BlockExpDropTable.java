package rd.dru.nms;

import java.util.Random;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockExpDropTable {
	private static final Random r = new Random();
	private static int total=0, min = 0, add= 0;

	public static int getExpDrop(Block b,ItemStack tool) {
		switch(b.getDrops(tool).iterator().next().getType().toString()) {
		case "COAL":
			add = 2;
			break;
		case "DIAMOND": case "EMERALD":
			min = 3;
			add = 7;			
			break;
		case "LAPIS_LAZULI":
			min = 2;
			add = 5;
			break;
		case "QUARTZ":
			min = 2;
			add = 5;
			break;
		case "REDSTONE":
			min = 1;
			add = 5;
			break;
		case "GOLD_NUGGET":
			if(b.getType().toString().equals("NETHER_GOLD_ORE"))
				add=1;
			break;
		default:
			return 0;
		}
		return  min + r.nextInt(add);
	}
}
