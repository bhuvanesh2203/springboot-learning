package Bhuvanesh.Mysql.sql.Repository;

import Bhuvanesh.Mysql.sql.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuth,Long> {


    Optional<UserAuth> findByEmail(String email);
}
