package me.BlackKnight625.DuringGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.scheduler.BukkitRunnable;

import me.BlackKnight625.MegaSkyWars.Main;
import me.BlackKnight625.MegaSkyWars.Utilities;

public class Team {
	
	
	
	private String teamColorString;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ChatColor chatColor;
	private TeamColor color;
	private ArrayList<Player> playersInATeam = new ArrayList<Player>();
	@SuppressWarnings("unused")
	private Location witherLocation;
	private Location communityChest = null;
	
	public ArrayList<Block> monsterSpawners = new ArrayList<Block>();
	public ArrayList<Block> powerBlocks = new ArrayList<Block>();
	public ArrayList<Monster> friendlyMobs = new ArrayList<Monster>();
	
	public ArrayList<Location> communityChests = new ArrayList<Location>();
	
	public static ArrayList<Team> teams = new ArrayList<Team>();
	
	public Team(TeamColor teamColor) {	
		this.teamColorString = teamColor.toString();
		this.color = teamColor;
		this.chatColor = getChatColorOfTeamColor(teamColor);
	}

	public int getSize() {
		return players.size();
	}
	public String getColor() {
		return teamColorString;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public TeamColor getTeamColor() {
		return color;
	}
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	@SuppressWarnings("deprecation")
	public void addPlayer(Player p) {
		players.add(p);
		playersInATeam.add(p);
		Main.setMetadata(p, "Team", color);
		Main.scoreboard.getTeam(teamColorString).addPlayer(p);
		p.sendMessage(ChatColor.RED + "You have entered the " + chatColor + ChatColor.BOLD + color + 
				ChatColor.RESET + ChatColor.RED +  " Team, which has " + players.size() + " players!");	
	}
	@SuppressWarnings("deprecation")
	public void addPlayers(Player... p) {
		for (Player player : p) {
			players.add(player);
			playersInATeam.add(player);
			Main.setMetadata(player, "Team", color);
			Main.scoreboard.getTeam(teamColorString).addPlayer(player);
			player.sendMessage(ChatColor.RED + "You have entered the " + chatColor + ChatColor.BOLD + color + 
					ChatColor.RESET + ChatColor.RED +  " Team, which has " + players.size() + " players!");
		}
	}
	@SuppressWarnings("deprecation")
	public void removePlayer(Player p) {
		players.remove(p);
		playersInATeam.remove(p);
		Main.removeMetadata(p, "Team");
		Main.scoreboard.getTeam(teamColorString).removePlayer(p);
	}
	@SuppressWarnings("deprecation")
	public void removePlayers(Player... p) {
		for (Player player : p) {
			players.remove(player);
			playersInATeam.remove(player);
			Main.removeMetadata(player, "Team");
			Main.scoreboard.getTeam(teamColorString).removePlayer(player);
		}
	}
	@SuppressWarnings("deprecation")
	public void removeAllPlayers() {
		for (Player p : players) {
			if (playersInATeam.contains(p)) {
				playersInATeam.remove(p);
			}
		}
		players.clear();
		Main.scoreboard.getTeam(color.toString()).getPlayers().clear();
	}
	public boolean hasPlayer(Player p) {
		for (Player player : players) {
			if (p.getName().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean playerIsInTeam(Player p, TeamColor color) {
		if (p.hasMetadata("Team") && Main.getMetadata(p, "Team") != null) {
			if (Main.getMetadata(p, "Team").equals(color)) {
				return true;
			}
			return false;
		}
		else {return false;}
	}
	public static boolean playerIsInATeam(Player p) {
		if (p.hasMetadata("Team") && Main.getMetadata(p, "Team") != null) {
			return true;
		}
		return false;
	}
	public static boolean playerIsInSameTeamAs(Player p1, Player p2) {
		if (p1.hasMetadata("Team") && Main.getMetadata(p1, "Team") != null && p2.hasMetadata("Team") && Main.getMetadata(p2, "Team") != null) {
			TeamColor c1 = (TeamColor) Main.getMetadata(p1, "Team");
			TeamColor c2 = (TeamColor) Main.getMetadata(p2, "Team");
			if (c1.equals(c2)) {
				return true;
			}
		}
		return false;
	}
	public static TeamColor getPlayerTeamColor(Player p) {
		if (p.hasMetadata("Team")) {
			return (TeamColor) Main.getMetadata(p, "Team");
		}
		return null;
	}

	public static void setTeamColorToObject(Metadatable o, TeamColor color) {
		Main.setMetadata(o, "Team", color);
	}
	public static boolean objectIsInATeam(Metadatable o) {
		if (o.hasMetadata("Team") && Main.getMetadata(o, "Team") != null) {
			return true;
		}
		return false;
	}
	public static boolean objectIsInSameTeamAs(Metadatable o1, Metadatable o2) {
		if (o1.hasMetadata("Team") && Main.getMetadata(o1, "Team") != null && o2.hasMetadata("Team") && Main.getMetadata(o2, "Team") != null) {
			TeamColor c1 = (TeamColor) Main.getMetadata(o1, "Team");
			TeamColor c2 = (TeamColor) Main.getMetadata(o2, "Team");
			if (c1.equals(c2)) {
				return true;
			}
		}
		return false;
	}
	public static TeamColor getTeamColorOfObject(Metadatable o) {
		if (o.hasMetadata("Team")) {
			return (TeamColor) Main.getMetadata(o, "Team");
		}
		return null;
	}
	public static ChatColor getChatColorOfTeamColor(TeamColor color) {
		switch (color) {
		case GREEN:
			return ChatColor.DARK_GREEN;
			
		case RED:
			return ChatColor.RED;
			
		case YELLOW:
			return ChatColor.YELLOW;
			
		case BLUE:
			return ChatColor.DARK_BLUE;
			
		case WHITE:
			return ChatColor.WHITE;
			
		case BLACK:
			return ChatColor.BLACK;
			
		case LIME:
			return ChatColor.GREEN;
			
		case ORANGE:
			return ChatColor.GOLD;
			
		case MAGENTA:
			return ChatColor.LIGHT_PURPLE;
			
		case LIGHT_BLUE:
			return ChatColor.BLUE;
			
		case GRAY:
			return ChatColor.DARK_GRAY;
			
		case LIGHT_GRAY:
			return ChatColor.GRAY;
			
		case CYAN:
			return ChatColor.AQUA;
			
		case PURPLE:
			return ChatColor.DARK_PURPLE;
			
		default:
			return ChatColor.GREEN;
		}
	}
	public static DyeColor getDyeColorOfTeam(TeamColor color) {
		return DyeColor.valueOf(color.toString());
	}
	
	public static void registerTeams() {
		for (TeamColor c : TeamColor.values()) {
			if (Main.scoreboard.getTeam(c.toString()) != null) {
				Main.scoreboard.getTeam(c.toString()).unregister();
			}
			Main.scoreboard.registerNewTeam(c.toString()).setPrefix(Team.getChatColorOfTeamColor(c).toString() + c.toString().charAt(0) + ": ");
			org.bukkit.scoreboard.Team t = Main.scoreboard.getTeam(c.toString());
			t.setAllowFriendlyFire(false);
			t.setCanSeeFriendlyInvisibles(true);
			t.setColor(Team.getChatColorOfTeamColor(c));
			
			Team team = new Team(c);
			teams.add(team);
			for (World w : Bukkit.getWorlds()) {
				for (Entity e : w.getEntities()) {
					if (e.hasMetadata("Team")) {
						String name = e.getCustomName();
						for (Team te : Team.teams) {
							if (name != null && name.toLowerCase().contains(team.getColor().toLowerCase())) {
								te.friendlyMobs.add((Monster) e);
								Main.setMetadata(e, "Team", team.getTeamColor());
							}
						}					
					}
				}
			}
		}
		
	}
	public static ArrayList<Team> getTeams() {
		return teams;
	}
	public static Team getTeamOfColor(TeamColor color) {
		for (Team team : teams) {
			if (team.getTeamColor().equals(color)) {
				return team;
			}
		}
		return null;
	}
	public static Team getTeamOfPlayer(Player p) {
		if (Team.playerIsInATeam(p)) {
			TeamColor color = (TeamColor) Main.getMetadata(p, "Team");
			for (Team team : teams) {
				if (team.getColor().toString().equalsIgnoreCase(color.toString())) {
					return team;
				}
			}
		}
		return null;
	}
	public static Team getTeamOfChatColor(ChatColor c) {
		for (Team team : teams) {
			if (team.getChatColor().equals(c)) {
				return team;
			}
		}
		return null;
	}
	public static Team getTeamOfString(String c) {
		for (Team team : teams) {
			if (team.getColor().equals(c)) {
				return team;
			}
		}
		return null;
	}

	public void setCommunityChest(Location l) {
		boolean lContainer = l.getBlock().getState() instanceof Chest || 
				l.getBlock().getState() instanceof ShulkerBox || l.getBlock().getState() instanceof Hopper;
		if (lContainer) {
			Main.setMetadata(l.getBlock(), "Community Chest", color);
			communityChests.add(l);
			this.communityChest = l;
			for (Block b : Utilities.getAdjacentBlocks(l.getBlock())) {
				BlockState s = b.getState();
				boolean container = s instanceof Chest || s instanceof ShulkerBox || s instanceof Hopper;
				if (container) {
					Main.setMetadata(b, "Community Chest", color);
					communityChests.add(b.getLocation());
					new BukkitRunnable() {
						
						@Override
						public void run() {
							b.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, b.getLocation(), 5, 0.5, 0.5, 0.5);
						}
					}.runTaskTimer(Main.plugin, 0, 20);
				}
			} 
		}
	}
	public Location getCommunityChest() {
		return communityChest;
	}
	public ArrayList<Location> getCommunityChests() {
		return communityChests;
	}
	public void putItemsInsideCommunityChest(ArrayList<ItemStack> items) {
		try {	
			for (ItemStack i : items) {
				Iterator<Location> it = communityChests.iterator();	
				while (it.hasNext()) {
					Chest chest = (Chest) it.next().getBlock().getState();
					if (chest.getInventory().firstEmpty() != -1) {
						chest.getInventory().addItem(i);
						break;
					} else if (it.hasNext()) {
						continue;
					} else {
						ArrayList<Block> dropLs = Utilities.getAdjacentBlocks(chest.getBlock());
						Collections.shuffle(dropLs);
						Location dropL = dropLs.get(0).getLocation();
						dropL.getWorld().dropItem(dropL, i);
					} 
				}			
			}
		}
		catch (NullPointerException e) {
			
		}
	}
	public void putItemInsideCommunityChest(ItemStack item) {
		try {
			Iterator<Location> it = communityChests.iterator();
			while (it.hasNext()) {
				Chest chest = (Chest) it.next().getBlock().getState();
				if (chest.getInventory().firstEmpty() != -1) {
					chest.getInventory().addItem(item);
					return;
				} else if (it.hasNext()) {
					continue;
				} else {
					ArrayList<Block> dropLs = Utilities.getAdjacentBlocks(chest.getBlock());
					Collections.shuffle(dropLs);
					Block dropL = dropLs.get(0);
					dropL.getWorld().dropItem(dropL.getLocation(), item);
				} 
			}		
		}
		catch (NullPointerException e) {
			
		}
	}
}
