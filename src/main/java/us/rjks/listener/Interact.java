package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import us.rjks.game.Main;
import us.rjks.utils.Config;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.05.2021 / 13:57
 *
 **************************************************************************/

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(event.getItem() != null && !Main.getGame().falling.contains(event.getPlayer())) {
                int i = 0;
                for (ItemStack itemStack : Config.getItemStackList("mlg-items")) {
                    i++;
                    if(event.getItem().getType().equals(itemStack.getType())) break;
                }
                if (Config.getItemStackList("mlg-items").size() == i) {
                    event.getPlayer().getInventory().setItem(Config.getInteger("mlg-type-slot"), Config.getItemStackList("mlg-items").get(0));
                } else {
                    event.getPlayer().getInventory().setItem(Config.getInteger("mlg-type-slot"), Config.getItemStackList("mlg-items").get(i));
                }
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.valueOf(Config.getString("mlg-type-switch-sound")), 1, 1);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            for (ItemStack itemStack : Config.getItemStackList("mlg-items")) {
                if (event.getCurrentItem().equals(itemStack)) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
