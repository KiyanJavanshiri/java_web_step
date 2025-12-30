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
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    private UserRepository userRepository;
    private List<User> users;
    private int limit = 10;
    private int index;

    public UsersServlet() {
        this.userRepository = new UserRepository(DatabaseConfig.getConnection());
    }

    private Configuration getCfg() {
        return (Configuration) getServletContext().getAttribute("freemarkerConfig");
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Configuration cfg = getCfg();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        model.put("isEmpty", users.isEmpty());
        if(index >= users.size()) {
            resp.sendRedirect("/liked");
            return;
        }

        User user = users.get(index);
        model.put("username", user.getUsername());
        model.put("avatar", user.getAvatar_url());
        model.put("id", user.getId());

        try {
            Template usersT = cfg.getTemplate("listOfUsers.ftl");
            model.put("request", req);
            usersT.process(model, resp.getWriter());
        } catch (TemplateException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        index = 0;
        HttpSession session = req.getSession(false);
        String currentUserLogin = (String) session.getAttribute("userLogin");
        int currentUserId = (Integer) session.getAttribute("userId");
        try {
            users = userRepository.getAllUsers(limit, currentUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        render(req, resp, new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action").toLowerCase();

        if(action.equals("yes")) {
            int matchedUserId = Integer.parseInt(req.getParameter("userId"));
            int userId = (Integer) req.getSession(false).getAttribute("userId");
            try {
                userRepository.saveLike(userId, matchedUserId);
                users.remove(index);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            index++;
        }

        render(req, resp, new HashMap<>());
    }
}
