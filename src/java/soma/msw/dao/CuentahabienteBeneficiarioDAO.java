/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.dao;

import constantes.ErrorLogConstante;
import form.ErrorLogForm;
import form.GeneralForm;
import form.catalogo.ParticipanteForm;
import hibernate.HibernateUtilMSW;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.general.CatalogoModelo;
import modelo.general.CuentaModelo;
import modelo.general.CuentahabienteModelo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 1/07/2015
 * @hora 05:10:56 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class CuentahabienteBeneficiarioDAO {

    /**
     * lista los beneficiarios por:
     *
     * @param personaId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<CuentahabienteModelo> cargaCuentahabiente(int personaId) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<CuentahabienteModelo> lCuentahabiente = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lCuentahabiente = session.createCriteria(CuentahabienteModelo.class)
                    .add(Restrictions.eq("personaId", personaId))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (HibernateException e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió un error al trata de listar un beneficiario.",
                    e.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "cargaCuentahabiente(int personaId)");
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return lCuentahabiente;
    }

    /**
     * Lista todos los beneficiarios
     *
     * @return
     */
    public List<CuentahabienteModelo> listaCuentahabientes() {

        CatalogoModelo part = ParticipanteForm.buscaCatalogoClave(GeneralForm
                .getClaveParticipante());
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<CuentahabienteModelo> lchm = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lchm = session.createCriteria(CuentahabienteModelo.class)
                    .createCriteria("cuenta")
                    .add(Restrictions.not(Restrictions
                                    .eq("participante", part)))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (Exception e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió un error al trata de listar un beneficiario.",
                    e.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "listaCuentahabientes()");
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return lchm;
    }

    /**
     * Guarda al cuentahabiente Beneficiario
     *
     * @param chm
     * @return
     */
    public synchronized boolean guardarBeneficiario(CuentahabienteModelo chm) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction = null;
        boolean res = false;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(chm);
            transaction.commit();
        } catch (HibernateException e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrio al tratar de insertar un cuentahabiente",
                    e.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "guardarBeneficiario(CuentahabienteModelo chm)");
            transaction.rollback();
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return res;
    }

    /**
     * Guarda n cuentas a un Beneficiario
     *
     * @param cm
     * @return
     */
    public synchronized boolean guardarCuentaBeneficiario(CuentahabienteModelo cm) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction = null;
        boolean res = false;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(cm);
            transaction.commit();
        } catch (HibernateException e) {
            ErrorLogForm.insertaError(ErrorLogConstante.ERROR_BD_ME,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrio al tratar de insertar un cuentahabiente",
                    e.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "guardarCuentaBeneficiario(CuentahabienteModelo cm))");
            transaction.rollback();
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return res;
    }

    /**
     * Busca una cuenta por ID
     *
     * @param cuentaId
     * @return
     */
    public CuentaModelo listaCuenta(int cuentaId) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        CuentaModelo lchm = new CuentaModelo();

        try {
            transaction = session.beginTransaction();
            lchm = (CuentaModelo) session.createCriteria(CuentaModelo.class)
                    .add(Restrictions.eq("cuentaId", cuentaId))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            //En caso de error hace rollback
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrió al tratar de Buscar una Instruccion.",
                    ex.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "listaCuenta(int cuentaId)");
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lchm;
    }

    /**
     * Busca beneficiario por ID
     *
     * @param benficiarioId
     * @return
     */
    public CuentahabienteModelo buscaBeneficiario(int benficiarioId) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        CuentahabienteModelo chm = new CuentahabienteModelo();
        try {
            transaction = session.beginTransaction();
            chm = (CuentahabienteModelo) session.createCriteria(CuentahabienteModelo.class)
                    .add(Restrictions.eq("personaId", benficiarioId))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            //En caso de error hace rollback
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrió al tratar de Buscar una beneficiario.",
                    ex.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "buscaBeneficiario(int benficiarioId)");
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return chm;
    }

    /**
     * Metodo que permite obtener el numero de cuenta de un cuentahabiente
     *
     * @param numero
     * @return
     */
    public CuentaModelo obtenerNumero(String numero) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        CuentaModelo cm = new CuentaModelo();
        try {
            transaction = session.beginTransaction();
            cm = (CuentaModelo) session.createCriteria(CuentaModelo.class)
                    .add(Restrictions.eq("numero", numero))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            //En caso de error hace rollback
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrió al tratar de obtener el numero de una cuenta.",
                    ex.getMessage(),
                    CuentahabienteBeneficiarioDAO.class,
                    "obtenerNumero(String numero)");
            Logger.getLogger(CuentahabienteBeneficiarioDAO.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return cm;
    }
}
