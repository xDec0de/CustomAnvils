package me.xdec0de.customanvils;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import me.xdec0de.customanvils.cmd.MainCmd;
import net.codersky.mcutils.MCPlugin;
import net.codersky.mcutils.files.yaml.MessagesFile;

public class CustomAnvils extends MCPlugin {

	private final CAConfig cfg = new CAConfig(this);
	private final MessagesFile msg = new MessagesFile(this, "messages.yml");

	@Override
	public void onEnable() {
		registerFile(cfg);
		registerFile(msg);
		reloadFiles();
		if (!validateCredits()) {
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		final UpdateChecker updateChecker = new UpdateChecker(this);
		registerEvents(new AnvilHandler(this), updateChecker);
		registerCommand(new MainCmd(this));
		logCol(	" ",
				"&8|------------------------------------------>",
				" ",
				"          &eCustomAnvils &8- &aEnabled",
				" ",
				"  &b- &7Author&8: &bxDec0de_",
				" ",
				"  &b- &7Version&8: &b" + getDescription().getVersion(),
				" ",
				"&8|------------------------------------------>",
				" ");
		update();
		updateChecker.checkUpdates(Bukkit.getConsoleSender());
	}

	@Override
	public void onDisable() {
		logCol(	" ",
				"&8|------------------------------------------>",
				" ",
				"          &eCustomAnvils &8- &cDisabled",
				" ",
				"  &b- &7Author&8: &bxDec0de_",
				" ",
				"  &b- &7Version&8: &b" + getDescription().getVersion(),
				" ",
				"&8|------------------------------------------>",
				" ");
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
