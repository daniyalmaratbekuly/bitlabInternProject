package kz.orynbek.bitlabInternProject.service;

import jakarta.persistence.EntityNotFoundException;
import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;
import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.controllers.ChapterController;
import kz.orynbek.bitlabInternProject.controllers.CourseController;
import kz.orynbek.bitlabInternProject.controllers.LessonController;
import kz.orynbek.bitlabInternProject.entities.Chapter;
import kz.orynbek.bitlabInternProject.entities.Course;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import kz.orynbek.bitlabInternProject.exception.ChapterNotFoundException;
import kz.orynbek.bitlabInternProject.exception.LessonNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.ChapterMapper;
import kz.orynbek.bitlabInternProject.mapper.CourseMapper;
import kz.orynbek.bitlabInternProject.mapper.LessonMapper;
import kz.orynbek.bitlabInternProject.repositories.ChapterRepository;
import kz.orynbek.bitlabInternProject.repositories.CourseRepository;
import kz.orynbek.bitlabInternProject.repositories.LessonRepository;
import kz.orynbek.bitlabInternProject.service.impl.ChapterServiceImpl;
import kz.orynbek.bitlabInternProject.service.impl.CourseServiceImpl;
import kz.orynbek.bitlabInternProject.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    private Chapter chapter;
    private ChapterDTO chapterDTO;

    @BeforeEach
    void setUp() {
        chapter = new Chapter();
        chapter.setId(1L);
        chapter.setName("Test Chapter");

        chapterDTO = new ChapterDTO();
        chapterDTO.setId(1L);
        chapterDTO.setName("Test Chapter DTO");
    }

    @Test
    void testGetAllChapters() {
        List<Chapter> chapters = Arrays.asList(chapter);
        List<ChapterDTO> chapterDTOs = Arrays.asList(chapterDTO);

        when(chapterRepository.findAll()).thenReturn(chapters);
        when(chapterMapper.mapToDTO(chapters)).thenReturn(chapterDTOs);

        List<ChapterDTO> result = chapterService.getAllChapters();
        assertEquals(1, result.size());
        verify(chapterRepository, times(1)).findAll();
    }

    @Test
    void testGetChapterById_Success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        when(chapterMapper.mapToDTO(chapter)).thenReturn(chapterDTO);

        ChapterDTO result = chapterService.getChapterById(1L);
        assertEquals(chapterDTO, result);
        verify(chapterRepository, times(1)).findById(1L);
    }

    @Test
    void testGetChapterById_NotFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> chapterService.getChapterById(1L));
    }

    @Test
    void testCreateChapter() {
        when(chapterMapper.mapToEntity(chapterDTO)).thenReturn(chapter);

        chapterService.createChapter(chapterDTO);
        verify(chapterRepository, times(1)).save(chapter);
    }

    @Test
    void testUpdateChapter() {
        when(chapterMapper.mapToEntity(chapterDTO)).thenReturn(chapter);

        chapterService.updateChapter(chapterDTO);
        verify(chapterRepository, times(1)).save(chapter);
    }

    @Test
    void testDeleteChapter() {
        chapterService.deleteChapter(1L);
        verify(chapterRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindChapterById_Success() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        chapterService.findChapterById(1L);
        verify(chapterRepository, times(1)).findById(1L);
    }

    @Test
    void testFindChapterById_NotFound() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ChapterNotFoundException.class, () -> chapterService.findChapterById(1L));
    }
}

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

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

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

class ChapterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ChapterService chapterService;

    @InjectMocks
    private ChapterController chapterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(chapterController).build();
    }

    @Test
    void testGetAllChapters() throws Exception {
        ChapterDTO chapter1 = new ChapterDTO();
        chapter1.setId(1L);
        ChapterDTO chapter2 = new ChapterDTO();
        chapter2.setId(2L);
        List<ChapterDTO> chapters = Arrays.asList(chapter1, chapter2);

        when(chapterService.getAllChapters()).thenReturn(chapters);

        mockMvc.perform(get("/chapter")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(chapterService, times(1)).getAllChapters();
    }

    @Test
    void testGetChapterById() throws Exception {
        Long chapterId = 1L;
        ChapterDTO chapter = new ChapterDTO();
        chapter.setId(chapterId);

        when(chapterService.getChapterById(chapterId)).thenReturn(chapter);

        mockMvc.perform(get("/chapter/{id}", chapterId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(chapterId));

        verify(chapterService, times(1)).findChapterById(chapterId);
        verify(chapterService, times(1)).getChapterById(chapterId);
    }

    @Test
    void testCreateChapter() throws Exception {
        ChapterDTO chapter = new ChapterDTO();
        chapter.setName("New Chapter");

        mockMvc.perform(post("/chapter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Chapter\"}"))
                .andExpect(status().isOk());

        verify(chapterService, times(1)).createChapter(any(ChapterDTO.class));
    }

    @Test
    void testUpdateChapter() throws Exception {
        ChapterDTO chapter = new ChapterDTO();
        chapter.setId(1L);
        chapter.setName("Updated Chapter");

        mockMvc.perform(put("/chapter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Updated Chapter\"}"))
                .andExpect(status().isOk());

        verify(chapterService, times(1)).updateChapter(any(ChapterDTO.class));
    }

    @Test
    void testDeleteChapter() throws Exception {
        Long chapterId = 1L;

        mockMvc.perform(delete("/chapter/{id}", chapterId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(chapterService, times(1)).findChapterById(chapterId);
        verify(chapterService, times(1)).deleteChapter(chapterId);
    }
}

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


