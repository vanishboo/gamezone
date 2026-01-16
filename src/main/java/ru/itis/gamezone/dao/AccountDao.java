package ru.itis.gamezone.dao;

import ru.itis.gamezone.dao.interfaces.IAccountDao;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountDao implements IAccountDao {

    private static final String SQL_SELECT_ALL = "SELECT * FROM account";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM account WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM account WHERE username = ?";
    private static final String SQL_INSERT = "INSERT INTO account(username, password) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE account SET username = ?, password = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM account WHERE id = ?";

    public List<Account> getAll() {

        List<Account> accounts = new ArrayList<>();
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while(resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getLong("id"));
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Account> getById(long id) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            pr.setLong(1, id);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {

                    Account account = new Account();
                    account.setId(resultSet.getLong("id"));
                    account.setUsername(resultSet.getString("username"));
                    account.setPassword(resultSet.getString("password"));

                    return Optional.of(account);
                }
            return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Account> getByUsername(String username) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_NAME)) {
            pr.setString(1, username);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {

                    Account account = new Account();
                    account.setId(resultSet.getLong("id"));
                    account.setUsername(resultSet.getString("username"));
                    account.setPassword(resultSet.getString("password"));
                    return Optional.of(account);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Account account) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_INSERT)) {

            pr.setString(1, account.getUsername());
            pr.setString(2, account.getPassword());
            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Account account) {
        try(Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_UPDATE)) {

            pr.setString(1, account.getUsername());
            pr.setString(2, account.getPassword());
            pr.setLong(3, account.getId());
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


}
