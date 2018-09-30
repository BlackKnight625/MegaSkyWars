package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class OreGenerator {
	
	private Material material;
	private Material replacedBlock;
	private int minGeneration;
	private int maxGeneration;
	private int radius;
	private int ammount;
	private Location loc;
	
	public OreGenerator(OreType type, Material replacedBlock, int radius, Location location, int ammount) {
		this.material = type.getMaterial();
		this.replacedBlock = replacedBlock;
		this.minGeneration = type.minNrGeneration();
		this.maxGeneration = type.maxNrGeneration();
		this.radius = radius;
		this.ammount = ammount;
		this.loc = location;
		
		spawnOres();
	}
	
	private void spawnOres() {
		ArrayList<Block> blocks = new ArrayList<Block>();
		for (Block b : Utilities.getBlocksInRadius(radius, loc)) {
			if (b.getType().equals(replacedBlock)) {
				blocks.add(b);
			}
		}
		
		Collections.shuffle(blocks);
		ArrayList<Block> adjacent = new ArrayList<Block>();
		
		for (Block b : blocks) {
			Random rand = new Random();
			int gen = rand.nextInt((maxGeneration +  1) - minGeneration) + minGeneration;
			if (!b.getType().equals(replacedBlock)) {
				continue;
			}
			if (ammount > 0) {
				while (gen > 0) {
					if (ammount > 0) {			
						if (b.getType().equals(replacedBlock)) {
							b.setType(material);
							ammount--;
							gen--;
						}
						
						adjacent = Utilities.getAdjacentBlocksOfMaterial(replacedBlock, b);
						if (adjacent.isEmpty()) {
							break;
						}
						Collections.shuffle(adjacent);
						for (Block adj : adjacent) {
							Random random = new Random();
							boolean odds = random.nextBoolean();
							if (odds && ammount > 0 && gen > 0) {
								adj.setType(material);
								ammount--;
								gen--;
							}
							else {break;}
						}
						
						b = adjacent.get(0);		
					}
					else {break;}
				} 
			}
			else {break;}
		}
	}
}
