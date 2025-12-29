package tinder.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class FreemarkerConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute("freemarkerConfig", cfg);
        System.out.println("Freemarker Configured");
    }
}
