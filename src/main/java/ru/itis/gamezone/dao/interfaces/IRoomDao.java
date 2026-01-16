package ru.itis.gamezone.dao.interfaces;

import ru.itis.gamezone.model.Room;

import java.util.Optional;

public interface IRoomDao {

    Optional<Room> findById(Long id);

}
