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

package ml.springpoint.springcore;

import ml.springpoint.springcore.feature.FeatureManager;
import ml.springpoint.springcore.json.JsonConfig;
import ml.springpoint.springcore.json.JsonMessages;
import ml.springpoint.springcore.utils.Txt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A special substitute for JavaPlugin that allows you to use the API,
 * and also handles boring boilerplate code for you.
 *
 * @author SirFaizdat
 * @since 1.0
 */
public class SpringPlugin extends JavaPlugin {

    private FeatureManager featureManager;
    private String logPrefix;
    private JsonConfig config;
    private JsonMessages messages;

    // > Enable

    public void onEnable() {
        long enableStartTime = System.currentTimeMillis();
        setLogPrefix("&8[&2" + getDescription().getName() + "&8]&r"); // Default log prefix

        // Create the data folder if it doesn't exist already (/plugins/<plugin name>)
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        config = new JsonConfig(this, new File(getDataFolder(), "config.json"));
        messages = new JsonMessages(this, new File(getDataFolder(), "messages.json"));

        // Load all of the features
        featureManager = new FeatureManager(this);

        if (!enable()) {
            this.setEnabled(false);
            return;
        }

        long enableElapsedTime = System.currentTimeMillis() - enableStartTime;
        log("&3%s&7 enabled successfully, in &3%dms&7.", getDescription().getFullName(), enableElapsedTime);
    }

    /**
     * Call all of your initialization here. Keep in mind that the API has already been
     * loaded, and that <b>enable messages will be handled for you</b>. You should
     * call the {@link SpringPlugin#use(String...)} method first, so that any additional
     * parts of the API that you will be using can be initialized. You would get all
     * the instances of these features' classes from the FeatureManager using {@link}
     * <p/>
     * In addition, if you plan to have a configuration or localization file for
     * your plugin, you would add the defaults and call <code>getConfiguration().load()</code>
     * or <code>getMessages().load()</code> here. <b>They will not be created otherwise!</b>
     *
     * @return true if the enable was successful, false otherwise. If this is false,
     * the plugin will be set to not enabled, and will not continue to function.
     */
    protected boolean enable() {
        return true;
    }

    /**
     * Initialize parts of the API for use.
     * This allows you to only use what you want, without bloating
     * your plugin with unnecessary instances, background processes, etc.
     *
     * @param features The names of the features you wish to use. For a complete
     *                 list of features, see {@link FeatureManager}
     */
    protected void use(String... features) {
        for (String s : features) featureManager.use(s);
    }

    // > Other methods

    /**
     * Logs a message to the console, with support for colors.
     *
     * @param message The message. It will be colored for you.
     * @param args    {@link String#format(String, Object...)} is run on the message, and the args provided
     *                here will be used in there. Any string args will be colored.
     */
    public void log(String message, Object... args) {
        message = Txt.color(logPrefix + " " + String.format(message, args));

        // All this code checks to see if the static Bukkit class is loaded so that we don't run
        // into an NPE along the way if the output destination is not a Bukkit console. This is primarily
        // the case during tests. In this case, System.out.println() is used.
        try {
            Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            m.setAccessible(true); // findLoadedClass is protected by default, we have to use reflection to get to it.
            if (m.invoke(ClassLoader.getSystemClassLoader(), "org.bukkit.Bukkit") == null) {
                System.out.println(ChatColor.stripColor(message));
                return; // Done!
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return;
        }

        // Console doesn't support color? Just print it in plain. Else, send in color.
        if (Bukkit.getConsoleSender() == null) Bukkit.getLogger().info(ChatColor.stripColor(message));
        else Bukkit.getConsoleSender().sendMessage(message);
    }

    // > Getters and setters

    public JsonConfig getConfiguration() {
        return config;
    }

    public JsonMessages getMessages() {
        return messages;
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    protected void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

}
