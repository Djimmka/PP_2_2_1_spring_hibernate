package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(Car car) {
      try {
         CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
         CriteriaQuery<Car> ctq = criteriaBuilder.createQuery(Car.class);
         Root<Car> carRoot = ctq.from(Car.class);
         Car qresult = sessionFactory.openSession().createQuery(ctq.select(carRoot)
                         .where(criteriaBuilder.equal(carRoot.get("model"), car.getModel()), criteriaBuilder.equal(carRoot.get("series"), car.getSeries())))
                 .getSingleResult();


         CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
         Root<User> userRoot = criteriaQuery.from(User.class);
         User queryResult = sessionFactory.openSession().createQuery(criteriaQuery.select(userRoot)
                         .where(criteriaBuilder.equal(userRoot.get("car"),qresult)))
                 .getSingleResult();
         return queryResult;
      } catch (TransactionException e) {
         System.out.println("cant get user by car");
         e.printStackTrace();
      }
      return null;
   }

}
