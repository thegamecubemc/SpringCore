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

package ml.springpoint.springcore.command;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.feature.Feature;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Annotation-based command library created by minnymin. By Springpoint Network's
 * standards, you are required to use this library rather than Bukkit's library if you
 * wish to add commands, or you will fail the code review.
 * <p/>
 * You don't have to include the commands in your plugin.yml file; they are
 * added to the command map for you.
 *
 * @author SirFaizdat
 */
public class CommandFeature implements Feature {

    private SpringPlugin plugin;
    private CommandFramework commandFramework;

    public CommandFeature(SpringPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        commandFramework = new CommandFramework(plugin);

        // Wait until the server's enable is complete before registering the help menu.
        // This is for plugins that have extensions that may load any time after the plugin.
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                commandFramework.registerHelp();
            }
        }, 5L);
    }

    @Override
    public void deinit() {

    }

    /**
     * Register all {@link Command} annotations within an object.
     * Similar to {@link org.bukkit.plugin.PluginManager#registerEvents(Listener, Plugin)}.
     *
     * @param obj The Object containing the {@link Command} annotations.
     */
    public void registerCommands(Object obj) {
        commandFramework.registerCommand(obj);
    }

}
