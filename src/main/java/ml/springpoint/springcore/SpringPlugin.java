package ml.springpoint.springcore;

import ml.springpoint.springcore.feature.FeatureManager;
import ml.springpoint.springcore.utils.Txt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A special substitute for JavaPlugin that allows you to use the API,
 * and also handles boring boilerplate code for you.
 *
 * @author SirFaizdat
 * @since 1.0
 */
public class SpringPlugin extends JavaPlugin {

    private FeatureManager featureManager;
    private String logPrefix;

    // > Enable

    public void onEnable() {
        long enableStartTime = System.currentTimeMillis();
        setLogPrefix("&8[&2" + getDescription().getName() + "&8]&r"); // Default log prefix

        // Create the data folder if it doesn't exist already (/plugins/<plugin name>)
        if(!getDataFolder().exists()) getDataFolder().mkdir();

        // Load all of the features
        featureManager = new FeatureManager(this);

        if (!enable()) {
            this.setEnabled(false);
            return;
        }

        long enableElapsedTime = System.currentTimeMillis() - enableStartTime;
        log("&3%s&7 enabled successfully, in &3%dms&7.", getDescription().getFullName(), enableElapsedTime);
    }

    /**
     * Call all of your initialization here. Keep in mind that the API has already been
     * loaded, and that <b>enable messages will be handled for you</b>. You should
     * call the {@link SpringPlugin#use(String...)} method first, so that any additional
     * parts of the API that you will be using can be initialized. You would get all
     * the instances of these features' classes from the FeatureManager using {@link}
     *
     * @return true if the enable was successful, false otherwise. If this is false,
     * the plugin will be set to not enabled, and will not continue to function.
     */
    protected boolean enable() {
        return true;
    }

    /**
     * Initialize parts of the API for use.
     * This allows you to only use what you want, without bloating
     * your plugin with unnecessary instances, background processes, etc.
     *
     * @param features The names of the features you wish to use. For a complete
     *                 list of features, see {@link FeatureManager}
     */
    protected void use(String... features) {
        for (String s : features) featureManager.use(s);
    }

    // > Other methods

    /**
     * Logs a message to the console, with support for colors.
     *
     * @param message The message. It will be colored for you.
     * @param args    {@link String#format(String, Object...)} is run on the message, and the args provided
     *                here will be used in there. Any string args will be colored.
     */
    public void log(String message, Object... args) {
        message = Txt.color(logPrefix + " " + String.format(message, args));

        // All this code checks to see if the static Bukkit class is loaded so that we don't run
        // into an NPE along the way if the output destination is not a Bukkit console. This is primarily
        // the case during tests. In this case, System.out.println() is used.
        try {
            Method m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            m.setAccessible(true); // findLoadedClass is protected by default, we have to use reflection to get to it.
            if (m.invoke(ClassLoader.getSystemClassLoader(), "org.bukkit.Bukkit") == null) {
                System.out.println(ChatColor.stripColor(message));
                return; // Done!
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return;
        }

        // Console doesn't support color? Just print it in plain. Else, send in color.
        if (Bukkit.getConsoleSender() == null) Bukkit.getLogger().info(ChatColor.stripColor(message));
        else Bukkit.getConsoleSender().sendMessage(message);
    }

    // > Getters and setters

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    protected void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }
}
