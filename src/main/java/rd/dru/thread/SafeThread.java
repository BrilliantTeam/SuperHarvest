package rd.dru.thread;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import rd.dru.SuperHarvest;


/**
 * 
 * @author Dru_TNT
 *
 */
public class SafeThread implements Runnable{

	Deque<Workload> queue = new ArrayDeque<>();
	public HashSet<Block> cach = new HashSet<>();
	long limit = 3l;
	
	public SafeThread(long limit) {
		// TODO Auto-generated constructor stub
		this.limit = limit;
	}
	/**
	 * 
	 * @param poll some workload to run it later
	 */
	public synchronized void poll(Workload work) {
		queue.addLast(work);
	}
	
	@Override
	public void run() {
		long reach = System.currentTimeMillis()+limit;
		ArrayList<Workload> rego = new ArrayList<>();
		while(!queue.isEmpty()&&System.currentTimeMillis()<reach) {
			Workload work = queue.poll();
			if(!work.compute())
				rego.add(work);
		}
		queue.addAll(rego);
	
	}
	
	int taskID = -1;
	public void start() {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperHarvest.getInstance(), this, 1, 1);
	}
	
	public void stop() {
		if(taskID>0)
			Bukkit.getScheduler().cancelTask(taskID);
	}
	
}
