/*
 * Copyright (C) 2015 Springpoint Software and Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ml.springpoint.springcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Command Framework - BukkitCompleter <br>
 * An implementation of the TabCompleter class allowing for multiple tab
 * completers per command
 *
 * @author minnymin3
 */
public class BukkitCompleter implements TabCompleter {

    private Map<String, Entry<Method, Object>> completers = new HashMap<String, Entry<Method, Object>>();

    public void addCompleter(String label, Method m, Object obj) {
        completers.put(label, new AbstractMap.SimpleEntry<Method, Object>(m, obj));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        for (int i = args.length; i >= 0; i--) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(label.toLowerCase());
            for (int x = 0; x < i; x++) {
                if (!args[x].equals("") && !args[x].equals(" ")) {
                    buffer.append("." + args[x].toLowerCase());
                }
            }
            String cmdLabel = buffer.toString();
            if (completers.containsKey(cmdLabel)) {
                Entry<Method, Object> entry = completers.get(cmdLabel);
                try {
                    return (List<String>) entry.getKey().invoke(entry.getValue(),
                            new CommandArgs(sender, command, label, args, cmdLabel.split("\\.").length - 1));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}