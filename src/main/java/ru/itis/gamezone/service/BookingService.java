package ru.itis.gamezone.service;

import ru.itis.gamezone.dao.AccountDao;
import ru.itis.gamezone.dao.BookingDao;
import ru.itis.gamezone.dao.ComputerDao;
import ru.itis.gamezone.dao.RoomDao;
import ru.itis.gamezone.dao.interfaces.IAccountDao;
import ru.itis.gamezone.dao.interfaces.IBookingDao;
import ru.itis.gamezone.dao.interfaces.IComputerDao;
import ru.itis.gamezone.dao.interfaces.IRoomDao;
import ru.itis.gamezone.model.Booking;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.model.Room;

import java.time.*;
import java.util.*;

public class BookingService {

    private final IBookingDao bookingDao;
    private final IComputerDao computerDao;
    private final IRoomDao roomDao;

    public BookingService(IBookingDao bookingDao, IComputerDao computerDao, IRoomDao roomDao) {
        this.bookingDao = bookingDao;
        this.computerDao = computerDao;
        this.roomDao = roomDao;
    }

    public int getPrice(Long computerId, int duration) {

        Computer computer = computerDao.getById(computerId).get();

        Room room = roomDao.findById(computer.getRoomId()).get();

        return room.getHourPrice() * duration / 60;
    }

    public Map<String, Boolean> getFreeSlots(LocalDate bookingDate, Long computerId) {
        Map<LocalTime, Boolean> fullSlots = getTimeMap();
        for (Booking booking : bookingDao.getAllBookings(bookingDate, computerId)) {
            if (booking.getStatus().equals("cancelled")) {
                continue;
            }
            LocalTime startBooking = LocalTime.from(booking.getStartTime());
            LocalTime endBooking = LocalTime.from(booking.getEndTime());
            while (startBooking.isBefore(endBooking)) {
                fullSlots.put(startBooking, false);
                startBooking = startBooking.plusMinutes(30);
            }
            fullSlots.put(endBooking, false);
        }
        Map<String, Boolean> result = new LinkedHashMap<>();
        for (Map.Entry<LocalTime, Boolean> entry : fullSlots.entrySet()) {
            result.put(entry.getKey().toString(), entry.getValue());
        }
        return result;

    }

    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingDao.getById(bookingId);
    }

    public void updateBooking(Long bookingId, Long computerId, LocalDateTime startTime, LocalDateTime endTime, Integer price) {
        bookingDao.update(bookingId, computerId, startTime, endTime, price);
    }

    public Map<LocalTime, Boolean> getTimeMap() {
        Map<LocalTime, Boolean> timeMap = new LinkedHashMap<>();
        LocalTime startTime = LocalTime.of(0,0);
        LocalTime endTime = LocalTime.of(23, 30);
        while (startTime.isBefore(endTime)) {
            timeMap.put(startTime, true);
            startTime = startTime.plusMinutes(30);
        }
        timeMap.put(startTime, true);
        return timeMap;
    }


    public List<LocalTime> getDurations(LocalDateTime bookingDate, Long computerId) {

        LocalDateTime near = bookingDao.getNearestBookingTime(bookingDate, computerId).orElse(null);
        if (near == null) {
            return fillDurations();
        } else {
            LocalTime startBooking = LocalTime.from(bookingDate);
            LocalTime endBooking = LocalTime.from(near);
            return fillDurations(startBooking, endBooking);
        }
    }

    private List<LocalTime> fillDurations() {
        List<LocalTime> list = new ArrayList<>();
        LocalTime end = LocalTime.of(0, 30);
        while (!end.equals(LocalTime.of(10, 0))) {
            list.add(end);
            end = end.plusMinutes(30);
        }
        return list;
    }

    private List<LocalTime> fillDurations(LocalTime start, LocalTime end) {
        List<LocalTime> list = new ArrayList<>();
        LocalTime startBooking = LocalTime.of(0,30);
        start = start.plusMinutes(30);
        while (start.isBefore(end)) {
            list.add(startBooking);
            start = start.plusMinutes(30);
            startBooking = startBooking.plusMinutes(30);
        }
        list.add(startBooking);
        return list;
    }


    public void saveBooking(Long accountId, Long computerId, LocalDateTime startTime,
                            LocalDateTime endTime, int totalPrice) {

        Booking booking = new Booking();
        booking.setAccountId(accountId);
        booking.setComputerId(computerId);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalPrice(totalPrice);
        booking.setStatus("confirmed");

        bookingDao.save(booking);

    }



    public List<Computer> findAvailableComputers(Long roomId, LocalDateTime start, int duration, int players) {
        List<Computer> computers = computerDao.findAvailableComputers(roomId, start, duration);
        if (computers.size() < players) {
            return new ArrayList<>();
        }
        return computers.subList(0, players);
    }



    public List<Booking> getBookingsByAccountId(Long accountId) {
        List<Booking> bookings = bookingDao.getBookingsByAccountId(accountId);

        for (Booking booking : bookings) {
            LocalDateTime startTime = booking.getStartTime();
            LocalDateTime endTime = booking.getEndTime();
            LocalDateTime now = LocalDateTime.now();

            if (booking.getStatus().equals("cancelled")) {
                continue;
            }
            if (endTime.isBefore(now)) {
                booking.setStatus("completed");
                bookingDao.updateStatus(booking.getId(), booking.getStatus());
            } else if (startTime.isBefore(now) && endTime.isAfter(now)) {
                booking.setStatus("active");
                bookingDao.updateStatus(booking.getId(), booking.getStatus());
            }


        }
        return bookings;
    }


    public void updateStatus(Long bookingId, String status) {
        bookingDao.updateStatus(bookingId, status);
    }

    public void deleteBooking(Long bookingId) {
        bookingDao.deleteById(bookingId);
    }

    public List<Booking> getBookingsByStatusAndAccountId(Long accountId, String status) {
        List<Booking> bookings = bookingDao.getBookingsByAccountId(accountId);
        return bookings
                .stream()
                .filter(booking -> booking.getStatus().equals(status))
                .sorted(Comparator.comparing(Booking::getStartTime).reversed())
                .toList();
    }

    public boolean checkIntersect(Booking oldBooking, LocalDateTime newStart, LocalDateTime newEnd) {


        List<Booking> allBookings = bookingDao.getAllBookings(newStart.toLocalDate(), oldBooking.getComputerId());
        for (Booking booking : allBookings) {
            if (booking == oldBooking) {
                continue;
            }
            LocalDateTime oldStart = booking.getStartTime();
            LocalDateTime oldEnd = booking.getEndTime();
            if ((newStart.isAfter(oldStart) && newEnd.isBefore(oldEnd))
            || (newStart.isAfter(oldStart) && newEnd.isAfter(oldEnd))
            || (newStart.isBefore(oldStart) && newEnd.isBefore(oldEnd))
            || (newStart.isBefore(oldStart) && newEnd.isAfter(oldEnd))) {
                return true;
            }
        }
        return false;
    }
}
