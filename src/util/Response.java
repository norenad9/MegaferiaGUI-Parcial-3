package util;

public class Response<T> {

    public enum Status {
        OK, CREATED, ERROR
    }

    private Status status;
    private String message;
    private T data;

    public Response(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Status getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }

}
