package me.xdec0de.customanvils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.codersky.mcutils.events.listeners.PluginListener;
import net.codersky.mcutils.java.strings.MCStrings;

public class AnvilHandler extends PluginListener<CustomAnvils> {

	AnvilHandler(final CustomAnvils plugin) {
		super(plugin);
	}

	@EventHandler
	public void onPrepare(final PrepareAnvilEvent e) {
		if (!isValidItem(e.getResult()))
			return;
		checkNameColor(e);
		checkRepairCost(e.getInventory());
		checkUnsafeEnchants(e);
	}

	// Apply color to item names //

	private void checkNameColor(final PrepareAnvilEvent e) {
		if (!getConfig().isColoredItemNamesEnabled())
			return;
		final ItemStack result = e.getResult();
		if (!hasValidMeta(result, meta -> meta.hasDisplayName()))
			return;
		if (getConfig().useColoredItemNamesPerm() && !checkPermission(e.getViewers(), "customanvils.colorednames"))
			return;
		final ItemMeta resultMeta = result.getItemMeta();
		resultMeta.setDisplayName(MCStrings.applyColor(resultMeta.getDisplayName()));
		result.setItemMeta(resultMeta);
		e.setResult(result);
	}

	// Repair cost //

	private void checkRepairCost(final AnvilInventory inv) {
		final int limit = getConfig().getRepairCostLimit();
		if (limit > -1 && inv.getRepairCost() > limit)
			inv.setRepairCost(limit);
		inv.setMaximumRepairCost(getConfig().getMaxRepairCost());
	}

	// Unsafe enchants //

	private void checkUnsafeEnchants(final PrepareAnvilEvent e) {
		if (!getConfig().getAllowUnsafeEnchants())
			removeUnsafeEnchants(e);
		else
			joinUnsafeEnchants(e, getConfig().getUpgradeEnchants());
	}

	private void removeUnsafeEnchants(final PrepareAnvilEvent e) {
		final Map<Enchantment, Integer> enchantments = new HashMap<>(e.getResult().getEnchantments());
		for (final Entry<Enchantment, Integer> enchant : enchantments.entrySet())
			if (enchant.getValue() > enchant.getKey().getMaxLevel())
				enchant.setValue(enchant.getKey().getMaxLevel());
		e.getResult().addEnchantments(enchantments);
	}

	private void joinUnsafeEnchants(final PrepareAnvilEvent e, boolean upgrade) {
		final ItemStack second = e.getInventory().getItem(1);
		if (!isValidItem(second))
			return;
		final Map<Enchantment, Integer> enchantments = new HashMap<>(second.getEnchantments());
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

	// Utility //

	private boolean checkPermission(final List<HumanEntity> viewers, final String permission) {
		for (HumanEntity viewer : viewers)
			if (!viewer.hasPermission(permission))
				return false;
		return true;
	}

	private boolean isValidItem(@Nullable final ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}

	private boolean hasValidMeta(@Nonnull final ItemStack item, @Nonnull final Predicate<ItemMeta> metaRequirements) {
		return item.hasItemMeta() ? metaRequirements.test(item.getItemMeta()) : false;
	}
}
