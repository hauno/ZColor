ZColor
======
Allows players to recolor their chat usernames with restrictions.


Features
======
* Restricted to ONLY recoloring/formatting names.
* Enforces case-sensitives names; changing them is not allowed.
* Configurable color/format code restrictions (amount of codes, which codes can be used).
* Configurable cooldown time (defaults to an hour).
* Command to see which formatting codes are available via a written book item.
* Configurable messages and prefix within in-game chat (brandable)!
* Uses [Essentials](http://dev.bukkit.org/bukkit-plugins/essentials/) to handle all name changes; no external storage to configure or manage.


Using
======
* Make sure you have [Essentials](http://dev.bukkit.org/bukkit-plugins/essentials/) installed on your server
* Add ZColor.
* Try it! (/nickcolor)


Commands & Permissions
======
* /nickcolor (aliases /color and /nc)
    * Requires `zcolor.use`
    * Allows players to change the formatting of their chat names.
* /colorbook (alias /cb)
    * Requires `zcolor.book`
    * Gives the player a written book containing a list of color codes that are allowed.
* The `zcolor.bypass` permission will allow a player to ignore the cooldown set in the configuration.

Building
=======
1. Install [Maven 3](http://maven.apache.org/)
2. Run `mvn clean package` in the project directory.
3. You can find the compiled plugin in the `target` directory.


**Originally written for [OP Factions](http://op-factions.org)**
