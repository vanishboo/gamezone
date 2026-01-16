package ru.itis.gamezone.dao.interfaces;

import ru.itis.gamezone.model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IBookingDao {

    void save(Booking booking);

    boolean deleteById(long id);

    void update(Long bookingId, Long computerId, LocalDateTime startTime, LocalDateTime endTime, Integer price);

    void updateStatus(Long bookingId, String newStatus);

    void updateTime(Long bookingId, LocalDateTime newStart, LocalDateTime newEnd);

    void updatePrice(Long bookingId, Double newPrice);

    Optional<Booking> getById(Long bookingId);

    List<Booking> getAllBookings();

    List<Booking> getAllBookings(LocalDate bookingDate, Long computerId);

    Optional<LocalDateTime> getNearestBookingTime(LocalDateTime bookingDate, Long computerId);

    List<Booking> getBookingsByAccountId(Long accountId);
}
