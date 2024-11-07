package se.semit.ykovtun.webappskyvlab2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import se.semit.ykovtun.webappskyvlab2.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab2.services.HospitalDepartmentService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
@WebServlet("/hospital/list")
public class HospitalDepartmentListServlet extends HttpServlet {
    private HospitalDepartmentService service = null;

    @Override
    public void init() {
        this.service = new HospitalDepartmentService();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        List<HospitalDepartment> departments = null;

        if (!req.getParameterMap().entrySet().isEmpty()) {
            HospitalDepartment template = this.service.getDepartmentFromRequest(req);
            departments = service.findByTemplate(template);
        }
        else {
            departments = service.findAll();
        }

        if (req.getParameter("sort") != null && !req.getParameter("sort").isEmpty()) {
            sortDepartments(departments, req.getParameter("sort"), req.getParameter("desc"));
        }

        req.setAttribute("hospitals", departments);
        req.setAttribute("codes", service.getUniqueCodeBuildings());
        req.getRequestDispatcher("/views/hospital-list.jsp").forward(req, res);
    }

    private void sortDepartments(List<HospitalDepartment> departments, String field, String sort) {
        if (field.equals("floor")) {
            if (sort != null && sort.equals("on")) {
                departments.sort(Comparator.comparing(HospitalDepartment::getFloor).reversed());
            }
            else {
                departments.sort(Comparator.comparing(HospitalDepartment::getFloor));
            }

        }
        else if (field.equals("box-count")) {
            if (sort != null && sort.equals("on")) {
                departments.sort(Comparator.comparing(HospitalDepartment::getBoxCount).reversed());
            }
            else {
                departments.sort(Comparator.comparing(HospitalDepartment::getBoxCount));
            }
        }
    }
}
