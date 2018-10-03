package me.BlackKnight625.DuringGame;

public enum StructureType {
	FORGE (false, 3), 
	ARCHER_TOWER_1 (true, 5),
	ARCHER_TOWER_2 (true, 8),
	ARCHER_TOWER_3 (true, 10),
	STONE_BRIDGE (false, 2),
	STONE_BRIDGE_DIAGONAL (false, 2),
	WOODEN_BRIDGE (false, 2),
	WOODEN_BRIDGE_DIAGONAL (false, 2),
	WOODEN_BRIDGE_3X3 (false, 2),
	STAIRCASE (false, 2),
	LADDER (false, 2),
	WALL_5X5 (true, 5),
	WALL_7X5 (true, 5.5),
	WALL_7X6 (true, 6),
	WALL_7X7 (true, 6.5),
	WALL_9X7 (true, 7),
	TNT_CANNON_1 (false, 5),
	TNT_CANNON_2 (false, 10),
	TNT_CANNON_3 (false, 15),
	TNT_CANNON_LOW (false, 5),
	RAILWAY (false, 5),
	POWER_STATION_1 (true, 10),
	POWER_STATION_2 (true, 12.5),
	POWER_STATION_3 (true, 15),
	NECROMANCER_PIT_1 (true, 10),
	NECROMANCER_PIT_2 (true, 13),
	NECROMANCER_PIT_3 (true, 16),
	NECROMANCER_PIT_4 (true, 19),
	NECROMANCER_PIT_5 (true, 22),
	DO_NOT_USE_TESTS_ONLY (false, 10),
	SPECIAL_FURNACE (false, 2);
	
	private boolean teamBuild;
	private double buildTime;
	private int size = 0;
	
	StructureType(boolean teamBuild, double buildTime) {
		this.teamBuild = teamBuild;
		this.buildTime = buildTime;
	}
	StructureType(boolean teamBuild, double buildTime, int size) {
		this.teamBuild = teamBuild;
		this.buildTime = buildTime;
		this.size = size;
	}
	
	public boolean isTeamBuild() {
		return teamBuild;
	}
	public double buildTime() {
		return buildTime;
	}
	public int getSize() {
		return size;
	}
	public static String intoString() {
		String string = null;
		for (StructureType s : StructureType.values()) {
			string =  ((string == null) ? (" ") : (string + ", ")) + s;
		}
		return string;
	}
}
