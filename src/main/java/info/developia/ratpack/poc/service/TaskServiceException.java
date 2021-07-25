package info.developia.ratpack.poc.service;

public class TaskServiceException extends RuntimeException {
    public TaskServiceException() {
    }

    public TaskServiceException(String message) {
        super(message);
    }
}
