package se.semit.ykovtun.webappskyvlab2.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import se.semit.ykovtun.webappskyvlab2.entities.HospitalDepartment;
import se.semit.ykovtun.webappskyvlab2.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
public class HospitalDepartmentDAO implements GenericDAO<HospitalDepartment> {
    public HospitalDepartmentDAO() { }

    @Override
    public HospitalDepartment findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(HospitalDepartment.class, id);
        }
        catch (HibernateException e) {
            return null;
        }
    }

    @Override
    public List<HospitalDepartment> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM HospitalDepartment", HospitalDepartment.class).list();
        }
        catch (HibernateException e) {
            return List.of();
        }
    }

    @Override
    public void save(HospitalDepartment department) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(department);
            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void update(Long id, HospitalDepartment department) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            HospitalDepartment hospitalDepartment = session.get(HospitalDepartment.class, id);

            hospitalDepartment.setName(department.getName());
            hospitalDepartment.setShortName(department.getShortName());
            hospitalDepartment.setCodeBuilding(department.getCodeBuilding());
            hospitalDepartment.setFloor(department.getFloor());
            hospitalDepartment.setBoxCount(department.getBoxCount());

            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            HospitalDepartment department = session.get(HospitalDepartment.class, id);
            session.remove(department);
            transaction.commit();
        }
        catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public HospitalDepartment findByKey(String key, Object value) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM HospitalDepartment WHERE " + key + " = :value";
            Query<HospitalDepartment> query = session.createQuery(hql, HospitalDepartment.class);

            query.setParameter("value", value);

            return query.getSingleResult();
        }
        catch (HibernateException | NoResultException e) {
            return null;
        }
    }

    public List<HospitalDepartment> findByTemplate(HospitalDepartment template) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<HospitalDepartment> query = builder.createQuery(HospitalDepartment.class);
            Root<HospitalDepartment> root = query.from(HospitalDepartment.class);
            List<Predicate> predicates = new ArrayList<>();

            if (template.getName() != null && !template.getName().isEmpty()) {
                predicates.add(builder.like(root.get("name"), template.getName() + "%"));
            }
            if (template.getShortName() != null && !template.getShortName().isEmpty()) {
                predicates.add(builder.like(root.get("shortName"), template.getShortName() + "%"));
            }
            if (template.getCodeBuilding() != null && !template.getCodeBuilding().isEmpty()) {
                predicates.add(builder.equal(root.get("codeBuilding"), template.getCodeBuilding()));
            }
            if (template.getFloor() != null) {
                predicates.add(builder.equal(root.get("floor"), template.getFloor()));
            }
            if (template.getBoxCount() != null) {
                predicates.add(builder.equal(root.get("boxCount"), template.getBoxCount()));
            }
            query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.createQuery(query).getResultList();
        }
        catch (HibernateException e) {
            return List.of();
        }
    }
}
