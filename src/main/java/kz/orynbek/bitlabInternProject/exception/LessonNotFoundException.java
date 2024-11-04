package kz.orynbek.bitlabInternProject.exception;

public class LessonNotFoundException extends IllegalArgumentException {
    public LessonNotFoundException(String message) {
        super(message);
    }
}

