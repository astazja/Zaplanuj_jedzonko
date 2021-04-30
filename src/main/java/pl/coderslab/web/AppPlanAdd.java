package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "AppPlanAdd", value = "/app/plan/add")
public class AppPlanAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userId") != null){
            String planName = request.getParameter("planName");
            String planDescription = request.getParameter("planDescription");

            if (planName.isEmpty() || planDescription.isEmpty()){
                request.setAttribute("info", "Nie wypełniono wszystkich pól!");
                getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);
            } else {
                PlanDao planDao = new PlanDao();

                Plan plan = new Plan();
                plan.setName(planName);
                plan.setDescription(planDescription);
                plan.setCreated(LocalDateTime.now());
                plan.setAdminId((int) session.getAttribute("userId"));

                planDao.create(plan);

                response.sendRedirect(request.getContextPath() + "/app/plan/list");
            }
        } else {
            request.setAttribute("info", "Nie jesteś zalogowany!");
            getServletContext().getRequestDispatcher("/app-add-schedules.jsp").forward(request, response);
        }
    }
}
