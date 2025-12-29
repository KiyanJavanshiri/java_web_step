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

@WebServlet(urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private UserRepository userRepository;

    public ProfileServlet() {
        this.userRepository = new UserRepository(DatabaseConfig.getConnection());
    }

    private Configuration getCfg() {
        return (Configuration) getServletContext().getAttribute("freemarkerConfig");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration cfg = getCfg();
        HttpSession session = req.getSession(false);
        String userLogin = (String) session.getAttribute("userLogin");
        Map<String, Object> model = new HashMap<>();
        model.put("request", req);

        User user;
        try {
            user = userRepository.getUserByLogin(userLogin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(user != null) {
            model.put("image_url", user.getAvatar_url());
            model.put("username", user.getUsername());
        }

        try {
            Template profileT = cfg.getTemplate("profile.ftl");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            profileT.process(model, resp.getWriter());
        } catch (TemplateException ex) {
            throw new IOException(ex);
        }
    }
}
