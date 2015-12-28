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

package ml.springpoint.springcore;

/**
 * @author SirFaizdat
 */
public class SpringCore extends SpringPlugin {

    // TODO: 12/28/15 HUD API
    // TODO: 12/28/15 Particle API

    private static SpringCore instance;

    @Override
    protected boolean enable() {
        instance = this;
        // All we do in the enable method is add some global messages.
        getMessages().addDefault("no-permission", "&cError: &7You need the permission &c%s&7 to do that.");
        getMessages().addDefault("must-be-player", "&cError: &7You must be in-game to use this command.");
        getMessages().addDefault("incorrect-usage", "&cError: &7Incorrect usage. Use the command like this: &c%s");
        getMessages().addDefault("command-not-found", "&cError: &7That command does not exist or is not handled.");
        return getMessages().load();
    }

    public static SpringCore get() {
        return instance;
    }
}
