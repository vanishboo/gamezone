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
import ru.itis.gamezone.service.ComputerService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private BookingService bookingService;
    private ComputerService computerService;

    public void init() {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
        computerService = (ComputerService) getServletContext().getAttribute("computerService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        request.setAttribute("edit", request.getParameter("edit"));

        String stringStep = request.getParameter("step");
        int step = stringStep == null ? 1 : Integer.parseInt(stringStep);

        String stringComputer = request.getParameter("computerId");
        Long computerId;
        if (stringComputer == null) {
            computerId = (Long) session.getAttribute("computerId");
        } else {
            computerId = Long.parseLong(stringComputer);
            session.setAttribute("computerId", computerId);
            Computer computer = computerService.getById(computerId);
            session.setAttribute("computer", computer);
        }

        switch (step) {

            case 1:
                firstStep(request, response, step); break;
            case 2:
                secondStep(request, response, step, session, computerId); break;
            case 3:
                stepThree(request, response, step, session, computerId); break;
            case 4:
                stepFour(request, response, step, session, computerId); break;
        }

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long accountId = ((Account) session.getAttribute("account")).getId();
        Long  computerId = (Long) session.getAttribute("computerId");
        LocalTime startTime = (LocalTime) session.getAttribute("startTime");
        int totalPrice = (int) session.getAttribute("price");
        int duration = (int) session.getAttribute("duration");
        LocalDate bookingDate = (LocalDate) session.getAttribute("bookingDate");


        LocalDateTime start = bookingDate.atTime(startTime);
        LocalDateTime end = start.plusMinutes(duration);


        if (start.isBefore(LocalDateTime.now())) {
            response.sendRedirect(request.getContextPath() + "/booking?step=1&error=past_time");
            return;
        }

        bookingService.saveBooking(accountId, computerId, start, end, totalPrice);

        session.removeAttribute("bookingDate");
        session.removeAttribute("startTime");
        session.removeAttribute("endTime");
        session.removeAttribute("price");
        session.removeAttribute("computerId");
        session.removeAttribute("duration");
        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }


    private void firstStep(HttpServletRequest request, HttpServletResponse response, int step) throws ServletException, IOException {
        request.setAttribute("step", step);
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }
        request.getRequestDispatcher("/booking.ftlh")
                .forward(request, response);
    }

    private void secondStep(HttpServletRequest request, HttpServletResponse response, int step, HttpSession session, Long computerId) throws ServletException, IOException {
        request.setAttribute("step", step);

        LocalDate bookingDate = LocalDate.parse(request.getParameter("bookingDate"));
        session.setAttribute("bookingDate", bookingDate);

        Map<String, Boolean> freeSlots = bookingService.getFreeSlots(bookingDate, computerId);

        request.setAttribute("freeSlots", freeSlots);
        request.getSession().setAttribute("freeSlots", freeSlots);

        request.getRequestDispatcher("/booking.ftlh")
                .forward(request, response);
    }

    private void stepThree(HttpServletRequest request, HttpServletResponse response, int step, HttpSession session, Long computerId) throws ServletException, IOException {
        request.setAttribute("step", step);

        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));

        LocalDate day = (LocalDate) session.getAttribute("bookingDate");
        LocalDateTime startDateTime = day.atTime(startTime);

        List<LocalTime> durations = bookingService.getDurations(startDateTime, computerId);
        request.setAttribute("durations", durations);

        session.setAttribute("durations", durations);
        session.setAttribute("startTime", startTime);

        request.getRequestDispatcher("/booking.ftlh")
                .forward(request, response);
    }

    private void stepFour(HttpServletRequest request, HttpServletResponse response, int step, HttpSession session, Long computerId) throws ServletException, IOException {
        request.setAttribute("step", step);

        int duration = Integer.parseInt(request.getParameter("duration"));
        session.setAttribute("duration", duration);

        LocalTime startTime = (LocalTime) session.getAttribute("startTime");
        LocalTime endTime = startTime.plusMinutes(duration);

        session.setAttribute("endTime", endTime);

        int price = bookingService.getPrice(computerId, duration);
        session.setAttribute("price", price);

        request.getRequestDispatcher("/booking.ftlh")
                .forward(request, response);
    }

}
