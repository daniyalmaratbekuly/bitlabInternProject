package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;
import kz.orynbek.bitlabInternProject.controllers.ChapterController;
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
