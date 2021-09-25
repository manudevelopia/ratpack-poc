package info.developia.ratpack.poc;

import info.developia.ratpack.poc.controller.TaskController;
import info.developia.ratpack.poc.filter.CORSFilter;
import info.developia.ratpack.poc.filter.RequestFilter;
import ratpack.server.RatpackServer;

public class App {
    public static String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        final TaskController taskController = new TaskController();

        RatpackServer.start(server -> server.handlers(chain -> chain
                .all(new CORSFilter())
                .all(new RequestFilter())
                .get(ctx -> ctx.render(getGreeting()))
                .get("greetings/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
                .path("tasks", ctx -> ctx.byMethod(action -> action.get(taskController::getAllTasks)))
                .path("tasks/:id", ctx -> ctx.byMethod(action -> action.get(taskController::getTask)))
        ));
    }
}
