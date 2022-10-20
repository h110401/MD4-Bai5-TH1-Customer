package rikkei.academy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import rikkei.academy.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerServiceIMPL implements ICustomerService {

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        String queryStr = "SELECT c from Customer as c";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        String queryStr = "SELECT c FROM Customer as c where id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(queryStr, Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Customer customer = null;
            if (id != null) {
                customer = findById(id);
            }
            if(customer != null) {
                session.delete(customer);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            if (customer.getId() != null) {
                Customer customer1 = findById(customer.getId());
                customer1.setName(customer.getName());
                customer1.setEmail(customer.getEmail());
                customer1.setAddress(customer.getAddress());
            }

            session.saveOrUpdate(customer);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
