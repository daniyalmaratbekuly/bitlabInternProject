package kz.orynbek.bitlabInternProject.repositories;

import jakarta.transaction.Transactional;
import kz.orynbek.bitlabInternProject.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
Chapter findAllById(Long id);
Chapter findChapterById(Long id);
}
