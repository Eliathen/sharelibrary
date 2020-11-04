package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.List;

public interface BookJPARepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleIsContainingIgnoreCase(String title);

}
