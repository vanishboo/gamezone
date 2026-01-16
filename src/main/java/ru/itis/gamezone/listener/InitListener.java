package ru.itis.gamezone.listener;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.gamezone.dao.AccountDao;
import ru.itis.gamezone.dao.BookingDao;
import ru.itis.gamezone.dao.ComputerDao;
import ru.itis.gamezone.dao.RoomDao;
import ru.itis.gamezone.dao.interfaces.IAccountDao;
import ru.itis.gamezone.dao.interfaces.IBookingDao;
import ru.itis.gamezone.dao.interfaces.IComputerDao;
import ru.itis.gamezone.dao.interfaces.IRoomDao;
import ru.itis.gamezone.service.AccountService;
import ru.itis.gamezone.service.BookingService;
import ru.itis.gamezone.service.ComputerService;
import ru.itis.gamezone.service.RoomService;
import ru.itis.gamezone.util.DataBaseUtil;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        DataBaseUtil.init();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        IAccountDao accountDao = new AccountDao();
        IBookingDao bookingDao = new BookingDao();
        IComputerDao computerDao = new ComputerDao();
        IRoomDao roomDao = new RoomDao();

        AccountService accountService = new AccountService(accountDao, bookingDao, encoder);
        BookingService bookingService = new BookingService(bookingDao, computerDao, roomDao);
        RoomService roomService = new RoomService(roomDao);
        ComputerService computerService = new ComputerService(computerDao);



        context.setAttribute("accountService", accountService);
        context.setAttribute("bookingService", bookingService);
        context.setAttribute("computerService", computerService);
        context.setAttribute("roomService", roomService);

    }
}
