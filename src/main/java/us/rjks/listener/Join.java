package us.rjks.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import us.rjks.game.GameManager;
import us.rjks.game.Main;
import us.rjks.utils.*;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.04.2021 / 16:27
 *
 **************************************************************************/

public class Join implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Config.getBoolean("enable-rank-system") && Config.getBoolean("enable-tab-rank")) {
            TabList.setTabList(event.getPlayer());
        }

        if(Config.getBoolean("enable-player-joins-message")) {
            event.setJoinMessage(Messages.getString("player-joins-message").replaceAll("%player%", event.getPlayer().getName()));
        }

        event.getPlayer().sendMessage(Messages.getString("join-welcome-message").replaceAll("%player%", event.getPlayer().getName()));

        if(Config.getBoolean("show-score-board")) {
            Main.getGame().getScoreBoard().setScoreBoard(event.getPlayer());
        }

        if (Main.getGame().getCurrentMap() != null) {
            Main.getGame().getInventory().setInv(event.getPlayer());
            event.getPlayer().teleport(Main.getGame().getCurrentMap().getRandomLocationCollection("spawn"));
        }

    }

}
