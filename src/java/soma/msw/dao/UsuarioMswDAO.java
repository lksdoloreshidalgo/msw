/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.dao;

import constantes.ErrorLogConstante;
import form.ErrorLogForm;
import hibernate.HibernateUtilMSW;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.general.CatalogoRolModelo;
import modelo.general.PersonaModelo;
import modelo.general.SucursalModelo;
import modelo.general.UsuarioModelo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 30/10/2015
 * @hora 12:08:19 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 * @author SOMA-LUNA
 *
 */
public class UsuarioMswDAO {

    private final UtilDAO util = new UtilDAO();

    /**
     * Lista todo los ususarios que podran ingresar al sistema
     *
     * @return
     */
    public List<UsuarioModelo> lUsuarios() {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<UsuarioModelo> lum = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lum = session.createCriteria(UsuarioModelo.class).list();
            transaction.commit();
        } catch (Exception e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio un error al trata de listar un Usuario.",
                    e.getMessage(),
                    UsuarioMswDAO.class,
                    "lUsuarios()");
            Logger.getLogger(UsuarioMswDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return lum;
    }

    /**
     *
     * @param nickname
     * @return
     */
    public UsuarioModelo buscaUsuarioRegistrado(String nickname) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        UsuarioModelo us = null;
        try {
            transaction = session.beginTransaction();
            us = (UsuarioModelo) session.createCriteria(UsuarioModelo.class)
                    .add(Restrictions.eq("nickname", nickname))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de Buscar un usuario registrado.",
                    ex.getMessage(),
                    UsuarioMswDAO.class,
                    "buscaUsuarioRegistrado(String nickname)");
            Logger.getLogger(UsuarioMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return us;
    }

    /**
     *
     * @param nickname
     * @param contrasenia
     * @param sm
     * @return
     */
    public UsuarioModelo buscaExisteUsuario(String nickname, String contrasenia,
            SucursalModelo sm) {
        Session session = hibernate.HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        UsuarioModelo us = null;

        try {
            transaction = session.beginTransaction();
            us = (UsuarioModelo) session.createCriteria(UsuarioModelo.class)
                    .add(Restrictions.eq("nickname", nickname))
                    .add(Restrictions.eq("contrasenia", contrasenia))
                    .add(Restrictions.eq("sucursal.sucursalId", sm.getSucursalId()))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio un error al tratar de buscar un usuario.",
                    ex.getMessage(),
                    UsuarioMswDAO.class,
                    "buscaExisteUsuario(String nickname, String contrasenia,"
                    + " int sucursalid)");
            Logger.getLogger(UsuarioMswDAO.class.getName()).
                    log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return us;
    }

    /**
     *
     * @param usuario
     * @return
     */
    public synchronized boolean guardaUsuario(UsuarioModelo usuario) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction = null;
        boolean res = false;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(usuario);
            transaction.commit();
            res = true;
        } catch (Exception e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de crear el CRUD Usuario. ",
                    e.getMessage(),
                    UsuarioMswDAO.class,
                    "crud(UsuarioModelo usuario)");
            transaction.rollback();
            Logger.getLogger(UsuarioMswDAO.class.getName())
                    .log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return res;
    }

    /**
     *
     * @param pm
     * @return
     */
    public boolean guardaPersona(PersonaModelo pm) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction = null;
        boolean res = false;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(pm);
            transaction.commit();
            res = true;
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio al tratar de guardar una cuenta.",
                    ex.getMessage(),
                    UsuarioMswDAO.class,
                    "guardaPersona(PersonaModelo ch)");
            transaction.rollback();
            Logger.getLogger(UsuarioMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return res;
    }

    /**
     * Busca Ususario por nickname
     *
     * @param nickname
     * @return
     */
    public UsuarioModelo buscarUsuario(String nickname) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        UsuarioModelo um = new UsuarioModelo();
        try {
            transaction = session.beginTransaction();
            um = (UsuarioModelo) session.createCriteria(UsuarioModelo.class)
                    .add(Restrictions.eq("nickname", nickname))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio al tratar de buscar una cuenta.",
                    ex.getMessage(),
                    UsuarioMswDAO.class,
                    "buscarUsuario(String nickname)");
            Logger.getLogger(UsuarioMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return um;
    }

    /**
     * Metodo encargado de de buscar a un ususarios mediante su ID.
     *
     * @param usuarioId
     * @return
     */
    public UsuarioModelo buscaUsuarioId(int usuarioId) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        UsuarioModelo us = null;
        try {
            transaction = session.beginTransaction();
            us = (UsuarioModelo) session.createCriteria(UsuarioModelo.class)
                    .add(Restrictions.eq("usuarioId", usuarioId))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrioal tratar de cargar cuentahabiente.",
                    ex.getMessage(),
                    UsuarioMswDAO.class,
                    "buscaUsuarioRegistrado(String nickname)");
            Logger.getLogger(UsuarioMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return us;
    }

    /**
     * Metodo encargado de optener en una lista todos los roles registrados
     *
     * @return @throws Exception
     */
    public List<CatalogoRolModelo> listaCatalogoRol() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM CatalogoRolModelo crm ");
        return util.getHQL(String.valueOf(sql));
    }

    /**
     * Metodo encargado de optener una lista de todas la sucursales registradas
     *
     * @return @throws Exception
     */
    public List<SucursalModelo> listaSucursal() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM SucursalModelo sm ");
        return util.getHQL(String.valueOf(sql));
    }

    /**
     * Metodo encargado de buscar un rol por 
     * @param identificador
     * @return
     * @throws Exception
     */
    public List<CatalogoRolModelo> listaCatalogoRol(int identificador) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM CatalogoRolModelo crm ")
                .append(" WHERE crm.identificador= ")
                .append(identificador);
        return util.getHQL(String.valueOf(sql));
    }
}
