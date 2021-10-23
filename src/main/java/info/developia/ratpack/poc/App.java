package info.developia.ratpack.poc;

import info.developia.ratpack.poc.controller.TaskController;
import info.developia.ratpack.poc.filter.CORSFilter;
import info.developia.ratpack.poc.filter.RequestFilter;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        final TaskController taskController = new TaskController();

        Path baseDir = BaseDir.find("public");
        ServerConfig config = ServerConfig.builder().baseDir(baseDir).development(true).build();
        RatpackServer.start(server -> server.serverConfig(config).handlers(chain -> chain
                .all(new CORSFilter())
                .all(new RequestFilter())
                .files(files -> files.dir("public").indexFiles("index.html"))
                .path("tasks", ctx -> ctx.byMethod(action -> action.get(taskController::getAllTasks)))
                .path("tasks/:id", ctx -> ctx.byMethod(action -> action.get(taskController::getTask)))
        ));
    }
}
