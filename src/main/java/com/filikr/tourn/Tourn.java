package com.filikr.tourn;

import com.filikr.tourn.commands.CommandCommand;
import com.filikr.tourn.game.GameManager;
import com.filikr.tourn.gameCommands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tourn extends JavaPlugin {

    private static Tourn instance;
    private static final GameManager gameManager = new GameManager();
    private static final CommandManager commandManager = new CommandManager();


    @Override
    public void onEnable() {
        System.out.println("Tourn plugin start!");

        instance = this;

        getCommand("command").setExecutor(new CommandCommand());

        getServer().getPluginManager().registerEvents(new Player(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Tourn plugin stop!");
    }

    public static Tourn getInstance() {
        return instance;
    }
}
