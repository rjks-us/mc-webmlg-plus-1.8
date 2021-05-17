package us.rjks.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import us.rjks.game.Main;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.05.2021 / 14:05
 *
 **************************************************************************/

public class Inventory {

    public void setInv(Player player) {
        if(Config.getItemStackList("mlg-items").size() > 0) {
            player.getInventory().clear();
            player.getInventory().setHeldItemSlot(Config.getInteger("mlg-type-slot"));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    player.getInventory().setItem(Config.getInteger("mlg-type-slot"), Config.getItemStackList("mlg-items").get(0));
                }
            }, 1);
        }
    }

}
