/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import dao.InstruccionDAO;
import form.OrdenForm;
import java.util.List;
import modelo.general.BitacoraSaldoInicialModelo;
import modelo.general.InstruccionModelo;
import modelo.general.SucursalModelo;
import soma.msw.dao.InstruccionMswDAO;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 8/09/2015
 * @hora 11:25:52 AM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 *
 */
public class InstruccionMswForm {

    /**
     * Guarda una instruccion por hibernate
     *
     * @param im
     * @return
     */
    public synchronized boolean guardaInstruccionWeb(InstruccionModelo im) {
        //Guarda Instruccion en el Dao
        InstruccionMswDAO idao = new InstruccionMswDAO();

        boolean bandera = idao.guardaInstruccionWeb(im);

        return bandera;

    }

    @SuppressWarnings("unchecked")
    public static List<InstruccionModelo> listaInstruccionWeb(String fecha) {
        //Lista Instruccion en el Dao
        InstruccionDAO idao = new InstruccionDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(fecha);
        return l;
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos segun la fecha.
     *
     * @param fecha
     * @return
     */
    public static List<Object[]> contenidoTablaOrden(String fecha) {
        InstruccionDAO idao = new InstruccionDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(fecha);
//        OrdenForm form = new OrdenForm(l);
        OrdenMswForm form = new OrdenMswForm(l);

        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     * Metodo que lista todos los beneficiarios de la base de datos.
     *
     * @param instruccionId
     * @return
     */
    public static InstruccionModelo buscaInstruccion(int instruccionId) {
        InstruccionMswDAO dao = new InstruccionMswDAO();
        return dao.buscaInstruccion(instruccionId);
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos.
     *
     * @return
     */
    public static List<Object[]> contenidoTablaOrden() {
        InstruccionDAO idao = new InstruccionDAO();
        List<InstruccionModelo> l = idao.listaInstruccion();
        OrdenForm form = new OrdenForm(l);
        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     *
     * @param fecha
     * @param sucursal
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<InstruccionModelo> listaInstruccionWeb(String fecha, SucursalModelo sucursal) {
        //Lista Instruccion en el Dao
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(fecha, sucursal);
        return l;
    }

    public static List<Object[]> contenidoTablaOrden(String fecha, SucursalModelo sucursal) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(fecha, sucursal);
//        OrdenForm form = new OrdenForm(l);
        OrdenMswForm form = new OrdenMswForm(l);

        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos.
     *
     * @param sm
     * @return
     */
    public static List<Object[]> contenidoTablaOrden(SucursalModelo sm) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(sm);
        OrdenForm form = new OrdenForm(l);
        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     *
     * @param idInstruccion
     * @return
     */
    public static List<Object[]> contenidoTablaOrden(int idInstruccion) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(idInstruccion);
//        OrdenForm form = new OrdenForm(l);
        OrdenMswForm form = new OrdenMswForm(l);

        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos.
     *
     * @param array
     * @return
     */
    public static List<Object[]> contenidoTablaOrden(String[] array) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(array);
        OrdenForm form = new OrdenForm(l);
        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos.
     *
     * @param sm
     * @param array
     * @return
     */
    public static List<Object[]> contenidoTablaOrden(SucursalModelo sm, String[] array) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(sm, array);
        OrdenForm form = new OrdenForm(l);
        List<Object[]> contenido = form.contenidoTabla();

        return contenido;
    }

    /**
     * Se encarga de convertir una lista de instrucciones en una lista de
     * arreglos de objetos.
     *
     * @param array
     * @return
     */
    public static List<InstruccionModelo> contenidoTablaOrdenR(String[] array) {
        InstruccionMswDAO idao = new InstruccionMswDAO();
        List<InstruccionModelo> l = idao.listaInstruccion(array);
        return l;
    }

    /**
     * Metodo encragado de listar el contenido de la ordenes segun el rango de
     * fechas
     *
     * @param arr
     * @return
     */
    public static List<InstruccionModelo> contOrden(String[] arr) {
        InstruccionMswDAO iDao = new InstruccionMswDAO();
        List<InstruccionModelo> l = iDao.listInstrucion(arr);
        return l;
    }

    /**
     * Metodo encragado de listar la bitacora de saldos iniciales por el sistema
     * segun el rango de fechas
     *
     * @param fechaI
     * @param fechaF
     * @return
     */
    public static List<BitacoraSaldoInicialModelo> listBitacoraSaldos(
            String fechaI, String fechaF) {
        InstruccionMswDAO iDao = new InstruccionMswDAO();
        List<BitacoraSaldoInicialModelo> l = iDao.listBitacoraSaldo(
                fechaI, fechaF);
        return l;
    }
}
