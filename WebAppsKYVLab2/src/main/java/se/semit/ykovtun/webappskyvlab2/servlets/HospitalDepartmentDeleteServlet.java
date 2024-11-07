package se.semit.ykovtun.webappskyvlab2.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import se.semit.ykovtun.webappskyvlab2.services.HospitalDepartmentService;

import java.io.IOException;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
@WebServlet("/hospital/delete")
public class HospitalDepartmentDeleteServlet extends HttpServlet {
    private HospitalDepartmentService service = null;

    @Override
    public void init() {
        this.service = new HospitalDepartmentService();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        service.delete(id);

        res.sendRedirect(req.getContextPath() + "/hospital/list");
    }
}
