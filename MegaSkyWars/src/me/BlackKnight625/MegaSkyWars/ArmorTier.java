package me.BlackKnight625.MegaSkyWars;

public enum ArmorTier {
	/* LEATHER_HELMET(56, 1, 0, null),
	LEATHER_CHESTPLATE(81, 3, 0, null),
	LEATHER_LEGGINGS(76, 2, 0, null), */
	
	LEATHER(56, 81, 76, 66, 1, 3, 2, 1, 0, null),
	TIN(60, 90, 84, 72, 1.2, 3.4, 2.4, 1.2, 0, null),
	GOLD(78, 113, 106, 92, 2, 5, 3, 1, 0, null),
	CHAIN(166, 241, 226, 196, 2, 5, 4, 1, 0, null),
	COPPER(152, 224, 215, 186, 2.1, 5.2, 4.1, 1, 0, null),
	BRONZE(160, 235, 220, 190, 2.2, 5.7, 4.5, 1.1, 0, "Arrow resistant"),
	QUARTZ(144, 210, 197, 160, 2.3, 5.9, 4.6, 1.2, 0, null),
	IRON(166, 241, 226, 196, 2, 6, 5, 2, 0, null),
	PRISMARINE(202, 280, 242, 223, 2.3, 6.5, 5.4, 2.3, 1, "Aqua speed"),
	MITHRIL(310, 467, 439, 379, 2.6, 7.2, 5.6, 2.6, 0, null),
	DIAMOND(364, 529, 496, 430, 3, 8, 6, 3, 2, null),
	ONYX(721, 877, 798, 761, 3.6, 9.2, 7.2, 3.6, 0, null);
	
	ArmorTier(int helmetDurability, int chestplateDurability, int leggingsDurability, int bootsDurability,
			double helmetProtecion, double chestplateProtection, double leggingsProtection, 
			double bootsProtecion, double toughnessPerPiece, String specialAtribute) {
		this.helmetDurability = helmetDurability;
		this.chestplateDurability = chestplateDurability;
		this.leggingsDurability = leggingsDurability;
		this.bootsDurability = bootsDurability;
		this.helmetProtecion = helmetProtecion;
		this.chestplateProtection = chestplateProtection;
		this.leggingsProtection = leggingsProtection;
		this.bootsProtecion = bootsProtecion;
		this.toughnessPerPiece = toughnessPerPiece;
		this.specialAtribute = specialAtribute;
	}
	
	int helmetDurability;
	int chestplateDurability;
	int leggingsDurability;
	int bootsDurability;
	double helmetProtecion;
	double chestplateProtection;
	double leggingsProtection;
	double bootsProtecion;
	double toughnessPerPiece;
	String specialAtribute;
	
	public int getHelmetDurability() {
		return helmetDurability;
	}

	public int getChestplateDurability() {
		return chestplateDurability;
	}

	public int getLeggingsDurability() {
		return leggingsDurability;
	}

	public int getBootsDurability() {
		return bootsDurability;
	}

	public double getHelmetProtecion() {
		return helmetProtecion;
	}

	public double getChestplateProtection() {
		return chestplateProtection;
	}

	public double getLeggingsProtection() {
		return leggingsProtection;
	}

	public double getBootsProtecion() {
		return bootsProtecion;
	}

	public double getToughnessPerPiece() {
		return toughnessPerPiece;
	}

	public String getSpecialAtribute() {
		return specialAtribute;
	}
}
