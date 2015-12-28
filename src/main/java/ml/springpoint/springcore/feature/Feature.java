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

/**
 * A Feature is responsible for loading and providing instances of resources
 * that have to do with a feature of the API. Features are chosen to be used
 * by the developer.
 *
 * @author SirFaizdat
 * @since 1.0
 */
public interface Feature {

    /**
     * Called when the feature is chosen to be used.
     */
    void init();

    /**
     * If the feature is being used, this method will be called when the plugin disables.
     * If not, this method will never be called.
     */
    void deinit();

}
