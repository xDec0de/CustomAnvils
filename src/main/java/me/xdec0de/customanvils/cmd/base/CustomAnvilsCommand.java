package me.xdec0de.customanvils.cmd.base;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;

import me.xdec0de.customanvils.CAConfig;
import me.xdec0de.customanvils.CustomAnvils;
import net.codersky.mcutils.files.yaml.MessagesFile;
import net.codersky.mcutils.general.MCCommand;

public abstract class CustomAnvilsCommand extends MCCommand<CustomAnvils> {

	public CustomAnvilsCommand(CustomAnvils plugin, String name) {
		super(plugin, name);
	}

	@Nonnull
	public CAConfig getConfig() {
		return getPlugin().getConfig();
	}

	@Nonnull
	public MessagesFile getMessages() {
		return getPlugin().getMessages();
	}

	@Override
	public boolean hasAccess(CommandSender sender, boolean message) {
		final boolean perm = getPermission() == null ? true : sender.hasPermission(getPermission());
		if (!perm && message)
			getMessages().send(sender, "noPerm");
		return perm;
	}
}
