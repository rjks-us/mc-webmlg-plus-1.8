package us.rjks.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.rjks.game.Main;
import us.rjks.utils.Config;
import us.rjks.utils.Messages;

import java.util.Random;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.05.2021 / 12:48
 *
 **************************************************************************/

public class Up implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Main.getGame().isSetup()) return false;
        if(sender instanceof Player) {
            if (Main.getGame().falling.contains(((Player) sender).getPlayer())) {
                sender.sendMessage(Messages.getString("already-mlg"));
                return false;
            }
            if(args.length == 1) {
                try {
                    int height = Integer.parseInt(args[0]);
                    if(height >= Config.getInteger("min-height") && height <= Config.getInteger("max-height")) {
                        int upx = (int)Main.getGame().getCurrentMap().getProperty("upperbound-x");
                        int upz = (int)Main.getGame().getCurrentMap().getProperty("upperbound-z");

                        int unx = (int)Main.getGame().getCurrentMap().getProperty("underbound-x");
                        int unz = (int)Main.getGame().getCurrentMap().getProperty("underbound-z");

                        int x = 0;
                        int z = 0;
                        if(upx >= unx) {
                            x = new Random().nextInt((upx - unx)) + unx;
                        } else {
                            x = new Random().nextInt((unx - upx)) + upx;
                        }
                        if(upz >= unz) {
                            z = new Random().nextInt((upz - unz)) + unz;
                        } else {
                            z = new Random().nextInt((unz - upz)) + upz;
                        }

                        Location loc = ((Player) sender).getLocation();

                        loc.setX(x + 0.5);
                        loc.setZ(z + 0.5);
                        loc.setY(height);

                        Block block = ((Player) sender).getWorld().getBlockAt(x, height - 1, z);
                        block.setType(Material.valueOf(Config.getString("block-material-from-jump")));
                        ((Player) sender).teleport(loc);
                        Main.getGame().falling.add(((Player) sender));
                        ((Player) sender).setFallDistance(0);

                        Config.getItemStackList("mlg-items").forEach(itemStack -> {
                            for (ItemStack content : ((Player) sender).getInventory().getContents()) {
                                if (content != null && content.equals(itemStack)) {
                                    ((Player) sender).getInventory().clear();
                                    ((Player) sender).getInventory().setItem(0, itemStack);
                                }
                            }
                        });
                        ((Player) sender).getInventory().setHeldItemSlot(0);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                if(Bukkit.getWorlds().contains(block.getWorld())) {
                                    block.setType(Material.AIR);
                                }
                            }
                        }, Config.getInteger("block-duration-until-disapear") * 20);
                    } else {
                        sender.sendMessage(Messages.getString("up-command-range-of-heights")
                                .replaceAll("%low%", Config.getInteger("min-height").toString())
                                .replaceAll("%height%", Config.getInteger("max-height").toString()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sender.sendMessage(Messages.getString("up-command-range-of-heights")
                            .replaceAll("%low%", Config.getInteger("min-height").toString())
                            .replaceAll("%height%", Config.getInteger("max-height").toString()));
                }
            } else {
                sender.sendMessage(Messages.getString("up-command-syntax"));
            }
        } else {
            sender.sendMessage(Messages.getString("no-player"));
        }
        return false;
    }
}
