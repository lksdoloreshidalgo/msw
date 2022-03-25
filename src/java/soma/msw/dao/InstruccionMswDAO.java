/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.dao;

import constantes.ErrorLogConstante;
import form.ErrorLogForm;
import form.InstruccionForm;
import form.SucursalForm;
import form.catalogo.EstadoOrdenForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoPagoForm;
import hibernate.HibernateUtilMSW;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.general.BitacoraSaldoInicialModelo;
import modelo.general.CatalogoModelo;
import modelo.general.InstruccionModelo;
import modelo.general.SucursalModelo;
import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 8/09/2015
 * @hora 11:22:31 AM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 *
 */
public class InstruccionMswDAO {

    /**
     * Hilo de las Instrucciones.
     */
    private static final List<Object> lHilo
            = new ArrayList<Object>();

    /**
     * Retorna la lista de hilos de instrucciones
     *
     * @return
     */
    public static List<Object> getlHilo() {
        return lHilo;
    }

    /**
     * Guarda una instruccionWeb por hibernate
     *
     * @param im
     * @return
     */
    public synchronized boolean guardaInstruccionWeb(InstruccionModelo im) {

        //Deten Hilo
        Util.detenHilo(InstruccionMswDAO.getlHilo());
        //Inicia Sesion Hibernate
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        //Inicia Transaccion
        Transaction transaction = null;
        //Inicia Respuesta
        boolean res = false;
        try {
            //Inicia transaccion
            transaction = session.beginTransaction();
            //Guarda Objeto
            session.saveOrUpdate(im);
            //Hace commit la transaccion
            transaction.commit();
            //Agrega exito a la respuesta
            res = true;
            //Inserta el estado  cerrespondiente en la bitacora de la orden
            InstruccionForm.insertaEstadoBitacoraOrden(im);
            //Carga tabla principal
//            JFPrincipal.cargaPagosLocal();
        } catch (HibernateException ex) {
            //En caso de error hace rollback
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrió al tratar de Guardar una Instruccion.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "guardaInstruccionWEB(InstruccionModelo im)");
            transaction.rollback();
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //Finaliza la sesion
            session.clear();
            session.close();
            //Libera Hilo
//            Util.liberaHilo(InstruccionMswDAO.getlHilo());
        }
        return res;
    }

    /**
     * Busca una la instruccion por la id
     *
     * @param instruccionId
     * @return
     */
    public InstruccionModelo buscaInstruccion(int instruccionId) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction = session.beginTransaction();
        InstruccionModelo lim = new InstruccionModelo();
        try {
            lim = (InstruccionModelo) session.createCriteria(
                    InstruccionModelo.class)
                    .add(Restrictions.eq("id", instruccionId))
                    .uniqueResult();
            transaction.commit();
        } catch (Exception ex) {
            //En caso de error hace rollback
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_ALTO,
                    "Ocurrió al tratar de Buscar una Instruccion.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "guardaInstruccion(int instruccionId)");
            transaction.rollback();
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            //Finaliza la sesion
            session.clear();
            session.close();
        }
        return lim;
    }

    /**
     * Lista las instrucciones por Fecha hibernate.
     *
     * @param fecha
     * @param sucursal
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized List<InstruccionModelo> listaInstruccion(
            String fecha, SucursalModelo sucursal) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lInstruccion = session.createCriteria(InstruccionModelo.class)
                    .add(Restrictions.eq("fecha", fecha))
                    .addOrder(Order.desc("entrada"))
                    .addOrder(Order.desc("folioInstruccion"))
                    .createCriteria("lOrden")
                    .add(Restrictions.eq("sucursal", sucursal))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio al tratar de cargar una lista de instrucciones.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listaInstruccion( String fecha, SucursalModelo sucursal");
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Lista las instrucciones por Sucursal.
     *
     * @param sucursal
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized List<InstruccionModelo> listaInstruccion(
            SucursalModelo sucursal) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lInstruccion = session.createCriteria(InstruccionModelo.class)
                    .addOrder(Order.desc("entrada"))
                    .addOrder(Order.desc("folioInstruccion"))
                    .createCriteria("lOrden")
                    .add(Restrictions.eq("sucursal", sucursal))
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio al tratar de cargar una lista de instrucciones.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listaInstruccion(SucursalModelo sucursal)");
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Lista las instrucciones por idInstrucion
     *
     * @param idInstruccion
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized List<InstruccionModelo> listaInstruccion(int idInstruccion) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            lInstruccion = session.createCriteria(InstruccionModelo.class)
                    .add(Restrictions.eq("id", idInstruccion))
                    .addOrder(Order.desc("entrada"))
                    .addOrder(Order.desc("folioInstruccion"))
                    .createCriteria("lOrden")
                    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                    .list();
            transaction.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de cargar una lista de instrucciones.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    " listaInstruccion(int idInstruccion)");
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Lista todas las instrucciones por filtros en bitacoras
     *
     * @param array
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<InstruccionModelo> listaInstruccion(String[] array) {
        //Deten Hilo
        //Util.detenHilo(InstruccionDAO.getlHilo());
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();
        try {
            Criteria criterio = session.createCriteria(InstruccionModelo.class);
            creaCriteria(criterio, array);

            transaction = session.beginTransaction();
            lInstruccion = criterio
                    .addOrder(Order.asc("fecha"))
                    .list();

            transaction.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de cargar la lista de Bitacora Orden.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listaInstruccion(String[] array)");
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Metodo encargado de crear el criterio de busqueda para el listado de las
     * ordenes de pago
     *
     * @param criterio
     * @param array
     */
    private void creaCriteria(Criteria criterio, String[] array) {
        String dato0 = null, dato1 = null, dato2 = null, dato3 = null,
                dato4 = null, dato5 = null, dato6 = null, dato7 = null,
                dato8 = null, dato9 = null, dato10 = null, dato11 = null;
        for (int i = 0; i < array.length; i++) {
            dato0 = array[0];
            dato1 = array[1];
            dato2 = array[2];
            dato3 = array[3];
            dato4 = array[4];
            dato5 = array[5];
            dato6 = array[6];
            dato7 = array[7];
            dato8 = array[8];
            dato9 = array[9];
            dato10 = array[10];
            dato11 = array[11];
        }

        if (dato0.equals("true")) {
            criterio.add(Restrictions.eq("entrada", true));
        }
        if (dato1.equals("true")) {
            criterio.add(Restrictions.eq("entrada", false));
        }
        if (dato11.equals("")) {
            String fechaInicio = dato10;

            criterio.add(Restrictions.eq("fecha", fechaInicio));

        } else {
            String fechaInicio = dato10;
            String fechaFin = dato11;

            criterio.add(Restrictions.between("fecha", fechaInicio, fechaFin));

        }
        String folioInstruccionS = dato5;
        int folioInstruccionI = 0;

        if (folioInstruccionS.length() > 0) {

            try {
                folioInstruccionI = Integer.parseInt(folioInstruccionS);
            } catch (Exception e) {
                Util.mensaje(
                        "En el campo Folio Instrucción existe un dato incorrecto.",
                        1);
                return;
            }
            criterio.add(Restrictions.eq("folioInstruccion", folioInstruccionI));
        }
        if (!dato3.equals("0")) {

            String partEmisro
                    = dato3;
            CatalogoModelo pe = ParticipanteForm.
                    buscaCatalogoDescripcion(partEmisro);
            criterio.add(Restrictions.eq("participanteOrdenante", pe));

        }
        if (!dato2.equals("0")) {

            String parReceptor = dato2;
            CatalogoModelo cr
                    = ParticipanteForm.buscaCatalogoDescripcion(parReceptor);
            criterio.add(Restrictions.eq("participanteBeneficiario", cr));

        }
        Criteria clo = criterio.createCriteria("lOrden");

        String claveRastreo = dato4;

        if (claveRastreo.length() > 0) {
            clo.add(Restrictions.like("claveRastreo", "%" + claveRastreo + "%"));
        }

        String est1 = "PARA ENVIAR";
        String est2 = "EN COLA DE ENVIO";
        String est3 = "POR LIQUIDAR";
        String est4 = "LIQUIDADO";
        String est5 = "DETENIDO";
        String est6 = "CANCELADO";
        String est7 = "ABONADO";
        String est8 = "TODOS";
        String est9 = "PER ABORTADA";
        String est10 = "ABORTADA";
        String est11 = "ACTUALIZADA";
        String est12 = "ELIMINADA";
        String est13 = "PRE DEVUELTO";
        String est14 = "DEVUELTO";
        String est15 = "POR DEVOLVER";
        String est16 = "DEVUELTO Y ABONADO";
        String est17 = "DECLINADO";

        if (dato7.equals("TODOS")) {

            CatalogoModelo estado1 = EstadoOrdenForm.buscaCatalogoDescripcion(est1);
            CatalogoModelo estado2 = EstadoOrdenForm.buscaCatalogoDescripcion(est2);
            CatalogoModelo estado3 = EstadoOrdenForm.buscaCatalogoDescripcion(est3);
            CatalogoModelo estado4 = EstadoOrdenForm.buscaCatalogoDescripcion(est4);
            CatalogoModelo estado5 = EstadoOrdenForm.buscaCatalogoDescripcion(est5);
            CatalogoModelo estado6 = EstadoOrdenForm.buscaCatalogoDescripcion(est6);
            CatalogoModelo estado7 = EstadoOrdenForm.buscaCatalogoDescripcion(est7);
            CatalogoModelo estado8 = EstadoOrdenForm.buscaCatalogoDescripcion(est8);
            CatalogoModelo estado9 = EstadoOrdenForm.buscaCatalogoDescripcion(est9);
            CatalogoModelo estado10 = EstadoOrdenForm.buscaCatalogoDescripcion(est10);
            CatalogoModelo estado11 = EstadoOrdenForm.buscaCatalogoDescripcion(est11);
            CatalogoModelo estado12 = EstadoOrdenForm.buscaCatalogoDescripcion(est12);
            CatalogoModelo estado13 = EstadoOrdenForm.buscaCatalogoDescripcion(est13);
            CatalogoModelo estado14 = EstadoOrdenForm.buscaCatalogoDescripcion(est14);
            CatalogoModelo estado15 = EstadoOrdenForm.buscaCatalogoDescripcion(est15);
            CatalogoModelo estado16 = EstadoOrdenForm.buscaCatalogoDescripcion(est16);
            CatalogoModelo estado17 = EstadoOrdenForm.buscaCatalogoDescripcion(est17);

            clo.add(Restrictions.or(
                    Restrictions.eq("m_EstadoPago", estado1),
                    Restrictions.eq("m_EstadoPago", estado2),
                    Restrictions.eq("m_EstadoPago", estado3),
                    Restrictions.eq("m_EstadoPago", estado4),
                    Restrictions.eq("m_EstadoPago", estado5),
                    Restrictions.eq("m_EstadoPago", estado6),
                    Restrictions.eq("m_EstadoPago", estado7),
                    Restrictions.eq("m_EstadoPago", estado8),
                    Restrictions.eq("m_EstadoPago", estado9),
                    Restrictions.eq("m_EstadoPago", estado10),
                    Restrictions.eq("m_EstadoPago", estado11),
                    Restrictions.eq("m_EstadoPago", estado12),
                    Restrictions.eq("m_EstadoPago", estado13),
                    Restrictions.eq("m_EstadoPago", estado14),
                    Restrictions.eq("m_EstadoPago", estado15),
                    Restrictions.eq("m_EstadoPago", estado16),
                    Restrictions.eq("m_EstadoPago", estado17)));

        } else if (dato7.equals("0")) {

            CatalogoModelo estado4 = EstadoOrdenForm.buscaCatalogoDescripcion(est4);
            CatalogoModelo estado7 = EstadoOrdenForm.buscaCatalogoDescripcion(est7);
            CatalogoModelo estado14 = EstadoOrdenForm.buscaCatalogoDescripcion(est14);
            CatalogoModelo estado16 = EstadoOrdenForm.buscaCatalogoDescripcion(est16);

            clo.add(Restrictions.or(
                    Restrictions.eq("m_EstadoPago", estado4),
                    Restrictions.eq("m_EstadoPago", estado7),
                    Restrictions.eq("m_EstadoPago", estado14),
                    Restrictions.eq("m_EstadoPago", estado16)));

        } else if (!dato7.equals("0")) {

            String estado = dato7;
            CatalogoModelo est = EstadoOrdenForm.buscaCatalogoDescripcion(estado);
            clo.add(Restrictions.eq("m_EstadoPago", est));

        }

        if (!dato8.equals("0")) {

            String sucursal = dato8;

            SucursalModelo suc = SucursalForm.buscaSucursalNombreSuc(sucursal);
            clo.add(Restrictions.eq("sucursal", suc));

        }
        if (!dato9.equals("0")) {

            String tipoPago = dato9;

            CatalogoModelo tp = TipoPagoForm.buscaCatalogoDescripcion(tipoPago);
            clo.add(Restrictions.eq("tipoPago", tp));
        }
        /**
         * Define Folio Orden.
         */
        String folioOrdenS = dato6;
        int folioOrdenI = 0;

        if (folioOrdenS.length() > 0) {

            try {
                folioOrdenI = Integer.parseInt(folioOrdenS);
            } catch (Exception e) {
                Util.mensaje(
                        "En el campo Folio Orden existe un dato incorrecto.",
                        1);
                return;
            }
            clo.add(Restrictions.eq("folioOrden", folioOrdenI));
        }
        clo.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    /**
     * Lista las instrucciones por sucursal y filtros.
     *
     * @param sucursal
     * @param array
     * @return
     */
    @SuppressWarnings("unchecked")
    public synchronized List<InstruccionModelo> listaInstruccion(
            SucursalModelo sucursal, String[] array) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction transaction;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();
        try {
            Criteria criterio = session.createCriteria(InstruccionModelo.class);
            creaCriteria(criterio, array, sucursal);

            transaction = session.beginTransaction();
            lInstruccion = criterio.list();

            transaction.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrio al tratar de cargar una lista de instrucciones.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listaInstruccion(SucursalModelo sucursal, String[] array)");
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Metodo encargado de crear el criterio de busqueda para el listado de las
     * ordenes de pago
     *
     * @param criterio
     * @param array
     */
    private void creaCriteria(Criteria criterio, String[] array, SucursalModelo sucursal) {
        String dato0 = null, dato1 = null, dato2 = null, dato3 = null,
                dato4 = null, dato5 = null, dato6 = null, dato7 = null,
                dato8 = null, dato9 = null, dato10 = null, dato11 = null;
        for (int i = 0; i < array.length; i++) {
            dato0 = array[0];
            dato1 = array[1];
            dato2 = array[2];
            dato3 = array[3];
            dato4 = array[4];
            dato5 = array[5];
            dato6 = array[6];
            dato7 = array[7];
            dato8 = array[8];
            dato9 = array[9];
            dato10 = array[10];
            dato11 = array[11];
        }

        if (dato0.equals("true")) {
            criterio.add(Restrictions.eq("entrada", true));
        }
        if (dato1.equals("true")) {
            criterio.add(Restrictions.eq("entrada", false));
        }
        if (dato11.equals("")) {
            String fechaInicio = dato10;

            criterio.add(Restrictions.eq("fecha", fechaInicio));

        } else {
            String fechaInicio = dato10;
            String fechaFin = dato11;

            criterio.add(Restrictions.between("fecha", fechaInicio, fechaFin));

        }
        String folioInstruccionS = dato5;
        int folioInstruccionI = 0;

        if (folioInstruccionS.length() > 0) {

            try {
                folioInstruccionI = Integer.parseInt(folioInstruccionS);
            } catch (Exception e) {
                Util.mensaje(
                        "En el campo Folio Instrucción existe un dato incorrecto.",
                        1);
                return;
            }
            criterio.add(Restrictions.eq("folioInstruccion", folioInstruccionI));
        }
        if (!dato3.equals("0")) {

            String partEmisro
                    = dato3;
            CatalogoModelo pe = ParticipanteForm.
                    buscaCatalogoDescripcion(partEmisro);
            criterio.add(Restrictions.eq("participanteOrdenante", pe));

        }
        if (!dato2.equals("0")) {

            String parReceptor = dato2;
            CatalogoModelo cr
                    = ParticipanteForm.buscaCatalogoDescripcion(parReceptor);
            criterio.add(Restrictions.eq("participanteBeneficiario", cr));

        }
        Criteria clo = criterio.createCriteria("lOrden");

        String claveRastreo = dato4;

        if (claveRastreo.length() > 0) {
            clo.add(Restrictions.like("claveRastreo", "%" + claveRastreo + "%"));
        }
        if (!dato7.equals("0")) {

            String estado = dato7;

            CatalogoModelo est = EstadoOrdenForm.buscaCatalogoDescripcion(estado);
            clo.add(Restrictions.eq("m_EstadoPago", est));

        }

        SucursalModelo su = sucursal;

        SucursalModelo suc = SucursalForm.buscaSucursalNombreSuc(su.getNombre());
        clo.add(Restrictions.eq("sucursal", suc));

        if (!dato9.equals("0")) {

            String tipoPago = dato9;

            CatalogoModelo tp = TipoPagoForm.buscaCatalogoDescripcion(tipoPago);
            clo.add(Restrictions.eq("tipoPago", tp));
        }
        /**
         * Define Folio Orden.
         */
        String folioOrdenS = dato6;
        int folioOrdenI = 0;

        if (folioOrdenS.length() > 0) {

            try {
                folioOrdenI = Integer.parseInt(folioOrdenS);
            } catch (Exception e) {
                Util.mensaje(
                        "En el campo Folio Orden existe un dato incorrecto.",
                        1);
                return;
            }
            clo.add(Restrictions.eq("folioOrden", folioOrdenI));
        }
        clo.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    /**
     * Metodo encargado de listar ordenes de pago por rango de fechas
     *
     * @param arrFechas
     * @return
     */
    public synchronized List<InstruccionModelo> listInstrucion(String[] arrFechas) {
        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction t;
        List<InstruccionModelo> lInstruccion = new ArrayList<>();

        try {
            Criteria criterio = session.createCriteria(InstruccionModelo.class);
            creaCriteriaF(criterio, arrFechas);

            t = session.beginTransaction();
            lInstruccion = criterio.list();

            t.commit();
        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de cargar una lista de instrucciones.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listInstrucion(String fechaI, String fechaF)");
            Logger.getLogger(InstruccionMswDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lInstruccion;
    }

    /**
     * Criteria encargada de filtrar mediante hibernate.
     *
     * @param criterio
     * @param fechaI
     * @param fechaF
     */
    private void creaCriteriaF(Criteria criterio, String[] arr) {
        String datoFi = null, datoFf = null;

        for (int i = 0; i < arr.length; i++) {
            datoFi = arr[0];
            datoFf = arr[1];
        }

        if (datoFf.equals("")) {
            String fechaInicio = datoFi;
            criterio.add(Restrictions.eq("fecha", fechaInicio));
        } else {
            String fechaInicio = datoFi;
            String fechaFin = datoFf;
            criterio.add(Restrictions.between("fecha", fechaInicio, fechaFin));
        }

        criterio.addOrder(Order.asc("fecha"));
        criterio.addOrder(Order.desc("entrada"));

        Criteria clo = criterio.createCriteria("lOrden");

        CatalogoModelo estado4 = EstadoOrdenForm.buscaCatalogoDescripcion("LIQUIDADO");
        CatalogoModelo estado7 = EstadoOrdenForm.buscaCatalogoDescripcion("ABONADO");
        CatalogoModelo estado14 = EstadoOrdenForm.buscaCatalogoDescripcion("DEVUELTO");
        CatalogoModelo estado16 = EstadoOrdenForm.buscaCatalogoDescripcion("DEVUELTO Y ABONADO");
        CatalogoModelo estado18 = EstadoOrdenForm.buscaCatalogoDescripcion("CONFIRMADO");

        clo.add(Restrictions.or(
                Restrictions.eq("m_EstadoPago", estado4),
                Restrictions.eq("m_EstadoPago", estado7),
                Restrictions.eq("m_EstadoPago", estado14),
                Restrictions.eq("m_EstadoPago", estado16),
                Restrictions.eq("m_EstadoPago", estado18)));

        clo.addOrder(Order.asc("hora"));

        clo.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    /**
     * Metodo encargado de realizar el listado de la bitacora de ordenes de pago
     *
     * @param fechaI
     * @param fechaF
     * @return
     */
    public synchronized List<BitacoraSaldoInicialModelo> listBitacoraSaldo(String fechaI,
            String fechaF) {

        Session session = HibernateUtilMSW.getSessionHibernate().openSession();
        Transaction t;
        List<BitacoraSaldoInicialModelo> lbsim = new ArrayList<>();
        try {

            Criteria criterio = session.createCriteria(BitacoraSaldoInicialModelo.class);
            creaCriteriaB(criterio, fechaI, fechaF);

            t = session.beginTransaction();
            lbsim = criterio.list();
            t.commit();

        } catch (HibernateException ex) {
            ErrorLogForm.insertaError(
                    ErrorLogConstante.ERROR_BD_MSW,
                    ErrorLogConstante.NIVEL_MEDIO,
                    "Ocurrió al tratar de cargar la bitacora saldo inicial.",
                    ex.getMessage(),
                    InstruccionMswDAO.class,
                    "listBitacoraSaldo(String fechaI, String fechaF)");
            Logger.getLogger(InstruccionMswDAO.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            session.clear();
            session.close();
        }
        return lbsim;
    }

    /**
     * Metodo encargado de crear el criterio de la bitacora de sañdo inicial
     *
     * @param criterio
     * @param fechaI
     * @param fechaF
     */
    private void creaCriteriaB(Criteria criterio, String fechaI, String fechaF) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fi = null;
        Date ff = null;

        try {

            fi = formatter.parse(fechaI);
            if (fechaF == null || fechaF.equals("")) {
                criterio.add(Restrictions.eq("fecha", fi));
            } else {
                ff = formatter.parse(fechaF);
                criterio.add(Restrictions.between("fecha", fi, ff));
            }

        } catch (ParseException ex) {
            Logger.getLogger(InstruccionMswDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        criterio.addOrder(Order.asc("fecha"));
    }
}
