package com.filikr.tourn.gameCommands;


import com.filikr.tourn.Tourn;
import com.filikr.tourn.database.GRUDUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandManager {

    private static CommandManager instance;

    public CommandManager(){
        instance = this;
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public Set<Command> getCommands () {
        return commands;
    }

    private final Set<Command> commands = new HashSet<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void dellCommand(String name) {
        Command command = findCommandByName(name);
        GRUDUtils.dellCommand(command);
        commands.remove(command);
    }

    public void editNameCommand(String name, String newName) {
        findCommandByName(name).setNameCommand(newName);
    }
    public void editColorCommand(String name, int r, int g, int b) {
        findCommandByName(name).setColor(r, g, b);
    }

    public void addPlayerToCommand(String name, String nickname) {
        findCommandByName(name).setPlayer(getUUID(nickname));
    }

    public void removePlayerToCommand(String name, String nickname) {
        findCommandByName(name).dellPlayer(getUUID(nickname));
    }

    public Command findCommandByName(String name) {
        for (Command command : commands) {
            if (command.getNameCommand().equals(name)) {
                return command;
            }
        }
        return null;
    }

    public UUID getUUID(String playerName) {
        Player onlinePlayer = Bukkit.getPlayerExact(playerName);
        if (onlinePlayer != null) {
            return onlinePlayer.getUniqueId();
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getUniqueId();
        }

        // Если игрок никогда не заходил на сервер
        return null;
    }

    public List<String> getNameCommands() {
        return commands.stream().map(Command::getNameCommand).collect(Collectors.toList());
    }


}
