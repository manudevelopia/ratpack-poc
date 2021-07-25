package info.developia.ratpack.poc.service;

import info.developia.ratpack.poc.model.Task;
import info.developia.ratpack.poc.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository taskRepository = new TaskRepository();

    public List<Task> getTasks() {
        return taskRepository.getAll();
    }

    public Optional<Task> getTask(String tid){
        return taskRepository.getById(tid);
    }
}
