package dev.andrylat.task2;

import dev.andrylat.task2.configs.AppConfig;
import dev.andrylat.task2.dao.CategoryDAO;
import dev.andrylat.task2.entities.Category;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

////        AnnotationConfigApplicationContext context =
////                new AnnotationConfigApplicationContext(AppConfig.class);
////
////        LocationDAO locationDAO = (LocationDAO) context.getBean("locationDAO");
////
////        List<Location> locations = locationDAO.getLocations();
////
////        for (Location location:locations) {
////            System.out.println(location);
////        }
////
////        Location locationById = locationDAO.getLocation(10);
////        System.out.println(locationById);
////
////        Location locationSave = new Location();
////
//////        locationSave.setId(15);
//////        locationSave.setTitle("Drunk oyster");
//////        locationSave.setWorkingHours("9:00-20:00");
//////        locationSave.setType("bar");
//////        locationSave.setAddress("blablabla");
//////        locationSave.setDescription("bobobo");
//////        locationSave.setCapacityPeople(1_000_000);
////
////        locationDAO.saveLocation(locationSave);
////
////        Location locationUpdated = new Location();
////
////        locationUpdated.setId(15);
////        locationUpdated.setTitle("Updated");
////        locationUpdated.setWorkingHours("Updated");
////        locationUpdated.setType("Updated");
////        locationUpdated.setAddress("Updated");
////        locationUpdated.setDescription("Updated");
////        locationUpdated.setCapacityPeople(1);
////
////        locationDAO.updateLocation(locationUpdated);
//
////        locationDAO.deleteLocation(1);
//        //        AnnotationConfigApplicationContext context =
////                new AnnotationConfigApplicationContext(AppConfig.class);
////
////        UserDAO userDAO = (UserDAO) context.getBean("userDAO");
////
////        List<User> users = userDAO.getUsers();
////
////        for (User user:users) {
////            System.out.println(user);
////        }
////
////        User userById = userDAO.getUserById(5);
////        System.out.println(userById);
////
//////        User userInsert = new User();
//////
//////        userInsert.setId(13);
//////        userInsert.setName("dennis");
//////        userInsert.setSurname("yudin");
//////        userInsert.setEmail("foo@bar");
//////        userInsert.setLogin("=)");
//////        userInsert.setPassword("1234");
//////        userInsert.setType("customer");
//////
//////        userDAO.saveUser(userInsert);
////
////        User userUpdate = new User();
////
////        userUpdate.setId(13);
////        userUpdate.setName("updated");
////        userUpdate.setSurname("updated");
////        userUpdate.setEmail("updated@bar");
////        userUpdate.setLogin("updated");
////        userUpdate.setPassword("updated");
////        userUpdate.setType("updated");
////
////        userDAO.updateUser(userUpdate);
////        userDAO.deleteUser(13);
//
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        CategoryDAO categoryDAO = (CategoryDAO) context.getBean("categoryDAO");

        Category categoryById = categoryDAO.getById(1);
        System.out.println(categoryById);
//
//        List<Category> categories = categoryDAO.getCategories();
//
//        for (Category category:categories) {
//            System.out.println(category);
//        }
//
////        Category categorySave = new Category();
////
////        categorySave.setId(14);
////        categorySave.setTitle("test one");
////
////        categoryDAO.saveCategory(categorySave);
//
//        Category categoryUpdate = new Category();
//
//        categoryUpdate.setId(14);
//        categoryUpdate.setTitle("updated");
//
//        categoryDAO.updateCategory(categoryUpdate);
//
//        categoryDAO.deleteCategory(14);
    }
}
