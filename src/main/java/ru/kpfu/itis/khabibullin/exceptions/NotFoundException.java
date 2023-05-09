package ru.kpfu.itis.khabibullin.exceptions;
/**
 * @author Khabibullin Alisher
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
