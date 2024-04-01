package me.xdec0de.customanvils;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;

import net.codersky.mcutils.events.listeners.PluginListener;
import net.codersky.mcutils.java.math.MCNumbers;

public class AnvilHandler extends PluginListener<CustomAnvils> {

	AnvilHandler(CustomAnvils plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPrepare(PrepareAnvilEvent e) {
		if (e.getResult() == null || e.getResult().getType() == Material.AIR)
			return;
		checkRepairCost(e.getInventory());
	}

	// Repair cost //

	private void checkRepairCost(final AnvilInventory inv) {
		final int max = getConfig().getInt("repairCost.max", 40);
		final int limit = getConfig().getInt("repairCost.limit", -1);

		inv.setMaximumRepairCost(MCNumbers.limit(max, 1, Integer.MAX_VALUE));
		if (limit > -1 && inv.getRepairCost() > limit)
			inv.setRepairCost(limit);
	}
}
