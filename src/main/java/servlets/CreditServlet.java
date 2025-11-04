package servlets;

import repo.PlayersRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreditServlet extends HttpServlet {
    private final PlayersRepo repo = new PlayersRepo();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("playerId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int playerId = (Integer) session.getAttribute("playerId");
        String action = req.getParameter("action");   // "earn" or "spend"
        String amountStr = req.getParameter("amount");

        try {
            int amount = Integer.parseInt(amountStr);
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");

            int delta = "spend".equals(action) ? -amount : amount;
            boolean ok = repo.changeCredits(playerId, delta);

            String tag = (String) session.getAttribute("gamerTag");
            if (!ok && delta < 0) {
                req.setAttribute("message", "Cannot spend that amount: balance would go below zero.");
            } else {
                int newBalance = repo.getCredits(playerId);
                req.setAttribute("message", "Hi " + tag + ", your new balance is " + newBalance + " credits.");
            }
            req.getRequestDispatcher("message.jsp").forward(req, resp);

        } catch (NumberFormatException nfe) {
            req.setAttribute("message", "Please enter a whole number amount.");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}
