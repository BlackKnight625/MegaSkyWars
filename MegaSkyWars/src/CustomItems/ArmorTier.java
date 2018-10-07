package CustomItems;

public enum ArmorTier {
	LEATHER_HELMET(56, 1, 0, null),
	LEATHER_CHESTPLATE(81, 3, 0, null),
	LEATHER_LEGGINGS(76, 2, 0, null),
	LEATHER_BOOTS(66, 1, 0, null),
	
	TIN_HELMET(60, 1.2, 0, null),
	TIN_CHESTPLATE(90, 3.4, 0, null),
	TIN_LEGGINGS(84, 2.4, 0, null),
	TIN_BOOTS(72, 1.2, 0, null),
	
	GOLDEN_HELMET(78, 2, 0, null),
	GOLDEN_CHESTPLATE(113, 5, 0, null),
	GOLDEN_LEGGINGS(106, 3, 0, null),
	GOLDEN_BOOTS(92, 1, 0, null),
	
	CHAINMAIL_HELMET(166, 2, 0, null),
	CHAINMAIL_CHESTPLATE(241, 5, 0, null),
	CHAINMAIL_LEGGINGS(226, 4, 0, null),
	CHAINMAIL_BOOTS(196, 1, 0, null),
	
	COPPER_HELMET(152, 2.1, 0, null),
	COPPER_CHESTPLATE(224, 5.2, 0, null),
	COPPER_LEGGINGS(215, 4.1, 0, null),
	COPPER_BOOTS(186, 1, 0, null),
	
	BRONZE_HELMET(160, 2.2, 0, "Arrow Resistant"),
	BRONZE_CHESTPLATE(235, 5.7, 0, "Arrow Resistant"),
	BRONZE_LEGGINGS(220, 4.5, 0, "Arrow Resistant"),
	BRONZE_BOOTS(190, 1.1, 0, "Arrow Resistant"),
	
	QUARTZ_HELMET(144, 2.3, 0, null),
	QUARTZ_CHESTPLATE(210, 5.9, 0, null),
	QUARTZ_LEGGINGS(197, 4.6, 0, null),
	QUARTZ_BOOTS(160, 1.2, 0, null),
	
	IRON_HELMET(166, 2, 0, null),
	IRON_CHESTPLATE(241, 6, 0, null),
	IRON_LEGGINGS(226, 5, 0, null),
	IRON_BOOTS(196, 2, 0, null),
	
	PRISMARINE_HELMET(202, 2.3, 1, "Aqua Speed"),
	PRISMARINE_CHESTPLATE(280, 6.5, 1, "Aqua Speed"),
	PRISMARINE_LEGGINGS(242, 5.4, 1, "Aqua Speed"),
	PRISMARINE_BOOTS(223, 2.3, 1, "Aqua Speed"),
	
	MITHRIL_HELMET(310, 2.6, 0, "Swift"),
	MITHRIL_CHESTPLATE(467, 7.2, 0, "Swift"),
	MITHRIL_LEGGINGS(439, 5.6, 0, "Swift"),
	MITHRIL_BOOTS(379, 2.6, 0, "Swift"),
	
	DIAMOND_HELMET(364, 3, 2, null),
	DIAMOND_CHESTPLATE(529, 8, 2, null),
	DIAMOND_LEGGINGS(496, 6, 2, null),
	DIAMOND_BOOTS(430, 3, 2, null),
	
	ONYX_HELMET(721, 3.6, 0, null),
	ONYX_CHESTPLATE(877, 9.2, 0, null),
	ONYX_LEGGINGS(798, 7.2, 0, null),
	ONYX_BOOTS(761, 3.6, 0, null);
	
	//LEATHER(56, 81, 76, 66, 1, 3, 2, 1, 0, null),
	//TIN(60, 90, 84, 72, 1.2, 3.4, 2.4, 1.2, 0, null),
	//GOLD(78, 113, 106, 92, 2, 5, 3, 1, 0, null),
	//CHAIN(166, 241, 226, 196, 2, 5, 4, 1, 0, null),
	//COPPER(152, 224, 215, 186, 2.1, 5.2, 4.1, 1, 0, null),
	//BRONZE(160, 235, 220, 190, 2.2, 5.7, 4.5, 1.1, 0, "Arrow resistant"),
	//QUARTZ(144, 210, 197, 160, 2.3, 5.9, 4.6, 1.2, 0, null),
	//IRON(166, 241, 226, 196, 2, 6, 5, 2, 0, null),
	//PRISMARINE(202, 280, 242, 223, 2.3, 6.5, 5.4, 2.3, 1, "Aqua speed"),
	//MITHRIL(310, 467, 439, 379, 2.6, 7.2, 5.6, 2.6, 0, "Swift"),
	//DIAMOND(364, 529, 496, 430, 3, 8, 6, 3, 2, null),
	//ONYX(721, 877, 798, 761, 3.6, 9.2, 7.2, 3.6, 0, null);
	
	ArmorTier(int durability, double protection, double toughness, String specialAtribute) {
		this.durability = durability;
		this.protection = protection;
		this.toughness = toughness;
		this.specialAtribute = specialAtribute;
	}
	
	int durability;
	double protection;
	double toughness;
	String specialAtribute;
	
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
}
