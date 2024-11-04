package kz.orynbek.bitlabInternProject.service.impl;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import kz.orynbek.bitlabInternProject.exception.LessonNotFoundException;
import kz.orynbek.bitlabInternProject.mapper.LessonMapper;
import kz.orynbek.bitlabInternProject.repositories.LessonRepository;
import kz.orynbek.bitlabInternProject.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonMapper lessonMapper;

    private static final Logger logger = LoggerFactory.getLogger(LessonServiceImpl.class);


    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessonMapper.mapToDTO(lessons);
    }

    @Override
    public LessonDTO getLessonById(Long id) {
        return lessonMapper.mapToDTO(lessonRepository.findAllById(id));
    }

    @Override
    public void createLesson(LessonDTO lessonDTO) {
       Lesson lesson = lessonMapper.mapToEntity(lessonDTO);
       logger.info("Creating new lesson: {}", lesson);
       logger.debug("Lesson data: {}", lesson);
       lessonRepository.save(lesson);
    }

    @Override
    public void updateLesson(LessonDTO lessonDTO) {
        logger.info("Updating lesson with id: {}", lessonDTO.getId());
        Lesson lesson = lessonRepository.findAllById(lessonDTO.getId());
        logger.debug("Original lesson data: {}", lesson);
        lesson.setName(lessonDTO.getName());
        lesson.setContent(lessonDTO.getContent());
        lesson.setOrder(lessonDTO.getOrder());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setCreatedTime(lessonDTO.getCreatedTime());
        lesson.setUpdatedTime(lessonDTO.getUpdatedTime());
        lesson.setChapter(lessonDTO.getChapter());
        logger.debug("Updated lesson data: {}", lesson);
        lessonRepository.save(lesson);

    }

    @Override
    public void deleteLesson(Long id) {
        logger.info("Deleting lesson with id: {}", id);
        lessonRepository.deleteById(id);
        logger.debug("Lesson with id {} deleted successfully", id);

    }
    public void findLessonById(Long id) {
        lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson with ID " + id + " not found"));
        lessonMapper.mapToDTO(lessonRepository.findLessonById(id));
    }



}

