package pl.szymanski.sharelibrary.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.szymanski.sharelibrary.entity.Book;

import java.util.List;
import java.util.Set;

public interface BookJPARepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleIsContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b JOIN UserBook ub ON b.id = ub.book.id JOIN User u ON ub.user.id = u.id WHERE u.id = :userId")
    Set<Book> findByUserId(@Param("userId") Long userId);
}
