package Bhuvanesh.Mysql.sql;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom finders here later, e.g., findByEmail(String email);
}