package us.rjks.listener;

import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import us.rjks.game.Main;
import us.rjks.utils.KitManager;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 17.05.2021 / 15:17
 *
 **************************************************************************/

public class Death implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setDeathMessage(null);
        event.getDrops().clear();

        Main.getGame().falling.remove(event.getEntity().getPlayer());
        respawn(event.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerRespawnEvent event) {
        if (!Main.getGame().isSetup()) {
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    event.getPlayer().getInventory().clear();
                    Main.getGame().getInventory().setInv(event.getPlayer());
                    Main.getGame().getCurrentMap().teleportPlayerToRandomLocationCollection(event.getPlayer(), "spawn");
                }
            }, 1);
        }
    }

    private void respawn(final Player p) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

            @Override
            public void run() {
                ((CraftPlayer) p).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
            }
        }, 10);
    }
}
