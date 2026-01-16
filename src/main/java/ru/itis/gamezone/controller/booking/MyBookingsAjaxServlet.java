package ru.itis.gamezone.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.model.Booking;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.service.BookingService;
import ru.itis.gamezone.service.ComputerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bookings-ajax")
public class MyBookingsAjaxServlet extends HttpServlet {

    private BookingService bookingService;
    private ComputerService computerService;

    public void init() throws ServletException {
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
        computerService = (ComputerService) getServletContext().getAttribute("computerService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Long accountId = ((Account) session.getAttribute("account")).getId();
        String status = request.getParameter("status");
        List<Booking> bookings = new ArrayList<>();
        if ("all".equals(status) || status == null) {
            bookings = bookingService.getBookingsByAccountId(accountId);
        } else {
            bookings = bookingService.getBookingsByStatusAndAccountId(accountId, status);
        }

        Map<String, Long> roomMap = new HashMap<>();
        for (Booking booking : bookings) {
            String computerId = String.valueOf(booking.getComputerId());
            if (!roomMap.containsKey(computerId)) {
                Computer computer = computerService.getById(booking.getComputerId());
                if (computer != null) {
                    roomMap.put(computerId, computer.getRoomId());
                }
            }
        }

        request.setAttribute("bookings", bookings);
        request.setAttribute("roomMap", roomMap);

        request.getRequestDispatcher("/fragments/bookings-list.ftlh").forward(request, response);
    }
}
