package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.annotation.PrivateAPI;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.Main;

@PrivateAPI
class BukkitClickBlockEventListener implements Listener{
	private Main main;
	private Scheduler scheduler;
	private Map<UUID, Boolean> activateRightMapNew = new HashMap<>();
	
	public BukkitClickBlockEventListener(Main main, Scheduler scheduler) {
		this.main = main;
		this.scheduler = scheduler;
	}
	
	@EventHandler
	public void onClickBlockEvent(PlayerInteractEvent evt0) {
		ClickBlockEvent evt;
		try{
			evt = new BukkitClickBlockEvent(evt0);
		}catch(IllegalArgumentException e) {
			return;
		}
		UUID uid = evt0.getPlayer().getUniqueId();
		Boolean canActivate = this.activateRightMapNew.get(uid);
		if(canActivate == null) {
			canActivate = true;
		}
		if(!canActivate) {
			return;
		}
		this.main.onClickBlockEvent(evt);
		this.activateRightMapNew.put(uid, false);
		this.scheduler.scheduleLater(1, () -> this.activateRightMapNew.put(uid, true));
	}

}
