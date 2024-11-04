package kz.orynbek.bitlabInternProject.repositories;

import jakarta.transaction.Transactional;
import kz.orynbek.bitlabInternProject.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LessonRepository extends JpaRepository<Lesson, Long> {
Lesson findAllById(Long id);
Lesson findLessonById(Long id);
}
