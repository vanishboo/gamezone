package ru.itis.gamezone.dao.interfaces;

import ru.itis.gamezone.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountDao {

    List<Account> getAll();
    Optional<Account> getById(long id);
    Optional<Account> getByUsername(String username);
    void save(Account account);
    void update(Account account);
    boolean deleteById(long id);


}
