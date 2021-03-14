package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {

//methods to create, retrieve, edit, delete

    private static final String GET_ALL_USERS = "select * from users";
    private static final String CREATE_USER = "insert into users " +
            "(username, email, password)" +
            "values (?,?,?)";
    private static final String DELETE_USER = "delete from users where id = ?";
    private static final String READ_USER = "select * from users where id = ?";
    private static final String UPDATE_USER = "update users set " +
            "username = ?, email = ?, password = ? where id = ?";

    public static void delete(int userID) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmDeleteUser = connection.prepareStatement(DELETE_USER)) {
            pstmDeleteUser.setInt(1, userID);
            pstmDeleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    below method doesn't use user object - to be confirmed why
//    static void update(int idToUpdate) {
//        try (Connection connection = DBUtil.getConnection();
//             PreparedStatement pstmDeleteUser = connection.prepareStatement(UPDATE_USER)) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Please enter user name to be updated");
//            String nameToBeUpdated = scanner.nextLine();
//            System.out.println("Please email to be updated");
//            String emailToBeUpdated = scanner.nextLine();
//            System.out.println("Please enter your new password");
//            String passToBeUpdated = scanner.nextLine();
//            scanner.close();
//
//            pstmDeleteUser.setInt(1, nameToBeUpdated);
//            pstmDeleteUser.setString(2, emailToBeUpdated);
//            pstmDeleteUser.setString(3, passToBeUpdated);
//            pstmDeleteUser.setInt(4, idToUpdate);
//            pstmDeleteUser.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void update(User user) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmUpdateUser = connection.prepareStatement(UPDATE_USER)) {

            pstmUpdateUser.setString(1, user.getUserName());
            pstmUpdateUser.setString(2, user.getEmail());
            pstmUpdateUser.setString(3, hashPassword(user.getPassword()));
            pstmUpdateUser.setInt(4, user.getId());
            pstmUpdateUser.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User read(int userId) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmReadUser = connection.prepareStatement(READ_USER)) {

            pstmReadUser.setInt(1, userId);
            ResultSet resultSet = pstmReadUser.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User createUser(User user) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmCreateUser = connection.prepareStatement(CREATE_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmCreateUser.setString(1, user.getUserName());
            pstmCreateUser.setString(2, user.getEmail());
            pstmCreateUser.setString(3, hashPassword(user.getPassword()));
            pstmCreateUser.executeUpdate();
            ResultSet resultSet = pstmCreateUser.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User[] findAll() {

        User[] allUsers = new User[0];

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmReadAllUsers = connection.prepareStatement(GET_ALL_USERS)) {

            ResultSet resultSet = pstmReadAllUsers.executeQuery();
        while (resultSet.next()) {
        allUsers = Arrays.copyOf(allUsers, allUsers.length + 1);
        allUsers[allUsers.length - 1] = new User(resultSet.getInt("id"), resultSet.getString("username"),
                resultSet.getString("email"), resultSet.getString("password"));

        } return allUsers;

        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
