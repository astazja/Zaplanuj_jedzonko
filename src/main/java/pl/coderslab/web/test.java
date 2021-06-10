package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "Test", value = "/test")
public class test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PlanDao planDao = new PlanDao();
        Plan plan = new Plan();
        response.getWriter().println(planDao.readLastAdded());
        response.getWriter().append("\n\n\n");
       // Integer num = planDao.getNumberOfPlan(1);
       // Integer qnum = planDao.getNumberOfPlan(2);
      //  response.getWriter().println(String.valueOf(num));
       // response.getWriter().append("\n\n\n");
       // response.getWriter().println(qnum);

    }
}

//    czyli instrukcja wygląda następująco:
//        git checkout develop
//        git checkuot -b inicjały/nazwa zadania
//        git add nazwa pliku
//        git commit -m"info"
//        git push -u origin nasz_branch
//        a potem pull request w githubie do develop
