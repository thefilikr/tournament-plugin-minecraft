package com.filikr.tourn;

import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.entity.Player;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GRUDUtils {

    public static Set<Command> getCommands(String query) {
        Set<Command> commands = new HashSet<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_command");
                String name = rs.getString("name");

                Set<UUID> uuid = new HashSet<>();
                try (Connection subConnection = DBUtils.getConnection();
                     PreparedStatement subPreparedStatement = subConnection.prepareStatement("SELECT UUID FROM players WHERE command = " + id)) {
                    ResultSet subRs = subPreparedStatement.executeQuery();
                    while (subRs.next()) uuid.add(UUID.fromString(subRs.getString("UUID")));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                ColorCommand color = new ColorCommand(rs.getInt("r"), rs.getInt("g"), rs.getInt("b"));

                Command command = new Command(name, color);
                command.setPlayers(uuid);
                commands.add(command);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return commands;
    }

    public static void addCommand(Command command) {
        String query = "INSERT INTO commands (name, r, g, b) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, command.getNameCommand());
            preparedStatement.setInt(2, command.getColor()[0]);
            preparedStatement.setInt(3, command.getColor()[1]);
            preparedStatement.setInt(4, command.getColor()[2]);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dellCommand(Command command) {
        String query = "SELECT id_command FROM commands WHERE name_command = ?";

        int idCommand =  0;

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, command.getNameCommand());

            ResultSet rs = preparedStatement.executeQuery();

            idCommand = rs.getInt("id_command");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "UPDATE players SET command_id = ? WHERE command_id = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, -1);
            preparedStatement.setInt(2, idCommand);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "DELETE FROM commands WHERE id_command = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idCommand);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void editCommand(Command command) {
        String query = "UPDATE commands SET name = ?, r = ?, g = ?, b = ? WHERE id_command = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, command.getNameCommand());
            preparedStatement.setInt(2, command.getColor()[0]);
            preparedStatement.setInt(3, command.getColor()[1]);
            preparedStatement.setInt(4, command.getColor()[2]);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addPlayer(UUID uuid) {
        String query = "INSERT INTO players (uuid) VALUES (?)";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void dellPlayer(UUID uuid) {
        String query = "DELETE FROM players WHERE uuid = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setPlayersToCommand(String nameCommand, UUID uuid) {
        String query = "SELECT id_command FROM commands WHERE name_command = ?";
        int id_command = 0;
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameCommand);

            ResultSet rs = preparedStatement.executeQuery();
            id_command = rs.getInt("id_command");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "UPDATE players SET command_id = ? WHERE uuid = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_command);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void dellPlayersToCommand(String nameCommand, UUID uuid) {
        String query = "SELECT id_command FROM commands WHERE name_command = ?";
        int id_command = 0;
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameCommand);

            ResultSet rs = preparedStatement.executeQuery();
            id_command = rs.getInt("id_command");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "UPDATE players SET command_id = ? WHERE uuid = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, -1);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
