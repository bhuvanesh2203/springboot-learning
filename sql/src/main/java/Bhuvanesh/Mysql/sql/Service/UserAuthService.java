package Bhuvanesh.Mysql.sql.Service;

import Bhuvanesh.Mysql.sql.Repository.UserAuthenticationRepository;

import Bhuvanesh.Mysql.sql.User;
import Bhuvanesh.Mysql.sql.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {
    @Autowired
    private UserAuthenticationRepository UserAuthenticationRepository;



    public UserAuth makePostcallForDB(UserAuth userAuth){
        return  UserAuthenticationRepository.save(userAuth);
    }

    public  UserAuth getID(Long id){
        return UserAuthenticationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User id not found in to list"));
    }


}
