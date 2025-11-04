package servlets;

import repo.PlayersRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private final PlayersRepo repo = new PlayersRepo();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tag = req.getParameter("gamerTag");
        String pass = req.getParameter("password");
        String confirm = req.getParameter("confirmPassword");

        if (tag == null || tag.isBlank() || pass == null || confirm == null) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if (!pass.equals(confirm)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        try {
            if (repo.gamerTagExists(tag)) {
                req.setAttribute("error", "Gamer tag already taken.");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }
            repo.createPlayer(tag, pass); // gives 500 credits by default
            req.setAttribute("message", "Welcome, " + tag + "! Your account has 500 credits.");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}

