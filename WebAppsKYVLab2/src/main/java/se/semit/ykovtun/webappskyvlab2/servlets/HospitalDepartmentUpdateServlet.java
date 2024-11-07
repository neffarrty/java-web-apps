package se.semit.ykovtun.webappskyvlab2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import se.semit.ykovtun.webappskyvlab2.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab2.services.HospitalDepartmentService;

import java.io.IOException;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
@WebServlet("/hospital/update")
public class HospitalDepartmentUpdateServlet extends HttpServlet {
    private HospitalDepartmentService service = null;
    private String path = "/views/hospital-update.jsp";

    @Override
    public void init() {
        this.service = new HospitalDepartmentService();
        this.path = "/views/hospital-update.jsp";
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        Long id = Long.parseLong(req.getParameter("id"));

        try {
            HospitalDepartment department = this.service.getDepartmentFromRequest(req);
            service.update(id, department);
        }
        catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(req.getContextPath() + path).forward(req, res);
            return;
        }
        catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String property = violation.getPropertyPath().toString();
                String message  = violation.getMessage();
                req.setAttribute(property + "-error", message);
            }
            req.getRequestDispatcher(req.getContextPath() + path).forward(req, res);
            return;
        }

        res.sendRedirect(req.getContextPath() + "/hospital/list");
    }
}
