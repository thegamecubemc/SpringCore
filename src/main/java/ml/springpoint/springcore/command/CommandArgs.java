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

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Framework - CommandArgs <br>
 * This class is passed to the command methods and contains various utilities as
 * well as the command info.
 *
 * @author minnymin3
 */
public class CommandArgs {

    private CommandSender sender;
    private org.bukkit.command.Command command;
    private String label;
    private String[] args;

    protected CommandArgs(CommandSender sender, org.bukkit.command.Command command, String label, String[] args,
                          int subCommand) {
        String[] modArgs = new String[args.length - subCommand];
        System.arraycopy(args, subCommand, modArgs, 0, args.length - subCommand);

        StringBuilder buffer = new StringBuilder();
        buffer.append(label);
        for (int x = 0; x < subCommand; x++) {
            buffer.append(".").append(args[x]);
        }
        String cmdLabel = buffer.toString();
        this.sender = sender;
        this.command = command;
        this.label = cmdLabel;
        this.args = modArgs;
    }

    /**
     * Gets the command sender
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets the original command object
     */
    public org.bukkit.command.Command getCommand() {
        return command;
    }

    /**
     * Gets the label including sub command labels of this command
     *
     * @return Something like 'test.subcommand'
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets all the arguments after the command's label. ie. if the command
     * label was test.subcommand and the arguments were subcommand foo foo, it
     * would only return 'foo foo' because 'subcommand' is part of the command
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Gets the argument at the specified index
     *
     * @param index The index to get
     * @return The string at the specified index
     */
    public String getArgs(int index) {
        return args[index];
    }

    /**
     * Returns the length of the command arguments
     *
     * @return int length of args
     */
    public int length() {
        return args.length;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        if (sender instanceof Player) {
            return (Player) sender;
        } else {
            return null;
        }
    }

}
