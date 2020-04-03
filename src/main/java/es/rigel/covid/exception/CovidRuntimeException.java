package es.rigel.covid.exception;

public class CovidRuntimeException extends RuntimeException {

    public CovidRuntimeException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Error con código de error: " + this.getMessage();
    }
}
