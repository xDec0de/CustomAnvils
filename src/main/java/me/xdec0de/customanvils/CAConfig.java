package me.xdec0de.customanvils;

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.bukkit.enchantments.Enchantment;
import org.checkerframework.checker.index.qual.Positive;

import net.codersky.mcutils.files.yaml.PluginFile;

public class CAConfig extends PluginFile {

	public CAConfig(@Nonnull CustomAnvils plugin) {
		super(plugin, "config.yml");
	}

	// Methods by order of appearance on config.yml //

	@Nonnegative
	public int getMaxRepairCost() {
		final int max = getInt("repairCost.max", 40);
		return max < 0 ? 0 : max;
	}

	public int getRepairCostLimit() {
		return getInt("repairCost.limit", 40);
	}

	public boolean getAllowUnsafeEnchants() {
		return getBoolean("unsafeEnchants.allow", false);
	}

	public boolean getUpgradeEnchants() {
		return getBoolean("unsafeEnchants.upgrade.enabled", false);
	}

	@Positive
	public int getMaxLevel(@Nonnull Enchantment ench) {
		final List<String> limits = getStringList("unsafeEnchants.upgrade.limits");
		final String enchName = ench.getKey().getKey();
		for (final String lvlLimit : limits) {
			final String[] enchData = lvlLimit.split(":");
			if (enchData[0].equalsIgnoreCase(enchName))
				return Integer.valueOf(enchData[1]);
		}
		return ench.getMaxLevel();
	}
}
