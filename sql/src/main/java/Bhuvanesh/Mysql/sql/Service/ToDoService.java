package Bhuvanesh.Mysql.sql.Service;

import Bhuvanesh.Mysql.sql.Repository.ToDoRepository;
import Bhuvanesh.Mysql.sql.User;
import Bhuvanesh.Mysql.sql.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepo;

    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository=toDoRepository;
    }

    public  void printService(String str){
        System.out.println(toDoRepository.printAllToDo(str));
    }
    public User makePostcallForDB(User user){
        return  userRepo.save(user);
    }

    public  User getID(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User id not found in to list"));
    }

    public  User delete(Long id){
        try {
            User user = getID(id);
            userRepo.delete(user);
            return user;
        }
        catch (Exception e){
            return null;
        }

    }

    public Page<User> getPage(int page , int size){
        Pageable pageable= PageRequest.of(page,size);
        return  userRepo.findAll(pageable);
    }
}
