package kz.orynbek.bitlabInternProject.service;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;

import java.util.List;

public interface LessonService {
    List<LessonDTO> getAllLessons();
    LessonDTO getLessonById(Long id);
    void createLesson(LessonDTO lessonDTO);
    void updateLesson(LessonDTO lessonDTO);
    void deleteLesson(Long id);
   void  findLessonById(Long id);
}
