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

package ml.springpoint.springcore.json;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.utils.Txt;
import org.bukkit.command.CommandSender;

import java.io.File;

/**
 * A specialized JsonConfig that deals with storing strings.
 *
 * @author SirFaizdat
 */
public class JsonMessages extends JsonConfig {

    // > Constructor

    /**
     * Creates a new instance of JsonMessages.
     *
     * @param plugin SpringPlugin instance
     * @param file The file that the messages file will be stored in.
     *             If the file does not yet exist, it will be created.
     *             I recommend making it a .json file for clarity.
     */
    public JsonMessages(SpringPlugin plugin, File file) {
        super(plugin, file);
    }

    // > Getters

    /**
     * Gets a message from the messages file. If the key provided does
     * not link to a String value, it will still be casted to a string.
     *
     * @param key    The key
     * @param format The Objects to insert for String.format
     * @return The colored and formatted string
     */
    public String get(String key, Object... format) {
        return Txt.color(String.format(String.valueOf(get(key)), format));
    }

    public void send(CommandSender sender, String key, Object... format) {
        sender.sendMessage(get(key, format));
    }

}
