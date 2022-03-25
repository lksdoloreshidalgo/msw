/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Luna
 */
public class HibernateUtilMSW {

    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

    private static SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.

//            File f = new File("hibernate.cfg.xml");
//            String url = "";
//            f = new File(url);
//            System.err.println(f.getAbsoluteFile());
//            System.err.println(f.getCanonicalFile());
//            System.err.println(f.getParentFile());
//            System.err.println(f.getAbsolutePath());
//            System.err.println(f.getPath());
//            System.out.println(new File(".").getAbsolutePath());
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
////            new HibernateUtil(sessionFactory);
//            HibernateUtil.sessionFactory = sessionFactory;
        } catch (HibernateException ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     *
     * @return @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession()
                    : null;
            threadLocal.set(session);
        }

        return session;
    }

    /**
     *
     */
    public static void rebuildSessionFactory() {
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionHibernate() {
        return sessionFactory;
    }
}
