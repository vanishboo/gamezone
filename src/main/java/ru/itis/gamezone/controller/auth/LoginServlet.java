package ru.itis.gamezone.controller.auth;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.gamezone.model.Account;
import ru.itis.gamezone.service.AccountService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AccountService accountService;

    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }
        request.getRequestDispatcher("login.ftlh").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<Account> account = accountService.authenticate(username, password);
        if (account.isPresent()) {
            request.getSession().setAttribute("account", account.get());
            response.sendRedirect(request.getContextPath() + "/home");
        } else  {
            response.sendRedirect(request.getContextPath() + "/login?error=invalid");
        }
    }
}
