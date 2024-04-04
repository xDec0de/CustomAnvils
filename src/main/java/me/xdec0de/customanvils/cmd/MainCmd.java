package me.xdec0de.customanvils.cmd;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.xdec0de.customanvils.CustomAnvils;
import me.xdec0de.customanvils.cmd.base.CustomAnvilsCommand;

public class MainCmd extends CustomAnvilsCommand {

	public MainCmd(CustomAnvils plugin) {
		super(plugin, "customanvils");
		inject(0, new ReloadCmd(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		return getMessages().send(sender, "info", "%version%", getPlugin().getDescription().getVersion());
	}

	@Override public List<String> onTab(CommandSender sender, String[] args) { return null; }
}
