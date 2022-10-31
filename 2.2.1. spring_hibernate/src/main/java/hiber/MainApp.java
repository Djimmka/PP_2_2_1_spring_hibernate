package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);




//      userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car("ra-ta-ta1", 1231)));
//      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car("ra-ta-ta2", 1232)));
//      userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car("ra-ta-ta3", 1233)));
//      userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car("ra-ta-ta4", 1234)));

      for (int i = 1; i<4; i++ ) {
         User userr = new User("User"+i, "Lastname"+i, "user"+i+"@mail.ru");
         Car car = new Car("ra-ta-ta"+i, 1230+i);
         userr.setCar(car);
         car.setUser(userr);
         userService.add(userr);
      }

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car ="+user.getCar().getModel());
         System.out.println();
      }

      Car car2 = new Car("ra-ta-ta1", 1231);
      User us1 = userService.getUserByCar(car2);
      System.out.println(us1.getFirstName()+" "+ us1.getLastName() + " "+ us1.getEmail()+ " " + us1.getCar().getModel() + " " + us1.getCar().getSeries());

      context.close();
   }
}
