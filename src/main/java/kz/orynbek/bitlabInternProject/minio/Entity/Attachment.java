package kz.orynbek.bitlabInternProject.minio.Entity;

import jakarta.persistence.*;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")

@Data
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lesson lesson;

    @CreationTimestamp
    private LocalDateTime createdTime;

}
