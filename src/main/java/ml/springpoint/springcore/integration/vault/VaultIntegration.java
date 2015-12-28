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

package ml.springpoint.springcore.integration.vault;

import ml.springpoint.springcore.integration.IntegrationAbstract;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * Integrate with Vault.
 *
 * @author SirFaizdat
 */
public class VaultIntegration extends IntegrationAbstract {

    private Economy economy;
    private Permission permission;
    private Chat chat;

    public VaultIntegration() {
        super("Vault");
    }

    @Override
    public void activate() {
        economy = setup(Economy.class);
        permission = setup(Permission.class);
        chat = setup(Chat.class);
    }

    private <T> T setup(Class<T> clazz) {
        RegisteredServiceProvider<T> provider = Bukkit.getServer().getServicesManager().getRegistration(clazz);
        if (provider != null) return provider.getProvider();
        return null;
    }


    /**
     * @return The Economy object if it could be initialized, null
     * if no economy plugin was found.
     */
    public Economy getEconomy() {
        return economy;
    }

    /**
     * @return The Permission object if it could be initialized, null
     * if no permission plugin was found.
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * @return The Chat object if it could be initialized, null
     * if no chat plugin was found.
     */
    public Chat getChat() {
        return chat;
    }

}
