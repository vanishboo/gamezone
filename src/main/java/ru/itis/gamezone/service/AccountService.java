package ru.itis.gamezone.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.gamezone.dao.AccountDao;
import ru.itis.gamezone.dao.BookingDao;
import ru.itis.gamezone.dao.interfaces.IAccountDao;
import ru.itis.gamezone.dao.interfaces.IBookingDao;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.model.Booking;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final IAccountDao accountDao;
    private final IBookingDao bookingDao;
    private final BCryptPasswordEncoder encoder;

    public AccountService(IAccountDao accountDao, IBookingDao bookingDao, BCryptPasswordEncoder encoder) {
        this.accountDao = accountDao;
        this.bookingDao = bookingDao;
        this.encoder = encoder;
    }

    public void register(String username, String password) {
        String encodedPassword = encoder.encode(password);
        accountDao.save(new Account(username, encodedPassword));
    }

    public boolean checkUserExists(String username) {
        return accountDao.getByUsername(username).isPresent();
    }

    public boolean checkPassword(String rawPassword, String hashPassword) {
        return encoder.matches(rawPassword, hashPassword);
    }

    public boolean checkCorrect(String username, String password) {

        if (username.length() <= 3 || username.length() >= 20 ||
                password.length() < 8) {
            return false;
        }
        return true;
    }

    public Optional<Account> authenticate(String username, String password) {
        if (accountDao.getByUsername(username).isPresent()) {
            Account account = accountDao.getByUsername(username).get();
            if (checkPassword(password, account.getPassword())) {
                return Optional.of(account);
            };
        }
        return Optional.empty();
    }

    public Account getById(Long id) {
        return accountDao.getById(id).orElse(null);
    }

    public int countActiveBookings(Long accountId) {
        List<Booking> bookings = bookingDao.getBookingsByAccountId(accountId);

        return (int) bookings.stream()
                .filter(b -> b.getStatus().equals("active") || b.getStatus().equals("confirmed"))
                .count();
    }
}
