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
