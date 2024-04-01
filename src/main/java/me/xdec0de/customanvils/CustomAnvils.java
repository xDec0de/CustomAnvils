package me.xdec0de.customanvils;

import javax.annotation.Nonnull;

import me.xdec0de.customanvils.cmd.CustomAnvilsCmd;
import net.codersky.mcutils.MCPlugin;

public class CustomAnvils extends MCPlugin {

	private final CAConfig cfg = new CAConfig(this);

	@Override
	public void onEnable() {
		registerFile(cfg);
		registerEvents(new AnvilHandler(this));
		registerCommand(new CustomAnvilsCmd(this));
	}

	@Nonnull
	@Override
	public CAConfig getConfig() {
		return cfg;
	}
}
