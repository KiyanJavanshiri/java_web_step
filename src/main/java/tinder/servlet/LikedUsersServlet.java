package tinder.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tinder.config.DatabaseConfig;
import tinder.models.User;
import tinder.repo.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/liked"})
public class LikedUsersServlet extends HttpServlet {
    private UserRepository userRepository;

    public LikedUsersServlet() {
        this.userRepository = new UserRepository(DatabaseConfig.getConnection());
    }

    public Configuration getCfg() {
        return (Configuration) getServletContext().getAttribute("freemarkerConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration cfg = getCfg();
        HttpSession session = req.getSession(false);
        int userId = (Integer) session.getAttribute("userId");
        Map<String, Object> model = new HashMap<>();
        Map<Integer, User> likedUsers;
        try {
            likedUsers = userRepository.getLikedUsers(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        model.put("isEmpty", likedUsers.isEmpty());
        model.put("likedUsers", likedUsers);

        try {
            Template matchesT = cfg.getTemplate("listOfMatches.ftl");
            model.put("request", req);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            matchesT.process(model, resp.getWriter());
        } catch (TemplateException ex) {
            throw new IOException(ex);
        }
    }
}
