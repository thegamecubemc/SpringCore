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

package ml.springpoint.springcore.integration;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.feature.Feature;
import ml.springpoint.springcore.integration.vault.VaultIntegration;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Integrate with various plugins. Right now, the only supported plugin is Vault.
 *
 * @author SirFaizdat
 */
public class IntegrationFeature implements Feature {

    private SpringPlugin plugin;
    private Map<String, IntegrationAbstract> integration = new HashMap<>();
    private List<IntegrationAbstract> activatedIntegrations = new ArrayList<>();

    public IntegrationFeature(SpringPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        integration.put("vault", new VaultIntegration());
    }

    @Override
    public void deinit() {
        for (IntegrationAbstract a : activatedIntegrations) a.deactivate();
        activatedIntegrations.clear();
        integration.clear();
    }

    public IntegrationAbstract integrate(String name) {
        name = name.toLowerCase();
        if (integration.get(name) == null) {
            plugin.log("&cError: &7Failed to integrate with &c%s&7: Integration not supported.", name);
            return null;
        }
        if (!hasPlugin(integration.get(name).getPlugin())) {
            plugin.log("&cError: &7Failed to integrate with &c%s&7: Plugin &c%s&7 was not found. Please get it.", name, integration.get(name).getPlugin());
            return null;
        }
        integration.get(name).activate();
        activatedIntegrations.add(integration.get(name)); // Success!
        return integration.get(name);
    }

    private boolean hasPlugin(String pluginName) {
        return Bukkit.getServer().getPluginManager().getPlugin(pluginName) != null;
    }

    public IntegrationAbstract get(String name) {
        return integration.get(name);
    }

}
