package tokyo.nakanaka.bukkit;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import tokyo.nakanaka.Scheduler;

public class BukkitScheduler implements Scheduler{
	private JavaPlugin plugin;

	public BukkitScheduler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void scheduleLater(int tick, Runnable runnable) {
		new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskLater(this.plugin, tick);
	}
	
}
