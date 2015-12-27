package ml.springpoint.springcore.module;

import ml.springpoint.springcore.SpringPlugin;

import java.io.File;

/**
 * @author SirFaizdat
 */
public abstract class Module {

    private SpringPlugin parentPlugin;
    private ModuleFeature moduleManager;
    private String name;
    private File dataFolder;

    // > Enable

    public final void onEnable() {
        // Ensure that ModuleFeature did its job.
        if (getParentPlugin() == null) throw new IllegalStateException("Must set parent plugin.");
        if (getName() == null) throw new IllegalStateException("Must set name.");

        getParentPlugin().log("&7Enabling module &3%s&7...", getName());

        dataFolder = new File(getParentPlugin().getDataFolder(), getName());
        if (!dataFolder.exists()) dataFolder.mkdir();

        // Run user enable
        if (!enable()) {
            getParentPlugin().log("&cError: &7Failed to enable module &3%s&7.", getName());
            return;
        }

        getParentPlugin().log("&7Enabled module &3%s&7.", getName());
    }

    protected boolean enable() {
        return true;
    }

    // > Disable

    public void onDisable() {
    }

    // > Other methods

    /**
     * Logs a message to the console. Use this for your module instead of
     * {@link SpringPlugin#log(String, Object...)} because it adds the name of
     * the module as the prefix, which is useful for the user in knowing which
     * module the message came from.
     *
     * @param msg The message
     * @param args The arguments to insert into {@link String#format(String, Object...)}
     */
    public void log(String msg, String... args) {
        msg = "&7<" + getName() + "> " + msg;
        getParentPlugin().log(msg, args);
    }

    // > Getters & setters

    public SpringPlugin getParentPlugin() {
        return parentPlugin;
    }

    // Package-level, set by ModuleFeature upon enable.
    void setParentPlugin(SpringPlugin plugin) {
        this.parentPlugin = plugin;
    }

    public String getName() {
        return name;
    }

    // Package-level, set by ModuleFeature upon enable.
    void setName(String name) {
        this.name = name;
    }

    public ModuleFeature getModuleManager() {
        return moduleManager;
    }

    public File getDataFolder() {
        return dataFolder;
    }
}
