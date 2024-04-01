package me.xdec0de.customanvils;

import net.codersky.mcutils.MCPlugin;
import net.codersky.mcutils.files.yaml.PluginFile;

public class CustomAnvils extends MCPlugin {

	private final PluginFile cfg = new PluginFile(this, "config.yml");

	@Override
	public void onEnable() {
		registerFile(cfg);
		registerEvents(new AnvilHandler(this));
	}

	@Override
	public void onDisable() {
		
	}
}
