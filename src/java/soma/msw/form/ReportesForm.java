/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import modelo.general.InstruccionModelo;
import modelo.general.OrdenModelo;
import modelo.general.ReporteResultadosModelo;
import soma.msw.dao.ReportesDAO;

/**
 *
 * @author SOMA-HUGO
 */
public class ReportesForm {

    private final ReportesDAO dao = ReportesDAO.getInstancia();

    /**
     * Se encarga de consultar los datos requeridos para generar el REPORTE DE
     * RESULTADOS para la entidad CONFIA.
     *
     * @param fechaI
     * @param fechaF
     * @return
     * @throws Exception
     */
    public List<ReporteResultadosModelo> reporteResultados(String fechaI, String fechaF) throws Exception {

        List<ReporteResultadosModelo> lrrm = new ArrayList<>();

        List<InstruccionModelo> lim;
        lim = dao.reporteResultados(fechaI, fechaF);
        int identificador = 0;
        String claverastreo = null;
        String tipopago = null;
        String causadevolucion = "ND";
        String estadopago = null;
        String fecha = null;
        BigDecimal monto = new BigDecimal(BigInteger.ZERO);

        for (InstruccionModelo im : lim) {

            List<OrdenModelo> lo = im.getlOrden();

            for (OrdenModelo o : lo) {

                identificador = o.getOrdenId();
                claverastreo = o.getClaveRastreo();
                tipopago = o.getTipoPago().getDescripcion();
                if (o.getP_devolucion() != null) {
                    causadevolucion = o.getP_devolucion().getCausaDevolucion().getDescripcion();
                }

                estadopago = o.getM_EstadoPago().getDescripcion();
                fecha = im.getFecha() + "T" + o.getHora();
                monto = o.getMonto();
            }
            ReporteResultadosModelo rrm = new ReporteResultadosModelo();

            rrm.setIdentificador(identificador);
            rrm.setClaverastreo(claverastreo);
            rrm.setTipoPago(tipopago);
            rrm.setCausaDevolucion(causadevolucion);
            rrm.setEstadoPago(estadopago);
            rrm.setFecha(fecha);
            rrm.setMonto(monto);

            lrrm.add(rrm);
        }
        /**
         * Retorna el objeto lleno
         */
        return lrrm;

    }
}
