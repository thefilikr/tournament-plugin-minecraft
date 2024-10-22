package com.filikr.tourn.gameCommands;

import java.util.*;

import com.filikr.tourn.database.GRUDUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Command {

    private String nameCommand;
    private final Set<UUID> players = new HashSet<>();
    private final ColorCommand color = new ColorCommand(0, 0, 0);

    private int initiativePoints;
    private int activePoints;
    private int passivePoints;

    public Command(String nameCommand, ColorCommand color) {
        this.nameCommand = nameCommand;
        this.color.setColor(color.getColor()[0], color.getColor()[1], color.getColor()[2]);
        GRUDUtils.addCommand(this);
    }


    //==POINTS==//
    public int getInitiativePoints() {
        return initiativePoints;
    }
    public void setInitiativePoints(int points) {
        this.initiativePoints += points;
    }

    public int getActivePoints() {
        return activePoints;
    }
    public void setActivePoints(int points) {
        this.activePoints += points;
    }

    public int getPassivePoints() {
        return passivePoints;
    }
    public void setPassivePoints(int points) {
        this.passivePoints += points;
    }

    //==PLAYERS==//
    public Set<String> getPlayers() {
        Set<String> playersNames = new HashSet<>();
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) playersNames.add(player.getName());
        }
        return playersNames;
    }
    public void setPlayer(UUID playerUUID){
        this.players.add(playerUUID);
        GRUDUtils.setPlayersToCommand(this.getNameCommand(), playerUUID);
    }
    public void dellPlayer(UUID playerUUID) {
        this.players.remove(playerUUID);
        GRUDUtils.dellPlayersToCommand(this.getNameCommand(), playerUUID);
    }
    public void setPlayers(Set<UUID> players){
        this.players.addAll(players);
    }
    public int getCountPlayers() {
        return this.players.size();
    }

    //==COMMAND==//
    public void setNameCommand(String nameCommand) {
        this.nameCommand = nameCommand;
        GRUDUtils.editCommand(this);
    }
    public String getNameCommand() {
        return nameCommand;
    }
    public void  setColor(int r, int g, int b) {
        color.setColor(r, g, b);
        GRUDUtils.editCommand(this);
    }
    public int[] getColor() {
        return color.getColor();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return nameCommand.equals(command.nameCommand);
    }

    @Override
    public int hashCode() {
        return nameCommand.hashCode();
    }
}


