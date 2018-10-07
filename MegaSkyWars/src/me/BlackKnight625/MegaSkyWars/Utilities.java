package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.BlackKnight625.DuringGame.Team;
import me.BlackKnight625.DuringGame.TeamColor;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.NBTTagDouble;
import net.minecraft.server.v1_13_R1.NBTTagInt;
import net.minecraft.server.v1_13_R1.NBTTagList;
import net.minecraft.server.v1_13_R1.NBTTagString;


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
	public static ArrayList<Block> getBlocksInRadius(int radius, Location location) {
		Location point1 = location.getBlock().getLocation().add(radius, radius, radius);
		Location point2 = location.getBlock().getLocation().add(-radius, -radius, -radius);
		ArrayList<Block> list = getStructure(point1.getBlock(), point2.getBlock());
		return list;
	}
	public static ArrayList<Block> getAdjacentBlocksOfMaterial(Material material, Block block) {
		ArrayList<Block> adjacent = new ArrayList<Block>();
		Location point1 = block.getLocation().add(1, 1, 1);
		Location point2 = block.getLocation().add(-1, -1, -1);
		for (Block b : getStructure(point1.getBlock(), point2.getBlock())) {
			if (b.getType().equals(material)) {
				adjacent.add(b);
			}
		}
		return adjacent;
	}
	public static ArrayList<Block> getAdjacentBlocks(Block block) {
		ArrayList<Block> adjacent = new ArrayList<Block>();
		Location point1 = block.getLocation().add(1, 1, 1);
		Location point2 = block.getLocation().add(-1, -1, -1);
		for (Block b : getStructure(point1.getBlock(), point2.getBlock())) {
				adjacent.add(b);		
		}
		return adjacent;
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
		try {
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
		} catch (ClassCastException e) {
			Bukkit.broadcastMessage("The mob that was trying to spawn is not an instance of Monster! As such, it will"
					+ " not be placed in a team");
		}
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
			if (((Team.objectIsInATeam(i) && !Team.getTeamColorOfObject(i).equals(color)) || !Team.objectIsInATeam(i)) && !i.getType().equals(EntityType.PLAYER)) {
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
				damaged.damage(damage, damager);
			}
			setKiller(damager, damaged);
		}
	}
	public static void setKiller(Player killer, Player killed) {
			Main.setMetadata(killed, "Killer", killer);
			Main.setMetadata(killed, "Age", killed.getTicksLived());
			killed.damage(0.001, killer);
	}
	
	public static ItemStack itemWithAddedAttribute(ItemStack item, Map<String, Double> nameAmount, String slot) {
		
		
		
		net.minecraft.server.v1_13_R1.ItemStack nmsStack;
        NBTTagCompound compound;
        NBTTagList modifiers;
        
        nmsStack = CraftItemStack.asNMSCopy(item);
		compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		modifiers = nmsStack.getEnchantments();
		
		Random rand = new Random();
        for (String name : nameAmount.keySet()) {
        	double amount = nameAmount.get(name);
			
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.set("AttributeName", new NBTTagString(name));
			tagCompound.set("Name", new NBTTagString(name));
			tagCompound.set("Amount", new NBTTagDouble((double)amount));
			tagCompound.set("Operation", new NBTTagInt(0));
			tagCompound.set("UUIDLeast", new NBTTagInt(rand.nextInt(100000) + 1));
			tagCompound.set("UUIDMost", new NBTTagInt(rand.nextInt(100000) + 1));
			tagCompound.set("Slot", new NBTTagString(slot));
			modifiers.add(tagCompound);
		}
		compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        item = CraftItemStack.asBukkitCopy(nmsStack);
		return item;
		//mainhand, offhand, head, chest, legs or feet
	}
	@SuppressWarnings("deprecation")
	public static ItemStack customArmor(ArmorTier tier) {	
		double protection = tier.getProtection();
		int durability = tier.getDurability();
		String special = tier.getSpecialAtribute();
		double toughness = tier.getToughness();
		ItemStack item;
		String slot = "head";

		int underline = tier.toString().indexOf("_");
		String tierF = tier.toString().substring(0, underline);
		String tierL = tier.toString().substring(underline + 1);
		
		if (tierF.equalsIgnoreCase("LEATHER") || tierF.equalsIgnoreCase("CHAINMAIL") || tierF.equalsIgnoreCase("IRON") || tierF.equalsIgnoreCase("GOLDEN") || tierF.equalsIgnoreCase("DIAMOND")) {
			item = new ItemStack(Material.valueOf(tier.toString()));
		}
		else {
			item = new ItemStack(Material.valueOf("DIAMOND_" + tierL));
		}
		switch (tierL) {
		case "HELMET":
			slot = "head";
			break;
		case "CHESTPLATE":
			slot = "chest";
			break;
		case "LEGGINGS":
			slot = "legs";
			break;
		case "BOOTS":
			slot = "feet";
			break;
		}
		
		java.util.Map<String, Double> map = new java.util.HashMap<>();
		map.put("generic.armor", protection);
		map.put("generic.armorToughness", toughness);
		ItemMeta meta = item.getItemMeta();
		String name1 = tierF.charAt(0) + tierF.substring(1).toLowerCase();
		String name2 = tierL.charAt(0) + tierL.substring(1).toLowerCase();
		
		
		ArrayList<String> list = new ArrayList<>();
		list.add(durability + "/" + durability);
		list.add(special);
		if (special != null) {
			switch (special) {
			case "Swift":
				map.put("generic.movementSpeed", 0.002);
			}
		}
		meta.setDisplayName(name1 + " " + name2);
		meta.setUnbreakable(true);
		item.setDurability((short) (item.getType().getMaxDurability() - durability));
		meta.setLore(list);
		item.setItemMeta(meta);
		item = Utilities.itemWithAddedAttribute(item, map, slot);
		return item;
	}
}
