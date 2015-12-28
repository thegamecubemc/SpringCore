/*
 *  Copyright (C) 2015 Springpoint Software and Contributors
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ml.springpoint.springcore.menu;

import ml.springpoint.springcore.menu.menus.ItemMenu;
import ml.springpoint.springcore.menu.menus.MenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Passes inventory click events to their menus for handling.
 */
public class MenuListener implements Listener {
    private Plugin plugin = null;
    private static final MenuListener INSTANCE = new MenuListener();

    private MenuListener() {
    }

    /**
     * Gets the {@link MenuListener} instance.
     *
     * @return The {@link MenuListener} instance.
     */
    public static MenuListener getInstance() {
        return INSTANCE;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof MenuHolder) {
            event.setCancelled(true);
            ((MenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }

    /**
     * Registers the events of the {@link MenuListener} to a plugin.
     *
     * @param plugin The plugin used to register the events.
     */
    public void register(JavaPlugin plugin) {
        if (!isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }

    /**
     * Checks if the {@link MenuListener} is registered to a plugin.
     *
     * @param plugin The plugin.
     * @return True if the {@link MenuListener} is registered to the plugin, else false.
     */
    public boolean isRegistered(JavaPlugin plugin) {
        if (plugin.equals(this.plugin)) {
            for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (listener.getListener().equals(INSTANCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            closeOpenMenus();
            plugin = null;
        }
    }

    /**
     * Closes all {@link ItemMenu}s currently open.
     */
    public static void closeOpenMenus() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory() != null) {
                Inventory inventory = player.getOpenInventory().getTopInventory();
                if (inventory.getHolder() instanceof MenuHolder) {
                    player.closeInventory();
                }
            }
        }
    }
}
