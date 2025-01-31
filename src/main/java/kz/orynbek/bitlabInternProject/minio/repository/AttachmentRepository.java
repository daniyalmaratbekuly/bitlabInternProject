package kz.orynbek.bitlabInternProject.minio.repository;

import kz.orynbek.bitlabInternProject.minio.Entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByLessonId(Long lessonId);
}
