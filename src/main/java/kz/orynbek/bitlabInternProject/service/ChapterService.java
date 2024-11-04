package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;

import java.util.List;

public interface ChapterService {
    List<ChapterDTO> getAllChapters();
    ChapterDTO getChapterById(Long id);
    void createChapter(ChapterDTO chapterDTO);
    void updateChapter(ChapterDTO chapterDTO);
    void deleteChapter(Long id);
    void findChapterById(Long id);
}
