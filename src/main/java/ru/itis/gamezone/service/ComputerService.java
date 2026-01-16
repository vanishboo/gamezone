package ru.itis.gamezone.service;

import ru.itis.gamezone.dao.interfaces.IComputerDao;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.model.Game;

import java.util.List;
import java.util.Set;

public class ComputerService {
    private final IComputerDao computerDao;

    public ComputerService(IComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    public List<Computer> getAll() {
        List<Computer> computers = computerDao.getAll();
        computers.forEach(this::updateGames);
        return computers;
    }

    public void updateGames(Computer computer) {
        computer.setGames(computerDao.getGamesByComputerId(computer.getId()));
    }

    public Set<Game> getGames(Long computer_id) {
        return computerDao.getGamesByComputerId(computer_id);
    }


    public Computer getById(Long computerId) {
        return computerDao.getById(computerId).orElse(null);
    }
}
