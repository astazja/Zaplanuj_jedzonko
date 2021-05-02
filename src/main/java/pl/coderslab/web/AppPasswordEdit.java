package pl.coderslab.web;

import pl.coderslab.dao.AdminDao;
import pl.coderslab.model.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppPasswordEdit", value = "/app/password/edit")
public class AppPasswordEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("userData");
        request.setAttribute("firstName", admin.getFirstName());

        getServletContext().getRequestDispatcher("/app-pass-edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null) {
            List <String> warnings = new ArrayList<>();
            Integer id = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
            String password = request.getParameter("password");
//            Walidacja danych, brak wyświetlania
            String repassword = request.getParameter("repassword");

            if(password.isEmpty() || repassword.isEmpty()) {
                warnings.add("Nie wypełniono wszystkich pól.");
            }else if (!password.equals(repassword)){
                warnings.add("Podane hasła nie są takie same.");
            }else if (warnings.size() > 0) {
                request.setAttribute("info", warnings);
                response.sendRedirect("/app/password/edit?id=" + id);
            }else {
                AdminDao adminDao = new AdminDao();
                Admin admin = adminDao.read(id);
                admin.setPassword(password);
                adminDao.update(admin);
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
}
