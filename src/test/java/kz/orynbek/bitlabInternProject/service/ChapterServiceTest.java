package kz.orynbek.bitlabInternProject.service;

import jakarta.persistence.EntityNotFoundException;
import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;
import kz.orynbek.bitlabInternProject.entities.Chapter;
import kz.orynbek.bitlabInternProject.exception.ChapterNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.ChapterMapper;
import kz.orynbek.bitlabInternProject.repositories.ChapterRepository;
import kz.orynbek.bitlabInternProject.service.impl.ChapterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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


