package kz.orynbek.bitlabInternProject.mapper;

import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.entities.Course;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO mapToDto(Course course);
    Course mapToEntity(CourseDTO courseDTO);
    List<CourseDTO>mapToDto(List<Course> courses);
    List<Course>mapToEntity(List<CourseDTO> courseDTOs);
}
