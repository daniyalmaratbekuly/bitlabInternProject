package kz.orynbek.bitlabInternProject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.entities.Course;
import kz.orynbek.bitlabInternProject.exception.CourseNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.CourseMapper;
import kz.orynbek.bitlabInternProject.repositories.CourseRepository;
import kz.orynbek.bitlabInternProject.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(courseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + id + " not found"));
        return courseMapper.mapToDto(course);
    }

    @Override
    public void createCourse(CourseDTO courseDTO) {
       Course course = courseMapper.mapToEntity(courseDTO);
       logger.info("Creating new course {}", course);
       logger.debug("Course data{}", course);
       courseRepository.save(course);
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {
        logger.info("Updating course with id: {}", courseDTO.getId());
        Course course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Course with id " + courseDTO.getId() + " not found"));
        logger.debug("Original course data: {}", course);
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setCreatedTime(courseDTO.getCreatedTime());
        course.setUpdatedTime(LocalDateTime.now());
        logger.debug("Updated course data: {}", course);
        courseRepository.save(course);
    }


    @Override
    public void deleteCourse(Long id) {
        logger.info("Deleting course with id: {}", id);
        courseRepository.deleteById(id);
        logger.debug("Course with id {} deleted successfully", id);
    }
    public void findCourseById(Long id) {
         courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + id + " not found"));
         courseMapper.mapToDto(courseRepository.findCourseById(id));
    }
}
