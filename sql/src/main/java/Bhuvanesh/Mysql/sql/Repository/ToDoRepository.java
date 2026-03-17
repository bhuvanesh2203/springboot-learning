package Bhuvanesh.Mysql.sql.Repository;

import org.springframework.stereotype.Component;
@Component
public class ToDoRepository {


    public String printAllToDo(String str){
        return  "printed toDO in "+str;
    }
}

