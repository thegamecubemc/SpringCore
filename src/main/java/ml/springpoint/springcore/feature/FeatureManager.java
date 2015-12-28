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

package ml.springpoint.springcore.feature;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.command.CommandFeature;
import ml.springpoint.springcore.menu.MenuFeature;
import ml.springpoint.springcore.module.ModuleFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for enabling features if they are chosen by the developer.
 * All features are registered here.
 * <p/>
 * <table>
 * <tr>
 * <th>Feature</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>Modules</td>
 * <td>Split up your plugin into sub-parts. Modules are like SpringPlugin classes,
 * but can be enabled/disabled independently of their parent plugin and have
 * a seperate data folder for their seperate configuration files.</td>
 * </tr>
 * <tr>
 * <td>Commands</td>
 * <td>Annotation-based command library created by minnymin. By Springpoint Network's
 * standards, you are required to use this library rather than Bukkit's library if you
 * wish to add commands, or you will fail the code review.</td>
 * </tr>
 * <tr>
 * <td>Menus</td>
 * <td>Easily create chest GUIs using AmpMenus by ampayne2.</td>
 * </tr>
 * </table>
 *
 * @author SirFaizdat
 * @since 1.0
 */
public class FeatureManager {

    SpringPlugin plugin;
    Map<String, Feature> featureMap;

    public FeatureManager(SpringPlugin plugin) {
        this.plugin = plugin;
        featureMap = new HashMap<>();
        featureMap.put("modules", new ModuleFeature(plugin));
        featureMap.put("commands", new CommandFeature(plugin));
        featureMap.put("menus", new MenuFeature(plugin));
    }

    /**
     * Initialize a feature so that it is ready for use.
     *
     * @param name The name of the feature to use. If the feature does not
     *             exist or the name is incorrect, this will throw a NullPointerException.
     */
    public void use(String name) {
        featureMap.get(name.toLowerCase()).init();
    }

    /**
     * De-initialize all of the features.
     */
    public void disable() {
        for (Feature f : featureMap.values()) f.deinit();
    }

    /**
     * Gets a feature by name.
     *
     * @param name The name of the feature.
     * @return The Feature object. From here, you would cast it to the appropriate object.
     * For example, if you called <code>get("modules");</code>, you would cast it to {@link ModuleFeature}.
     */
    public Feature get(String name) {
        return featureMap.get(name.toLowerCase());
    }

}
