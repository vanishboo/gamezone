package ru.itis.gamezone.service;


import ru.itis.gamezone.dao.interfaces.IRoomDao;
import ru.itis.gamezone.model.Room;

import java.util.Optional;

public class RoomService {
    private final IRoomDao roomDao;

    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }


    public Optional<Room> findById(long l) {
        return roomDao.findById(l);
    }
}
