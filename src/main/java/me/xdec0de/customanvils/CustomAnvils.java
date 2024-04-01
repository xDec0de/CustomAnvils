package me.xdec0de.customanvils;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import me.xdec0de.customanvils.cmd.CustomAnvilsCmd;
import net.codersky.mcutils.MCPlugin;
import net.codersky.mcutils.files.yaml.MessagesFile;

public class CustomAnvils extends MCPlugin {

	private final CAConfig cfg = new CAConfig(this);
	private final MessagesFile msg = new MessagesFile(this, "messages.yml");

	@Override
	public void onEnable() {
		registerFile(cfg);
		registerFile(msg);
		update();
		if (!validateCredits())
			Bukkit.getPluginManager().disablePlugin(this);
		registerEvents(new AnvilHandler(this));
		registerCommand(new CustomAnvilsCmd(this));
		
	}

	@Nonnull
	@Override
	public CAConfig getConfig() {
		return cfg;
	}

	@Nonnull
	@Override
	public MessagesFile getMessages() {
		return msg;
	}

	private boolean validateCredits() {
		if (msg.getString("info", "").contains("xDec0de_"))
			return true;
		logCol(" ",
				"&8-------------------------------------------",
				"&cFailed to enable CustomAnvils&8:",
				"&8- &7Add &exDec0de_ &7back to the plugin info&8.",
				" ",
				"&7This plugin is free, give me some credit!",
				"&8-------------------------------------------",
				" ");
		return false;
	}
}
