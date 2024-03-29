package me.BlackKnight625.MegaSkyWars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import CustomItems.ArmorTier;
import CustomItems.OreType;
import CustomItems.ResourceType;
import CustomItems.ToolTier;
import me.BlackKnight625.DuringGame.Structure;
import me.BlackKnight625.DuringGame.StructureType;
import me.BlackKnight625.DuringGame.Team;

public class Main extends JavaPlugin implements Listener {

	public static Main plugin;
	
	public Team team;
	public static Scoreboard scoreboard;
	int recipeID = 0;

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
			

		//Friendly mobs
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
		//Recipes
		new BukkitRunnable() {
			
			
			
			@Override
			public void run() {
				//Removes vanilla armor recipes
				for (ArmorTier tier : ArmorTier.values()) {
					ItemStack result = Utilities.customArmor(tier);
					Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
				    
			        while(it.hasNext()){
			        		Recipe rec = it.next();
			            	Material maybe = result.getType();
							ItemStack results = rec.getResult();
							if(results.getType().equals(maybe)){
							    it.remove();
							}
			        }
				}
	
				//Replaces smelting recipes' smelting time 
				Iterator<Recipe> it = Bukkit.getServer().recipeIterator();
				while (it.hasNext()) {
					Recipe rec = it.next();
					if (rec instanceof FurnaceRecipe) {	
						it.remove();
						((FurnaceRecipe) rec).setCookingTime(66);
						new BukkitRunnable() {
								
							@Override
							public void run() {
								getServer().addRecipe(rec);
							}
						}.runTaskLater(plugin, 5);	
					}
				}
			//Creates smelting recipes for custom ores/ingots
			for (ResourceType type : ResourceType.values()) {
				recipeID++;
				int underline = type.toString().indexOf("_");
				String typeF = type.toString().substring(0, underline);
				String typeL = type.toString().substring(underline + 1);
				if (typeL.equalsIgnoreCase("ORE") && ResourceType.contains(typeF + "_INGOT")) {
					NamespacedKey key = new NamespacedKey(plugin, type.toString().toLowerCase() + recipeID);
					ItemStack result = Utilities.customResource(ResourceType.valueOf(typeF + "_INGOT"), 1);
					FurnaceRecipe recipe = new FurnaceRecipe(key, result, type.getMaterial(), 1, 66);
					getServer().addRecipe(recipe);
				}
			}		
				
			//Creates crafting recipes for custom armor
			for (ArmorTier tier : ArmorTier.values()) {
				recipeID++;
				int underline = tier.toString().indexOf("_");
				String tierF = tier.toString().substring(0, underline);
				String tierL = tier.toString().substring(underline + 1);
				
				NamespacedKey key = new NamespacedKey(plugin, tier.toString().toLowerCase() + recipeID);
				NamespacedKey key2 = new NamespacedKey(plugin, tier.toString().toLowerCase() + (recipeID + 1));
				ItemStack result = Utilities.customArmor(tier);
				Material craftItem = null;
		        ShapedRecipe recipe = new ShapedRecipe(key, result);
		        
				switch (tierF) {
				case "LEATHER":
					craftItem = Material.LEATHER;
					break;
				case "TIN":
					craftItem = ResourceType.TIN_INGOT.getMaterial();
					break;
				case "GOLDEN":
					craftItem = Material.GOLD_INGOT;
					break;
				case "CHAINMAIL":
					craftItem = Material.IRON_NUGGET;
					break;
				case "COPPER":
					craftItem = ResourceType.COPPER_INGOT.getMaterial();
					break;
				case "BRONZE":
					craftItem = ResourceType.BRONZE_INGOT.getMaterial();
					break;
				case "QUARTZ":
					craftItem = Material.QUARTZ;
					break;
				case "IRON":
					craftItem = Material.IRON_INGOT;
					break;
				case "PRISMARINE":
					craftItem = Material.PRISMARINE_SHARD;
					break;
				case "MITHRIL":
					craftItem = ResourceType.MITHRIL_INGOT.getMaterial();
					break;
				case "DIAMOND":
					craftItem = Material.DIAMOND;
					break;
				case "ONYX":
					craftItem = ResourceType.ONYX_GEM.getMaterial();
					break;
				}
				
				
				switch (tierL) {
				case "HELMET":					
					recipe.shape("xxx", "x x", "   ");
					recipe.setIngredient('x', craftItem);
					ShapedRecipe recipe2 = new ShapedRecipe(key2, result);
					recipe2.shape("   ", "xxx", "x x");
					recipe2.setIngredient('x', craftItem);
					getServer().addRecipe(recipe2);
					break;
				case "CHESTPLATE":
					recipe.shape("x x", "xxx", "xxx");
					recipe.setIngredient('x', craftItem);
					break;
				case "LEGGINGS":
					recipe.shape("xxx", "x x", "x x");
					recipe.setIngredient('x', craftItem);
					break;
				case "BOOTS":
					recipe.shape("x x", "x x", "   ");
					recipe.setIngredient('x', craftItem);
					ShapedRecipe recipe3 = new ShapedRecipe(key2, result);
					recipe3.shape("   ", "x x", "x x");
					recipe3.setIngredient('x', craftItem);
					getServer().addRecipe(recipe3);
					break;
					}

				getServer().addRecipe(recipe);
			}
			//Creates crafting recipes for custom tools
			for (ToolTier tier : ToolTier.values()) {
				
				int underline = tier.toString().indexOf("_");
				String tierF = tier.toString().substring(0, underline);
				String tierL = tier.toString().substring(underline + 1);	
				ItemStack result = Utilities.customTool(tier);
				ArrayList<Material> craftItems = new ArrayList<>();	        
				
				switch (tierF) {
				case "WOODEN":
					craftItems.add(Material.OAK_PLANKS);
					craftItems.add(Material.DARK_OAK_PLANKS);
					craftItems.add(Material.BIRCH_PLANKS);
					craftItems.add(Material.ACACIA_PLANKS);
					craftItems.add(Material.JUNGLE_PLANKS);
					craftItems.add(Material.SPRUCE_PLANKS);
					break;
				case "TIN":
					craftItems.add(ResourceType.TIN_INGOT.getMaterial());
					break;
				case "GOLDEN":
					craftItems.add(Material.GOLD_INGOT);
					break;
				case "STONE":
					craftItems.add(Material.COBBLESTONE);
					craftItems.add(Material.DIORITE);
					craftItems.add(Material.ANDESITE);
					craftItems.add(Material.GRANITE);
					break;
				case "COPPER":
					craftItems.add(ResourceType.COPPER_INGOT.getMaterial());
					break;
				case "BRONZE":
					craftItems.add(ResourceType.BRONZE_INGOT.getMaterial());
					break;
				case "QUARTZ":
					craftItems.add(Material.QUARTZ);
					break;
				case "IRON":
					craftItems.add(Material.IRON_INGOT);
					break;
				case "PRISMARINE":
					craftItems.add(Material.PRISMARINE_SHARD);
					break;
				case "MITHRIL":
					craftItems.add(ResourceType.MITHRIL_INGOT.getMaterial());
					break;
				case "DIAMOND":
					craftItems.add(Material.DIAMOND);
					break;
				case "ONYX":
					craftItems.add(ResourceType.ONYX_GEM.getMaterial());
					break;
				}
				
				
				for (Material craftItem : craftItems) {
					recipeID++;
					NamespacedKey key = new NamespacedKey(plugin, tier.toString().toLowerCase() + recipeID);
					NamespacedKey key2 = new NamespacedKey(plugin, tier.toString().toLowerCase() + (recipeID + 20));
					NamespacedKey key3 = new NamespacedKey(plugin, tier.toString().toLowerCase() + (recipeID + 40));
					ShapedRecipe recipe = new ShapedRecipe(key, result);
					
					switch (tierL) {
					case "AXE":
						recipe.shape("xx ", "xy ", " y ");
						recipe.setIngredient('x', craftItem);
						recipe.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe);
						ShapedRecipe recipe2 = new ShapedRecipe(key2, result);
						recipe2.shape(" xx", " yx", " y ");
						recipe2.setIngredient('x', craftItem);
						recipe2.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe2);
						break;
					case "PICKAXE":
						recipe.shape("xxx", " y ", " y ");
						recipe.setIngredient('x', craftItem);
						recipe.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe);
						break;
					case "SHOVEL":
						recipe.shape("x  ", "y  ", "y  ");
						recipe.setIngredient('x', craftItem);
						recipe.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe);
						ShapedRecipe recipe3 = new ShapedRecipe(key2, result);
						recipe3.shape(" x ", " y ", " y ");
						recipe3.setIngredient('x', craftItem);
						recipe3.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe3);
						ShapedRecipe recipe4 = new ShapedRecipe(key3, result);
						recipe4.shape("x  ", "y  ", "y  ");
						recipe4.setIngredient('x', craftItem);
						recipe4.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe4);
						break;
					case "SWORD":
						recipe.shape("x  ", "x  ", "y  ");
						recipe.setIngredient('x', craftItem);
						recipe.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe);
						ShapedRecipe recipe5 = new ShapedRecipe(key2, result);
						recipe5.shape(" x ", " x ", " y ");
						recipe5.setIngredient('x', craftItem);
						recipe5.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe5);
						ShapedRecipe recipe6 = new ShapedRecipe(key3, result);
						recipe6.shape("x  ", "x  ", "y  ");
						recipe6.setIngredient('x', craftItem);
						recipe6.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe6);
						break;
					case "SPEAR":
						recipe.shape("x  ", " y ", "  y");
						recipe.setIngredient('x', craftItem);
						recipe.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe);
						ShapedRecipe recipe7 = new ShapedRecipe(key2, result);
						recipe7.shape("  x", " y ", "y  ");
						recipe7.setIngredient('x', craftItem);
						recipe7.setIngredient('y', Material.STICK);
						getServer().addRecipe(recipe7);
						break;
					}
				}

				
			}
				
			}
		}.runTaskLater(plugin, 5);
		
		
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
					for (ToolTier tier : ToolTier.values()) {
						ItemStack item = Utilities.customTool(tier);
						p.getWorld().dropItem(p.getLocation(), item);
					}
					for (ArmorTier tier : ArmorTier.values()) {
						ItemStack item = Utilities.customArmor(tier);
						p.getWorld().dropItem(p.getLocation(), item);
					}
					
					
					return true;
				}
				else {sender.sendMessage("Sender must be a player!"); return false;}
			}
			else if (c.equalsIgnoreCase("oioi")) {
				if (isPlayer) {
					Player p = (Player) sender;
					Team team = Team.getTeamOfPlayer(p);
					team.setCommunityChest(p.getLocation().add(0, -1, 0));
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
