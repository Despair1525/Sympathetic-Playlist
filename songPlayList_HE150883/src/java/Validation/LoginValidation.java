/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

/**
 *
 * @author smileymask
 */
import java.util.*;
import model.*;
public class LoginValidation {
public String status;
    public LoginValidation() {
    }
    
    public String getStatus(){
    String temp = status;
    status = null;
    return temp;
    };
    public User_HE150883 checkUser(String id , ArrayList<User_HE150883> userLst){      
       for(User_HE150883 user: userLst){
           if(id.equalsIgnoreCase(user.getUserId())) return user;
       };
    status ="Not Found UserId";
    return null;
    };
    
    
    public String checkPassword(String password,String RePass){
        String check="(?=.*[0-9])(?=.*[a-z])\\w{5,31}";
        if(password.equals(RePass)) {
            if(password.matches(check)){
                return password;
            }else{
            status = "password muse have length from 8 to 31 and have at least one Number and String ";
            return null;
            }
        };
    status = "Confirm Password must same with Password";
    return null;
    };
    
    public static void main(){
    
    };
    
    public String checkEmail(String email){
    
    String check ="[\\w-.]+@([\\w-]+\\.)+[\\w-]+";
    if(email.matches(check)){
    return email;
    };
    status="Email not valid";
    return null;
    };
    
    public String checkPhone(String phone){
    
        try {
         Integer.parseInt(phone);
         if(phone.length() == 10) {
         return phone;
         };
        } catch (Exception e) {
        }
   status="Phone must be number and have 10 digits";
    return null;
    };
    
    
    
    
}
