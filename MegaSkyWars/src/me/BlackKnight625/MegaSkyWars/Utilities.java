package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.BlackKnight625.DuringGame.Team;
import me.BlackKnight625.DuringGame.TeamColor;


public class Utilities {
	
	public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 45) {
            return "W";
        } else if (45 <= rotation && rotation < 135) {
            return "N";           
        } else if (135 <= rotation && rotation < 225) {
            return "E";
        } else if (225 <= rotation && rotation < 315) {
            return "S";
        } else if (315 <= rotation && rotation < 360) {
            return "W";
        } else {
            return null;
        }
    }
	public static String getAproximateCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "W";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NW";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "N";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "NE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "E";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SE";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "S";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "SW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "W";
        } else {
            return null;
        }
    }
	public static String getDiagonalCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 90) {
            return "NW";
        } else if (90 <= rotation && rotation < 180) {
            return "NE";           
        } else if (180 <= rotation && rotation < 270) {
            return "SE";
        } else if (270 <= rotation && rotation < 360) {
            return "SW";
        } else {
            return null;
        }
    }
	
	public static ArrayList<Block> getStructure(Block block1, Block block2){
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
 
        minX = Math.min(block1.getX(), block2.getX());
        minZ = Math.min(block1.getZ(), block2.getZ());
        minY = Math.min(block1.getY(), block2.getY());
 
        maxX = Math.max(block1.getX(), block2.getX());
        maxZ = Math.max(block1.getZ(), block2.getZ());
        maxY = Math.max(block1.getY(), block2.getY());
 
        Location l = new Location(block1.getWorld(), minX, minY, minZ);
        l.getChunk().load();
        l = new Location(block1.getWorld(), maxX, minY, minZ);
        l.getChunk().load();
        l = new Location(block1.getWorld(), maxX, minY, maxZ);
        l.getChunk().load();
        l = new Location(block1.getWorld(), minX, minY, maxZ);
        l.getChunk().load();
        
        ArrayList<Block> blocks = new ArrayList<Block>();
 
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
 
                    Block b = block1.getWorld().getBlockAt(x, y, z);              
                    blocks.add(b);    
                    
                }
            }
        }
 
        return blocks;
 
    }
	public static void rotateBlock(Block b, String dir) {			
			if (b.getBlockData() instanceof Directional) {
				BlockFace face = BlockFace.NORTH;
				Directional bl = (Directional) b.getBlockData();
				BlockFace f = bl.getFacing();
				if (f.equals(BlockFace.SOUTH)) {
					switch (dir) {
					case "E":
						face = BlockFace.EAST;
						break;
					case "W":
						face = BlockFace.WEST;
						break;
					case "S":
						face = BlockFace.SOUTH;
						break;
					case "N":
						face = BlockFace.NORTH;
					}
				}
				else if (f.equals(BlockFace.WEST)) {
					switch (dir) {
					case "E":
						face = BlockFace.SOUTH;
						break;
					case "W":
						face = BlockFace.NORTH;
						break;
					case "S":
						face = BlockFace.WEST;
						break;
					case "N":
						face = BlockFace.EAST;
					}
				}
				else if (f.equals(BlockFace.NORTH)) {
					switch (dir) {
					case "E":
						face = BlockFace.WEST;
						break;
					case "W":
						face = BlockFace.EAST;
						break;
					case "S":
						face = BlockFace.NORTH;
						break;
					case "N":
						face = BlockFace.SOUTH;
					}
				}
				else if (f.equals(BlockFace.EAST)) {
					switch (dir) {
					case "E":
						face = BlockFace.NORTH;
						break;
					case "W":
						face = BlockFace.SOUTH;
						break;
					case "S":
						face = BlockFace.EAST;
						break;
					case "N":
						face = BlockFace.WEST;
					}
				}	
				else if (f.equals(BlockFace.UP)) {
					face = BlockFace.UP;
				}
				else if (f.equals(BlockFace.DOWN)) {
					face = BlockFace.DOWN;
				}
				
				bl.setFacing(face);
				b.setBlockData(bl);
			}
			else if (b.getBlockData() instanceof MultipleFacing) {
				MultipleFacing bl = (MultipleFacing) b.getBlockData();
				Set<BlockFace> f = bl.getFaces();
				BlockFace face = BlockFace.NORTH;
				ArrayList<BlockFace> faces = new ArrayList<BlockFace>();
				for (BlockFace fa : f) {
					bl.setFace(fa, false);
				}
				
				if (f.contains(BlockFace.SOUTH)) {
					switch (dir) {
					case "E":
						face = BlockFace.EAST;
						faces.add(face);
						break;
					case "W":
						face = BlockFace.WEST;
						faces.add(face);
						break;
					case "S":
						face = BlockFace.SOUTH;
						faces.add(face);
						break;
					case "N":
						face = BlockFace.NORTH;
						faces.add(face);
					}
				}
				if (f.contains(BlockFace.WEST)) {
					switch (dir) {
					case "E":
						face = BlockFace.SOUTH;
						faces.add(face);
						break;
					case "W":
						face = BlockFace.NORTH;
						faces.add(face);
						break;
					case "S":
						face = BlockFace.WEST;
						faces.add(face);
						break;
					case "N":
						face = BlockFace.EAST;
						faces.add(face);
					}
				}
				if (f.contains(BlockFace.NORTH)) {
					switch (dir) {
					case "E":
						face = BlockFace.WEST;
						faces.add(face);
						break;
					case "W":
						face = BlockFace.EAST;
						faces.add(face);
						break;
					case "S":
						face = BlockFace.NORTH;
						faces.add(face);
						break;
					case "N":
						face = BlockFace.SOUTH;
						faces.add(face);
					}
				}
				if (f.contains(BlockFace.EAST)) {
					switch (dir) {
					case "E":
						face = BlockFace.NORTH;
						faces.add(face);
						break;
					case "W":
						face = BlockFace.SOUTH;
						faces.add(face);
						break;
					case "S":
						face = BlockFace.EAST;
						faces.add(face);
						break;
					case "N":
						face = BlockFace.WEST;
						faces.add(face);
					}
				}	
				if (f.contains(BlockFace.UP)) {
					face = BlockFace.UP;
					faces.add(face);
				}
				if (f.contains(BlockFace.DOWN)) {
					face = BlockFace.DOWN;
					faces.add(face);
				}
				
				for (BlockFace fa : faces) {
					bl.setFace(fa, true);
				}
		
				b.setBlockData(bl);
			}
		
	}

	public static void createFriendlyTeamMob(Team team, EntityType type, Location loc) {
		Entity e = loc.getWorld().spawnEntity(loc, type);		
		Monster m = (Monster) e;
		Main.setMetadata(m, "Team", team.getTeamColor());
		m.setCanPickupItems(true);
		char name1 = team.getColor().charAt(0);
		String name2 = team.getColor().substring(1).toLowerCase();
		char name3 = m.getType().toString().charAt(0);
		String name4 = m.getType().toString().substring(1).toLowerCase();
		m.setCustomName(team.getChatColor() + "" + name1 + name2 + ": " + name3 + name4);
		team.friendlyMobs.add(m);
	}
	public static synchronized void refreshTargetOfFriendlyMob(Monster mob, Team team) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Player player = getClosestEnemyPlayer(mob.getLocation(), team, 20);
				LivingEntity entity = getClosestEnemyLivingEntity(mob.getLocation(), team, 20);
				
				if (entity != null) {
					mob.setTarget(entity);
				}
				else if (player != null && (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.ADVENTURE))) {
					mob.setTarget(player);
				}
				else {
					mob.setTarget(null);
				}			
			}
		}.runTaskLater(Main.plugin, 10/Main.friendlyMonsterCount);	
	}
	
	public static Player getClosestPlayer(Location l, double radius) {
		double closest = radius;
		Player closestp = null;
		for(Player i : Bukkit.getOnlinePlayers()){
			double dist = i.getLocation().distance(l);
			if (closest == Double.MAX_VALUE || dist < closest){
				closest = dist;
				closestp = i;
	  	}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	public static Player getClosestEnemyPlayer(Location l, Team team, double radius) {
		TeamColor color = team.getTeamColor();
		double closest = radius;
		Player closestp = null;
		for(Player i : Bukkit.getOnlinePlayers()){
			if (Team.playerIsInATeam(i)) {
				double dist = i.getLocation().distance(l);
				if (closest == Double.MAX_VALUE || dist < closest) {
					if (!Team.getTeamOfPlayer(i).getTeamColor().equals(color)) {
						closest = dist;
						closestp = i;
					}
				} 
			}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	public static Player getClosestAllyPlayer(Location l, Team team, double radius) {
		TeamColor color = team.getTeamColor();
		double closest = radius;
		Player closestp = null;
		for(Player i : Bukkit.getOnlinePlayers()){
			if (Team.playerIsInATeam(i)) {
				double dist = i.getLocation().distance(l);
				if (closest == Double.MAX_VALUE || dist < closest) {
					if (Team.getTeamOfPlayer(i).getTeamColor().equals(color) && dist != 0) {
						closest = dist;
						closestp = i;
					}
				} 
			}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	
	public static LivingEntity getClosestLivingEntity(Location l, double radius) {
		double closest = radius;
		LivingEntity closestp = null;
		for(LivingEntity i : l.getWorld().getLivingEntities()){
			if (!i.getType().equals(EntityType.PLAYER)) {
				double dist = i.getLocation().distance(l);
				if (closest == Double.MAX_VALUE || dist < closest) {
					closest = dist;
					closestp = i;
				} 
			}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	public static LivingEntity getClosestEnemyLivingEntity(Location l, Team team, double radius) {
		TeamColor color = team.getTeamColor();
		double closest = radius;
		LivingEntity closestp = null;
		for(LivingEntity i : l.getWorld().getLivingEntities()){
			if ((Team.objectIsInATeam(i) && !Team.getTeamColorOfObject(i).equals(color)) || !Team.objectIsInATeam(i) && !i.getType().equals(EntityType.PLAYER)) {
				double dist = i.getLocation().distance(l);
				if (closest == Double.MAX_VALUE || dist < closest) {
						closest = dist;
						closestp = i;
					
				} 
			}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	public static LivingEntity getClosestAllyLivingEntity(Location l, Team team, double radius) {
		TeamColor color = team.getTeamColor();
		double closest = radius;
		LivingEntity closestp = null;
		for(LivingEntity i : l.getWorld().getLivingEntities()){
			if (Team.objectIsInATeam(i) && !i.getType().equals(EntityType.PLAYER)) {
				double dist = i.getLocation().distance(l);
				if (closest == Double.MAX_VALUE || dist < closest) {
					if (Team.getTeamColorOfObject(i).equals(color)) {
						closest = dist;
						closestp = i;
					}
				} 
			}
		}
		if (closestp == null){
		  return null;
		}
		else{
		  return closestp;
		}
	}
	
	//Depends on the Player's necromancer level
	public static EntityType getEntityTypeDependingOnNecromancerLevel(int level) {
		EntityType e = EntityType.ZOMBIE;
		Random rand = new Random();
		int odds = rand.nextInt(1000) + 1;
		switch (level) {
		case 1:
			if (odds <= 500) {
				e = EntityType.SKELETON;
				break;
			}
		case 2:
			if (odds <= 400) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 600) {
				e = EntityType.SPIDER;
				break;
			}
		case 4:
			if (odds <= 350) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 550) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 650) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
		case 5:
			if (odds <= 350) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 450) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 550) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
			if (odds <= 650) {
				e = EntityType.GUARDIAN;
				break;
			}
		case 6:
			if (odds <= 350) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 420) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 490) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
			if (odds <= 590) {
				e = EntityType.GUARDIAN;
				break;
			}
			if (odds <= 650) {
				e = EntityType.VEX;
				break;
			}
		case 7:
			if (odds <= 350) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 410) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 470) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
			if (odds <= 530) {
				e = EntityType.GUARDIAN;
				break;
			}
			if (odds <= 590) {
				e = EntityType.VEX;
				break;
			}
			if (odds <= 650) {
				e = EntityType.BLAZE;
				break;
			}
		case 8:
			if (odds <= 350) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 400) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 450) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
			if (odds <= 500) {
				e = EntityType.GUARDIAN;
				break;
			}
			if (odds <= 550) {
				e = EntityType.VEX;
				break;
			}
			if (odds <= 600) {
				e = EntityType.BLAZE;
				break;
			}
			if (odds <= 650) {
				e = EntityType.WITHER_SKELETON;
				break;
			}
		case 9:
			if (odds <= 325) {
				e = EntityType.SKELETON;
				break;
			}
			if (odds <= 375) {
				e = EntityType.SPIDER;
				break;
			}
			if (odds <= 425) {
				e = EntityType.CAVE_SPIDER;
				break;
			}
			if (odds <= 475) {
				e = EntityType.GUARDIAN;
				break;
			}
			if (odds <= 525) {
				e = EntityType.VEX;
				break;
			}
			if (odds <= 575) {
				e = EntityType.BLAZE;
				break;
			}
			if (odds <= 625) {
				e = EntityType.WITHER_SKELETON;
				break;
			}
			if (odds <= 675) {
				e = EntityType.ENDERMAN;
				break;
			}
		}	
		return e;
	}

	public static void damageEnemy(Player damager, Player damaged, int damage, boolean trueDamage) {
		if (!Team.playerIsInSameTeamAs(damaged, damager)) {
			if (trueDamage) {
				double health = damaged.getHealth();
				health = health - damage;
				damaged.setHealth(health);
			}
			else {
				damaged.damage(damage);
			}
			setKiller(damager, damaged);
		}
	}
	public static void setKiller(Player killer, Player killed) {
			Main.setMetadata(killed, "Killer", killer);
			Main.setMetadata(killed, "Age", killed.getTicksLived());
			killed.damage(0.001, killer);
	}
}
