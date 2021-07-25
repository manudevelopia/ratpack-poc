package info.developia.ratpack.poc.repository;

import info.developia.ratpack.poc.model.Task;
import info.developia.ratpack.poc.repository.mapper.TaskMapper;
import info.developia.ratpack.poc.service.TaskServiceException;

import java.util.List;
import java.util.Optional;

public class TaskRepository extends Repository<TaskMapper> {

    public List<Task> getAll() {
        try {
            return repository(TaskMapper::getAll);
        } catch (PersistenceException e) {
            throw new TaskServiceException(e.getMessage());
        }
    }

    public Optional<Task> getById(String tid) {
        try {
            return Optional.ofNullable(repository(tm -> tm.getById(tid)));
        } catch (PersistenceException e) {
            throw new TaskServiceException(e.getMessage());
        }
    }

    void create(Task task) {
        try {
            repository(tm -> tm.create(task));
        } catch (PersistenceException e) {
            throw new TaskServiceException(e.getMessage());
        }
    }

    void update(Task task) {
        try {
            repository(tm -> tm.update(task));
        } catch (PersistenceException e) {
            throw new TaskServiceException(e.getMessage());
        }
    }

    void markDoneAs(Task task) {
        try {
            repository(tm -> tm.markDoneAs(task));
        } catch (PersistenceException e) {
            throw new TaskServiceException(e.getMessage());
        }
    }
}
