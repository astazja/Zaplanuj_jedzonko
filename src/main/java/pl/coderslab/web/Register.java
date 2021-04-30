package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> warnings = new ArrayList<>();

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");

        if (name.length() < 1){
            warnings.add("Imie nie zostało podane!");
        }

        if (surname.length() < 1){
            warnings.add("Nazwisko nie zostało podane!");
        }

        if (email.length() < 1){
            warnings.add("Email nie został podany!");
        }

        if (password.length() < 1){
            warnings.add("Hasło nie zostało podane!");
        }

        if (!password.equals(repassword)){
            warnings.add("Podane hasła się nie zgadzają!");
        }

        if (warnings.size() > 0) {
            req.setAttribute("warnings", warnings);

            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        } else {
            req.setAttribute("registrationSuccess", "Pomyślnie zarejestrowano, teraz się zaloguj.");

            AdminDao adminDao = new AdminDao();
            Admin admin = new Admin();
            admin.setFirstName(name);
            admin.setLastName(surname);
            admin.setEmail(email);
            admin.setPassword(password);
            admin.setSuperadmin((byte) 1);
            admin.setEnable((byte) 1);
            adminDao.create(admin);

            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }

    }
}
