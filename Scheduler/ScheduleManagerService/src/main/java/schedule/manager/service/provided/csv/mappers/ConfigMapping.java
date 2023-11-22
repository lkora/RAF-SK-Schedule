
package schedule.manager.service.provided.csv.mappers;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a configuration mapping with index, custom, and original values.
 */
@Getter
@Setter
public class ConfigMapping {
    private Integer index;
    private String custom;
    private String original;

    /**
     * Initializes a new instance of the ConfigMapping class with the specified parameters.
     *
     * @param index    The index associated with the configuration mapping.
     * @param custom   The custom value for the configuration mapping.
     * @param original The original value for the configuration mapping.
     */
    public ConfigMapping(Integer index, String custom, String original) {
        this.index = index;
        this.custom = custom;
        this.original = original;
    }

}
