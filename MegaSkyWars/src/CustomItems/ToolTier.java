package CustomItems;

public enum ToolTier {
	WOODEN_AXE(100, 3);
	
	
	
	ToolTier(int durability, double damage) {
		this.durability = durability;
		this.damage = damage;
	}
	int durability;
	double damage;
}
