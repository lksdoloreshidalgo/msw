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
import modelo.general.CatalogoCLABEEntidadModelo;
import modelo.general.CuentahabienteSucursalModelo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 3/08/2015
 * @hora 11:32:13 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class CuentahabienteOrdenanteDAO {

    private final UtilDAO util = new UtilDAO();
    private static final CuentahabienteOrdenanteDAO instacia = new CuentahabienteOrdenanteDAO();

    /**
     *
     * @return
     */
    public static CuentahabienteOrdenanteDAO getInstacia() {
        return instacia;
    }

    /**
     *
     * @return
     */
    public List<CuentahabienteSucursalModelo> lOrdenante() {

        Session session = hibernate.HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<CuentahabienteSucursalModelo> lchsm = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lchsm = session.createCriteria(CuentahabienteSucursalModelo.class).list();
            transaction.commit();
        } catch (Exception e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió un error al trata de listar un Ordenante.",
                    e.getMessage(),
                    CuentahabienteOrdenanteDAO.class,
                    "lOrdenante()");
            Logger.getLogger(CuentahabienteOrdenanteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }

        return lchsm;
    }

    /**
     * lista los beneficiarios por:
     *
     * @param ordenanteId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<CuentahabienteSucursalModelo> cargaCuentahabienteSucursal(int ordenanteId) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<CuentahabienteSucursalModelo> lCuentahabienteSucursal = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lCuentahabienteSucursal = session.createCriteria(CuentahabienteSucursalModelo.class)
                    .add(Restrictions.eq("personaId", ordenanteId))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (HibernateException e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió un error al trata de listar un beneficiario.",
                    e.getMessage(),
                    CuentahabienteOrdenanteDAO.class,
                    "cargaCuentahabienteSucursal(int ordenanteId)");
            Logger.getLogger(CuentahabienteOrdenanteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return lCuentahabienteSucursal;
    }

    /**
     * Metodo que busca a un ordenante por id
     *
     * @param personaId
     * @return
     */
    public CuentahabienteSucursalModelo buscaOrdenante(int personaId) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        CuentahabienteSucursalModelo chsm = new CuentahabienteSucursalModelo();
        try {
            transaction = session.beginTransaction();
            chsm = (CuentahabienteSucursalModelo) session.createCriteria(CuentahabienteSucursalModelo.class)
                    .createCriteria("cuentaHabiente")
                    .add(Restrictions.eq("personaId", personaId))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió un error al trata de listar un beneficiario.",
                    e.getMessage(),
                    CuentahabienteOrdenanteDAO.class,
                    "buscaOrdenante(int personaId)");
            Logger.getLogger(CuentahabienteOrdenanteDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            session.clear();
            session.close();
        }
        return chsm;
    }

    /**
     *
     * @return @throws Exception
     */
    public List<CuentahabienteSucursalModelo> listarCuentaHabienteOrdenante() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT chs.id, ch.nombre, chs.numeroCuentahabiente, ch.rfc, chs.identificador, ch.estado, ch.personaId, cu.cuentaId ")
                .append(" FROM CuentahabienteSucursalModelo chs JOIN chs.cuentaHabiente ch JOIN ch.cuenta cu");
        return util.getHQL(String.valueOf(sql));
    }

    /**
     * Lista el catalogo de CLABE
     *
     * @return
     * @throws Exception
     */
    public List<CatalogoCLABEEntidadModelo> listarClabeEntidad() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" From CatalogoCLABEEntidadModelo ");
        return util.getHQL(String.valueOf(sql));

    }

    /**
     * Lista el catalogo de CLABE por identificador
     *
     * @param identificador
     * @return
     * @throws Exception
     */
    public List<CatalogoCLABEEntidadModelo> listarClabeEntidad(int identificador) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM CatalogoCLABEEntidadModelo ccem ")
                .append(" WHERE ccem.identificador=").append(identificador);
        return util.getHQL(String.valueOf(sql));

    }

    /**
     * Lista el catalogo de CLABE por identificador
     *
     * @param numClabe
     * @return
     * @throws Exception
     */
    public List<CuentahabienteSucursalModelo> listaCuentahabienteSucursal(String numClabe) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM CuentahabienteSucursalModelo csm ")
                .append(" WHERE csm.numeroCuentahabiente='")
                .append(numClabe).append("'");
        return util.getHQL(String.valueOf(sql));

    }

    /**
     * Metodo que se encarga de hacer un barrido de los Cuentahabientes
     * ordenantes por sucursal.
     *
     * @param sucursal
     * @return
     * @throws Exception
     */
    public List<Object[]> lCuentahabienteSucursal(String sucursal) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ch.nombre, chs.numeroCuentahabiente, ch.rfc, chs.identificador, ch.estado, ch.personaId, chs.id, cat.clave ")
                .append(" FROM CuentahabienteSucursalModelo chs ")
                .append(" JOIN chs.sucursal su ")
                .append(" JOIN chs.cuentaHabiente ch ")
                .append(" JOIN ch.cuenta cue ")
                //                .append(" full JOIN chs.catalogoClabeentidad cce ")
                .append(" JOIN cue.tipoCuenta cat")
                .append(" WHERE su.numeroClabe='")
                .append(sucursal)
                .append("'");
        return util.getHQL(String.valueOf(sql));

    }
}
