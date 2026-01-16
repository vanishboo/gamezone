package ru.itis.gamezone.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.gamezone.service.AccountService;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AccountService accountService;

    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String error = req.getParameter("error");
        if (error != null) {
            req.setAttribute("error", error);
        }

        req.getRequestDispatcher("register.ftlh").forward(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");



        if (!accountService.checkCorrect(username, password)) {
            response.sendRedirect(request.getContextPath() + "/register?error=invalid_data");
            return;
        }

        if (accountService.checkUserExists(username)) {
            response.sendRedirect(request.getContextPath() + "/register?error=user_exists");
            return;
        }
        accountService.register(username, password);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
