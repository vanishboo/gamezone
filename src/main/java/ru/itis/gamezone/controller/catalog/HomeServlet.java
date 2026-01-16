package ru.itis.gamezone.controller.catalog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.service.AccountService;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private AccountService accountService;

    public void init() throws ServletException {
        accountService = (AccountService)getServletContext().getAttribute("accountService");
    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("account");
        Account updated = accountService.getById(account.getId());

        int cntBookings = accountService.countActiveBookings(updated.getId());
        req.setAttribute("cntBookings", cntBookings);
        req.setAttribute("account", account.getUsername());
        req.getRequestDispatcher("/home.ftlh").forward(req, resp);
    }
}
