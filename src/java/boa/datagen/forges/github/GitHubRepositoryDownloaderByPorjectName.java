package boa.datagen.forges.github;

import java.io.File;

import boa.datagen.util.FileIO;

public class GitHubRepositoryDownloaderByPorjectName {
	
	private static String INPUT_NAMES_PATH;
	private static String OUTPUT_REPOS_PATH;
	private static int THREAD_NUM;
	private static boolean done = false;
	

	public static void main(String[] args) {
		
		if (args.length < 3) {
			System.out.println("args: INPUT_NAMES_PATH, OUTPUT_REPOS_PATH, THREAD_NUM");
		} else {
			INPUT_NAMES_PATH = args[0];
			OUTPUT_REPOS_PATH = args[1];
			THREAD_NUM = Integer.parseInt(args[2]);
			String input = FileIO.readFileContents(new File(INPUT_NAMES_PATH));
			String[] projectNames = input.split("\\r?\\n");
			
			DownloadWorker[] workers = new DownloadWorker[THREAD_NUM];
			Thread[] threads = new Thread[THREAD_NUM];
			for (int i = 0; i < THREAD_NUM; i++) {
				workers[i] = new DownloadWorker(i);
				threads[i] = new Thread(workers[i]);
				threads[i].start();
			}
			
			// assign tasks to workers
			for (String name : projectNames) {
				System.out.println(name);
				boolean assigned = false;
				while (!assigned) {
					for (int j = 0; j < THREAD_NUM; j++) {
						if (workers[j].isReady()) {
							workers[j].setName(name);
							workers[j].setReady(false);
							assigned = true;
							break;
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			// wait for all done
			for (int j = 0; j < THREAD_NUM; j++) {
				while (!workers[j].isReady())
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			
			setDone(true);
		}

	}
	
	synchronized static boolean getDone() {
		return GitHubRepositoryDownloaderByPorjectName.done;
	}
	
	synchronized static void setDone(boolean done) {
		GitHubRepositoryDownloaderByPorjectName.done = done;
	}

	private static class DownloadWorker implements Runnable {
		private int id;
		private String name;
		private boolean ready = true;
		
		public DownloadWorker(int id) {
			setId(id);
		}

		@Override
		public void run() {
			while (true) {
				
				while (isReady()) {
					if (getDone())
						break;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if (getDone())
					break;
				
				String projectName = getName();
				String url = "https://github.com/" + projectName + ".git";
				File gitDir = new File(OUTPUT_REPOS_PATH + "/" + projectName + "/.git");
				String[] args = { url, gitDir.getAbsolutePath() };
				try {
					RepositoryCloner.clone(args);
				} catch (Throwable t) {
					System.err.println(t);
					System.out.println("Error cloning " + url);
					setReady(true);
				}
				
				setReady(true);
			}
		}

		synchronized boolean isReady() {
			return this.ready;
		}

		synchronized void setReady(boolean ready) {
			this.ready = ready;
		}

		synchronized int getId() {
			return this.id;
		}
		
		synchronized void setId(int id) {
			this.id = id;
		}

		synchronized String getName() {
			return name;
		}

		synchronized void setName(String name) {
			this.name = name;
		}
	}

	
}
