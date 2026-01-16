package ru.itis.gamezone.dao;

import ru.itis.gamezone.dao.interfaces.IBookingDao;
import ru.itis.gamezone.dao.interfaces.IComputerDao;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.model.Game;
import ru.itis.gamezone.util.DataBaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ComputerDao implements IComputerDao {

    private static final String SQL_SELECT_ALL = "SELECT id, room_id, is_available FROM computer";
    private static final String SQL_SELECT_BY_ID = "SELECT id, room_id, is_available FROM computer WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO computer(room_id) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE computer SET room_id = ?, is_available = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
    private static final String SQL_UPDATE_AVAILABLE = "UPDATE computer SET is_available = ? WHERE id = ?";

    private static final String SQL_SELECT_AVAILABLE = """
                        select c.* from computer c
                        where c.room_id = ? and c.is_available = true and
                        c.id not in
                        (SELECT computer_id FROM booking
                        WHERE start_time < ? AND end_time > ?
                        );
                        """;
    private static final String SQL_SELECT_GAMES_BY_COMPUTER_ID = """
            
            select g.id, g.name
            from game g
            left join public.computer_game cg on g.id = cg.game_id
            where computer_id = ?
            """;
    private static final String SQL_INSERT_GAME = "insert into computer_game (computer_id, game_id) VALUES (?, ?)";


    public List<Computer> getAll() {
        List<Computer> computers = new ArrayList<>();
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Computer computer = getComputer(resultSet);
                computers.add(computer);
            }
            return computers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Computer> getById(long id) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            pr.setLong(1, id);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {
                    Computer computer = getComputer(resultSet);

                    return Optional.of(computer);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void save(Computer computer) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_INSERT)) {

            pr.setLong(1, computer.getRoomId());
            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Computer computer) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_UPDATE)) {

            pr.setLong(1, computer.getRoomId());
            pr.setBoolean(2, computer.getAvailable());
            pr.setLong(3, computer.getId());
            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateAvailable(Long bookingId, boolean available) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_AVAILABLE)) {

            pr.setBoolean(1, available);
            pr.setLong(2, bookingId);
            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    
    public boolean deleteById(long id) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_DELETE)) {

            pr.setLong(1, id);
            int cntUpdate = pr.executeUpdate();
            return cntUpdate > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Computer> findAvailableComputers(Long roomId, LocalDateTime start, int duration) {
        List<Computer> computers = new ArrayList<>();
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_AVAILABLE)) {
            pr.setLong(1, roomId);
            LocalDateTime end = start.plusMinutes(duration);

            pr.setTimestamp(2, Timestamp.valueOf(end)); // end
            pr.setTimestamp(3, Timestamp.valueOf(start)); // start

            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    Computer computer = getComputer(resultSet);
                    computers.add(computer);
                }
            }
            return computers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Set<Game> getGamesByComputerId(Long computerId) {
        Set<Game> games = new HashSet<>();
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_GAMES_BY_COMPUTER_ID)) {
            pr.setLong(1, computerId);
            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {
                    Game game = new Game();
                    game.setId(resultSet.getLong("id"));
                    game.setName(resultSet.getString("name"));
                    games.add(game);
                }
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addGameToComputer(Long computerId, Long gameId) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_INSERT_GAME)) {
            ps.setLong(1, computerId);
            ps.setLong(2, gameId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private static Computer getComputer(ResultSet resultSet) throws SQLException {
        Computer computer = new Computer();
        computer.setId(resultSet.getLong("id"));
        computer.setRoomId(resultSet.getLong("room_id"));
        computer.setAvailable(resultSet.getBoolean("is_available"));
        return computer;
    }
}
