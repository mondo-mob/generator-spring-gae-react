package threewks.controller.dto;

public class SimpleRequest<T> {
    private T value;

    private SimpleRequest() {
    }

    public SimpleRequest(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
