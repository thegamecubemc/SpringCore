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
import ml.springpoint.springcore.feature.Feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows you to split up your plugin into sub-parts, allowing for modularity.
 * <p/>
 * Modules are like SpringPlugin classes,
 * but can be enabled/disabled independently of their parent plugin and have
 * a seperate data folder for their seperate configuration files.
 *
 * @author SirFaizdat
 */
public class ModuleFeature implements Feature {

    private SpringPlugin plugin;
    private List<Module> loadedModules;

    public ModuleFeature(SpringPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        loadedModules = new ArrayList<>();
    }

    @Override
    public void deinit() {
    }

    /**
     * Loads a module if it has not been loaded already.
     *
     * @param m The initialized Module object.
     */
    public void load(Module m) {
        if (loadedModules.contains(m)) return; // Already loaded

        ModuleDef def = m.getClass().getAnnotation(ModuleDef.class);
        if (def == null) {
            plugin.log("&cError: &7Could not load module &c%s&7 because it does not have a @ModuleDef definition annotation.", m.getClass().getName());
            return;
        }

        m.setParentPlugin(plugin);
        m.setName(def.name());
        m.onEnable();
        loadedModules.add(m);
    }

    public Module getModule(String name) {
        for (Module m : loadedModules) if (m.getName() != null && m.getName().equals(name)) return m;
        return null;
    }

}
