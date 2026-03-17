package Bhuvanesh.Mysql.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue
    Long id;
    @Email
    String email;
    String password;
}
