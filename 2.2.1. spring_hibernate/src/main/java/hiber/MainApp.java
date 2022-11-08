package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      CarService carService = context.getBean(CarService.class);

      for (int i = 0; i<5; i++ ) {
         User userr = new User("User"+i, "Lastname"+i, "user"+i+"@mail.ru");
         Car car = new Car("ra-ta-ta"+i, 1230+i);
         car.setUser(userr);
         userr.setCar(car);
         carService.add(car);
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
