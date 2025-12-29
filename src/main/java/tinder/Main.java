package tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        Server server = new Server(port);

        WebAppContext context = new WebAppContext();
        context.setContextPath("/");

        String resourceBase = Main.class.getClassLoader().getResource("templates").toExternalForm();
        context.setResourceBase(resourceBase);

        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*");

        context.setParentLoaderPriority(true);

        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

        server.setHandler(context);
        server.start();
        server.join();
    }
}
