package CustomItems;

import org.bukkit.Material;

public enum ResourceType {
	COPPER_INGOT(Material.FERMENTED_SPIDER_EYE, "Copper Ingot", "Mix 3 Copper Ingots with 1 Tin Ingot inside a Forge to craft 4 Bronze Ingots"),
	TIN_INGOT(Material.RABBIT_HIDE, "Tin Ingot", "Mix 3 Copper Ingots with 1 Tin Ingot inside a Forge to craft 4 Bronze Ingots"),
	BRONZE_INGOT(Material.PHANTOM_MEMBRANE, "Bronze Ingot"),
	MITHRIL_INGOT(Material.GHAST_TEAR, "Mithril Ingot"),
	ONYX_GEM(Material.HEART_OF_THE_SEA, "Onyx"),
	COPPER_ORE(Material.DEAD_TUBE_CORAL_BLOCK, "Copper Ore"),
	TIN_ORE(Material.DEAD_BUBBLE_CORAL_BLOCK, "Tin Ore"),
	MITHRIL_ORE(Material.DEAD_HORN_CORAL_BLOCK, "Mithril Ore"),
	PRISMARINE_ORE(Material.DEAD_BRAIN_CORAL_BLOCK, "Prismarine Ore"),
	ONYX_ORE(Material.BLUE_ICE, "Onyx Ore")
	;
	ResourceType(Material material, String name, String lore) {
		this.material = material;
		this.name = name;
		this.lore = lore;
	}
	ResourceType(Material material, String name) {
		this.material = material;
		this.name = name;
	}
	Material material;
	String name;
	String lore = null;
	
	public Material getMaterial() {
		return this.material;
	}
	public String getName() {
		return this.name;
	}
	public boolean hasLore() {
		return lore != null;
	}
	public String getLore() {
		return lore;
	}
	public static boolean contains(String type) {
		for (ResourceType t : ResourceType.values()) {
			if (type.equalsIgnoreCase(t.toString())) {
				return true;
			}
		}
		return false;
	}
	public static boolean materialIsAResource(Material mat) {
		for (ResourceType type : ResourceType.values()) {
			if (type.getMaterial().equals(mat)) {
				return true;
			}
		}
		return false;
	}
	public static ResourceType getResourceFromMaterial(Material mat) {
		for (ResourceType type : ResourceType.values()) {
			if (type.getMaterial().equals(mat)) {
				return type;
			}
		}
		return null;
	}
}
