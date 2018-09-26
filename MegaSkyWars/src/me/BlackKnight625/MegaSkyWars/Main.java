package me.BlackKnight625.MegaSkyWars;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import me.BlackKnight625.DuringGame.Structure;
import me.BlackKnight625.DuringGame.StructureType;
import me.BlackKnight625.DuringGame.Team;

public class Main extends JavaPlugin implements Listener {

	public static Main plugin;
	
	public Team team;
	public static Scoreboard scoreboard;

	public static int friendlyMonsterCount = 0;
	
	public void onEnable() {
		plugin = this;
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		new EventsHandler(this);	
		
		scoreboard = getServer().getScoreboardManager().getMainScoreboard();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Team.registerTeams();
			}
		}.runTaskLater(plugin, 2);
			

		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Team team : Team.getTeams()) {
					friendlyMonsterCount += team.friendlyMobs.size();
				}
				Iterator<Team> itt = Team.getTeams().iterator();
				if (!itt.hasNext()) {
					this.cancel();
				}
				Team team = itt.next();
				Iterator<Monster> itm = team.friendlyMobs.iterator();
				while (itm.hasNext()) {
					Monster mob = itm.next();
					Utilities.refreshTargetOfFriendlyMob(mob, team);
				}		
			}
		}.runTaskTimer(this, 10, 20);
	}
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasMetadata("Team")) {
				p.removeMetadata("Team", plugin);
				
			}
		}
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (((Metadatable) p).hasMetadata("Team")) {
				((Metadatable) p).removeMetadata("Team", plugin);
			}
		}
		
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.isOp()) {
			String c = cmd.getName();
			int ammount = args.length;
			boolean hasArgs = ammount >= 1;
			boolean isPlayer = sender instanceof Player;
			if (c.equalsIgnoreCase("oi")) {
				if (isPlayer) {
					Player p = (Player) sender;
					if (Team.playerIsInATeam(p)) {
						Utilities.createFriendlyTeamMob(Team.getTeamOfPlayer(p), Utilities.getEntityTypeDependingOnNecromancerLevel(9), p.getLocation());
						return true;
					}
				}
				else {sender.sendMessage("Sender must be a player!"); return false;}
			}
			else if (c.equalsIgnoreCase("team")) {
					Player player = null;
					
					if (ammount >= 2 && args[0].equalsIgnoreCase("join") && isPlayer) {
						player = (Player) sender;
						if (Team.playerIsInATeam(player)) {
							Team team = Team.getTeamOfPlayer(player);
							team.removePlayer(player);
						}
						for (Team team : Team.teams) {
							if (args[1].equalsIgnoreCase(team.getColor().toString())) {
								team.addPlayer(player);
								return true;
							}
						}
						sender.sendMessage("Invalid team color!");
						return false;
					}
					else if (ammount >= 3 && args[1].equalsIgnoreCase("join")) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.getName().equalsIgnoreCase(args[0])) {
								player = Bukkit.getPlayer(args[0]);
							}
						}
						if (player == null) {
							sender.sendMessage("Specified player is not online!");
							return false;
						}
						if (Team.playerIsInATeam(player)) {
							Team team = Team.getTeamOfPlayer(player);
							team.removePlayer(player);
						}
						for (Team team : Team.teams) {
							if (args[2].equalsIgnoreCase(team.getColor().toString())) {
								team.addPlayer(player);
								return true;
							}
						}
						sender.sendMessage("Invalid team color!");
						return false;
					}
					else {return false;}
				
			}
			else if (c.equalsIgnoreCase("structure")) {
				if (isPlayer) {
					if (hasArgs) {
						Stream<StructureType> values = Arrays.stream(StructureType.values());
						if (values.anyMatch(v -> v.toString().equalsIgnoreCase(args[0]))) {
							try {
								Structure tower = new Structure(StructureType.valueOf(args[0]), (Player) sender);
								return true;
							} catch (ArithmeticException e) {
								sender.sendMessage("You are not in a team");
								return false;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						sender.sendMessage("The name of the Structure must be written in caps! If that was the case,"
								+ " then it does not exist! You must type one of the following: " + StructureType.intoString());
						return false;
					}
					else {
						sender.sendMessage("Not enough parameters! You must type one of the following: " + StructureType.intoString());
						return false;
					}
				}
				sender.sendMessage("You must be a player to perform this command");
				return false;
			}
			return false;
		}
		else {
			sender.sendMessage("You are not allowed to use this command!");
			return false;
		}
	}
	
	public static void setMetadata(Metadatable object, String key, Object value) {
		object.setMetadata(key, new FixedMetadataValue(plugin,value));
		}
	public static void removeMetadata(Metadatable object, String key) {
		object.removeMetadata(key, plugin);
	}
	public static Object getMetadata(Metadatable object, String key) {
		 List<MetadataValue> values = object.getMetadata(key);  
		 for (MetadataValue value : values) {
		    if (value.getOwningPlugin() == plugin) {
		       return value.value();
		    }
		 }
		  
		 return null;
	}

	
	
}
