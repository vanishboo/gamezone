package ru.itis.gamezone.dao.interfaces;

import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.model.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IComputerDao {

    List<Computer> getAll();
    Optional<Computer> getById(long id);
    void save(Computer computer);
    void update(Computer computer);
    void updateAvailable(Long computerId, boolean available);
    boolean deleteById(long id);
    List<Computer> findAvailableComputers(Long roomId, LocalDateTime start, int duration);
    Set<Game> getGamesByComputerId(Long computerId);
    void addGameToComputer(Long computerId, Long gameId);

}
