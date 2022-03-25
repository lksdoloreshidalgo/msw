/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import hibernate.HibernateUtilMSW;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 25/08/2016
 * @hora 11:19:58 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
public class UtilDAO {

    /**
     *
     * @param hql
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected synchronized List getHQL(final String hql) throws Exception {
        List res = new ArrayList();
        Session session = HibernateUtilMSW.getSession();
        try {
            Query query = session.createQuery(hql);
            res = query.list();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
        } finally {
            session.flush();
            session.clear();
        }
        return res;
    }

}
