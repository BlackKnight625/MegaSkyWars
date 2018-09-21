package me.BlackKnight625.DuringGame;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.block.data.Directional;
import org.bukkit.util.Vector;

import me.BlackKnight625.MegaSkyWars.Main;
import me.BlackKnight625.MegaSkyWars.Utilities;
import sun.security.action.GetLongAction;

@SuppressWarnings("unused")
public class Structure {
	
	private StructureType structureType;
	private Player builder;
	private TeamColor color;
	private Team team;
	private double buildTime = 1;
	private boolean teamBuild = false;
	private Block specialBlock = null;
	private String specialBlockKey;
	private int specialBlockValue;
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<Block> scheduledBlocks = new ArrayList<Block>();
	private ArrayList<Location> scheduledBlocksLoc = new ArrayList<Location>();
	private int farAway = 1;
	private int downwards = 1;
	private boolean diagonalStructure = false;
	
	public Structure(StructureType type, Player player) throws Exception {
		if (!Team.playerIsInATeam(player)) {
			throw new ArithmeticException("The Structure builder player does not belong in a team.");
		}
		structureType = type;
		builder = player;
		team = Team.getTeamOfPlayer(player);
		color = Team.getPlayerTeamColor(player);
		handleStructureType();
		if (specialBlock != null) {
			handleSpecialBlocks(specialBlockKey, specialBlockValue);
		}
		
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
			break;
		case ARCHER_TOWER_2:
			x1 = -3;
			y1 = 3;
			z1 = 7;
			
			x2 = 5;
			y2 = 10;
			z2 = 15;
			
			farAway = 2;
			break;
		case ARCHER_TOWER_3:
			break;
		case FORGE:
			break;
		case LADDER:
			break;
		case NECROMANCER_PIT_1:
			teamBuild = true;
			specialBlockKey = "Spawner";
			specialBlockValue = 10;
			break;
		case NECROMANCER_PIT_2:
			break;
		case NECROMANCER_PIT_3:
			break;
		case NECROMANCER_PIT_4:
			break;
		case NECROMANCER_PIT_5:
			break;
		case POWER_STATION_1:
			break;
		case POWER_STATION_2:
			break;
		case POWER_STATION_3:
			break;
		case RAILWAY:
			break;
		case STAIRCASE:
			break;
		case STONE_BRIDGE:
			break;
		case STONE_BRIDGGE_DIAGONAL:
			break;
		case TNT_CANNON_1:
			break;
		case TNT_CANNON_2:
			break;
		case TNT_CANNON_3:
			break;
		case TNT_CANNON_LOW:
			break;
		case WALL_5X5:
			break;
		case WALL_7X5:
			break;
		case WALL_7X6:
			break;
		case WALL_7X7:
			break;
		case WALL_9X7:
			break;
		case WOODEN_BRIDGE:
			break;
		case WOODEN_BRIDGE_3X3:
			break;
		case WOODEN_BRIDGE_DIAGONAL:
			break;
		}
		Block block1 = Bukkit.getWorld("Mega").getBlockAt(new Location(builder.getWorld(), x1, y1, z1));
		Block block2 = Bukkit.getWorld("Mega").getBlockAt(new Location(builder.getWorld(), x2, y2, z2));
		blocks = Utilities.getStructure(block1, block2);
		
	}
	
	
	
	
	
	private void handleSpecialBlocks(String key, int value) {
		
	}
	
	private void build() {
		String dir = Utilities.getCardinalDirection(builder);
		int x = 0, y = 0, z = 0, relX = 0, relY = 0, relZ = 0;
		int playerX = builder.getLocation().getBlockX();
		int playerY = builder.getLocation().getBlockY();
		int playerZ = builder.getLocation().getBlockZ();
		int taskID = 0;
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
					b.setType(block.getType());
					b.setBlockData(block.getBlockData());
					Utilities.rotateBlock(b, builder);
					
					builder.getWorld().playSound(b.getLocation(), Sound.BLOCK_GRAVEL_STEP, 0.5f, 1);
				}			
			}
		}.runTaskTimer(Main.plugin, 1, (long) buildTime/scheduledBlocks.size()*20);
		dealWithSpecialBlock();
	}
	private void dealWithSpecialBlock() {
		if (!specialBlock.equals(null)) {
			
		}
	}
}
