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
