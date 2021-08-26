package tokyo.nakanaka.shapeGenerator.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tokyo.nakanaka.Scheduler;
import tokyo.nakanaka.event.ClickBlockEvent;
import tokyo.nakanaka.shapeGenerator.Main;

public class BukkitClickBlockEventListener implements Listener{
	private Main main;
	private Scheduler scheduler;
	private Map<UUID, Boolean> activateRightMapNew = new HashMap<>();
	
	public BukkitClickBlockEventListener(Main main, Scheduler scheduler) {
		this.main = main;
		this.scheduler = scheduler;
	}
	
	@EventHandler
	public void onLeftClickBlock(BlockBreakEvent evt0) {
		ClickBlockEvent evt = new BukkitClickBlockEvent(evt0);
		this.main.onClickBlockEvent(evt);
	}
	
	@EventHandler
	public void onRightClickBlock(PlayerInteractEvent evt0) {
		ClickBlockEvent evt = new BukkitClickBlockEvent(evt0);
		this.main.onClickBlockEvent(evt);
		UUID uid = evt0.getPlayer().getUniqueId();
		Boolean canActivate = this.activateRightMapNew.get(uid);
		if(canActivate == null) {
			canActivate = true;
		}
		if(!canActivate) {
			return;
		}
		this.activateRightMapNew.put(uid, false);
		this.scheduler.scheduleLater(1, () -> this.activateRightMapNew.put(uid, true));
	}

}
