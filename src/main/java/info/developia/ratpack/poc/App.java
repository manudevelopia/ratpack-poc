package info.developia.ratpack.poc;

import info.developia.hodor.annotation.Hodor;
import info.developia.hodor.annotation.HoldTheDoor;
import info.developia.ratpack.poc.controller.TaskController;
import info.developia.ratpack.poc.filter.CORSFilter;
import info.developia.ratpack.poc.filter.RequestFilter;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.nio.file.Path;

public class App {
    @Hodor
    public static void main(String[] args) {
        final TaskController taskController = new TaskController();

        Path baseDir = BaseDir.find("public");
        ServerConfig config = ServerConfig.builder().baseDir(baseDir).development(true).build();
        try {
            RatpackServer.start(server -> server.serverConfig(config).handlers(chain -> chain
                    .all(new CORSFilter())
                    .all(new RequestFilter())
                    .files(files -> files.dir("public").indexFiles("index.html"))
                    .path("tasks", ctx -> ctx.byMethod(action -> action.get(taskController::getAllTasks)))
                    .path("tasks/:id", ctx -> ctx.byMethod(action -> action.get(taskController::getTask)))
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to start server. " + e.getMessage());
        }
    }

    @HoldTheDoor
    static void doSomething(Throwable t) {
        System.out.println("Something went wrong: " + t.getMessage());
        System.out.println("Sending a SOS!");
    }
}
