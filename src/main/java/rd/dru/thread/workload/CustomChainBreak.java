package rd.dru.thread.workload;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import rd.dru.Helper;
import rd.dru.SuperHarvest;
import rd.dru.thread.Workload;

/**
 * 
 * @author Dru_TNT
 *
 */
public class CustomChainBreak implements Workload{

	Deque<Block> going = new ArrayDeque<>();
	Material type;
	Player player;
	
	public CustomChainBreak(Player player, Block loc) {
		this.player = player;
		this.type = loc.getType();
		chains(loc);
	}
	
	@Override
	public boolean compute() {
		if(!player.isOnline()||player==null||going.isEmpty())
			return true;
		
			Block b = going.poll();
			if(b.getType().equals(type)&&SuperHarvest.nms.breakBlock(player, b))
				chains(b);
		
		return going.isEmpty();
	}
	
	private void chains(Block b) {
		List<Block> sels = Helper.getNear(b).stream().filter(w->w.getType().equals(type)).collect(Collectors.toList());
		going.addAll(sels);
		SuperHarvest.thread.cach.addAll(sels);
	}
	

}
