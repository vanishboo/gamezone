package ru.itis.gamezone.controller.booking;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.model.Booking;
import ru.itis.gamezone.service.BookingService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@WebServlet("/edit-booking")
public class EditBookingServlet extends HttpServlet {
    private BookingService bookingService;

    public void init() {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long bookingId = Long.parseLong(request.getParameter("id"));
        Account account = (Account) request.getSession().getAttribute("account");
        String error = request.getParameter("error");

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);

        if (bookingOpt.isEmpty() || !bookingOpt.get().getAccountId().equals(account.getId())) {
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=not_found");
            return;
        }
        if (error != null) {
            request.setAttribute("error", error);
        }

        request.setAttribute("booking", bookingOpt.get());
        request.setAttribute("freeSlots", bookingService.getTimeMap());

        request.getRequestDispatcher("/edit-booking.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long bookingId = Long.parseLong(request.getParameter("bookingId"));
        Account account = (Account) request.getSession().getAttribute("account");


        LocalDate newDate = LocalDate.parse(request.getParameter("date"));
        LocalTime newTime = LocalTime.parse(request.getParameter("time"));
        int newDuration = Integer.parseInt(request.getParameter("duration"));

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isEmpty() || !bookingOpt.get().getAccountId().equals(account.getId())) {
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=not_found");
            return;
        }

        Booking oldBooking = bookingOpt.get();

        LocalDateTime newStart = newDate.atTime(newTime);
        LocalDateTime newEnd = newStart.plusMinutes(newDuration);
        int newPrice = bookingService.getPrice(oldBooking.getComputerId(), newDuration);

        if (bookingService.checkIntersect(oldBooking, newStart, newEnd)) {
            response.sendRedirect(request.getContextPath() + "/edit-booking?id=" + bookingId + "&error=intersect");
            return;
        }

        bookingService.updateBooking(bookingId, oldBooking.getComputerId(), newStart, newEnd, newPrice);
        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }

}