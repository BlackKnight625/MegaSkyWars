package me.BlackKnight625.MegaSkyWars;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
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
				while (itt.hasNext()) {
					Team team = itt.next();
					Iterator<Monster> itm = team.friendlyMobs.iterator();
					while (itm.hasNext()) {
						Monster mob = itm.next();
						Utilities.refreshTargetOfFriendlyMob(mob, team);
					}
				}
			}
		}.runTaskTimer(this, 10, 20);
		
		/* for (ArmorTier tier : ArmorTier.values()) {
			for (int x = 1; x <= 4; x++) {
				ShapedRecipe recipe = new ShapedRecipe(null);
				switch (x) {
				case 1:
					ItemStack item = new ItemStack(stack)
					item.getItemMeta().
					
					recipe.shape("xxy", "xyy", "xxy");
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
			}
		}*/
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
	
	@SuppressWarnings({"deprecation" })
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.isOp()) {
			String c = cmd.getName();
			int ammount = args.length;
			boolean hasArgs = ammount >= 1;
			boolean isPlayer = sender instanceof Player;
			if (c.equalsIgnoreCase("oi")) {
				if (isPlayer) {
					Player p = (Player) sender;
					ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
					p.sendMessage("" + NBTEditor.getItemTag(sword, "generic.attackDamage"));
				}
				else {sender.sendMessage("Sender must be a player!"); return false;}
			}
			else if (c.equalsIgnoreCase("oioi")) {
				if (isPlayer) {
					Player p = (Player) sender;
					if (Team.playerIsInATeam(p)) {
						Team team = Team.getTeamOfPlayer(p);
						team.setCommunityChest(p.getLocation().getBlock().getLocation().add(0, 0, 0));
					}
					return true;
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
						if (values.anyMatch(v -> v.toString().equalsIgnoreCase(args[0].toUpperCase()))) {
							try {
								new Structure(StructureType.valueOf(args[0].toUpperCase()), (Player) sender);
								return true;
							} catch (ArithmeticException e) {
								sender.sendMessage("You are not in a team");
								return false;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						sender.sendMessage("The name of the Structure must be written in caps! If that was the case,"
								+ " then it does not exist! You must type one of the following: " + StructureType.intoString().toLowerCase());
						return false;
					}
					else {
						sender.sendMessage("Not enough parameters! You must type one of the following: " + StructureType.intoString().toLowerCase());
						return false;
					}
				}
				sender.sendMessage("You must be a player to perform this command");
				return false;
			}
			else if (c.equalsIgnoreCase("friendlymob")) {
				if (isPlayer) {
					Player p = (Player) sender;
					if (hasArgs) {
						if (Team.playerIsInATeam(p)) {
							try {
								EntityType m = EntityType.valueOf(args[0].toUpperCase());
								Utilities.createFriendlyTeamMob(Team.getTeamOfPlayer(p), m, p.getLocation());
								return true;
							}
							catch (CommandException e) {
								p.sendMessage("Invalid entity type or entity type is not an instance of Monster!");
								return false;
							}
						}
						p.sendMessage("You must be in a team to perform this command!");
						return false;
					}
					p.sendMessage("You must specify the type of mob you want to spawn!");
					return false;
				}
				sender.sendMessage("You must be a player to perform this command");
				return false;
			}
			else if (c.equalsIgnoreCase("spawnores")) {
				if (isPlayer) {
					Player p = (Player) sender;
					if (hasArgs) {
						if (ammount >= 4) {
							Stream<OreType> values = Arrays.stream(OreType.values());
							if (values.anyMatch(v -> v.toString().equalsIgnoreCase(args[0].toUpperCase()))) {
								OreType ore = OreType.valueOf(args[0].toUpperCase());
								Material m = Material.valueOf(args[1].toUpperCase());
								int radius = Integer.valueOf(args[2]);
								int blocks = Integer.valueOf(args[3]);
								new OreGenerator(ore, m, radius, p.getLocation(), blocks);
								
								return true;
							}
						}
					}
					p.sendMessage("You must specify the type of Ore you want to spawn, the material that will get replaced, "
							+ "the radius and the ammount! You must choose one of the following Ore Types: " + 
					OreType.intoString().toLowerCase());
					return false;
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
