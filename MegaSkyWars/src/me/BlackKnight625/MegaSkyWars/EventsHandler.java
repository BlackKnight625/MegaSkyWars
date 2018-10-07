package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import CustomItems.ResourceType;
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
			if (b.hasMetadata("Team") && Main.getMetadata(b, "Team") != null) {
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
		int amount = 1;
		Material m = b.getType();
		if (ResourceType.materialIsAResource(m)) {
			ResourceType type = ResourceType.getResourceFromMaterial(m);
			ItemStack drop;
			switch (type) {
			case ONYX_ORE:
				drop = Utilities.customResource(ResourceType.ONYX_GEM, amount);
				break;
			case PRISMARINE_ORE:
				drop = new ItemStack(Material.PRISMARINE_SHARD, amount);
				break;
			default:
				drop = Utilities.customResource(type, amount);
				break;
			}
			e.setDropItems(false);
			b.getWorld().dropItem(b.getLocation().add(0.5, 0.5, 0.5), drop);
		}
	}
	
	@EventHandler
	public void playerDieEvent(PlayerDeathEvent e) {
		Random rand = new Random();	
		Player p = e.getEntity();
		Entity k = null;
		if (p.hasMetadata("Killer") && Main.getMetadata(p, "Killer") != null) {
			int timeOfLastHit = (int) Main.getMetadata(p, "Age");
			int timeOfDeath = p.getTicksLived();
			int timeSinceLastHit = timeOfDeath - timeOfLastHit;			
			
			if (timeSinceLastHit <= 600) {
				k = (Player) Main.getMetadata(p, "Killer");
			}
		}
				int odds = rand.nextInt(1000) + 1;
				if (k != null) {
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
							if (p.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
								e.setDeathMessage("You thrown into the void by " + killer.getName() + ". 30% of your items"
										+ " have been placed in his team's community chest.");
								ArrayList<ItemStack> items = new ArrayList<ItemStack>();
								for (ItemStack item : e.getDrops()) {
									odds = rand.nextInt(100) + 1;
									if (odds <= 30) {
										items.add(item);
									}
								}
								if (!items.isEmpty()) {
									team.putItemsInsideCommunityChest(items);
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
			if (Team.objectIsInATeam(damager)) {
				
				if (Team.objectIsInSameTeamAs(p, damaged)) {
					e.setCancelled(true);
				}
				if (damaged instanceof Player) {
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							Utilities.setKiller((Player) damager, (Player) damaged);
							
						}
					}.runTaskLater(Main.plugin, 1);
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
	public void entityDamageEvent(EntityDamageEvent e) {
		if (e.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
			double damage = e.getDamage();
			damage = 0.4 * damage;
			e.setDamage(damage);
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

	@EventHandler
	public void furnaceSmelt(FurnaceSmeltEvent e) {
		Furnace fur = (Furnace) e.getBlock().getState();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if (fur.hasMetadata("Special Furnace") && Main.getMetadata(fur, "Special Furnace") != null) {
					ItemStack items = fur.getInventory().getResult();
					double tt = (double) Main.getMetadata(fur, "Special Furnace");
					int bonus = (int) tt;
					int amount = items.getAmount();
					if (amount + bonus <= 64) {
						items.setAmount(amount + bonus - 1);
						fur.getInventory().setResult(items);
					}
					else {
						int droppedBonus = amount + bonus - 65;
						items.setAmount(64);
						fur.getInventory().setResult(items);
						ItemStack droppedItems = items;
						droppedItems.setAmount(droppedBonus);
						fur.getWorld().dropItem(fur.getLocation().add(0, 1, 0), droppedItems);
					}
				}	
			}
		}.runTaskLater(Main.plugin, 0);	
	}
	@EventHandler
	public void furnaceBurn(FurnaceBurnEvent e) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Furnace fur = (Furnace) e.getBlock().getState();
				Bukkit.broadcastMessage("Initial burn time: " + fur.getBurnTime());
				int iBurn = fur.getBurnTime();
				int fBurn = iBurn/3;
				fur.setBurnTime((short) fBurn);
				fur.update();		
			}
		}.runTaskLater(Main.plugin, 0);
	}

	@EventHandler
	public void playerDropItems(PlayerDropItemEvent e) {
		Item item = e.getItemDrop();
		Player p = e.getPlayer();
		Main.setMetadata(item, "Owner", p);
		Main.setMetadata(item, "Age", item.getTicksLived());
		Material m = item.getItemStack().getType();
		
		if (ResourceType.materialIsAResource(m)) {
			ResourceType type = ResourceType.getResourceFromMaterial(m);
			if (type.equals(ResourceType.COPPER_INGOT) || type.equals(ResourceType.TIN_INGOT)) {
				
				new BukkitRunnable() {

					@Override
					public void run() {
						for (Block b : Utilities.getBlocksInRadius(item.getLocation().add(0, -1, 0).getBlock(), 3)) {
							if (b.getType().equals(Material.STONE_SLAB) && b.hasMetadata("Forge")) {
								ArrayList<Item> ciIngots = new ArrayList<>();
								int copperIngots = 0;
								int tinIngots = 0;
								if (!Utilities.getClosestEntitiesInRangeOfType(b.getLocation(), 3, EntityType.DROPPED_ITEM).isEmpty()) {
									for (Entity ent : Utilities.getClosestEntitiesInRangeOfType(b.getLocation(), 3, EntityType.DROPPED_ITEM)) {
										Item i = (Item) ent;
										if (ResourceType.materialIsAResource(i.getItemStack().getType())) {
											Material mi = i.getItemStack().getType();
											ResourceType ti = ResourceType.getResourceFromMaterial(mi);
											if (ti.equals(ResourceType.COPPER_INGOT)) {
												copperIngots = copperIngots + i.getItemStack().getAmount();
												ciIngots.add(i);
											} else if (ti.equals(ResourceType.TIN_INGOT)) {
												tinIngots = tinIngots + i.getItemStack().getAmount();
												ciIngots.add(i);
											}
										}
									}
									if (copperIngots >= 3 && tinIngots > 0) {
										while (copperIngots >= 3) {
											if (tinIngots > 0) {
												tinIngots--;
												copperIngots = copperIngots - 3;
												
												Item bronze = b.getWorld().dropItem(b.getLocation().add(0.5, 1, 0.5),
														Utilities.customResource(ResourceType.BRONZE_INGOT, 4));
												Main.setMetadata(bronze, "Owner", p);
												Main.setMetadata(bronze, "Age", bronze.getTicksLived());
											}
											else {break;}
										}
										for (Item ingots : ciIngots) {
											ingots.remove();
										}
										if (copperIngots > 0) {
											Item copper = b.getWorld().dropItem(b.getLocation().add(0.5, 0.5, 0.5),
													Utilities.customResource(ResourceType.COPPER_INGOT, copperIngots));
											Main.setMetadata(copper, "Owner", p);
											Main.setMetadata(copper, "Age", copper.getTicksLived());
										}
										if (tinIngots > 0) {
											Item tin = b.getWorld().dropItem(b.getLocation().add(0.5, 0.5, 0.5),
													Utilities.customResource(ResourceType.TIN_INGOT, tinIngots));
											Main.setMetadata(tin, "Owner", p);
											Main.setMetadata(tin, "Age", tin.getTicksLived());
										}
										b.getWorld().spawnParticle(Particle.LAVA, b.getLocation().add(0, 1, 0), 20, 0.5,
												0.5, 0.5);
										b.getWorld().playSound(b.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
									} 
								}
							}
						}
					}
				}.runTaskLater(Main.plugin, 4);
			}
		}
	}
	@EventHandler
	public void entityPickItems(EntityPickupItemEvent e) {
		Item item = e.getItem();
		if (e.getEntity() instanceof Player) {
			Player picker = (Player) e.getEntity();
			if (item.hasMetadata("Owner") && Main.getMetadata(item, "Owner") != null) {
				Player dropper = (Player) Main.getMetadata(item, "Owner");
				if (!picker.getName().equalsIgnoreCase(dropper.getName())) {
					int iniAge = (int) Main.getMetadata(item, "Age");
					int curAge = item.getTicksLived();
					if ((curAge - iniAge) <= 80) {
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
