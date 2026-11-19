package rd.dru.thread;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface Workload {

	public Player player();
	/**
	 * 
	 * @return true if its done otherwise it will be reschedule
	 */
	public boolean compute();

	public default boolean owns(Block block) {
		return Bukkit.isOwnedByCurrentRegion(block);
	}
}

