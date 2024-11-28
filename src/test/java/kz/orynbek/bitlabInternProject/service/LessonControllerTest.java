package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.controllers.LessonController;
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

class LessonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonController lessonController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).build();
    }

    @Test
    void testGetAllLessons() throws Exception {
        LessonDTO lesson1 = new LessonDTO();
        lesson1.setId(1L);
        LessonDTO lesson2 = new LessonDTO();
        lesson2.setId(2L);
        List<LessonDTO> lessons = Arrays.asList(lesson1, lesson2);

        when(lessonService.getAllLessons()).thenReturn(lessons);

        mockMvc.perform(get("/lesson")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(lessonService, times(1)).getAllLessons();
    }

    @Test
    void testGetLessonById() throws Exception {
        Long lessonId = 1L;
        LessonDTO lesson = new LessonDTO();
        lesson.setId(lessonId);

        when(lessonService.getLessonById(lessonId)).thenReturn(lesson);

        mockMvc.perform(get("/lesson/{id}", lessonId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(lessonId));

        verify(lessonService, times(1)).findLessonById(lessonId);
        verify(lessonService, times(1)).getLessonById(lessonId);
    }

    @Test
    void testCreateLesson() throws Exception {
        mockMvc.perform(post("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Lesson\"}"))
                .andExpect(status().isOk());

        verify(lessonService, times(1)).createLesson(any(LessonDTO.class));
    }

    @Test
    void testUpdateLesson() throws Exception {
        mockMvc.perform(put("/lesson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Lesson\"}"))
                .andExpect(status().isOk());

        verify(lessonService, times(1)).updateLesson(any(LessonDTO.class));
    }

    @Test
    void testDeleteLesson() throws Exception {
        Long lessonId = 1L;

        mockMvc.perform(delete("/lesson/{id}", lessonId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(lessonService, times(1)).findLessonById(lessonId);
        verify(lessonService, times(1)).deleteLesson(lessonId);
    }
}
