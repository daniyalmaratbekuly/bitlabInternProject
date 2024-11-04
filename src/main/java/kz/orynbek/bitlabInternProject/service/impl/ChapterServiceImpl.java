package kz.orynbek.bitlabInternProject.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;
import kz.orynbek.bitlabInternProject.entities.Chapter;
import kz.orynbek.bitlabInternProject.exception.ChapterNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.ChapterMapper;
import kz.orynbek.bitlabInternProject.repositories.ChapterRepository;
import kz.orynbek.bitlabInternProject.service.ChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ChapterMapper chapterMapper;
    private static final Logger logger = LoggerFactory.getLogger(ChapterServiceImpl.class);


    public List<ChapterDTO>getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();
        return chapterMapper.mapToDTO(chapters);
    }

    public ChapterDTO getChapterById(Long id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chapter with id " + id + " not found"));
        return chapterMapper.mapToDTO(chapter);
    }
    public void createChapter(ChapterDTO chapterDTO) {
        Chapter chapter = chapterMapper.mapToEntity(chapterDTO);
        logger.info("Creating new chapter: {}", chapter);
        logger.debug("Chapter data: {}", chapter);
        chapterRepository.save(chapter);
    }
    public void updateChapter(ChapterDTO chapterDTO) {
        logger.info("Updating chapter with id: {}", chapterDTO.getId());
        Chapter chapter = chapterMapper.mapToEntity(chapterDTO);
        logger.debug("Original chapter data: {}", chapter);
        chapter.setName(chapterDTO.getName());
        chapter.setDescription(chapterDTO.getDescription());
        chapter.setOrder(chapterDTO.getOrder());
        chapter.setCreatedTime(chapterDTO.getCreatedTime());
        chapter.setUpdatedTime(chapterDTO.getUpdatedTime());
        chapter.setCourse(chapterDTO.getCourse());
        logger.debug("Updated chapter data: {}", chapter);
        chapterRepository.save(chapter);
    }

    public void deleteChapter(Long id) {
        logger.info("Deleting chapter with id: {}", id);
        chapterRepository.deleteById(id);
        logger.debug("Chapter with id {} deleted successfully", id);
    }


    public void findChapterById(Long id) {
         chapterRepository.findById(id)
                .orElseThrow(() -> new ChapterNotFoundException("Chapter with ID " + id + " not found"));
          chapterMapper.mapToDTO(chapterRepository.findChapterById(id));
    }
}
