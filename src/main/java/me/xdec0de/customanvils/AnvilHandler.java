package me.xdec0de.customanvils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import net.codersky.mcutils.events.listeners.PluginListener;

public class AnvilHandler extends PluginListener<CustomAnvils> {

	AnvilHandler(CustomAnvils plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPrepare(PrepareAnvilEvent e) {
		if (e.getResult() == null || e.getResult().getType() == Material.AIR)
			return;
		checkRepairCost(e.getInventory());
		checkUnsafeEnchants(e);
	}

	// Repair cost //

	private void checkRepairCost(final AnvilInventory inv) {
		final int limit = getConfig().getRepairCostLimit();
		inv.setMaximumRepairCost(getConfig().getMaxRepairCost());
		if (limit > -1 && inv.getRepairCost() > limit)
			inv.setRepairCost(limit);
	}

	// Unsafe enchants //

	private void checkUnsafeEnchants(final PrepareAnvilEvent e) {
		if (!getConfig().getBoolean("unsafeEnchants.allow"))
			removeUnsafeEnchants(e);
		else
			joinUnsafeEnchants(e, getConfig().getBoolean("unsafeEnchants.upgrade.enabled"));
	}

	private void removeUnsafeEnchants(final PrepareAnvilEvent e) {
		final Map<Enchantment, Integer> enchantments = e.getResult().getEnchantments();
		for (final Entry<Enchantment, Integer> enchant : enchantments.entrySet())
			if (enchant.getValue() > enchant.getKey().getMaxLevel())
				enchant.setValue(enchant.getKey().getMaxLevel());
		e.getResult().addEnchantments(enchantments);
	}

	private void joinUnsafeEnchants(final PrepareAnvilEvent e, boolean upgrade) {
		final ItemStack first = e.getInventory().getItem(0);
		final ItemStack second = e.getInventory().getItem(1);
		if (first == null || second == null)
			return;
		final Map<Enchantment, Integer> enchantments = new HashMap<>(first.getEnchantments());
		for (final Entry<Enchantment, Integer> enchant : enchantments.entrySet()) {
			final int max = getConfig().getMaxLevel(enchant.getKey());
			int lvl = enchantments.getOrDefault(enchant.getKey(), 0);
			if (enchant.getValue() == lvl && upgrade)
				lvl++;
			else if (lvl < enchant.getValue())
				lvl = enchant.getValue();
			enchantments.put(enchant.getKey(), lvl > max ? max : lvl);
		}
		e.getResult().addUnsafeEnchantments(enchantments);
	}

	// Shortcuts //

	@Override
	public CAConfig getConfig() {
		return getPlugin().getConfig();
	}
}
