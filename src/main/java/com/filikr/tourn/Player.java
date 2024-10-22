package com.filikr.tourn;

import com.filikr.tourn.database.GRUDUtils;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Player implements Listener {

    public void onPlayerJoin(PlayerJoinEvent event) {
        GRUDUtils.addPlayer(event.getPlayer().getUniqueId());
    }
}
