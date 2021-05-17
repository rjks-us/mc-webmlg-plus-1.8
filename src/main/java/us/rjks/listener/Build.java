package us.rjks.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import us.rjks.game.Main;
import us.rjks.utils.Config;

import javax.annotation.Nonnull;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.05.2021 / 12:45
 *
 **************************************************************************/

public class Build implements Listener {

    @EventHandler
    public void onBlockEvent(@Nonnull BlockFromToEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(@Nonnull WeatherChangeEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(@Nonnull FoodLevelChangeEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplodeEvent(@Nonnull EntityExplodeEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplodeEvent(@Nonnull EntitySpawnEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecayEvent(@Nonnull LeavesDecayEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecayEvent(@Nonnull BlockGrowEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onAchievment(@Nonnull PlayerAchievementAwardedEvent event) {
        if (Main.getGame().isSetup()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPhysics(@Nonnull BlockPhysicsEvent event) {
        if (Main.getGame().isSetup()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (Main.getGame().isSetup()) return;

        if(!Main.getGame().falling.contains(event.getPlayer())) {
            event.setCancelled(true);
        } else {
            Main.getGame().falling.remove(event.getPlayer());
            event.getPlayer().getInventory().clear();
            Main.getGame().getInventory().setInv(event.getPlayer());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if(Bukkit.getWorlds().contains(event.getBlock().getWorld())) {
                        event.getBlock().setType(Material.AIR);
                    }
                }
            }, Config.getInteger("block-duration-until-disapear") * 20);
        }
    }

    @EventHandler
    public void onBucketEmpty (PlayerBucketEmptyEvent event) {
        if (Main.getGame().isSetup()) return;
        if (event.getBucket() != Material.WATER_BUCKET) return;

        if(!Main.getGame().falling.contains(event.getPlayer())) {
            event.setCancelled(true);
            event.getBlockClicked().getRelative(event.getBlockFace()).setType(Material.AIR);
        } else {
            Main.getGame().falling.remove(event.getPlayer());
            event.getPlayer().getInventory().clear();
            Main.getGame().getInventory().setInv(event.getPlayer());

            Block water = event.getBlockClicked().getRelative(event.getBlockFace());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if(Bukkit.getWorlds().contains(water.getWorld())) {
                        water.setType(Material.AIR);
                    }
                }
            }, Config.getInteger("block-duration-until-disapear") * 20);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

}
