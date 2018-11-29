package cmtop.persistence.service;

import java.util.ArrayList;
import java.util.List;

public class MyThread extends Thread {

	private static boolean debugThreads = false;

	private static List<MyThread> runningThreadsList = new ArrayList<>();

	public synchronized static void addRunningThread(MyThread thread) {
		runningThreadsList.add(thread);
	}

	public synchronized static void removeRunningThread(MyThread thread) {
		runningThreadsList.remove(thread);
	}

	public synchronized static void setDebugThreadsEnabled(boolean debugThreads) {
		MyThread.debugThreads = debugThreads;
	}

	public synchronized static boolean isDebugThreadsEnabled() {
		return debugThreads;
	}

	private String name;

	public MyThread(Runnable runnable, String name) {
		super(runnable, name);
		this.name = name;
	}

	@Override
	public synchronized void start() {
		if (isDebugThreadsEnabled()) {
			addRunningThread(this);
			System.out.println("Thread started:  " + getId() + " - \"" + name + "\"");
		}

		super.start();

		if (isDebugThreadsEnabled()) {
			removeRunningThread(this);
			System.out.println("Thread finished: " + getId() + " - \"" + name + "\"");
		}
	}

	public String getMyName() {
		return name;
	}

}
