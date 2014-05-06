package me.hauno.bukkit.zcolor.command;

import me.hauno.bukkit.zcolor.ZColor;
import net.lordsofcode.framework.Command;
import net.lordsofcode.framework.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ColorbookCommand {

    @Command(name = "colorbook", aliases = {"cb"}, permission = "zcolor.book")
    public void handleColorbook(CommandArgs args) {
        if (!args.isPlayer()) return;

        Player player = args.getPlayer();
        FileConfiguration config = ZColor.getInstance().getPlugin().getConfig();

        List<String> illegalCodes = config.getStringList("illegal-codes");

        for (int i = 0; i < illegalCodes.size(); i++) {
            String code = illegalCodes.get(i);
            illegalCodes.set(i, code.toUpperCase());
        }

        String colorPage = "";

        for (ChatColor color : ChatColor.values()) {
            if (illegalCodes.contains(String.valueOf(color.getChar()).toUpperCase())) {
                continue;
            }

            colorPage = colorPage.concat(String.format("%s%s - %s\n", color, color.getChar(), color.name()));
        }

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = book.getItemMeta();
        BookMeta bookMeta = (BookMeta)meta;

        bookMeta.setAuthor(ChatColor.translateAlternateColorCodes('&', config.getString("book.author")));
        bookMeta.addPage(colorPage);
        bookMeta.setTitle(ChatColor.translateAlternateColorCodes('&', config.getString("book.title")));

        book.setItemMeta(bookMeta);

        player.getInventory().addItem(book);
    }

}
