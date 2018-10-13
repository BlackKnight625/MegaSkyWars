package CustomItems;

import org.bukkit.Material;

public enum ToolTier {
	WOODEN_SWORD(Material.WOODEN_SWORD, 59, 4, 0),
	WOODEN_SPEAR(Material.TRIDENT, 59, 3.5, 249),
	WOODEN_AXE(Material.WOODEN_AXE, 59, 3, 0),
	WOODEN_PICKAXE(Material.WOODEN_PICKAXE, 59, 2, 0),
	WOODEN_SHOVEL(Material.WOODEN_SHOVEL, 59, 1, 0),
	
	GOLDEN_SWORD(Material.GOLDEN_SWORD, 32, 4, 0),
	GOLDEN_SPEAR(Material.TRIDENT, 32, 3.5, 248),
	GOLDEN_AXE(Material.GOLDEN_AXE, 32, 3, 0),
	GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE, 32, 2, 0),
	GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL, 32, 1, 0),
	
	TIN_SWORD(Material.GOLDEN_SWORD, 60, 4.5, 31),
	TIN_SPEAR(Material.TRIDENT, 60, 4, 247),
	TIN_AXE(Material.GOLDEN_AXE, 60, 3.5, 31),
	TIN_PICKAXE(Material.GOLDEN_PICKAXE, 60, 2.5, 31),
	TIN_SHOVEL(Material.GOLDEN_SHOVEL, 60, 1.5, 31),
	
	STONE_SWORD(Material.STONE_SWORD, 131, 5, 0),
	STONE_SPEAR(Material.TRIDENT, 131, 4.5, 246),
	STONE_AXE(Material.STONE_AXE, 131, 4, 0),
	STONE_PICKAXE(Material.STONE_PICKAXE, 131, 3, 0),
	STONE_SHOVEL(Material.STONE_SHOVEL, 131, 2, 0),
	
	COPPER_SWORD(Material.STONE_SWORD, 156, 5.2, 130),
	COPPER_SPEAR(Material.TRIDENT, 156, 4.7, 245),
	COPPER_AXE(Material.STONE_AXE, 156, 4.2, 130),
	COPPER_PICKAXE(Material.STONE_PICKAXE, 156, 3.2, 130),
	COPPER_SHOVEL(Material.STONE_SHOVEL, 156, 2.2, 130),
	
	PRISMARINE_SWORD(Material.STONE_SWORD, 198, 5.4, 129),
	PRISMARINE_SPEAR(Material.TRIDENT, 198, 4.9, 244),
	PRISMARINE_AXE(Material.STONE_AXE, 198, 4.4, 129),
	PRISMARINE_PICKAXE(Material.STONE_PICKAXE, 198, 3.4, 129),
	PRISMARINE_SHOVEL(Material.STONE_SHOVEL, 198, 2.4, 129),
	
	BRONZE_SWORD(Material.IRON_SWORD, 312, 5.7, 249),
	BRONZE_SPEAR(Material.TRIDENT, 312, 5.2, 243),
	BRONZE_AXE(Material.IRON_AXE, 312, 4.7, 249),
	BRONZE_PICKAXE(Material.IRON_PICKAXE, 312, 3.7, 249),
	BRONZE_SHOVEL(Material.IRON_SHOVEL, 312, 2.7, 249),
	
	IRON_SWORD(Material.IRON_SWORD, 250, 6, 0),
	IRON_SPEAR(Material.TRIDENT, 250, 5.5, 242),
	IRON_AXE(Material.IRON_AXE, 250, 5, 0),
	IRON_PICKAXE(Material.IRON_PICKAXE, 250, 4, 0),
	IRON_SHOVEL(Material.IRON_SHOVEL, 250, 3, 0),
	
	QUARTZ_SWORD(Material.IRON_SWORD, 188, 6.35, 248),
	QUARTZ_SPEAR(Material.TRIDENT, 188, 5.85, 241),
	QUARTZ_AXE(Material.IRON_AXE, 188, 5.35, 248),
	QUARTZ_PICKAXE(Material.IRON_PICKAXE, 188, 4.35, 248),
	QUARTZ_SHOVEL(Material.IRON_SHOVEL, 188, 3.35, 248),
	
	MITHRIL_SWORD(Material.DIAMOND_SWORD, 561, 6.5, 1000),
	MITHRIL_SPEAR(Material.TRIDENT, 561, 6, 240),
	MITHRIL_AXE(Material.DIAMOND_AXE, 561, 5.5, 1000),
	MITHRIL_PICKAXE(Material.DIAMOND_PICKAXE, 561, 4.5, 1000),
	MITHRIL_SHOVEL(Material.DIAMOND_SHOVEL, 561, 3.5, 1000),
	
	DIAMOND_SWORD(Material.DIAMOND_SWORD, 1561, 7, 0),
	DIAMOND_SPEAR(Material.TRIDENT, 1561, 6.5, 239),
	DIAMOND_AXE(Material.DIAMOND_AXE, 1561, 6, 0),
	DIAMOND_PICKAXE(Material.DIAMOND_PICKAXE, 1561, 5, 0),
	DIAMOND_SHOVEL(Material.DIAMOND_SHOVEL, 1561, 4, 0),
	
	ONYX_SWORD(Material.DIAMOND_SWORD, 2625, 7.8, 995),
	ONYX_SPEAR(Material.TRIDENT, 2625, 7.3, 238),
	ONYX_AXE(Material.DIAMOND_AXE, 2625, 6.8, 995),
	ONYX_PICKAXE(Material.DIAMOND_PICKAXE, 2625, 5.8, 995),
	ONYX_SHOVEL(Material.DIAMOND_SHOVEL, 2625, 4.8, 995),
	;
	
	
	
	ToolTier(Material material, int durability, double damage, int fakeDurability) {
		this.durability = durability;
		this.fakeDurability = fakeDurability;
		this.damage = damage;
		this.material = material;
	}
	int durability;
	int fakeDurability;
	double damage;
	Material material;
	
	public int getDurability() {
		return this.durability;
	}
	public int getFakeDurability() {
		return this.fakeDurability;
	}
	public double getDamage() {
		return this.damage;
	}
	public Material getMaterial() {
		return this.material;
	}
	public static boolean materialIsATool(Material mat) {
		for (ToolTier tier : ToolTier.values()) {
			if (tier.getMaterial().equals(mat)) {
				return true;
			}
		}
		return false;
	}
}
