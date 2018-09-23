package me.BlackKnight625.MegaSkyWars;

import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.BlackKnight625.DuringGame.Team;

public final class EventsHandler implements Listener {
	public EventsHandler(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		@SuppressWarnings("unused")
		Team team = Team.getTeamOfPlayer(p);
		Block b = e.getBlock();
		
		
		
		if (Team.playerIsInATeam(p)) {
			if (b.hasMetadata("Team")) {
				if (Team.getTeamColorOfObject(b).equals(Team.getTeamOfPlayer(p).getTeamColor())) {
					e.setCancelled(true);
				}
				else if (b.hasMetadata("Spawner")) {
					Team blockTeam = Team.getTeamOfColor(Team.getTeamColorOfObject(b));
					blockTeam.monsterSpawners.remove(b);
					e.setDropItems(false);
				}
				else if (b.hasMetadata("Power Station")) {
					Team blockTeam = Team.getTeamOfColor(Team.getTeamColorOfObject(b));
					blockTeam.powerBlocks.remove(b);
					e.setDropItems(false);
				}
			}
		}
	}
	
	@EventHandler
	public void playerDieEvent(PlayerDeathEvent e) {
		Random rand = new Random();
		int odds = rand.nextInt(1000) + 1;
		if (e.getEntityType().equals(EntityType.PLAYER)) {
			Player p = e.getEntity();
			LivingEntity k = p.getKiller();
			if (k.getType().equals(EntityType.PLAYER)) {
				Player killer = (Player) k;
				if (Team.playerIsInATeam(p) && Team.playerIsInATeam(killer)) {
					Team team = Team.getTeamOfPlayer(killer);
					if (!team.monsterSpawners.isEmpty()) {
						for (Block b : team.monsterSpawners) {
							odds = rand.nextInt(100) + 1;
							double n = (double) Main.getMetadata(b, "Spawner");
							if (odds <= n) {
								EntityType etype = Utilities.getEntityTypeDependingOnNecromancerLevel(9);					
								Utilities.createFriendlyTeamMob(team, etype, b.getLocation().add(0, 1, 0));
							}
						} 
					}
				}
			}
		}
	}

	@EventHandler
	public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if (damager instanceof Player) {
			Player p = (Player) damager;
			if (Team.playerIsInATeam(p)) {
				if (Team.objectIsInATeam(damager)) {
					if (Team.objectIsInSameTeamAs(p, damaged)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void entityTargetEntityEvent(EntityTargetLivingEntityEvent e) {
		Entity ent = e.getEntity();
		Entity target = e.getTarget();
		
		if (target != null && ent != null) {
			if (Team.objectIsInATeam(ent) && Team.objectIsInATeam(target)) {
				if (Team.objectIsInSameTeamAs(ent, target)) {
					e.setCancelled(true);
				}
			} 
		}
	}
	
	@EventHandler
	public void entityDeathEvent(EntityDeathEvent e) {
		if (!e.getEntity().getType().equals(EntityType.PLAYER)) {
			Monster ent = (Monster) e.getEntity();
			if (Team.objectIsInATeam(ent)) {
				Team team = Team.getTeamOfColor(Team.getTeamColorOfObject(ent));
				team.friendlyMobs.remove(ent);
			}
		}
	}


}
