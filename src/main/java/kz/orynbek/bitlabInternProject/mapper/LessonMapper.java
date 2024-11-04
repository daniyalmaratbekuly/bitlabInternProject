package kz.orynbek.bitlabInternProject.mapper;

import kz.orynbek.bitlabInternProject.DTO.LessonDTO;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface LessonMapper {
    LessonDTO mapToDTO(Lesson lesson);
    Lesson mapToEntity(LessonDTO lessonDTO);
    List<LessonDTO>mapToDTO(List<Lesson>lessons);
    List<Lesson>mapToEntity(List<LessonDTO>lessonDTOs);
}
