package me.Sand.WhenISwear;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

/**
 * Created by Sand on 30-6-2016.
 */
public class WhenISwear extends JavaPlugin implements Listener {

    private CommandExecutor executor;

    @Override
    public void onEnable() {
        getLogger().info("WhenISwear Enabled");
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("wis").setExecutor(executor);
    }

    @Override
    public void onDisable() {
        getLogger().info("WhenISwear Disabled");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String msg = "";
        for(String word : e.getMessage().split(" ")) {
            if (getConfig().getStringList("swearwords").contains(word)) {
                e.getPlayer().sendMessage(ChatColor.RED + "Please refrain from using foul language!");
                word = ChatColor.GOLD + getConfig().getString("replacement");
            }
            msg = msg + word + " ";
        }
        e.setMessage(msg);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args ) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Invalid Arguments! Type /wis help for more information.");
            return true;
        }
        String subcommand = args[0];
        if(subcommand.equalsIgnoreCase("reload")) {
            Bukkit.getServer().reload();
            sender.sendMessage(ChatColor.DARK_AQUA + "WhenISwear Reloaded!");
        } else if(subcommand.equalsIgnoreCase("addswear")) {
            if(!getConfig().getStringList("swearwords").contains(args[1])) {
                getConfig().set("swearwords", args[1]);
                for(String word : getConfig().getStringList("swearwords")) {
                    sender.sendMessage(ChatColor.YELLOW + word);
                }
            }
            sender.sendMessage(ChatColor.DARK_AQUA + "Swear Word Added!");
        }
        return false;
    }
}
