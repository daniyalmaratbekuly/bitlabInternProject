package kz.orynbek.bitlabInternProject.controllers;

import kz.orynbek.bitlabInternProject.DTO.ChapterDTO;
import kz.orynbek.bitlabInternProject.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter")
@Tag(name = "Chapter Controller", description = "Управление главами курса")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Operation(summary = "Получить все главы", description = "Возвращает список всех глав")
    @GetMapping
    public List<ChapterDTO> getAllChapters() {
        return chapterService.getAllChapters();
    }

    @Operation(summary = "Получить главу по ID", description = "Возвращает главу по ее уникальному ID")
    @GetMapping("/{id}")
    public ChapterDTO getChapterById(@PathVariable("id") Long id) {
        chapterService.findChapterById(id);
        return chapterService.getChapterById(id);
    }

    @Operation(summary = "Создать новую главу", description = "Создает новую главу и сохраняет её")
    @PostMapping
    public void createChapter(@RequestBody ChapterDTO chapterDTO) {
        chapterService.createChapter(chapterDTO);
    }

    @Operation(summary = "Обновить главу", description = "Обновляет существующую главу")
    @PutMapping
    public void updateChapter(@RequestBody ChapterDTO chapterDTO) {
        chapterService.updateChapter(chapterDTO);
    }

    @Operation(summary = "Удалить главу по ID", description = "Удаляет главу по уникальному ID")
    @DeleteMapping("/{id}")
    public void deleteChapter(@PathVariable("id") Long id) {
        chapterService.findChapterById(id);
        chapterService.deleteChapter(id);
    }
}
