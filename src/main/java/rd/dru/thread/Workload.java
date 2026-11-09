package rd.dru.thread;

import org.bukkit.entity.Player;

public interface Workload {

	public Player player();
	/**
	 * 
	 * @return true if its done otherwise it will be reschedule
	 */
	public boolean compute();
}

