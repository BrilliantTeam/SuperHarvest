package rd.dru.thread;

public interface Workload {

	/**
	 * 
	 * @return true if its done otherwise it will be reschedule
	 */
	public boolean compute();
}

