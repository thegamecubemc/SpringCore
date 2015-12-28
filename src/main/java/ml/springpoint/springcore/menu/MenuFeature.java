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

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.feature.Feature;

/**
 * Easily create chest GUIs using AmpMenus by ampayne2.
 *
 * @author SirFaizdat
 */
public class MenuFeature implements Feature {

    private SpringPlugin plugin;

    public MenuFeature(SpringPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        MenuListener.getInstance().register(plugin);
    }

    @Override
    public void deinit() {

    }

}
