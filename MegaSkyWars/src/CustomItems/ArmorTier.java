package CustomItems;

import org.bukkit.Material;

public enum ArmorTier {
	LEATHER_HELMET(56, 1, 0, null, 56),
	LEATHER_CHESTPLATE(81, 3, 0, null, 81),
	LEATHER_LEGGINGS(76, 2, 0, null, 76),
	LEATHER_BOOTS(66, 1, 0, null, 66),
	
	TIN_HELMET(60, 1.2, 0, null, 50, 151, 171, 170),
	TIN_CHESTPLATE(90, 3.4, 0, null, 50, 151, 171, 170),
	TIN_LEGGINGS(84, 2.4, 0, null, 50, 151, 171, 170),
	TIN_BOOTS(72, 1.2, 0, null, 50, 151, 171, 170),
	
	GOLDEN_HELMET(78, 2, 0, null, 78),
	GOLDEN_CHESTPLATE(113, 5, 0, null, 113),
	GOLDEN_LEGGINGS(106, 3, 0, null, 106),
	GOLDEN_BOOTS(92, 1, 0, null, 92),
	
	CHAINMAIL_HELMET(166, 2, 0, null, 166),
	CHAINMAIL_CHESTPLATE(241, 5, 0, null, 241),
	CHAINMAIL_LEGGINGS(226, 4, 0, null, 226),
	CHAINMAIL_BOOTS(196, 1, 0, null, 196),
	
	COPPER_HELMET(152, 2.1, 0, null, 49, 255, 128, 0),
	COPPER_CHESTPLATE(224, 5.2, 0, null, 49, 255, 128, 0),
	COPPER_LEGGINGS(215, 4.1, 0, null, 49, 255, 128, 0),
	COPPER_BOOTS(186, 1, 0, null, 49, 255, 128, 0),
	
	BRONZE_HELMET(160, 2.2, 0, "Arrow Resistant", 48, 255, 179, 60),
	BRONZE_CHESTPLATE(235, 5.7, 0, "Arrow Resistant", 48, 255, 179, 60),
	BRONZE_LEGGINGS(220, 4.5, 0, "Arrow Resistant", 48, 255, 179, 60),
	BRONZE_BOOTS(190, 1.1, 0, "Arrow Resistant", 48, 255, 179, 60),
	
	QUARTZ_HELMET(144, 2.3, 0, null, 47, 243, 234, 234),
	QUARTZ_CHESTPLATE(210, 5.9, 0, null, 47, 243, 234, 234),
	QUARTZ_LEGGINGS(197, 4.6, 0, null, 47, 243, 234, 234),
	QUARTZ_BOOTS(160, 1.2, 0, null, 47, 243, 234, 234),
	
	IRON_HELMET(166, 2, 0, null, 166),
	IRON_CHESTPLATE(241, 6, 0, null, 241),
	IRON_LEGGINGS(226, 5, 0, null, 226),
	IRON_BOOTS(196, 2, 0, null, 196),
	
	PRISMARINE_HELMET(202, 2.3, 1, "Aqua Speed", 46, 97, 198, 198),
	PRISMARINE_CHESTPLATE(280, 6.5, 1, "Aqua Speed", 46, 97, 198, 198),
	PRISMARINE_LEGGINGS(242, 5.4, 1, "Aqua Speed", 46, 97, 198, 198),
	PRISMARINE_BOOTS(223, 2.3, 1, "Aqua Speed", 46, 97, 198, 198),
	
	MITHRIL_HELMET(310, 2.6, 0, "Swift", 45, 77, 139, 186),
	MITHRIL_CHESTPLATE(467, 7.2, 0, "Swift", 45, 77, 139, 186),
	MITHRIL_LEGGINGS(439, 5.6, 0, "Swift", 45, 77, 139, 186),
	MITHRIL_BOOTS(379, 2.6, 0, "Swift", 45, 77, 139, 186),
	
	DIAMOND_HELMET(364, 3, 2, null, 364),
	DIAMOND_CHESTPLATE(529, 8, 2, null, 529),
	DIAMOND_LEGGINGS(496, 6, 2, null, 496),
	DIAMOND_BOOTS(430, 3, 2, null, 430),
	
	ONYX_HELMET(721, 3.6, 0, null, 44, 51, 23, 23),
	ONYX_CHESTPLATE(877, 9.2, 0, null, 44, 51, 23, 23),
	ONYX_LEGGINGS(798, 7.2, 0, null, 44, 51, 23, 23),
	ONYX_BOOTS(761, 3.6, 0, null, 44, 51, 23, 23);
	
	ArmorTier(int durability, double protection, double toughness, String specialAtribute, int fakeDurability) {
		this.durability = durability;
		this.protection = protection;
		this.toughness = toughness;
		this.specialAtribute = specialAtribute;
		this.fakeDurability = fakeDurability;
	}
	ArmorTier(int durability, double protection, double toughness, String specialAtribute, int fakeDurability, int red
			, int green, int blue) {
		this.durability = durability;
		this.protection = protection;
		this.toughness = toughness;
		this.specialAtribute = specialAtribute;
		this.fakeDurability = fakeDurability;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	int durability;
	double protection;
	double toughness;
	String specialAtribute;
	int fakeDurability;
	int red;
	int green;
	int blue;
	
	public int getDurability() {
		return this.durability;
	}
	
	public double getProtection() {
		return this.protection;
	}

	public double getToughness() {
		return toughness;
	}

	public String getSpecialAtribute() {
		return specialAtribute;
	}
	public int getFakeDurability() {
		return this.fakeDurability;
	}
	public int getRed() {
		return this.red;
	}
	public int getGreen() {
		return this.green;
	}
	public int getBlue() {
		return this.blue;
	}
	public static boolean materialIsCustomArmor(Material mat) {
		for (ArmorTier tier : ArmorTier.values()) {
			if (tier.toString().equalsIgnoreCase(mat.name())) {
				return true;
			}
		}
		return false;
	}
}
