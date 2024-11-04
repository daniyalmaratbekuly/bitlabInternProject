package kz.orynbek.bitlabInternProject.controllers;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lesson")
@Tag(name = "Lesson Controller", description = "Управление уроками")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Operation(summary = "Получить все уроки", description = "Возвращает список всех уроков")
    @GetMapping
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @Operation(summary = "Получить урок по ID", description = "Возвращает урок по уникальному ID")
    @GetMapping("/{id}")
    public LessonDTO getLessonById(@PathVariable("id") Long id) {
        lessonService.findLessonById(id);
        return lessonService.getLessonById(id);
    }

    @Operation(summary = "Создать новый урок", description = "Создает новый урок и сохраняет его в базе данных")
    @PostMapping
    public void createLesson(@RequestBody LessonDTO lessonDTO) {
        lessonService.createLesson(lessonDTO);
    }

    @Operation(summary = "Обновить урок", description = "Обновляет информацию об уроке")
    @PutMapping
    public void updateLesson(@RequestBody LessonDTO lessonDTO) {
        lessonService.updateLesson(lessonDTO);
    }

    @Operation(summary = "Удалить урок по ID", description = "Удаляет урок по уникальному ID")
    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable("id") Long id) {
        lessonService.findLessonById(id);
        lessonService.deleteLesson(id);
    }
}
