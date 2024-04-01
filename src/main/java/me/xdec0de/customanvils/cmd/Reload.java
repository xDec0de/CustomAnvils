package me.xdec0de.customanvils.cmd;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.xdec0de.customanvils.CustomAnvils;
import net.codersky.mcutils.general.MCCommand;

public class Reload extends MCCommand<CustomAnvils> {

	Reload(CustomAnvils plugin) {
		super(plugin, "reload");
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission("customAnvils.reload"))
			return getPlugin().getMessages().send(sender, "noPerm");
		final int errors = getPlugin().reloadFiles();
		if (errors != 0)
			return getPlugin().getMessages().send(sender, "reload.err", "%errors%", errors);
		return getPlugin().getMessages().send(sender, "reload.ok");
	}

	@Override public List<String> onTab(CommandSender sender, String[] args) { return null; }
}
