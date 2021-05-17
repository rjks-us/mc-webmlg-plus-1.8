package us.rjks.game;

import org.bukkit.entity.Player;
import us.rjks.utils.*;

import java.util.ArrayList;

/***************************************************************************
 *
 *  Urheberrechtshinweis
 *  Copyright Ⓒ Robert Kratz 2021
 *  Erstellt: 25.04.2021 / 16:27
 *
 **************************************************************************/

public class GameManager {

    private MapManager.Map currentMap;
    private MapManager.Map forcemap;
    private ScoreBoard scoreBoard;
    private Counter mapchange;
    private Inventory inventory;
    public ArrayList<Player> falling = new ArrayList<>();
    private boolean setup;

    public GameManager() {
        mapchange = new Counter(Type.MAP, Config.getInteger("map-change-counter"));
        scoreBoard = new ScoreBoard();
        inventory = new Inventory();
    }

    public MapManager.Map getCurrentMap() {
        return currentMap;
    }

    public Counter getMapchange() {
        return mapchange;
    }

    public MapManager.Map getForcemap() {
        return forcemap;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isSetup() {
        return setup;
    }

    public void setForcemap(MapManager.Map forcemap) {
        this.forcemap = forcemap;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public void setMapchange(Counter mapchange) {
        this.mapchange = mapchange;
    }

    public void setCurrentMap(MapManager.Map currentMap) {
        this.currentMap = currentMap;
    }
}