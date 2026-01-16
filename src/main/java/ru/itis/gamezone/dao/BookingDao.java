package ru.itis.gamezone.dao;

import ru.itis.gamezone.dao.interfaces.IBookingDao;
import ru.itis.gamezone.model.Booking;
import ru.itis.gamezone.util.DataBaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDao implements IBookingDao {


    private final static String SQL_INSERT = "insert into booking(account_id, total_price, computer_id," +
            " start_time, end_time, status)" +
            "values (?, ?, ?, ?, ?, ?)";
    private final static String SQL_DELETE = "delete from booking where id=?";
    private final static String SQL_UPDATE = "update booking set computer_id = ?, start_time = ?, end_time = ?, total_price = ? where id = ?";
    private final static String SQL_UPDATE_STATUS = "update booking set status = ? where id = ?";
    private final static String SQL_UPDATE_TIME = "update booking set start_time = ?, end_time = ? where id = ?";
    private final static String SQL_UPDATE_PRICE = "update booking set total_price = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from booking where id = ?";
    private final static String SQL_SELECT_ALL_BOOKINGS = "select * from booking";
    private final static String SQL_SELECT_ALL_BOOKINGS_ONE_DAY = "select * from booking where start_time::date = ? and computer_id = ? order by start_time";
    private final static String SQL_SELECT_NEAREST_BOOKING = "select start_time from booking where start_time > ? and start_time < ? and computer_id = ? order by start_time limit 1";
    private final static String SQL_SELECT_BOOKING_BY_ACCOUNT_ID = "select * from booking where account_id = ? order by start_time desc";



    public void save(Booking booking) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            preparedStatement.setLong(1, booking.getAccountId());
            preparedStatement.setInt(2, booking.getTotalPrice());
            preparedStatement.setLong(3, booking.getComputerId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(booking.getStartTime()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(booking.getEndTime()));
            preparedStatement.setString(6, booking.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(long id) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_DELETE)) {

            pr.setLong(1, id);

            int cntUpdate = pr.executeUpdate();
            return cntUpdate > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Long bookingId, Long computerId, LocalDateTime startTime, LocalDateTime endTime, Integer price) {

        try (Connection connection = DataBaseUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(SQL_UPDATE)) {
            pr.setLong(1, computerId);
            pr.setTimestamp(2, Timestamp.valueOf(startTime));
            pr.setTimestamp(3, Timestamp.valueOf(endTime));
            pr.setInt(4, price);
            pr.setLong(5, bookingId);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Только статус
    public void updateStatus(Long bookingId, String newStatus) {

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_STATUS)) {

            pr.setString(1, newStatus);
            pr.setLong(2, bookingId);

            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Только время
    public void updateTime(Long bookingId, LocalDateTime newStart, LocalDateTime newEnd) {

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_TIME)) {

            pr.setTimestamp(1, Timestamp.valueOf(newStart));
            pr.setTimestamp(2, Timestamp.valueOf(newEnd));
            pr.setLong(3, bookingId);

            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Только цена
    public void updatePrice(Long bookingId, Double newPrice) {

        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_UPDATE_PRICE)) {

            pr.setDouble(1, newPrice);
            pr.setLong(2, bookingId);

            pr.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Booking> getById(Long bookingId) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            pr.setLong(1, bookingId);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {

                    Booking booking = getBooking(resultSet);

                    return Optional.of(booking);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_SELECT_ALL_BOOKINGS);
             ResultSet resultSet = pr.executeQuery()) {
            while (resultSet.next()) {

                Booking booking = getBooking(resultSet);

                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Booking> getAllBookings(LocalDate bookingDate, Long computerId) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_SELECT_ALL_BOOKINGS_ONE_DAY)) {


            pr.setDate(1, Date.valueOf(bookingDate));
            pr.setLong(2, computerId);

            try (ResultSet resultSet = pr.executeQuery()) {
                while (resultSet.next()) {

                    Booking booking = getBooking(resultSet);

                    bookings.add(booking);
                }
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Booking getBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setId(resultSet.getLong("id"));
        booking.setAccountId(resultSet.getLong("account_id"));
        booking.setTotalPrice(resultSet.getInt("total_price"));
        booking.setComputerId(resultSet.getLong("computer_id"));
        booking.setStatus(resultSet.getString("status"));

        Timestamp startTimestamp = resultSet.getTimestamp("start_time");
        if (startTimestamp != null) {
            booking.setStartTime(startTimestamp.toLocalDateTime());
        }

        Timestamp endTimestamp = resultSet.getTimestamp("end_time");
        if (endTimestamp != null) {
            booking.setEndTime(endTimestamp.toLocalDateTime());
        }
        return booking;
    }

    public Optional<LocalDateTime> getNearestBookingTime(LocalDateTime bookingDate, Long computerId) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_SELECT_NEAREST_BOOKING)) {
            Timestamp ts1 = Timestamp.valueOf(bookingDate);
            Timestamp ts2 = Timestamp.valueOf(bookingDate.plusHours(10));
            pr.setTimestamp(1, ts1);
            pr.setTimestamp(2, ts2);
            pr.setLong(3, computerId);
            try (ResultSet resultSet = pr.executeQuery()) {
                if (resultSet.next()) {

                    return Optional.of(resultSet.getTimestamp("start_time").toLocalDateTime());
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Booking> getBookingsByAccountId(Long accountId) {

        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(SQL_SELECT_BOOKING_BY_ACCOUNT_ID)) {

                pr.setLong(1, accountId);
                ResultSet resultSet = pr.executeQuery();
                while (resultSet.next()) {

                    Booking booking = getBooking(resultSet);

                    bookings.add(booking);
                }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return bookings;

    }
}
