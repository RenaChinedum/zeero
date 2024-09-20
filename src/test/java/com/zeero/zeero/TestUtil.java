package com.zeero.zeero;


import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.model.Users;

public class TestUtil {
    public static Users getUsers(UserDetailRequest request){
        Users users = new Users();
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setEmail(request.getEmail());
        users.setPassword(request.getPassword());
        return users;
    }
    public static UserDetailRequest getRequest(){
        UserDetailRequest request = new UserDetailRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("johndoe@gmail.com");
        request.setPassword("John@doe1");
        return request;
    }
}
