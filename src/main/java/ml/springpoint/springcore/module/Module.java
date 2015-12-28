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

package ml.springpoint.springcore.module;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.json.JsonConfig;
import ml.springpoint.springcore.json.JsonMessages;

import java.io.File;

/**
 * @author SirFaizdat
 */
public abstract class Module {

    private SpringPlugin parentPlugin;
    private ModuleFeature moduleManager;
    private String name;
    private File dataFolder;
    private JsonConfig config;
    private JsonMessages messages;

    // > Enable

    public final void onEnable() {
        // Ensure that ModuleFeature did its job.
        if (getParentPlugin() == null) throw new IllegalStateException("Must set parent plugin.");
        if (getName() == null) throw new IllegalStateException("Must set name.");

        getParentPlugin().log("&7Enabling module &3%s&7...", getName());

        dataFolder = new File(getParentPlugin().getDataFolder(), getName());
        if (!dataFolder.exists()) dataFolder.mkdir();
        config = new JsonConfig(parentPlugin, new File(dataFolder, "config.json"));
        messages = new JsonMessages(parentPlugin, new File(dataFolder, "messages.json"));

        // Run user enable
        if (!enable()) {
            getParentPlugin().log("&cError: &7Failed to enable module &3%s&7.", getName());
            return;
        }

        getParentPlugin().log("&7Enabled module &3%s&7.", getName());
    }

    /**
     * Call all of your initialization code here.
     * <p/>
     * if you plan to have a configuration or localization file for
     * your plugin, you would add the defaults and call <code>getConfiguration().load()</code>
     * or <code>getMessages().load()</code> here. <b>They will not be created otherwise!</b>
     *
     * @return true if the enable was successful, false otherwise. If this is false,
     * the plugin will be set to not enabled, and will not continue to function.
     */
    protected boolean enable() {
        return true;
    }

    // > Disable

    public void onDisable() {
    }

    // > Other methods

    /**
     * Logs a message to the console. Use this for your module instead of
     * {@link SpringPlugin#log(String, Object...)} because it adds the name of
     * the module as the prefix, which is useful for the user in knowing which
     * module the message came from.
     *
     * @param msg  The message
     * @param args The arguments to insert into {@link String#format(String, Object...)}
     */
    public void log(String msg, String... args) {
        msg = "&7<" + getName() + "> " + msg;
        getParentPlugin().log(msg, args);
    }

    // > Getters & setters

    public SpringPlugin getParentPlugin() {
        return parentPlugin;
    }

    // Package-level, set by ModuleFeature upon enable.
    void setParentPlugin(SpringPlugin plugin) {
        this.parentPlugin = plugin;
    }

    public String getName() {
        return name;
    }

    // Package-level, set by ModuleFeature upon enable.
    void setName(String name) {
        this.name = name;
    }

    public ModuleFeature getModuleManager() {
        return moduleManager;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public JsonConfig getConfiguration() {
        return config;
    }

    public JsonMessages getMessages() {
        return messages;
    }

}
