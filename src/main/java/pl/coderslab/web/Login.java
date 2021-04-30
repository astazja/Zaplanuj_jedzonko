package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> warnings = new ArrayList<>();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email.length() < 1){
            warnings.add("Email nie został podany!");
        }

        if (password.length() < 1){
            warnings.add("Hasło nie zostało podane!");
        }

        if (warnings.size() > 0) {
            req.setAttribute("warnings", warnings);

            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.auth(email, password);

            if (admin != null){
                HttpSession session = req.getSession();

                if (session.getAttribute("userId") == null){
                    session.setAttribute("userId", admin.getId());
                }

                resp.sendRedirect(req.getContextPath() + "/");
            } else {
                req.setAttribute("loginSuccess", false);
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        }

    }
}
