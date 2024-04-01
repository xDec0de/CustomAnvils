package me.xdec0de.customanvils.cmd;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.xdec0de.customanvils.CustomAnvils;
import net.codersky.mcutils.general.MCCommand;
import net.codersky.mcutils.java.strings.MCStrings;

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
		getPlugin().getMessages().send(sender, "reload.ok");
		if (!getPlugin().getMessages().getString("info", "").contains("xDec0de_"))
			sender.sendMessage(MCStrings.applyColor("&8- &7Please add &exDec0de_ &7back to the &6info &7message, the"
					+ " plugin won't work after a restart otherwise&8."));
		return true;
	}

	@Override public List<String> onTab(CommandSender sender, String[] args) { return null; }
}
