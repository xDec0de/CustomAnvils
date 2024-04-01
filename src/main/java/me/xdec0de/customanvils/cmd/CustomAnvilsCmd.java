package me.xdec0de.customanvils.cmd;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.xdec0de.customanvils.CustomAnvils;
import net.codersky.mcutils.general.MCCommand;

public class CustomAnvilsCmd extends MCCommand<CustomAnvils> {

	public CustomAnvilsCmd(CustomAnvils plugin) {
		super(plugin, "customanvils");
		inject(0, new Reload(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		return getPlugin().getMessages().send(sender, "info", "%version%", getPlugin().getDescription().getVersion());
	}

	@Override public List<String> onTab(CommandSender sender, String[] args) { return null; }
}
