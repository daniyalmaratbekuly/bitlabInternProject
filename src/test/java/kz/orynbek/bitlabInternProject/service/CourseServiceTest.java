package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.entities.Course;
import kz.orynbek.bitlabInternProject.mapper.CourseMapper;
import kz.orynbek.bitlabInternProject.repositories.CourseRepository;
import kz.orynbek.bitlabInternProject.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;
    private CourseDTO courseDTO;
    private Course course;

    @BeforeEach
    public void setup() {
        courseDTO = new CourseDTO(1L, "Java Developer", "Description of Java course", null, null);
        course = new Course(1L, "Java Developer", "Description of Java course", null, null);
    }

    @Test
    public void testGetAllCourses() {
        Course mockCourse = new Course();
        mockCourse.setId(1L);
        mockCourse.setName("Test Course");
        CourseDTO mockCourseDTO = new CourseDTO();
        mockCourseDTO.setId(1L);
        mockCourseDTO.setName("Test Course");
        when(courseRepository.findAll()).thenReturn(List.of(mockCourse));
        when(courseMapper.mapToDto(any(Course.class))).thenReturn(mockCourseDTO);
        List<CourseDTO> result = courseService.getAllCourses();
        assertEquals(1, result.size());
        assertEquals(mockCourseDTO, result.get(0));
        verify(courseRepository, times(1)).findAll();
        verify(courseMapper, times(1)).mapToDto(any(Course.class));
    }

    @Test
    public void testGetCourseById() {
        Course mockCourse = new Course();
        mockCourse.setId(1L);
        mockCourse.setName("Test Course");
        mockCourse.setDescription("Description of Test Course");
        CourseDTO mockCourseDTO = new CourseDTO();
        mockCourseDTO.setId(1L);
        mockCourseDTO.setName("Test Course");
        mockCourseDTO.setDescription("Description of Test Course");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(mockCourse));
        when(courseMapper.mapToDto(any(Course.class))).thenReturn(mockCourseDTO);
        CourseDTO result = courseService.getCourseById(1L);
        assertEquals(mockCourseDTO, result); // Проверка соответствия DTOВ
        verify(courseRepository, times(1)).findById(1L);
        verify(courseMapper, times(1)).mapToDto(any(Course.class));
    }


    @Test
    public void testCreateCourse() {
        when(courseMapper.mapToEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        courseService.createCourse(courseDTO);

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testUpdateCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.mapToEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        courseService.updateCourse(courseDTO);

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(1L);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.findCourseById(1L);

        verify(courseRepository, times(1)).findById(1L);
    }
}
