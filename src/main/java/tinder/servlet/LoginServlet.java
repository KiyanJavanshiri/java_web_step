package tinder.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import tinder.config.DatabaseConfig;
import tinder.models.User;
import tinder.repo.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private UserRepository userRepository;

    public LoginServlet() {
        this.userRepository = new UserRepository(DatabaseConfig.getConnection());
    }

    private Configuration getCfg() {
        return (Configuration) getServletContext().getAttribute("freemarkerConfig");
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Configuration cfg = getCfg();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            Template loginT = cfg.getTemplate("login.ftl");
            model.put("request", req);
            loginT.process(model, resp.getWriter());
        } catch (TemplateException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        render(req, resp, new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            User user = userRepository.getUserByLogin(login);

            if(user != null && user.getPassword().equals(password)) {
                HttpSession session = req.getSession(true);
                session.setAttribute("userLogin", user.getLogin());
                session.setAttribute("userId", user.getId());

                Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
                cookie.setMaxAge(3600);
                resp.addCookie(cookie);

                resp.sendRedirect("/users");
                return;
            }

            Map<String, Object> error = new HashMap<>();
            error.put("error", user == null ? "User was not found" : "Invalid password");
            render(req, resp, error);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
