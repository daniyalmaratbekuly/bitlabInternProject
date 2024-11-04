package kz.orynbek.bitlabInternProject.DTO;

import kz.orynbek.bitlabInternProject.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChapterDTO {
    private Long id;
    private String name;
    private String description;
    private int order;
    private LocalDateTime createdTime;
    private  LocalDateTime updatedTime;
    private Course course;
}
