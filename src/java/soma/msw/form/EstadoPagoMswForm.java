/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import dao.InstruccionDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.general.BitacoraEstadoOrdenModelo;
import modelo.general.CatalogoModelo;
import modelo.general.OrdenModelo;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 18/09/2015
 * @hora 01:06:03 PM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 *
 */
public class EstadoPagoMswForm {

    /**
     * Metodo encargado de mostrar los estatus de la orden de pago
     *
     * @param o
     * @return
     */
    public static List<Object[]> contenidoTablaEstados(List<BitacoraEstadoOrdenModelo> loe) {
        List<Object[]> lObj = new ArrayList<Object[]>();

        int cont = 1;
        for (BitacoraEstadoOrdenModelo oe : loe) {
            List<Object> aloe = new ArrayList<Object>();

            CatalogoModelo ce = oe.getEstado();

            aloe.add(cont++);
            aloe.add(ce.getDescripcion());
            aloe.add(Util.defineHoraDate(oe.getHora()));
            String usuario;
            if (oe.getUsuario() != null) {
                usuario = oe.getUsuario().getNickname();
            } else {
                usuario = "Sistema";
            }
            aloe.add(usuario);

            lObj.add(Util.convArregloObj(aloe));

        }
        cont = 1;
        return lObj;
    }

    public static List<Object[]> contenidoEstados(OrdenModelo o) {
        List<BitacoraEstadoOrdenModelo> l = InstruccionDAO.listaBitacoraEstadoOrden(o);
        List<Object[]> contenido = contenidoTablaEstados(l);
        return contenido;
    }

}
