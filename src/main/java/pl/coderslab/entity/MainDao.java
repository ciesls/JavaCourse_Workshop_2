package pl.coderslab.entity;

import java.util.Scanner;

public class MainDao {

    public static void main(String[] args) {

//    add user options, switch case, confirmation

//    UserDao userDao = new UserDao();
//    User user = new User();
//    user.setUserName("testUser12");
//    user.setEmail("qqaa@gg.com");
//    user.setPassword("passwe");
//    userDao.createUser(user);

//    extract scanner part to method in new class based on scanner returning particular type, assign variables to
//    returns from methods
//    option - to be checked with if statement

//    deleting user - working

//        System.out.println("Please enter ID of user to delete");
//        Scanner scanner1 = new Scanner(System.in);
//        int userID = scanner1.nextInt();
//        UserDao.delete(userID);
//        System.out.println("User has been deleted");

//     updating user
//        System.out.println("Please enter user ID you want to update");
//        Scanner scanner2 = new Scanner(System.in);
//        int idToBeUpdated = scanner2.nextInt();
//        UserDao.update(idToBeUpdated);


//reading user test - not printing correctly
//        User read = UserDao.read(1);
//        System.out.println(read);

//      update test - working
//        User userToUpdate = UserDao.read(3);
//        userToUpdate.setUserName("pppp");
//        userToUpdate.setEmail("lllll");
//        userToUpdate.setPassword("mmmmm");
//        UserDao.update(userToUpdate);

//        reading all users
        User[] all = UserDao.findAll();
        for (User u : all) {
            System.out.println(u);
        }


    }




}
