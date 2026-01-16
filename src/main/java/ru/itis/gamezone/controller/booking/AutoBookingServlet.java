package ru.itis.gamezone.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.service.BookingService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/auto-booking")
public class AutoBookingServlet extends HttpServlet {

    private BookingService bookingService;

    public void init() throws ServletException {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/auto-booking.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        Long roomId = Long.parseLong(request.getParameter("roomId"));
        LocalDate date = LocalDate.parse(request.getParameter("bookingDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        int players = Integer.parseInt(request.getParameter("players"));

        LocalDateTime start = date.atTime(startTime);
        Long accountId = ((Account) session.getAttribute("account")).getId();

        List<Computer> chosen = bookingService.findAvailableComputers(roomId, start, duration, players);


        if (chosen.isEmpty()) {
            request.setAttribute("error", "Недостаточно свободных компьютеров для этого времени");
            request.getRequestDispatcher("/auto-booking.ftlh").forward(request, response);
            return;
        }

        session.setAttribute("autoChosenComputers", chosen);
        session.setAttribute("autoStart", start);
        session.setAttribute("autoDuration", duration);
        session.setAttribute("autoAccountId", accountId);

        response.sendRedirect(request.getContextPath() + "/auto-confirm");

    }
}

