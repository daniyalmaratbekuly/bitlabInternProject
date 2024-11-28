package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import kz.orynbek.bitlabInternProject.exception.LessonNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.LessonMapper;
import kz.orynbek.bitlabInternProject.repositories.LessonRepository;
import kz.orynbek.bitlabInternProject.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Lesson lesson;
    private LessonDTO lessonDTO;

    @BeforeEach
    void setUp() {
        lesson = new Lesson();
        lesson.setId(1L);
        lesson.setName("Test Lesson");

        lessonDTO = new LessonDTO();
        lessonDTO.setId(1L);
        lessonDTO.setName("Test Lesson DTO");
    }

    @Test
    void testGetAllLessons() {
        List<Lesson> lessons = Arrays.asList(lesson);
        List<LessonDTO> lessonDTOs = Arrays.asList(lessonDTO);

        when(lessonRepository.findAll()).thenReturn(lessons);
        when(lessonMapper.mapToDTO(lessons)).thenReturn(lessonDTOs);

        List<LessonDTO> result = lessonService.getAllLessons();
        assertEquals(1, result.size());
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void testGetLessonById_Success() {
        when(lessonRepository.findAllById(1L)).thenReturn(lesson);
        when(lessonMapper.mapToDTO(lesson)).thenReturn(lessonDTO);

        LessonDTO result = lessonService.getLessonById(1L);
        assertEquals(lessonDTO, result);
        verify(lessonRepository, times(1)).findAllById(1L);
    }

    @Test
    void testCreateLesson() {
        when(lessonMapper.mapToEntity(lessonDTO)).thenReturn(lesson);

        lessonService.createLesson(lessonDTO);
        verify(lessonRepository, times(1)).save(lesson);
    }

    @Test
    void testUpdateLesson() {
        lenient().when(lessonRepository.findAllById(1L)).thenReturn(lesson);

        lessonService.updateLesson(lessonDTO);

        verify(lessonRepository, times(1)).save(lesson);
        verify(lessonRepository, times(1)).findAllById(1L);
    }

    @Test
    void testDeleteLesson() {
        lessonService.deleteLesson(1L);
        verify(lessonRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindLessonById_Success() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        lessonService.findLessonById(1L);
        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    void testFindLessonById_NotFound() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(LessonNotFoundException.class, () -> lessonService.findLessonById(1L));
    }
}
