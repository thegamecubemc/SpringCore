package ml.springpoint.springcore.feature;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.module.ModuleFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for enabling features if they are chosen by the developer.
 * All features are registered here.
 * <p/>
 * <table>
 * <tr>
 * <th>Feature</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>Modules</td>
 * <td>Split up your plugin into sub-parts. Modules are like SpringPlugin classes,
 * but can be enabled/disabled independently of their parent plugin and have
 * a seperate data folder for their seperate configuration files.</td>
 * </tr>
 * </table>
 *
 * @author SirFaizdat
 * @since 1.0
 */
public class FeatureManager {

    SpringPlugin plugin;
    Map<String, Feature> featureMap;

    public FeatureManager(SpringPlugin plugin) {
        this.plugin = plugin;
        featureMap = new HashMap<>();
        featureMap.put("modules", new ModuleFeature(plugin));
    }

    /**
     * Initialize a feature so that it is ready for use.
     *
     * @param name The name of the feature to use. If the feature does not
     *             exist or the name is incorrect, this will throw a NullPointerException.
     */
    public void use(String name) {
        featureMap.get(name).init();
    }

    /**
     * De-initialize all of the features.
     */
    public void disable() {
        for (Feature f : featureMap.values()) f.deinit();
    }

    /**
     * Gets a feature by name.
     *
     * @param name The name of the feature.
     * @return The Feature object. From here, you would cast it to the appropriate object.
     * For example, if you called <code>get("modules");</code>, you would cast it to {@link ModuleFeature}.
     */
    public Feature get(String name) {
        return featureMap.get(name);
    }

}
