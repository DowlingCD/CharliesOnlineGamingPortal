package servlets;

import repo.PlayersRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final PlayersRepo repo = new PlayersRepo();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tag = req.getParameter("gamerTag");
        String pass = req.getParameter("password");

        try {
            Integer id = repo.findIdByCredentials(tag, pass);
            if (id == null) {
                req.setAttribute("error", "Invalid credentials.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("playerId", id);
            session.setAttribute("gamerTag", tag);
            resp.sendRedirect("dashboard.jsp");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
