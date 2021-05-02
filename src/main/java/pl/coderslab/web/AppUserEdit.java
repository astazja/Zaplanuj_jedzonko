package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AppUserEdit", value = "/app/user/edit")
public class AppUserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null) {
            AdminDao adminDao = new AdminDao();
            Admin admin = adminDao.read(Integer.valueOf(String.valueOf(session.getAttribute("userId"))));
            request.setAttribute("admin", admin);
            getServletContext().getRequestDispatcher("/app-edit-user.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null) {
            Integer id = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String email = request.getParameter("email");
            if(name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
                request.setAttribute("info", "Nie wypełniono wszystkich pól.");
                response.sendRedirect("/app/user/edit?id=" + id);
            }else{
                AdminDao adminDao = new AdminDao();
                Admin admin = adminDao.read(id);
                admin.setFirstName(name);
                admin.setLastName(surname);
                admin.setEmail(email);
                adminDao.update(admin);
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
}
