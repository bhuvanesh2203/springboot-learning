package Bhuvanesh.Mysql.sql.Controller;


import Bhuvanesh.Mysql.sql.Service.ToDoService;
import Bhuvanesh.Mysql.sql.User;
import Bhuvanesh.Mysql.sql.Repository.UserRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoService toDoService;


    // POST Method: Pushes data to the table
    // URL: http://localhost:8080/api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    @PostMapping("/createServicelevelEntry")
    public ResponseEntity<User> createUserService(@Valid @RequestBody User user) {
        return new ResponseEntity<>(toDoService.makePostcallForDB(user), HttpStatus.CREATED);
    }


    // GET Method: Fetches all data from the table
    // URL: http://localhost:8080/api/users
    @GetMapping
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<User>> getPaginatedData(@RequestParam int page, @RequestParam int size) {
        return  new ResponseEntity<>(toDoService.getPage(page,size),HttpStatus.OK);

        }


     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "value retrived"),
             @ApiResponse(responseCode = "404",description = "value NotFound")
     })
    @GetMapping("/{id}")
    public ResponseEntity<User> getId(@PathVariable Long id){
        try {
            return new ResponseEntity<>( toDoService.getID(id), HttpStatus.OK);
        }
        catch (RuntimeException e){
            log.info("Errror");
            log.warn("waring");
            return  new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "todoID")
    public  String getIDParams(@RequestParam("todoID") String str){
        return  "ParamsToDo"+str;
    }
    @PostMapping("/create")
    public  String GetIDBody(@RequestBody String str){
        return  str;
    }


    @GetMapping("/createWithID/{id}")
    public void createwithID(@PathVariable String id){
        toDoService.printService(id);
    }


}