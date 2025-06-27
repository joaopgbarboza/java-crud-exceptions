import br.com.dio.dao.UserDAO;
import br.com.dio.exception.EmptyStorageException;
import br.com.dio.exception.UserNotFoundException;
import br.com.dio.exception.ValidatorException;
import br.com.dio.model.MenuOption;
import br.com.dio.model.UserModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static br.com.dio.validator.UserValidator.verifyModel;

public class Main {

    private final static UserDAO dao = new UserDAO();

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){


        while (true) {
            System.out.println("Welcome to user registration, choose the operation");
            System.out.println("1 - Register");
            System.out.println("2 - Update");
            System.out.println("3 - Delete");
            System.out.println("4 - Search by Id");
            System.out.println("5 - List");
            System.out.println("6 - Exit");
            var userInput = scanner.nextInt();
            var selectedOption = MenuOption.values()[userInput -1];
            switch (selectedOption) {
                case SAVE -> {
                    try{
                        var user = dao.save(requestToSave());
                        System.out.printf("User %s registered!", user);
                    } catch (ValidatorException ex){
                        ex.printStackTrace();
                    }
                }

                case UPDATE -> {
                    try{
                        var user = dao.update(requestToUpdate());
                        System.out.printf("User %s is updated", user);
                    } catch (UserNotFoundException | EmptyStorageException ex){
                            System.out.println(ex.getMessage());
                    } catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                    finally {
                        System.out.println("=================");
                    }
                    }

                case DELETE -> {
                    try{
                        dao.delete(requestId());
                        System.out.println("User deleted!");
                    } catch (UserNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    } finally {
                        System.out.println("=================");
                    }
                }

                case FIND_BY_ID -> {
                    try {
                        var id = requestId();
                        var user = dao.findById(id);
                        System.out.printf("User found: %s", id);
                        System.out.println(user);
                    } catch (UserNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    } finally {
                        System.out.println("=================");
                    }
                }

                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.println("Users registered");
                    System.out.println("================");
                    users.forEach(System.out::println);
                    System.out.println("================");
                }

                case EXIT -> System.exit(0);
            }
        }
    }

    private static long requestId(){
        System.out.println("Insert user id");
        return scanner.nextLong();
    }

    private static UserModel requestToSave() {
        System.out.println("Insert user name");
        var name = scanner.next();
        System.out.println("Insert user email");
        var email = scanner.next();
        System.out.println("Insert user birthday (MM/dd/yyyy)");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);
        return validateInput(0, name, email, birthday);


    }

    private static UserModel validateInput(final long id, final String name,
                              final String email, final LocalDate birthday) throws ValidatorException{
        var user = new UserModel(0, name, email, birthday);
        verifyModel(user);
        return user;
    }

    private static UserModel requestToUpdate() {
        System.out.println("Insert user id");
        var id = scanner.nextLong() ;
        System.out.println("Insert user name");
        var name = scanner.next();
        System.out.println("Insert user email");
        var email = scanner.next();
        System.out.println("Insert user birthday (MM/dd/yyyy)");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        UserModel model = new UserModel();
        var birthday = LocalDate.parse(birthdayString, formatter);
        return validateInput(0, name, email, birthday);
    }
}
