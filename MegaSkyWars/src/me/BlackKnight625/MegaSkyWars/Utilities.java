package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;


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
        l = new Location(block1.getWorld(), maxX, minY, maxZ);
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
	public static void rotateBlock(Block b, Player builder) {
		String dir = Utilities.getCardinalDirection(builder);
		BlockFace face = BlockFace.NORTH;		
			if (b.getBlockData() instanceof Directional) {
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
				bl.setFacing(face);
				b.setBlockData(bl);
			}
		
	}
}
