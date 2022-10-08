package library.repository;

import library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT * FROM library2 WHERE status like %?1% ",nativeQuery = true)
    List<Book> findAll( String status);

    @Query(value = "SELECT * FROM library2  WHERE ( title like %?1% OR genre like %?2% OR " +
            "authorsFirstName like %?3% OR authorsLastName like %?4% ) AND status like %?5% ", nativeQuery = true)
    List<Book> find(String titleReq, String genreReq,
                    String firstNameReq, String lastNameReq,String status);

    @Override
    void deleteById(Integer integer);

    @Transactional
    @Modifying
    @Query(value = "UPDATE library2 SET status = :status WHERE id = :idReq",nativeQuery = true)
    void updateStatus(@Param("status") String status, @Param("idReq") Integer idReq);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO library2 (title,genre,authorsFirstName," +
            "authorsLastName,status) VALUES (:titleReq,:genreReq,:firstNameReq," +
            ":lastNameReq,:statusReq)",nativeQuery = true)
    void addBook(@Param("titleReq") String titleReq, @Param("genreReq") String genreReq,
                 @Param("firstNameReq") String firstNameReq, @Param("lastNameReq") String lastNameReq,
                 @Param("statusReq") String statusReq);
}
