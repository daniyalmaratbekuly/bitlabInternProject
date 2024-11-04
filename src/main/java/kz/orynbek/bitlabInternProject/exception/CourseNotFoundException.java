package kz.orynbek.bitlabInternProject.exception;

public class CourseNotFoundException extends IllegalArgumentException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
