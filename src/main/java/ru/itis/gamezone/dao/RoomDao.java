package ru.itis.gamezone.dao;

import ru.itis.gamezone.dao.interfaces.IRoomDao;
import ru.itis.gamezone.model.Room;
import ru.itis.gamezone.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoomDao implements IRoomDao {

    private static final String SQL_SELECT = "SELECT * FROM rooms WHERE id = ?";


    public Optional<Room> findById(Long id) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Room room = new Room();
                    room.setId(resultSet.getLong("id"));
                    room.setName(resultSet.getString("name"));
                    room.setType(resultSet.getString("type"));
                    room.setHourPrice(resultSet.getInt("hour_price"));

                    return Optional.of(room);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
