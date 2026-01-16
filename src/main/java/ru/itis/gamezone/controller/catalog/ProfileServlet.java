package ru.itis.gamezone.controller.catalog;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.service.AccountService;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private AccountService accountService;

    public void init() throws ServletException {
        accountService = (AccountService)getServletContext().getAttribute("accountService");
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Account account = (Account) req.getSession().getAttribute("account");

        Account updated = accountService.getById(account.getId());

        req.setAttribute("user", updated);

        req.getRequestDispatcher("/profile.ftlh").forward(req, resp);
    }
}
