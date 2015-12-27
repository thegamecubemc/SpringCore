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
