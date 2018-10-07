package me.BlackKnight625.DuringGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Banner;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.block.data.Directional;
import org.bukkit.util.Vector;

import me.BlackKnight625.MegaSkyWars.Main;
import me.BlackKnight625.MegaSkyWars.Utilities;
//import sun.security.action.GetLongAction;

@SuppressWarnings({ "unused", "deprecation" })
public class Structure {
	

	
	private StructureType structureType;
	private Player builder;
	private TeamColor color;
	private Team team;
	private double buildTime = 1; //Defined in StructureType
	private boolean teamBuild = false; //Defined in StructureType
	private Material specialBlock = null; //Defined in handle
	private String specialBlockKey; //Defined in handle
	private double specialBlockValue; //Defined in handle
	private String specialBlockKey2; //Defined in handle
	private int specialBlockValue2; //Defined in handle
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<Block> scheduledBlocks = new ArrayList<Block>();
	private ArrayList<Location> scheduledBlocksLoc = new ArrayList<Location>();
	private int farAway = 1; //Defined in handle
	private int downwards = 0; //Defined in handle
	private boolean diagonalStructure = false; //Defined in handle
	
	public Structure(StructureType type, Player player) throws Exception {
		if (!Team.playerIsInATeam(player)) {
			throw new ArithmeticException("The Structure builder player does not belong in a team.");
		}
		structureType = type;
		builder = player;
		team = Team.getTeamOfPlayer(player);
		color = Team.getPlayerTeamColor(player);
		handleStructureType();
		
		build();
	}
	
	private void handleStructureType() {
		int x1 = 0;
		int y1 = 0;
		int z1 = 0;
		int x2 = 0;
		int y2 = 0;
		int z2 = 0;
		buildTime = structureType.buildTime();
		teamBuild = structureType.isTeamBuild();
		switch (structureType) {
		case ARCHER_TOWER_1:
			x1 = 12;
			y1 = 3;
			z1 = 9;
			
			x2 = 8;
			y2 = 11;
			z2 = 13;
			
			farAway = 2;
			downwards = 1;
			break;
		case ARCHER_TOWER_2:
			x1 = -3;
			y1 = 3;
			z1 = 7;
			
			x2 = 5;
			y2 = 10;
			z2 = 15;
			
			farAway = 1;
			downwards = 1;
			break;
		case ARCHER_TOWER_3:
			x1 = -16;
			y1 = 3;
			z1 = 7;
			
			x2 = -6;
			y2 = 14;
			z2 = 17;
			
			farAway = 1;
			downwards = 1;
			break;
		case FORGE:
			x1 = 5;
			y1 = 3;
			z1 = 18;
			
			x2 = 7;
			y2 = 6;
			z2 = 20;
			
			specialBlockKey = "Forge";
			specialBlockValue = 1;
			specialBlock = Material.STONE_SLAB;
			farAway = 1;
			downwards = 1;
			break;
		case LADDER:
			x1 = 26;
			y1 = 4;
			z1 = 5;
			
			x2 = 26;
			y2 = 11;
			z2 = 8;
			
			farAway = 0;
			downwards = 0;
			break;
		case NECROMANCER_PIT_1:
			x1 = 137;
			y1 = 4;
			z1 = 7;
			
			x2 = 143;
			y2 = 11;
			z2 = 14;
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Spawner";
			specialBlockKey2 = "Monster";
			specialBlockValue = 10;
			specialBlockValue2 = 1;
			specialBlock = Material.SPAWNER;
			break;
		case NECROMANCER_PIT_2:
			x1 = 146;
			y1 = 4;
			z1 = 6;
			
			x2 = 156;
			y2 = 14;
			z2 = 17;
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Spawner";
			specialBlockKey2 = "Monster";
			specialBlockValue = 11;
			specialBlockValue2 = 2;
			specialBlock = Material.SPAWNER;
			break;
		case NECROMANCER_PIT_3:
			x1 = 160;
			y1 = 4;
			z1 = 6;
			
			x2 = 170;
			y2 = 14;
			z2 = 17;
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Spawner";
			specialBlockKey2 = "Monster";
			specialBlockValue = 12;
			specialBlockValue2 = 3;
			specialBlock = Material.SPAWNER;
			break;
		case NECROMANCER_PIT_4:
			x1 = 175;
			y1 = 4;
			z1 = 6;
			
			x2 = 187;
			y2 = 18;
			z2 = 20;
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Spawner";
			specialBlockKey2 = "Monster";
			specialBlockValue = 13;
			specialBlockValue2 = 4;
			specialBlock = Material.SPAWNER;
			break;
		case NECROMANCER_PIT_5:
			x1 = 189;
			y1 = 4;
			z1 = 6;
			
			x2 = 207;
			y2 = 21;
			z2 = 21;
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Spawner";
			specialBlockKey2 = "Monster";
			specialBlockValue = 100;
			specialBlockValue2 = 5;
			specialBlock = Material.SPAWNER;
			break;
		case POWER_STATION_1:
			x1 = 101;
			y1 = 4;
			z1 = 6;
			
			x2 = 109;
			y2 = 19;
			z2 = 14;
			
			farAway = 0;
			downwards = 0;
			specialBlockKey = "Power Station";
			specialBlockValue = 1.2;
			specialBlock = Material.REDSTONE_BLOCK;
			break;
		case POWER_STATION_2:
			x1 = 113;
			y1 = 4;
			z1 = 6;
			
			x2 = 121;
			y2 = 20;
			z2 = 14;
			
			farAway = 0;
			downwards = 0;
			specialBlockKey = "Power Station";
			specialBlockValue = 1.25;
			specialBlock = Material.REDSTONE_BLOCK;
			break;
		case POWER_STATION_3:
			x1 = 124;
			y1 = 4;
			z1 = 4;
			
			x2 = 136;
			y2 = 24;
			z2 = 16;
			
			farAway = 0;
			downwards = 0;
			specialBlockKey = "Power Station";
			specialBlockValue = 1.3;
			specialBlock = Material.REDSTONE_BLOCK;
			break;
		case RAILWAY:
			x1 = 96;
			y1 = 4;
			z1 = 6;
			
			x2 = 98;
			y2 = 7;
			z2 = 22;
			
			farAway = 1;
			downwards = 3;
			break;
		case STAIRCASE:
			x1 = 24;
			y1 = 4;
			z1 = 6;
			
			x2 = 24;
			y2 = 16;
			z2 = 13;
			
			farAway = 0;
			downwards = 5;
			break;
		case STONE_BRIDGE:
			x1 = 16;
			y1 = 4;
			z1 = 7;
			
			x2 = 16;
			y2 = 6;
			z2 = 14;
			
			farAway = 1;
			downwards = 2;
			break;
		case STONE_BRIDGE_DIAGONAL:
			x1 = 10;
			y1 = 4;
			z1 = 17;
			
			x2 = 16;
			y2 = 6;
			z2 = 23;
			
			farAway = 1;
			downwards = 2;
			diagonalStructure = true;
			break;
		case TNT_CANNON_1:
			x1 = 70;
			y1 = 4;
			z1 = 5;
			
			x2 = 72;
			y2 = 7;
			z2 = 13;
			
			farAway = 0;
			downwards = 0;
			break;
		case TNT_CANNON_2:
			x1 = 74;
			y1 = 4;
			z1 = 5;
			
			x2 = 78;
			y2 = 8;
			z2 = 13;
			
			farAway = 0;
			downwards = 0;
			break;
		case TNT_CANNON_3:
			x1 = 89;
			y1 = 4;
			z1 = 6;
			
			x2 = 93;
			y2 = 9;
			z2 = 17;
			
			farAway = 0;
			downwards = 0;
			break;
		case TNT_CANNON_LOW:
			x1 = 83;
			y1 = 4;
			z1 = 3;
			
			x2 = 85;
			y2 = 10;
			z2 = 14;
			
			farAway = -3;
			downwards = 0;
			break;
		case WALL_5X5:
			x1 = 29;
			y1 = 4;
			z1 = 5;
			
			x2 = 33;
			y2 = 10;
			z2 = 9;
			
			specialBlock = Material.STONE_BRICKS;
			farAway = 0;
			downwards = 0;
			break;
		case WALL_7X5:
			x1 = 35;
			y1 = 4;
			z1 = 5;
			
			x2 = 41;
			y2 = 10;
			z2 = 9;
			
			specialBlock = Material.STONE_BRICKS;
			farAway = 0;
			downwards = 0;
			break;
		case WALL_7X6:
			x1 = 43;
			y1 = 4;
			z1 = 5;
			
			x2 = 49;
			y2 = 11;
			z2 = 9;
			
			specialBlock = Material.STONE_BRICKS;
			farAway = 0;
			downwards = 0;
			break;
		case WALL_7X7:
			x1 = 51;
			y1 = 4;
			z1 = 5;
			
			x2 = 57;
			y2 = 12;
			z2 = 9;
			
			specialBlock = Material.STONE_BRICKS;
			farAway = 0;
			downwards = 0;
			break;
		case WALL_9X7:
			x1 = 59;
			y1 = 4;
			z1 = 5;
			
			x2 = 67;
			y2 = 12;
			z2 = 9;
			
			specialBlock = Material.STONE_BRICKS;
			farAway = 0;
			downwards = 0;
			break;
		case WOODEN_BRIDGE:
			x1 = 18;
			y1 = 4;
			z1 = 7;
			
			x2 = 18;
			y2 = 6;
			z2 = 14;
			
			
			farAway = 1;
			downwards = 2;
			break;
		case WOODEN_BRIDGE_3X3:
			x1 = 19;
			y1 = 4;
			z1 = 6;
			
			x2 = 23;
			y2 = 6;
			z2 = 14;
			
			
			farAway = 0;
			downwards = 2;
			break;
		case WOODEN_BRIDGE_DIAGONAL:
			x1 = 18;
			y1 = 4;
			z1 = 17;
			
			x2 = 24;
			y2 = 6;
			z2 = 23;
			
			
			farAway = 1;
			downwards = 2;
			diagonalStructure = true;
			break;
		case DO_NOT_USE_TESTS_ONLY:
			x1 = -140;
			y1 = 51;
			z1 = -41;
			
			x2 = -58;
			y2 = 87;
			z2 = 26;
			
			
			farAway = 5;
			downwards = 0;
			break;
		case SPECIAL_FURNACE:
			x1 = 211;
			y1 = 4;
			z1 = 6;
			
			x2 = 215;
			y2 = 7;
			z2 = 10;
			
			
			farAway = 1;
			downwards = 0;
			specialBlockKey = "Special Furnace";
			specialBlockValue = 3;
			specialBlock = Material.FURNACE;
			break;
		}
		Block block1 = Bukkit.getWorld("Mega").getBlockAt(new Location(builder.getWorld(), x1, y1, z1));
		Block block2 = Bukkit.getWorld("Mega").getBlockAt(new Location(builder.getWorld(), x2, y2, z2));
		blocks = Utilities.getStructure(block1, block2);
		
	}

	
	private void build() {
		String dir = Utilities.getCardinalDirection(builder);
		String dirDia = Utilities.getDiagonalCardinalDirection(builder);
		int x = 0, y = 0, z = 0, relX = 0, relY = 0, relZ = 0;
		int playerX = builder.getLocation().getBlockX();
		int playerY = builder.getLocation().getBlockY();
		int playerZ = builder.getLocation().getBlockZ();
		Vector moveAside;
		
		Block firstBlock = this.blocks.get(0);
		Block lastBlock = this.blocks.get(this.blocks.size()-1);
		
		moveAside = new Vector((firstBlock.getX()-lastBlock.getX())/2, 0, 0);
		
		for (Block b : this.blocks) {
			if (b.getType().equals(Material.AIR) || b.getType().equals(Material.STRUCTURE_BLOCK)) {
				continue;
			}
			
			relX = b.getX() -firstBlock.getX() + moveAside.getBlockX();
			relY = b.getY() -firstBlock.getY() - this.downwards;
			relZ = b.getZ() -firstBlock.getZ() + this.farAway;

			if (diagonalStructure) {
				relX = b.getX() -firstBlock.getX() + 1;
				switch (dirDia) {
				case "SE":
					x = relX + playerX;
					z = relZ + playerZ;
					break;
				case "SW":
					z = relX + playerZ;
					x = -relZ + playerX;
					break;
				case "NW":
					x = -relX + playerX;
					z = -relZ + playerZ;
					break;
				case "NE":
					z = -relX + playerZ;
					x = relZ + playerX;
					break;
				}
			} else {
				switch (dir) {
				case "S":
					x = relX + playerX;
					z = relZ + playerZ;
					break;
				case "W":
					z = relX + playerZ;
					x = -relZ + playerX;
					break;
				case "N":
					x = -relX + playerX;
					z = -relZ + playerZ;
					break;
				case "E":
					z = -relX + playerZ;
					x = relZ + playerX;
					break;
				}
			}
			
			
			y = relY + playerY;

			
			final Location l = new Location(builder.getWorld(), x, y, z);

			if (l.getBlock().getType().equals(Material.AIR)) {	
				
				
				scheduledBlocks.add(b);	
				scheduledBlocksLoc.add(l);
			}
		}
		placeBlocks(scheduledBlocks, scheduledBlocksLoc);
	}
	private void placeBlocks(ArrayList<Block> blocks, ArrayList<Location> locs) {
		Iterator<Block> itb = blocks.iterator();
		Iterator<Location> itl = locs.iterator();
		String dir = Utilities.getCardinalDirection(builder);
		new BukkitRunnable() {	
			@Override
			public void run() {
				if (!itb.hasNext()) {
					this.cancel();
				}
				else {
					Block block = itb.next();
					Location l = itl.next();
					Block b = l.getBlock();
					if (!(l.equals(builder.getLocation()) || l.equals(builder.getEyeLocation()))) {
						
					}
					
					b.setType(block.getType());
					b.setBlockData(block.getBlockData());
					
					if (b.getType().toString().contains("WOOL")) {
						b.setType(Material.valueOf(color.toString() + "_WOOL"));
					}
					else if (b.getType().toString().contains("CONCRETE")) {
						b.setType(Material.valueOf(color.toString() + "_CONCRETE"));
					}
					else if (b.getType().toString().contains("TERRACOTTA")) {
						b.setType(Material.valueOf(color.toString() + "_TERRACOTTA"));
					}
					else if (b.getType().toString().contains("BANNER")) {
						b.setType(Material.valueOf(color.toString() + "_BANNER"));
					}
					
					if (b.getType().equals(Material.DISPENSER)) {
						Dispenser d = (Dispenser) b.getState();
						ItemStack tnt = new ItemStack(Material.TNT, 3);;
						if (structureType.equals(StructureType.TNT_CANNON_3)) {
							tnt = new ItemStack(Material.TNT, 4);
						}
						else if (structureType.equals(StructureType.TNT_CANNON_LOW)) {
							tnt = new ItemStack(Material.TNT, 8);
						}
						d.getInventory().addItem(tnt);
					}
					
					Utilities.rotateBlock(b, dir);
					dealWithSpecialBlock(b);
					
					if (b.getType().toString().contains("FENCE")) {
						
					}
					
					b.getState().update();
					
					Sound sound = Sound.BLOCK_GRASS_STEP;
					
					Random rand = new Random();
					int sx = rand.nextInt(5) + 1;
					switch (sx) {
					case 1:
						sound = Sound.BLOCK_GRAVEL_STEP;
						break;
					case 2:
						sound = Sound.BLOCK_GLASS_STEP;
						break;
					case 3:
						sound = Sound.BLOCK_STONE_STEP;
						break;
					case 4:
						sound = Sound.BLOCK_WOOD_STEP;
						break;
					case 5:
						sound = Sound.BLOCK_METAL_STEP;
						break;
					}
					builder.getWorld().playSound(b.getLocation(), sound, 0.5f, 1f);
					builder.getWorld().spawnParticle(Particle.SPELL_INSTANT, b.getLocation(), 1);
				}			
			}
		}.runTaskTimer(Main.plugin, 1, (long) buildTime/scheduledBlocks.size()*20);
		
	}
	private void dealWithSpecialBlock(Block b) {
		if (b.getType().equals(specialBlock)) {
			Team.setTeamColorToObject(b, color);
			if (b.getType().equals(Material.SPAWNER)) {
				Main.setMetadata(b, specialBlockKey, specialBlockValue);
				Main.setMetadata(b, specialBlockKey2, specialBlockValue2);
				team.monsterSpawners.add(b);
			}
			else if (b.getType().equals(Material.REDSTONE_BLOCK)) {
				Main.setMetadata(b, specialBlockKey, specialBlockValue);
				team.powerBlocks.add(b);
			}
			else if (b.getType().equals(Material.FURNACE)) {
				Main.setMetadata(b, specialBlockKey, specialBlockValue);
				Main.setMetadata(b, "Owner", builder);
			}
			else if (b.getType().equals(Material.STONE_SLAB)) {
				Main.setMetadata(b, specialBlockKey, specialBlockValue);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (b.getType().equals(Material.STONE_SLAB)) {
							b.getWorld().spawnParticle(Particle.FLAME, b.getLocation(), 5, 0.5, 0.5, 0.5);
						}
						else {
							this.cancel();
						}		
					}
				}.runTaskTimer(Main.plugin, 0, 20);
			}
		}
	}
}
