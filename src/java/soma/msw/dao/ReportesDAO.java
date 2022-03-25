/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.dao;

import java.util.List;
import modelo.general.InstruccionModelo;
import modelo.general.ReporteResultadosModelo;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 20/09/2018
 * @hora 11:32:13 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
public class ReportesDAO {

    private final UtilDAO util = new UtilDAO();
    private static final ReportesDAO instancia = new ReportesDAO();

    /**
     *
     * @return
     */
    public static ReportesDAO getInstancia() {
        return instancia;
    }

    /**
     * Consulta mendiante un rango de fechas que muestra la informacion
     * solicitada para generer el reporte de resultados para la entidad CONFIA.
     *
     * @param fechaI
     * @param fechaF
     * @return
     * @throws Exception
     */
    public List<InstruccionModelo> reporteResultados(String fechaI, String fechaF) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM InstruccionModelo ins ")
                //                .append(" JOIN ins.lOrden o ")
                //                .append(" JOIN o.tipoPago cat ")
                //                .append(" FULL JOIN o.p_devolucion dev ")
                //                .append(" JOIN o.m_EstadoPago catEP ")
                .append(" WHERE ins.fecha BETWEEN '")
                .append(fechaI).append("' AND '").append(fechaF).append("'")
                .append(" ORDER BY ins.fecha");
        return util.getHQL(String.valueOf(sql));
    }
//    public List<InstruccionModelo> reporteResultados(String fechaI, String fechaF) throws Exception {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" SELECT o.ordenId, o.claveRastreo, cat.descripcion, dev.causaDevolucion, catEP.descripcion, ins.fecha, o.hora, o.monto")
//                .append(" FROM InstruccionModelo ins")
//                .append(" JOIN ins.lOrden o ")
//                .append(" JOIN o.tipoPago cat ")
//                .append(" FULL JOIN o.p_devolucion dev ")
//                .append(" JOIN o.m_EstadoPago catEP ")
//                .append(" WHERE ins.fecha BETWEEN '")
//                .append(fechaI).append("' AND '").append(fechaF).append("'")
//                .append(" ORDER BY ins.fecha");
//        return util.getHQL(String.valueOf(sql));
//    }

}
