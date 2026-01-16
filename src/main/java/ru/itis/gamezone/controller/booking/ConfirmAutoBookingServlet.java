package ru.itis.gamezone.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.service.BookingService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/auto-confirm")
public class ConfirmAutoBookingServlet extends HttpServlet {
    BookingService bookingService;

    public void init() throws ServletException {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (!isValidAutoBookingSession(session)) {
            response.sendRedirect(request.getContextPath() + "/auto-booking");
            return;
        }

        List<Computer> computers = (List<Computer>) session.getAttribute("autoChosenComputers");
        LocalDateTime start = (LocalDateTime) session.getAttribute("autoStart");
        int duration = (Integer) session.getAttribute("autoDuration");


        int totalPrice = 0;
        for (Computer comp : computers) {
            totalPrice += bookingService.getPrice(comp.getId(), duration);
        }

        request.setAttribute("computers", computers);
        request.setAttribute("bookingDate", start.toLocalDate());
        request.setAttribute("startTime", start.toLocalTime());
        request.setAttribute("duration", duration);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/auto-confirm.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();

        List<Computer> computers = (List<Computer>) session.getAttribute("autoChosenComputers");
        LocalDateTime start = (LocalDateTime) session.getAttribute("autoStart");
        int duration = (Integer) session.getAttribute("autoDuration");
        Long accountId = (Long) session.getAttribute("autoAccountId");


        for (Computer comp : computers) {
            int price = bookingService.getPrice(comp.getId(), duration);
            bookingService.saveBooking(accountId, comp.getId(), start, start.plusMinutes(duration), price);
        }

        session.removeAttribute("autoBooking");
        session.removeAttribute("autoChosenComputers");
        session.removeAttribute("autoStart");
        session.removeAttribute("autoDuration");
        session.removeAttribute("autoAccountId");


        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }

    private boolean isValidAutoBookingSession(HttpSession session) {
        if (session == null) return false;

        return session.getAttribute("autoChosenComputers") != null &&
                session.getAttribute("autoStart") != null &&
                session.getAttribute("autoDuration") != null &&
                session.getAttribute("autoAccountId") != null;
    }
}
