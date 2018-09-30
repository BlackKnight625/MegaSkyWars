package me.BlackKnight625.MegaSkyWars;

import org.bukkit.Material;


public enum OreType {
	COAL(Material.COAL_ORE, true, false, 10, 15),
	COPPER(Material.DEAD_TUBE_CORAL_BLOCK, false, true, 12, 18),
	TIN(Material.DEAD_BUBBLE_CORAL_BLOCK, false, true, 6, 10),
	MITHRIL(Material.DEAD_HORN_CORAL_BLOCK, false, true, 4, 8),
	ONYX(Material.BLUE_ICE, true, true, 2, 5),
	PRISMARINE(Material.DEAD_BRAIN_CORAL_BLOCK, true, true, 8, 12),
	IRON(Material.IRON_ORE, false, false, 14, 20),
	GOLD(Material.GOLD_ORE, false, false, 18, 26),
	REDSTONE(Material.REDSTONE_ORE, true, false, 2, 4),
	LAPIS(Material.LAPIS_ORE, true, false, 5, 8),
	DIAMOND(Material.DIAMOND_ORE, true, false, 5, 10),
	EMERALD(Material.EMERALD_ORE, true, false, 4, 8);
	
	private Material material;
	private boolean isGemOre;
	private boolean isSpecial;
	private int minGeneration;
	private int maxGeneration;
	
	OreType(Material material, boolean isGemOre, boolean isSpecial, int minGeneration, int maxGeneration) {
		this.material = material;
		this.isGemOre = isGemOre;
		this.isSpecial = isSpecial;
		this.minGeneration = minGeneration;
		this.maxGeneration = maxGeneration;
	}

	public Material getMaterial() {
		return material;
	}
	public boolean isGemOre() {
		return this.isGemOre;
	}
	public boolean isSpecial() {
		return this.isSpecial;
	}
	public int minNrGeneration() {
		return this.minGeneration;
	}
	public int maxNrGeneration() {
		return this.maxGeneration;
	}
	
	public static String intoString() {
		String string = null;
		for (OreType s : OreType.values()) {
			string =  ((string == null) ? (" ") : (string + ", ")) + s;
		}
		return string;
	}
}
