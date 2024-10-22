package com.filikr.tourn.commands;

import com.filikr.tourn.game.GameManager;
import com.filikr.tourn.gameCommands.ColorCommand;
import com.filikr.tourn.gameCommands.Command;
import com.filikr.tourn.gameCommands.CommandManager;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CommandCommand implements CommandExecutor {

//    public CommandCommand(){ super("command"); }

    private int[] validationColor(CommandSender commandSender, String _r, String _g, String _b) {
        try {
            int r = Integer.parseInt(_r);
            int g = Integer.parseInt(_g);
            int b = Integer.parseInt(_b);

            // Валидация значений цвета
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                commandSender.sendMessage("Значения цветов должны быть в диапазоне от 0 до 255");
                return null;
            }

            return new int[]{r, g, b};

        } catch (NumberFormatException e) {
            commandSender.sendMessage("Значения r, g и b должны быть целыми числами.");
        }
        return null;
    }



//    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        if(args.length == 0) {
            commandSender.sendMessage("/command add <name> <r> <g> <b>\n/command remove <name>\n/command edit name <name>\n/command edit color <r> <g> <b>\n/command addPlayer <name> <nickname>\n/command removePlayer <name> <nickname>\n/command info <name>\n/command infoPoints <name>\n/command list");
            return;
        }

        switch (args[0]) {
            case "add":
                if (args.length != 5) {
                    commandSender.sendMessage("/command add <name> <r> <g> <b>");
                    break;
                }

                int[] rgb = validationColor(commandSender, args[2], args[3], args[4]);
                if (rgb == null) break;

                CommandManager.getInstance().addCommand(new Command(args[1], new ColorCommand(rgb[0], rgb[1], rgb[2])));
                commandSender.sendMessage("Команда добавлена: " + args[1]);
                break;

            case "remove":
                if (args.length != 2) {
                    commandSender.sendMessage("/command remove <name>");
                    break;
                }

                CommandManager.getInstance().dellCommand(args[1]);
                commandSender.sendMessage("Команда удалена: " + args[1]);
                break;

            case "edit":
                switch (args[1]) {
                    case "name":
                        if (args.length !=3) {
                            commandSender.sendMessage("/command edit name <newName>");
                            break;
                        }

                        CommandManager.getInstance().editNameCommand(args[1], args[2]);

                        break;
                    case "color":
                        if (args.length !=5) {
                            commandSender.sendMessage("/command edit color <r> <g> <b>");
                            break;
                        }

                        rgb = validationColor(commandSender, args[2], args[3], args[4]);
                        if (rgb == null) break;

                        CommandManager.getInstance().editColorCommand(args[1], rgb[0], rgb[1], rgb[2]);
                        break;
                    default:
                        commandSender.sendMessage("Можно изменить только name или color");
                }

            case "addPlayer":
                if (args.length != 3) {
                    commandSender.sendMessage("/command addPlayer <name> <nickname>");
                    break;
                }

                CommandManager.getInstance().addPlayerToCommand(args[1], args[2]);
                break;

            case "removePlayer":
                if (args.length != 3) {
                    commandSender.sendMessage("/command removePlayer <name> <nickname>");
                    break;
                }

                CommandManager.getInstance().removePlayerToCommand(args[1], args[2]);
                break;

            case "list":
                if (args.length != 1) {
                    commandSender.sendMessage("/command list");
                    break;
                }
                commandSender.sendMessage(CommandManager.getInstance().getCommands().toString());
                break;

            default:
                commandSender.sendMessage("Неизвестная команда. Использование: /command <add|remove> <args>");
                break;
        }
        commandSender.sendMessage("Not this command!");
        return;
    }

//    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                return Lists.newArrayList("add", "remove", "edit", "addPlayer", "removePlayer", "info", "infoPoints", "list");

            case 2:
                if (args[0] == "add" |
                        args[0] == "remove" |
                        args[0] == "addPlayer" |
                        args[0] == "info" |
                        args[0] == "infoPoints") {
                    return Lists.newArrayList(CommandManager.getInstance().getNameCommands());
                }
                if (args[0] == "edit") return Lists.newArrayList("name", "color");
                return Lists.newArrayList();

            case 3:
                if (args[0] == "addPlayer" | args[0] == "removePlayer") {
                    List<String> onlinePlayerNames = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
                    return Lists.newArrayList(onlinePlayerNames);
                }
                return Lists.newArrayList();

            default:
                return Lists.newArrayList();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, s, strings);
        return true;
    }
}
