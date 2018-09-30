package me.BlackKnight625.MegaSkyWars;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.BlackKnight625.DuringGame.Team;

@SuppressWarnings("unused")
public final class EventsHandler implements Listener {
	public EventsHandler(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
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
			if (p.hasMetadata("Killer") && Main.getMetadata(p, "Killer") != null) {
				int timeOfLastHit = (int) Main.getMetadata(p, "Age");
				int timeOfDeath = p.getTicksLived();
				int timeSinceLastHit = timeOfDeath - timeOfLastHit;
				if (timeSinceLastHit <= 30*20) {
					Utilities.setKiller((Player) Main.getMetadata(p, "Killer"), p);
				}
			}
		
	}

	@EventHandler
	public void entityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if (damager instanceof Player) {
			Player p = (Player) damager;
			if (Team.objectIsInATeam(damager)) {
				if (Team.objectIsInSameTeamAs(p, damaged)) {
					e.setCancelled(true);
				}
			}	
		}
		else if (damager instanceof Entity) {
			if (Team.objectIsInSameTeamAs(damaged, damager)) {
				e.setCancelled(true);
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
			Entity entity = e.getEntity();
			if (Team.objectIsInATeam(entity)) {
				Monster ent = (Monster) e.getEntity();
				Team team = Team.getTeamOfColor(Team.getTeamColorOfObject(ent));
				team.friendlyMobs.remove(ent);
			}
		}
	}

	@EventHandler
	public void projectileHitEvent(ProjectileHitEvent e) {
		if (e.getHitEntity() != null) {
			if (e.getEntity().getShooter() instanceof LivingEntity) {
				LivingEntity shooter = (LivingEntity) e.getEntity().getShooter();
				Entity shot = e.getHitEntity();
				if (Team.objectIsInSameTeamAs(shooter, shot)) {
					shot.setInvulnerable(true);
					new BukkitRunnable() {	
						@Override
						public void run() {
							shot.setInvulnerable(false);
						}
					}.runTaskLater(Main.plugin, 4);
				}
			} 
		}
	}
}
