package ru.itis.gamezone.controller.catalog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.gamezone.dao.ComputerDao;
import ru.itis.gamezone.dao.RoomDao;
import ru.itis.gamezone.model.Computer;
import ru.itis.gamezone.model.Game;
import ru.itis.gamezone.model.Room;
import ru.itis.gamezone.service.ComputerService;
import ru.itis.gamezone.service.RoomService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@WebServlet("/computers")
public class ComputersServlet extends HttpServlet {

    private ComputerService computerService;
    private RoomService roomService;

    public void init() throws ServletException {
        computerService = (ComputerService) getServletContext().getAttribute("computerService");
        roomService = (RoomService) getServletContext().getAttribute("roomService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Computer> computers = computerService.getAll();

        List<Computer> mainRoom = computers
                .stream()
                .filter(c -> c.getRoomId() == 1)
                .sorted((c1, c2) -> c1.getId() > c2.getId() ? 1 : -1)
                .toList();

        List<Computer> vipRoom = computers
                .stream()
                .filter(c -> c.getRoomId() == 2)
                .sorted((c1, c2) -> c1.getId() > c2.getId() ? 1 : -1)
                .toList();

        List<Computer> consoleRoom = computers
                .stream()
                .filter(c -> c.getRoomId() == 3)
                .sorted((c1, c2) -> c1.getId() > c2.getId() ? 1 : -1)
                .toList();

        request.setAttribute("mainComputers", mainRoom);
        request.setAttribute("vipComputers", vipRoom);
        request.setAttribute("consoleComputers", consoleRoom);



        Optional<Room> main = roomService.findById(1L);
        Optional<Room> vip = roomService.findById(2L);
        Optional<Room> console = roomService.findById(3L);

        request.setAttribute("mainRoomPrice", main.get().getHourPrice());
        request.setAttribute("vipRoomPrice", vip.get().getHourPrice());
        request.setAttribute("consoleRoomPrice", console.get().getHourPrice());


        request.getRequestDispatcher("/computers.ftlh").forward(request, response);
    }
}
