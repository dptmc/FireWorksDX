package io.dpteam.FireWorksDX;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	final HashMap Timer = new HashMap();
	public HashMap cooldowns = new HashMap();

	public Main() {
		super();
	}

	public void onEnable() {
		super.onEnable();
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		System.out.println("FireWorksDX Enabled");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		System.out.println("FireWorksDX Disabled");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cL, String[] args) {
		int cdt = Integer.parseInt(this.getConfig().getString("fw command cooldown"));
		String launch = this.getConfig().getString("Launch message");
		String notallowed = this.getConfig().getString("No access message");
		String wrong = this.getConfig().getString("Command used incorrrectly message");
		String wait = this.getConfig().getString("Time left message");
		Player player = (Player)sender;
		if (cmd.getName().equalsIgnoreCase("fw") || cmd.getName().equalsIgnoreCase("Firework")) {
			if (args.length == 0) {
				if (player.hasPermission("FireWorksDX.Use")) {
					if (player.hasPermission("FireWorksDX.Admin")) {
						this.shootFirework(player);
						player.sendMessage(this.formatVariables(launch, player));
					} else {
						if (this.cooldowns.containsKey(sender.getName()) && (Long)this.cooldowns.get(sender.getName()) / 1000L + (long)cdt - System.currentTimeMillis() / 1000L > 0L) {
							sender.sendMessage(this.formatVariables(wait, player));
							return true;
						}

						this.cooldowns.put(sender.getName(), System.currentTimeMillis());
						this.shootFirework(player);
						player.sendMessage(this.formatVariables(launch, player));
					}
				} else {
					player.sendMessage(this.formatVariables(notallowed, player));
				}
			} else if (args.length == 3) {
				if (player.hasPermission("FireWorksDX.Coord")) {
					double x = (double)Integer.parseInt(args[0]);
					double y = (double)Integer.parseInt(args[1]);
					double z = (double)Integer.parseInt(args[2]);
					this.shootFirework2(player, x, y, z);
					player.sendMessage(this.formatVariables(launch, player));
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("FireWorksDX.Admin")) {
						this.reloadConfig();
						player.sendMessage(ChatColor.GREEN + "Config reloaded!");
					}
				} else {
					player.sendMessage(this.formatVariables(wrong, player));
				}
			}
		}

		return false;
	}

	public String formatVariables(String string, Player player) {
		int cdt = Integer.parseInt(this.getConfig().getString("fw command cooldown"));
		return ChatColor.translateAlternateColorCodes("&".charAt(0), string).replace("%time", String.valueOf(cdt));
	}

	private void shootFirework(Player player) {
		Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
		FireworkMeta fm = firework.getFireworkMeta();
		Random r = new Random();
		Type type = null;
		int fType = r.nextInt(5) + 1;
		switch(fType) {
		case 2:
			type = Type.BALL_LARGE;
			break;
		case 3:
			type = Type.BURST;
			break;
		case 4:
			type = Type.CREEPER;
			break;
		case 5:
			type = Type.STAR;
			break;
		default:
			type = Type.BALL;
		}

		int c1i = r.nextInt(16) + 1;
		int c2i = r.nextInt(16) + 1;
		Color c1 = this.getColor(c1i);
		Color c2 = this.getColor(c2i);
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		fm.addEffect(effect);
		int power = r.nextInt(2) + 1;
		fm.setPower(power);
		firework.setFireworkMeta(fm);
	}

	private void shootFirework2(Player player, double x, double y, double z) {
		World world = player.getWorld();
		Location loc = new Location(world, x, y, z);
		Firework firework = (Firework)player.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fm = firework.getFireworkMeta();
		Random r = new Random();
		Type type = null;
		int fType = r.nextInt(5) + 1;
		switch(fType) {
		case 2:
			type = Type.BALL_LARGE;
			break;
		case 3:
			type = Type.BURST;
			break;
		case 4:
			type = Type.CREEPER;
			break;
		case 5:
			type = Type.STAR;
			break;
		default:
			type = Type.BALL;
		}

		int c1i = r.nextInt(16) + 1;
		int c2i = r.nextInt(16) + 1;
		Color c1 = this.getColor(c1i);
		Color c2 = this.getColor(c2i);
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
		fm.addEffect(effect);
		int power = r.nextInt(2) + 1;
		fm.setPower(power);
		firework.setFireworkMeta(fm);
	}

	public Color getColor(int c) {
		switch(c) {
		case 2:
			return Color.BLACK;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.FUCHSIA;
		case 5:
			return Color.GRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.LIME;
		case 8:
			return Color.MAROON;
		case 9:
			return Color.NAVY;
		case 10:
			return Color.OLIVE;
		case 11:
			return Color.ORANGE;
		case 12:
			return Color.PURPLE;
		case 13:
			return Color.RED;
		case 14:
			return Color.SILVER;
		case 15:
			return Color.TEAL;
		case 16:
			return Color.WHITE;
		case 17:
			return Color.YELLOW;
		default:
			return Color.AQUA;
		}
	}
}
