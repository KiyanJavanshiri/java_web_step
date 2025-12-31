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
import tinder.repo.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/messages/*"})
public class MessagesServlet extends HttpServlet {
    private UserRepository userRepository;
    public MessagesServlet() {
        this.userRepository = new UserRepository(DatabaseConfig.getConnection());
    }

    private Configuration getCfg() {
        return (Configuration) getServletContext().getAttribute("freemarkerConfig");
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) throws IOException {
        Configuration cfg = getCfg();
        int dialogId = Integer.parseInt(req.getPathInfo().substring(1));
        List<Map<String, Object>> messages;
        try {
            messages = userRepository.getAllDialogMessages(dialogId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        model.put("isEmpty", messages.isEmpty());

        if(!messages.isEmpty()) {
            int currentUserId = (Integer) req.getSession().getAttribute("userId");
            model.put("messages", messages);
            model.put("currentUserId", currentUserId);
        }

        Template dialogT = cfg.getTemplate("dialog.ftl");
        model.put("request", req);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            dialogT.process(model, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        render(req, resp, new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("text");
        int dialogId = Integer.parseInt(req.getPathInfo().substring(1));
        int currentUserId = (Integer) req.getSession().getAttribute("userId");

        if(message.trim().isEmpty()) {
            return;
        }

        try {
            userRepository.sendMessage(message, dialogId, currentUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        render(req, resp, new HashMap<>());
    }
}
