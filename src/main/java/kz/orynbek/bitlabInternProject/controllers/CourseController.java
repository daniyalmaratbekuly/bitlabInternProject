package kz.orynbek.bitlabInternProject.controllers;

import kz.orynbek.bitlabInternProject.DTO.CourseDTO;
import kz.orynbek.bitlabInternProject.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/course")
@Tag(name = "Course Controller", description = "Управление курсами")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Получить все курсы", description = "Возвращает список всех доступных курсов")
    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @Operation(summary = "Получить курс по ID", description = "Возвращает курс по его уникальному ID")
    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable("id") Long id) {
        courseService.findCourseById(id);
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Создать новый курс", description = "Создает новый курс и сохраняет его в базе данных")
    @PostMapping
    public void createCourse(@RequestBody CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
    }

    @Operation(summary = "Обновить существующий курс", description = "Обновляет информацию о существующем курсе")
    @PutMapping
    public void updateCourse(@RequestBody CourseDTO courseDTO) {
        courseService.updateCourse(courseDTO);
    }

    @Operation(summary = "Удалить курс по ID", description = "Удаляет курс по уникальному ID")
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") Long id) {
        courseService.findCourseById(id);
        courseService.deleteCourse(id);
    }
}
