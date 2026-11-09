package rd.dru.thread;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
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
	
	private final boolean isFolia = isFolia();
	private boolean isFolia() {
	    try {
	        Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
	        return true;
	    } catch (ClassNotFoundException e) {
	        return false;
	    }
	}
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
			if(work==null)
				continue;
			if(isFolia) 
				work.player().getScheduler().run(SuperHarvest.getInstance(), t->{
					if(!work.compute()) 
						queue.add(work);
				}, new Runnable() {
					@Override
					public void run() {
						work.compute();
					}
				});
			else
				if(!work.compute())
					rego.add(work);		
			
		}
		queue.addAll(rego);
	
	}
	
	Task task;
	
	public void start() {
		if(!isFolia)
			task = new Task(Bukkit.getScheduler().runTaskTimer(SuperHarvest.getInstance(), this, 1, 1));
		else
			task = new Task(Bukkit.getAsyncScheduler().runAtFixedRate(SuperHarvest.getInstance(), t->run(),50l,50l,TimeUnit.MILLISECONDS));
	}
	
	public void stop() {
		if(task!=null)
			task.cancel();
	}

	//thx to youtube: Matej
	//link: https://youtu.be/JwSqUnncVk8?si=G1vHUGjkgQ4G_2Nn&t=148
	public static class Task {
		private Object foliaTask;
		private BukkitTask bukkitTask;
		
		Task(Object foliaTask) {
			this.foliaTask = foliaTask;
		}
		
		Task(BukkitTask foliaTask) {
			this.bukkitTask = foliaTask;
		}
		
		public void cancel() {
//			if(bukkitTask!=null)
//				bukkitTask.cancel();
//			else 
			if(foliaTask!=null)
				((ScheduledTask) foliaTask).cancel();
		}
	}
}
