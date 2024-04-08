package me.xdec0de.customanvils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import net.codersky.mcutils.events.listeners.PluginListener;
import net.codersky.mcutils.updaters.sources.SpigotUpdaterSource;
import net.codersky.mcutils.updaters.sources.VersionInfo;

public class UpdateChecker extends PluginListener<CustomAnvils> {

	public UpdateChecker(CustomAnvils plugin) {
		super(plugin);
	}

	void checkUpdates(CommandSender target) {
		Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
			final String path = target instanceof Player ? "player" : "console";
			final String current = getPlugin().getDescription().getVersion();
			final VersionInfo latest = new SpigotUpdaterSource(116057).getLatestVersion();
			if (latest == null)
				getMessages().send(target, "updater.error." + path);
			else if (!latest.getVersion().equals(current))
				getMessages().send(target, "updater.available." + path, "%current%", current, "%latest%", latest.getVersion(), "%link%", latest.getVersionUrl());
			else
				getMessages().send(target, "updater.latest." + path, "%current%", current);
		});
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		checkUpdates(e.getPlayer());
	}
}
