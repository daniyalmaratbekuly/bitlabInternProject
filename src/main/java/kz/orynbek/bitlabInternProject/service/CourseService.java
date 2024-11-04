package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    void createCourse(CourseDTO courseDTO);
    void updateCourse(CourseDTO courseDTO);
    void deleteCourse(Long id);
    void findCourseById(Long id);
}
