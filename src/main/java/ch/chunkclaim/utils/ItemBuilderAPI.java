package ch.chunkclaim.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilderAPI {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilderAPI(Material material) {
        this.item  = new ItemStack(material);
        this.meta = this.item.getItemMeta();

    }


    public ItemBuilderAPI(Material material, short subID) {

        this.item  = new ItemStack(material, subID);
        this.meta = this.item.getItemMeta();

    }

    public ItemBuilderAPI(Material material, int amount) {

        this.item  = new ItemStack(material, amount);
        this.meta = this.item.getItemMeta();

    }

    public ItemBuilderAPI(Material material, int amount, short subID) {

        this.item  = new ItemStack(material, amount, subID);
        this.meta = this.item.getItemMeta();

    }

    /**
     * Sets the localized name of the item.
     *
     * @param name The localized name to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI setLocalizedName(String name) {

        this.meta.setLocalizedName(name);
        return this;

    }

    /**
     * Sets the display name of the item.
     *
     * @param displayName The display name to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI setDisplayName(String displayName) {

        displayName = displayName.replace('&', '§');

        this.meta.setDisplayName(displayName);
        return this;

    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment The enchantment to add.
     * @param level The level of the enchantment.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI addEnchantment(Enchantment enchantment, int level) {

        this.meta.addEnchant(enchantment, level, false);
        return this;

    }

    /**
     * Sets the lore of the item.
     *
     * @param lore The lines of lore to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI addLore(String... lore){

        this.meta.setLore(Arrays.asList(lore));
        return this;

    }

    /**
     * Sets the unbreakable state of the item.
     *
     * @param unbreakable The unbreakable state to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI setUnbreakable(boolean unbreakable){

        this.meta.setUnbreakable(unbreakable);
        return this;

    }

    /**
     * Adds item flags to the item.
     *
     * @param flags The item flags to add.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI addItemFlags(ItemFlag... flags) {

        this.meta.addItemFlags(flags);
        return this;

    }

    /**
     * Applies a glow effect to the item.
     *
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI glow(){

        if (this.item.getType().equals(Material.BOW)) {

            this.meta.addEnchant(Enchantment.THORNS, 1, true);

        } else {

            this.meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);

        }

        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;

    }

    /**
     * Sets the amount of the item.
     *
     * @param amount The amount to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI setAmount(int amount) {

        this.item.setAmount(amount);
        return this;

    }

    /**
     * Sets the durability of the item.
     *
     * @param durability The durability to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilderAPI setDurability(short durability) {

        this.item.setDurability(durability);
        return this;

    }

    /**
     * Builds the final ItemStack.
     *
     * @return The built ItemStack.
     */
    public ItemStack build(){

        this.item.setItemMeta(this.meta);
        return this.item;

    }

}
