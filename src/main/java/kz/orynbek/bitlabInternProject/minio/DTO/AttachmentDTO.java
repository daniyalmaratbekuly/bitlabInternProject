package kz.orynbek.bitlabInternProject.minio.DTO;

import kz.orynbek.bitlabInternProject.entities.Lesson;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentDTO {

    private Long id;
    private String name;
    private String url;
    private Lesson lesson;
    private LocalDateTime createdTime;
}