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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command Framework - Command <br>
 * The command annotation used to designate methods as commands. All methods
 * should have a single CommandArgs argument
 *
 * @author minnymin3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * The name of the command. If it is a sub command then its values would be
     * separated by periods. ie. a command that would be a subcommand of test
     * would be 'test.subcommandname'
     */
    String name();

    /**
     * Gets the required permission of the command
     */
    String permission() default "";

    /**
     * A list of alternate names that the command is executed under. See
     * name() for details on how names work
     */
    String[] aliases() default {};

    /**
     * The description that will appear in /help of the command
     */
    String description() default "";

    /**
     * The usage that will appear in /help (commandname)
     */
    String usage() default "";

    /**
     * The amount of arguments that are needed.
     */
    int minArgs() default 0;

    /**
     * Whether or not the command is available to players only
     */
    boolean inGameOnly() default false;
}
