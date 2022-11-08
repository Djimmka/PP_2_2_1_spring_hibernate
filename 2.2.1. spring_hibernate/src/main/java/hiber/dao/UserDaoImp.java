package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }



   @Override
   public User getUserByCar(Car car) {
      try {
         String HQL = "FROM User user INNER JOIN FETCH user.car WHERE (user.car.model = :carModel and user.car.series = :carSeries) ";
         Query<User> query = sessionFactory.openSession().createQuery(HQL, User.class).setParameter("carModel", car.getModel()).setParameter("carSeries", car.getSeries());
         return query.getSingleResult();

      } catch (TransactionException e) {
         System.out.println("cant get user by car");
         e.printStackTrace();
      }
      return null;
   }

}
