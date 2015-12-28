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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.util.NumberConversions.*;

/**
 * A configuration file in JSON. I find it better than using YAML.
 * It works very similar to the Bukkit FileConfiguration.
 * <p/>
 * With JsonConfig, default entries are specified in the code so that
 * if new defaults are added, they will automatically be added to the configuration
 * if they are not already present. This removes the annoying requirement of
 * deleting the configuration(s) each time the plugin updates.
 *
 * @author SirFaizdat
 */
@SuppressWarnings("unused")
public class JsonConfig {

    // Eventually, this class will probably just extend FileConfiguration

    // > Fields

    private SpringPlugin plugin;
    private File file;
    private Map<String, Object> entries = new HashMap<>();
    private Map<String, Object> defaults = new HashMap<>();

    // > Constructor

    /**
     * Creates a new instance of JsonConfig.
     *
     * @param plugin SpringPlugin instance
     * @param file   The file that the configuration will be stored in.
     *               If the file does not yet exist, it will be created.
     *               I recommend making it a .json file for clarity.
     */
    public JsonConfig(SpringPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        if (!file.exists()) try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            plugin.log("&cError: &7Failed to create the file &c%s&7. Stack trace:", file.getName());
            e.printStackTrace();
        }
    }

    // > Methods

    @SuppressWarnings("unchecked")
    public boolean load() {
        try {
            this.entries = GsonFactory.getCompactGson().fromJson(new FileReader(file), HashMap.class);
            // Try again if entries is null
            if (entries == null) {
                this.entries = new HashMap<>();
                save();
                load();
            }
            checkDefaults();
            return true;
        } catch (Exception e) { // Catch any exception
            plugin.log("&cError: &7Failed to load the configuration &c%s&7. Stack trace:", file.getName());
            e.printStackTrace();
            return false;
        }
    }

    public boolean save() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(GsonFactory.getPrettyGson().toJson(entries));
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkDefaults() {
        boolean addedDefaults = false;
        for (String defaultKey : defaults.keySet())
            if (!this.entries.containsKey(defaultKey)) {
                this.entries.put(defaultKey, defaults.get(defaultKey));
                addedDefaults = true;
            }

        if (addedDefaults) save();
    }

    // > Getters
    // This code is based off the code in the Bukkit MemorySection class.

    public Object get(String key) {
        return entries.get(key);
    }

    public Object get(String key, Object def) {
        return entries.get(key) == null ? def : entries.get(key);
    }

    public Object getDefault(String key) {
        return defaults.get(key);
    }

    public String getString(String key) {
        Object def = getDefault(key);
        return getString(key, def != null ? def.toString() : null);
    }

    public String getString(String key, String def) {
        Object val = get(key, def);
        return (val != null) ? val.toString() : def;
    }

    public boolean isString(String key) {
        Object val = get(key);
        return val instanceof String;
    }

    public int getInt(String key) {
        Object def = getDefault(key);
        return getInt(key, (def instanceof Number) ? toInt(def) : 0);
    }

    public int getInt(String key, int def) {
        Object val = get(key, def);
        return (val instanceof Number) ? toInt(val) : def;
    }

    public boolean isInt(String key) {
        Object val = get(key);
        return val instanceof Integer;
    }

    public boolean getBoolean(String key) {
        Object def = getDefault(key);
        return getBoolean(key, (def instanceof Boolean) ? (Boolean) def : false);
    }

    public boolean getBoolean(String key, boolean def) {
        Object val = get(key, def);
        return (val instanceof Boolean) ? (Boolean) val : def;
    }

    public boolean isBoolean(String key) {
        Object val = get(key);
        return val instanceof Boolean;
    }

    public double getDouble(String key) {
        Object def = getDefault(key);
        return getDouble(key, (def instanceof Number) ? toDouble(def) : 0);
    }

    public double getDouble(String key, double def) {
        Object val = get(key, def);
        return (val instanceof Number) ? toDouble(val) : def;
    }

    public boolean isDouble(String key) {
        Object val = get(key);
        return val instanceof Double;
    }

    public long getLong(String key) {
        Object def = getDefault(key);
        return getLong(key, (def instanceof Number) ? toLong(def) : 0);
    }

    public long getLong(String key, long def) {
        Object val = get(key, def);
        return (val instanceof Number) ? toLong(val) : def;
    }

    public boolean isLong(String key) {
        Object val = get(key);
        return val instanceof Long;
    }

    // Java
    public List<?> getList(String key) {
        Object def = getDefault(key);
        return getList(key, (def instanceof List) ? (List<?>) def : null);
    }

    public List<?> getList(String key, List<?> def) {
        Object val = get(key, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    public boolean isList(String key) {
        Object val = get(key);
        return val instanceof List;
    }

    public List<String> getStringList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<String> result = new ArrayList<>();

        for (Object object : list) {
            if ((object instanceof String) || (isPrimitiveWrapper(object))) {
                result.add(String.valueOf(object));
            }
        }

        return result;
    }

    public List<Integer> getIntegerList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Integer> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer) object);
            } else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((int) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).intValue());
            }
        }

        return result;
    }

    public List<Boolean> getBooleanList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Boolean> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean) object);
            } else if (object instanceof String) {
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                } else if (Boolean.FALSE.toString().equals(object)) {
                    result.add(false);
                }
            }
        }

        return result;
    }

    public List<Double> getDoubleList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Double> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Double) {
                result.add((Double) object);
            } else if (object instanceof String) {
                try {
                    result.add(Double.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((double) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).doubleValue());
            }
        }

        return result;
    }

    public List<Float> getFloatList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Float> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Float) {
                result.add((Float) object);
            } else if (object instanceof String) {
                try {
                    result.add(Float.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((float) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).floatValue());
            }
        }

        return result;
    }

    public List<Long> getLongList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Long> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Long) {
                result.add((Long) object);
            } else if (object instanceof String) {
                try {
                    result.add(Long.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((long) (Character) object);
            } else if (object instanceof Number) {
                result.add(((Number) object).longValue());
            }
        }

        return result;
    }

    public List<Byte> getByteList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Byte> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Byte) {
                result.add((Byte) object);
            } else if (object instanceof String) {
                try {
                    result.add(Byte.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((byte) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).byteValue());
            }
        }

        return result;
    }

    public List<Character> getCharacterList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Character> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Character) {
                result.add((Character) object);
            } else if (object instanceof String) {
                String str = (String) object;

                if (str.length() == 1) {
                    result.add(str.charAt(0));
                }
            } else if (object instanceof Number) {
                result.add((char) ((Number) object).intValue());
            }
        }

        return result;
    }

    public List<Short> getShortList(String key) {
        List<?> list = getList(key);

        if (list == null) {
            return new ArrayList<>(0);
        }

        List<Short> result = new ArrayList<>();

        for (Object object : list) {
            if (object instanceof Short) {
                result.add((Short) object);
            } else if (object instanceof String) {
                try {
                    result.add(Short.valueOf((String) object));
                } catch (Exception ignored) {
                }
            } else if (object instanceof Character) {
                result.add((short) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).shortValue());
            }
        }

        return result;
    }

    public List<Map<?, ?>> getMapList(String key) {
        List<?> list = getList(key);
        List<Map<?, ?>> result = new ArrayList<>();

        if (list == null) {
            return result;
        }

        for (Object object : list) {
            if (object instanceof Map) {
                result.add((Map<?, ?>) object);
            }
        }

        return result;
    }

    protected boolean isPrimitiveWrapper(Object input) {
        return input instanceof Integer || input instanceof Boolean ||
                input instanceof Character || input instanceof Byte ||
                input instanceof Short || input instanceof Double ||
                input instanceof Long || input instanceof Float;
    }

    // > Setters

    /**
     * Sets a key to a value. If the entry is already in the configuration, it will be
     * overwritten. Remember to save after you are done setting.
     *
     * @param key   The key
     * @param value The value
     */
    public void set(String key, Object value) {
        entries.put(key, value);
    }

    /**
     * Adds a default value. Remember to add your defaults before you call load().
     * If you need to add any defaults after calling load(), simply reload() after
     * you are done.
     *
     * @param key   The key
     * @param value The value
     */
    public void addDefault(String key, Object value) {
        defaults.put(key, value);
    }

}