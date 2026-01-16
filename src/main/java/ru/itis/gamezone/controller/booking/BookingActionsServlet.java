package ru.itis.gamezone.controller.booking;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.model.Booking;
import ru.itis.gamezone.service.BookingService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


@WebServlet("/booking-actions")
public class BookingActionsServlet extends HttpServlet {

    private BookingService bookingService;

    public void init () {
        bookingService = (BookingService)getServletContext().getAttribute("bookingService");
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Long accountId = ((Account) session.getAttribute("account")).getId();
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (idStr == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=invalid");
            return;
        }

        Long bookingId = Long.parseLong(idStr);
        Booking b = bookingService.getBookingById(bookingId)
                .orElse(null);
        if (b == null || !b.getAccountId().equals(accountId)) {
            response.sendRedirect(request.getContextPath() + "/my-bookings?error=access");
            return;
        }

        if ("delete".equals(action)) {
            bookingService.deleteBooking(bookingId);
        }
        if ("cancel".equals(action)) {
            bookingService.updateStatus(bookingId, "cancelled");
        }

        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }


}
