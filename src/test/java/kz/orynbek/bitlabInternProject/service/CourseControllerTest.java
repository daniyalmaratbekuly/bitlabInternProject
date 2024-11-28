package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.controllers.CourseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testGetAllCourses() throws Exception {
        CourseDTO course1 = new CourseDTO();
        course1.setId(1L);
        CourseDTO course2 = new CourseDTO();
        course2.setId(2L);
        List<CourseDTO> courses = Arrays.asList(course1, course2);

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void testGetCourseById() throws Exception {
        Long courseId = 1L;
        CourseDTO course = new CourseDTO();
        course.setId(courseId);

        when(courseService.getCourseById(courseId)).thenReturn(course);

        mockMvc.perform(get("/course/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId));

        verify(courseService, times(1)).findCourseById(courseId);
        verify(courseService, times(1)).getCourseById(courseId);
    }

    @Test
    void testCreateCourse() throws Exception {
        CourseDTO course = new CourseDTO();
        course.setName("New Course");

        mockMvc.perform(post("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Course\"}"))
                .andExpect(status().isOk());

        verify(courseService, times(1)).createCourse(any(CourseDTO.class));
    }

    @Test
    void testUpdateCourse() throws Exception {
        CourseDTO course = new CourseDTO();
        course.setId(1L);
        course.setName("Updated Course");

        mockMvc.perform(put("/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Course\"}"))
                .andExpect(status().isOk());

        verify(courseService, times(1)).updateCourse(any(CourseDTO.class));
    }

    @Test
    void testDeleteCourse() throws Exception {
        Long courseId = 1L;

        mockMvc.perform(delete("/course/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(courseService, times(1)).findCourseById(courseId);
        verify(courseService, times(1)).deleteCourse(courseId);
    }
}
