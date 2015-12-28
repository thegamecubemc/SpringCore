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

package ml.springpoint.springcore.utils;

import org.bukkit.ChatColor;

/**
 * Provides utilities for dealing with text.
 *
 * @author SirFaizdat
 */
public class Txt {

    /**
     * Translates a string using an ampersand into a
     * string that uses the Bukkit ChatColor.COLOR_CODE color code
     * character. The ampersand will only be replaced if
     * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * @param txt The text to colorize.
     * @return The colorized string.
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public static String color(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

}
