/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.reportes.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import dao.ClabeDAO;
import dao.OrdenDAO;
import form.GeneralForm;
import form.SucursalForm;
import form.catalogo.TipoCuentaForm;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.BitacoraSaldoInicialModelo;
import modelo.general.CatalogoModelo;
import modelo.general.CuentaModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.CuentahabienteSucursalModelo;
import modelo.general.EntidadModeloNuevo;
import modelo.general.InstruccionModelo;
import modelo.general.OrdenModelo;
import modelo.general.ReporteResultadosModelo;
import modelo.general.SucursalModelo;
import modelo.reporte.ConsolidadoPagosComparacionModelo;
import modelo.reporte.ConsolidadoPagosModelo;
import modelo.reporte.EstadoCuentaModelo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.repo.OutputStreamResource;
import reportes.OrdenReportes;
import soma.msw.dao.InstruccionMswDAO;
import soma.msw.form.CuentahabienteBeneficiarioForm;
import soma.msw.form.CuentahabienteOrdenanteForm;
import soma.msw.form.InstruccionMswForm;
import soma.msw.form.ReportesForm;
import soma.msw.reportes.ReportesMswForm;
import soma.msw.servlet.InstruccionServlet;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 6/10/2015
 * @hora 11:05:57 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
@WebServlet(name = "ReportesServlet", urlPatterns = {"/ReportesServlet"})
public class ReportesServlet extends HttpServlet {

    private Map<String, Object> parametros;
    ClassLoader classLoader = ReportesServlet.class.getClassLoader();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ServletOutputStream out = response.getOutputStream();
            ServletContext context = getServletContext();
            InputStream inputStream;

            String accion = request.getParameter("accion");
            if (accion == null) {
                accion = request.getParameter("accionRep");
            }

            URL urlLogoSPEI = classLoader.getResource("reportes/Logo_spei.jpg.jpeg");
            URL logoReforma = classLoader.getResource("reportes/reforma_nuevo.jpg");
//        URL logoReforma = classLoader.getResource("reportes/inmaculada.jpg");

            switch (Integer.parseInt(accion)) {
                /**
                 * Estructura para el reporte Orden de Pago
                 */
                case 1:

                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/pdf");

                    List<Object> listaO = new ArrayList<>();
                    listaO.add(".");

                    try {

                        parametros = new HashMap<>();

                        String idIn = request.getParameter("instruccionIdRep");

                        response.setHeader("Content-Disposition", "inline; filename=\"ordenPago" + idIn + ".pdf\"");

                        InstruccionMswDAO dao = new InstruccionMswDAO();
                        InstruccionModelo inm = dao.buscaInstruccion(Integer.parseInt(idIn));
                        OrdenModelo om = InstruccionMswForm.buscaInstruccion(Integer.parseInt(idIn)).getlOrden().get(0);
                        int tipoPago = om.getTipoPago().getClave();
                        BigDecimal monto = om.getMonto();
                        BigDecimal iva = om.getIva();
                        if (iva == null) {
                            iva = BigDecimal.ZERO;
                        }
                        BigDecimal importe = monto.subtract(iva);

                        switch (tipoPago) {
                            /**
                             * Estructura para el reporte de Tipo de Pago
                             * Devoluciones
                             */
                            case 0:
                                parametros.put("FechaImprecion", Util.fechaSistema('-', 1));
                                parametros.put("FechaEnvio", inm.getFecha());
                                parametros.put("TipoPago", om.getTipoPago().getDescripcion());
                                parametros.put("Topologia", inm.getTopologia());
                                String prioridad = "";
                                if (inm.getPrioridad() == 0) {
                                    prioridad = "BAJA";
                                } else {
                                    prioridad = "ALTA";
                                }
                                parametros.put("Prioridad", prioridad);
                                parametros.put("NumeroCliente", "");
                                String sucursal;
                                if (om.getSucursal() == null) {
                                    sucursal = "";
                                } else {
                                    sucursal = om.getSucursal().getNombre();
                                }
                                parametros.put("NombreSucursal", sucursal);
                                parametros.put("ClaveRastreo", om.getClaveRastreo());
                                parametros.put("TipoCuentaOrdenante", "");
                                parametros.put("CuentaOrdenante", "");
                                parametros.put("NombreOrdenante", "");
                                parametros.put("FRCOrdenante", "");
                                parametros.put("TipoCuentaBenenficiario", "");
                                parametros.put("CuentaBeneficiario", "");
                                parametros.put("NombreBeneficiario", "");
                                parametros.put("RFCBeneficiario", "");
                                parametros.put("Participante", "");
                                parametros.put("Concepto", "");
                                parametros.put("ReferenciaNumerica", "");
                                if (om.getIva() == null) {
                                    om.setIva(new BigDecimal("0.00"));
                                }
                                parametros.put("IVA", om.getIva());
                                parametros.put("Monto", om.getMonto());
                                parametros.put("Importe", importe);
                                parametros.put("FolioOrden", om.getFolioOrden());
                                parametros.put("FolioInstruccion", inm.getFolioInstruccion());
                                if (om.getComision() == null) {
                                    om.setComision(new BigDecimal("0.00"));
                                }
                                parametros.put("Comicion", om.getComision());
                                parametros.put("urlLogoSPEI", urlLogoSPEI);
                                parametros.put("logoReforma", logoReforma);

                                break;
                            /**
                             * Estructura para el reporte de Tipo de Pago
                             * Tercero a Tercero
                             */
                            case 1:
                                int ordenante = om.getP_terceroTercero().getOrdenante().getPersonaId();
                                CuentahabienteSucursalModelo chsm = CuentahabienteOrdenanteForm.buscaOrdenante(ordenante);
                                int beneficiario = om.getP_terceroTercero().getBeneficiario().getPersonaId();
                                CuentahabienteModelo chm = CuentahabienteBeneficiarioForm.buscaBeneficiario(beneficiario);

                                parametros.put("FechaImprecion", Util.fechaSistema('-', 1));
                                parametros.put("FechaEnvio", inm.getFecha());
                                parametros.put("TipoPago", om.getTipoPago().getDescripcion());
                                parametros.put("Topologia", inm.getTopologia());
                                if (inm.getPrioridad() == 0) {
                                    prioridad = "BAJA";
                                } else {
                                    prioridad = "ALTA";
                                }

                                String numeroCliente = "";

                                if (chsm != null) {
                                    numeroCliente
                                            = chsm.getIdentificador();
                                }

                                parametros.put("Prioridad", prioridad);
                                parametros.put("NumeroCliente", numeroCliente);
                                parametros.put("NombreSucursal", om.getSucursal().getNombre());
                                parametros.put("ClaveRastreo", om.getClaveRastreo());
                                parametros.put("TipoCuentaOrdenante", om.getP_terceroTercero().getOrdenante().getCuenta().getTipoCuenta().getDescripcion());
                                parametros.put("CuentaOrdenante", om.getP_terceroTercero().getOrdenante().getCuenta().getNumero());
                                parametros.put("NombreOrdenante", om.getP_terceroTercero().getOrdenante().getNombre());
                                parametros.put("FRCOrdenante", om.getP_terceroTercero().getOrdenante().getRfc());
                                parametros.put("TipoCuentaBenenficiario", chm.getCuenta().getTipoCuenta().getDescripcion());
                                parametros.put("CuentaBeneficiario", chm.getCuenta().getNumero());
                                parametros.put("NombreBeneficiario", chm.getNombre());
                                parametros.put("RFCBeneficiario", chm.getRfc());
                                parametros.put("Participante", chm.getCuenta().getParticipante().getDescripcion());
                                parametros.put("Concepto", om.getConcepto());
                                parametros.put("ReferenciaNumerica", om.getReferenciaNumerica());
                                if (om.getIva() == null) {
                                    om.setIva(new BigDecimal("0.00"));
                                }
                                parametros.put("IVA", om.getIva());
                                parametros.put("Monto", om.getMonto());
                                parametros.put("Importe", importe);
                                parametros.put("FolioOrden", om.getFolioOrden());
                                parametros.put("FolioInstruccion", inm.getFolioInstruccion());
                                if (om.getComision() == null) {
                                    om.setComision(new BigDecimal("0.00"));
                                }
                                parametros.put("Comicion", om.getComision());
                                parametros.put("urlLogoSPEI", urlLogoSPEI);
                                parametros.put("logoReforma", logoReforma);
                                break;
                        }
                        inputStream = context.getResourceAsStream("/reportes/reporteOrdenPago.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametros,
                                new JRBeanCollectionDataSource(listaO));
                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.exportReport();

                    } catch (JRException e) {
                        Logger.getLogger(InstruccionServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }

                    break;

                /**
                 * Estructura para el reporte Cuenta CLABE
                 */
                case 2:
                    /**
                     * Case 2.- Creacion del reporte de Cuenta CLABE
                     */
                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/pdf");

                    List<Object> listaC = new ArrayList<>();
                    listaC.add(".");

                    try {
                        inputStream
                                = context.getResourceAsStream(
                                        "/reportes/ReporteCuentaClabe.jasper");

                        parametros = new HashMap<>();
                        String identificador = request.getParameter("identificador");
                        response.setHeader("Content-Disposition",
                                "inline; filename=\"cuentaCLABE_" + identificador + ".pdf\"");

                        ClabeDAO dao = new ClabeDAO();
                        CuentahabienteSucursalModelo cs = dao.
                                buscaCuentahabienteSucursalxclb(identificador);

                        if (cs == null) {

                            return;
                        }

                        EntidadModeloNuevo ent = GeneralForm.getEntidad();
                        CuentahabienteModelo ch = cs.getCuentaHabiente();
                        CuentaModelo cuenta = ch.getCuenta();
                        String numeroCuenta = cuenta.getNumero();
                        CatalogoModelo tipoCuenta = TipoCuentaForm.buscaCatalogoClave(
                                cuenta.getTipoCuenta().getClave());
                        cuenta.setTipoCuenta(tipoCuenta);
                        ReportesMswForm rmf = new ReportesMswForm();
                        SucursalModelo nc = rmf.defineSucursalCuenta(numeroCuenta);

                        parametros.put("p_nombre_entidad", ent.getNombre());
                        parametros.put("p_nombre_cuentahabiente", ch.getNombre());
                        parametros.put("p_rfc", ch.getRfc());
                        parametros.put("p_numero_cliente", cs.getIdentificador());
                        parametros.put("p_sucursal", nc.getNombre());
                        parametros.put("p_cuenta_CLABE", numeroCuenta);
                        parametros.put("p_tipo_cuenta",
                                cuenta.getTipoCuenta().getDescripcion());
                        parametros.put("urlLogoSPEI", urlLogoSPEI);
                        parametros.put("logoReforma", logoReforma);

                        inputStream = context.getResourceAsStream("/reportes/ReporteCuentaClabe.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametros, new JRBeanCollectionDataSource(listaC));

                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.exportReport();

                    } catch (JRException e) {
                        Logger.getLogger(ReportesServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }

                    break;

                /**
                 * Estructura para el reporte Numero de Pagos
                 */
                case 3:
                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/pdf");

                    OrdenReportes or = new OrdenReportes();
                    int anio = Integer.parseInt(request.getParameter("anio"));
                    int mes = Integer.parseInt(request.getParameter("mes"));

                    List<List<ConsolidadoPagosModelo>> llConsolidado = new ArrayList<>();

                    List<Date> lrm = or.defineRangoFechaMes(anio, mes);

                    List<ConsolidadoPagosModelo> lConsolidadoActual
                            = or.consolidado(lrm.get(0), lrm.get(1));

                    if (mes == 1) {
                        anio = anio - 1;
                    }

                    mes = mes - 1;
                    lrm = or.defineRangoFechaMes(anio, mes);

                    List<ConsolidadoPagosModelo> lConsolidadoPasado
                            = or.consolidado(lrm.get(0), lrm.get(1));

                    llConsolidado.add(lConsolidadoActual);
                    llConsolidado.add(lConsolidadoPasado);

                    List<ConsolidadoPagosComparacionModelo> list = new ArrayList<>();

                    for (int i = 0; i < lConsolidadoActual.size(); i++) {

                        ConsolidadoPagosModelo cActual = lConsolidadoActual.get(i);
                        ConsolidadoPagosModelo cPasado = lConsolidadoPasado.get(i);

                        ConsolidadoPagosComparacionModelo cpcm
                                = new ConsolidadoPagosComparacionModelo();

                        cpcm.setActualMontoEntrada(cActual.getMontoEntrada());
                        cpcm.setActualMontoSalida(cActual.getMontoSalida());
                        cpcm.setActualNoEntrada(cActual.getNoEntrada());
                        cpcm.setActualNoSalida(cActual.getNoSalida());
                        cpcm.setActualNumeroPagos(cActual.getNumeroPagos());

                        cpcm.setPasadoMontoEntrada(cPasado.getMontoEntrada());
                        cpcm.setPasadoMontoSalida(cPasado.getMontoSalida());
                        cpcm.setPasadoNoEntrada(cPasado.getNoEntrada());
                        cpcm.setPasadoNoSalida(cPasado.getNoSalida());
                        cpcm.setPasadoNumeroPagos(cPasado.getNumeroPagos());

                        cpcm.setSucursa(cActual.getSucursa());

                        list.add(cpcm);
                    }

                    try {
//                    inputStream
//                            = context.getResourceAsStream(
//                                    "/reportes/ReporteConsolidado.jrxml");
//                    JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//                    JasperReport jr
//                            = JasperCompileManager.compileReport(jasperDesign);
                        parametros = new HashMap<>();
                        response.setHeader("Content-Disposition",
                                "inline; filename=\"consolidado_" + mes + "_" + anio + ".pdf\"");
                        /**
                         * parte encargada de definir los parametros que llevara
                         * el reporte
                         */
                        parametros.put("urlLogoSPEI", urlLogoSPEI);
                        parametros.put("logoReforma", logoReforma);
                        /**
                         * Parte encargada de compilar y tranformar los byte's
                         * en reportes
                         */
                        inputStream = context.getResourceAsStream("/reportes/ReporteConsolidado.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                                parametros, new JRBeanCollectionDataSource(list));

                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.exportReport();

                    } catch (JRException e) {
                        Logger.getLogger(ReportesServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }

                    break;
                case 4:

                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/pdf");

                    Date fechaInicio = new Date(request.getParameter("fechaInicio"));
                    Date fechaFin = new Date(request.getParameter("fechaFin"));

                    List<ConsolidadoPagosModelo> lpm = new ArrayList<>();
                    List<SucursalModelo> ls = SucursalForm.getlSucursal();

                    int noEntrada;
                    int noSalida;
                    BigDecimal montoEntrada;
                    BigDecimal montoSalida;

                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");

                    String fechaInicioS = fechaHora.format(fechaInicio);
                    String fechaFinS = fechaHora.format(fechaFin);

                    for (SucursalModelo s : ls) {
                        List<InstruccionModelo> lInstruccion = OrdenDAO.
                                getConsolidadoPagos(fechaInicioS, fechaFinS, s);

                        noEntrada = 0;
                        noSalida = 0;

                        montoEntrada = new BigDecimal(BigInteger.ZERO);
                        montoSalida = new BigDecimal(BigInteger.ZERO);

                        for (InstruccionModelo im : lInstruccion) {

                            List<OrdenModelo> lo = im.getlOrden();

                            for (OrdenModelo o : lo) {

                                if (im.isEntrada()) {
                                    noEntrada++;
                                    montoEntrada = montoEntrada.add(o.getMonto());
                                } else {
                                    noSalida++;
                                    montoSalida = montoSalida.add(o.getMonto());
                                }
                            }

                        }

                        ConsolidadoPagosModelo cpm = new ConsolidadoPagosModelo();
                        cpm.setSucursa(s);

                        cpm.setMontoEntrada(montoEntrada);
                        cpm.setMontoSalida(montoSalida);
                        cpm.setNoSalida(noSalida);
                        cpm.setNoEntrada(noEntrada);
                        cpm.setFechaInicio(fechaInicio);
                        cpm.setFechaFin(fechaFin);
                        lpm.add(cpm);
                    }

                    try {

                        parametros = new HashMap<>();
                        response.setHeader("Content-Disposition",
                                "inline; filename=\"consolidado_" + fechaInicio + "_" + fechaFin + ".pdf\"");
                        /**
                         * parte encargada de definir los parametros que llevara
                         * el reporte
                         */
                        parametros.put("urlLogoSPEI", urlLogoSPEI);
                        parametros.put("logoReforma", logoReforma);
                        /**
                         * Parte encargada de compilar y tranformar los byte's
                         * en reportes
                         */

                        inputStream = context.getResourceAsStream("/reportes/ConsolidadoRangoFechas.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                                parametros, new JRBeanCollectionDataSource(lpm));

                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.exportReport();

                    } catch (JRException e) {
                        Logger.getLogger(ReportesServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }
                    break;
                /**
                 * Case 5: reporte bitacora orden de pago
                 */
                case 5:
                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/pdf");

                    String entrada;
                    String salida;
                    String partReceptor;
                    String partEmisor;
                    String cveRastreo;
                    String folioIns;
                    String folioOrd;
                    String estado;
                    String sucursal;
                    String tipoPago;
                    String fechaInicios;
                    String fechaFins;

                    entrada = request.getParameter("entradaR");
                    salida = request.getParameter("salidaR");
                    partReceptor = request.getParameter("partReceptorR");
                    partEmisor = request.getParameter("partEmisorR");
                    cveRastreo = request.getParameter("cveRastreoR");
                    folioIns = request.getParameter("folioInsR");
                    folioOrd = request.getParameter("folioOrdR");
                    estado = request.getParameter("estadoR");
                    sucursal = request.getParameter("sucursalR");
                    tipoPago = request.getParameter("tipoPagoR");
                    fechaInicios = request.getParameter("fechaInicioR");
                    fechaFins = request.getParameter("fechaFinR");

                    String[] array = new String[]{
                        entrada, salida, partReceptor, partEmisor, cveRastreo,
                        folioIns, folioOrd, estado, sucursal, tipoPago,
                        fechaInicios, fechaFins
                    };

                    List<InstruccionModelo> lop;

                    lop = InstruccionMswForm.contenidoTablaOrdenR(array);

                    int refNumerica = 0;
                    String beneficiario = null;
                    String ordenante = null;
                    int no = 0;
                    BigDecimal abono = new BigDecimal(BigInteger.ZERO);
                    BigDecimal retiro = new BigDecimal(BigInteger.ZERO);

                    List<EstadoCuentaModelo> lecm = new ArrayList<>();

                    for (InstruccionModelo im : lop) {

                        List<OrdenModelo> lo = im.getlOrden();
                        for (OrdenModelo o : lo) {
                            abono = new BigDecimal(BigInteger.ZERO);
                            retiro = new BigDecimal(BigInteger.ZERO);
                            no++;
                            fechaInicios = im.getFecha();
                            cveRastreo = o.getClaveRastreo();
                            refNumerica = o.getReferenciaNumerica();
                            beneficiario = im.getParticipanteBeneficiario().getDescripcion();
                            ordenante = im.getParticipanteOrdenante().getDescripcion();
                            if (im.isEntrada()) {
                                if (o.getM_EstadoPago().getClave() == 4
                                        || o.getM_EstadoPago().getClave() == 7
                                        || o.getM_EstadoPago().getClave() == 14
                                        || o.getM_EstadoPago().getClave() == 16
                                        || o.getM_EstadoPago().getClave() == 18) {
                                    abono = o.getMonto();
                                } else {
                                    abono = BigDecimal.ZERO;
                                }
                            } else {
                                if (o.getM_EstadoPago().getClave() == 4
                                        || o.getM_EstadoPago().getClave() == 7
                                        || o.getM_EstadoPago().getClave() == 14
                                        || o.getM_EstadoPago().getClave() == 16
                                        || o.getM_EstadoPago().getClave() == 18) {
                                    retiro = o.getMonto();
                                } else {
                                    retiro = BigDecimal.ZERO;
                                }
                            }
                        }

                        EstadoCuentaModelo ecm = new EstadoCuentaModelo();

                        ecm.setNo(no);
                        ecm.setFecha(fechaInicios);
                        ecm.setClaveRastreo(cveRastreo);
                        ecm.setReferenciaNumerica(refNumerica);
                        ecm.setBeneficiario(beneficiario);
                        ecm.setOrdenante(ordenante);
                        ecm.setAbono(Util.formatoMoney(abono.toString()));
                        ecm.setRetiro(Util.formatoMoney(retiro.toString()));
                        lecm.add(ecm);
                    }

                    try {

                        parametros = new HashMap<>();
                        response.setHeader("Content-Disposition",
                                "inline; filename=\"EstadoCuenta.pdf\"");
                        /**
                         * parte encargada de definir los parametros que llevara
                         * el reporte
                         */

                        String nombreEntidad;
                        int cuenta;
                        String rfc;
                        nombreEntidad = GeneralForm.getEntidad().getNombre();
                        cuenta = GeneralForm.getEntidad().getNumero();
                        rfc = GeneralForm.getEntidad().getRfc();

                        int noEntradaEc = 0;
                        int noSalidaEc = 0;
                        BigDecimal montoEntradaEc = new BigDecimal(BigInteger.ZERO);
                        BigDecimal montoSalidaEc = new BigDecimal(BigInteger.ZERO);

                        for (InstruccionModelo im : lop) {

                            List<OrdenModelo> lo = im.getlOrden();

                            for (OrdenModelo o : lo) {

                                if (im.isEntrada()) {
                                    if (o.getM_EstadoPago().getClave() == 4
                                            || o.getM_EstadoPago().getClave() == 7
                                            || o.getM_EstadoPago().getClave() == 14
                                            || o.getM_EstadoPago().getClave() == 16
                                            || o.getM_EstadoPago().getClave() == 18) {
                                        noEntradaEc++;
                                        montoEntradaEc = montoEntradaEc.add(o.getMonto());
                                    }

                                } else {
                                    if (o.getM_EstadoPago().getClave() == 4
                                            || o.getM_EstadoPago().getClave() == 7
                                            || o.getM_EstadoPago().getClave() == 14
                                            || o.getM_EstadoPago().getClave() == 16
                                            || o.getM_EstadoPago().getClave() == 18) {
                                        noSalidaEc++;
                                        montoSalidaEc = montoSalidaEc.add(o.getMonto());
                                    }
                                }
                            }
                        }

                        parametros.put("nombreEntidad", nombreEntidad);
                        parametros.put("numeroCuenta", cuenta);
                        parametros.put("rfc", rfc);
                        parametros.put("noEntrada", noEntradaEc);
                        parametros.put("noSalida", noSalidaEc);
                        parametros.put("montoEntrada", Util.formatoMoney(montoEntradaEc.toString()));
                        parametros.put("montoSalida", Util.formatoMoney(montoSalidaEc.toString()));
                        parametros.put("urlLogoSPEI", urlLogoSPEI);
                        parametros.put("logoReforma", logoReforma);

                        /**
                         * Parte encargada de compilar y tranformar los byte's
                         * en reportes
                         */
                        inputStream = context.getResourceAsStream("/reportes/EstadoCuenta.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                                parametros, new JRBeanCollectionDataSource(lecm));

                        JRExporter exporter = new JRPdfExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                        exporter.exportReport();

                    } catch (JRException e) {
                        Logger.getLogger(ReportesServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }

                    break;

                /**
                 * CASE 6: Se encarga de realizar el reporte del estado de
                 * cuenta por un rango de fechas en formato "XLS"(Excel)
                 */
                case 6:

                    response.setCharacterEncoding("ISO-8859-1");
                    response.setContentType("application/vnd.ms-excel");

                    String fechaI = request.getParameter("fechaInicio");
                    String fechaF = request.getParameter("fechaFin");

                    String[] arrFechas = new String[]{
                        fechaI, fechaF
                    };

                    List<InstruccionModelo> lim = new ArrayList<>();
                    lim = InstruccionMswForm.contOrden(arrFechas);

                    List<BitacoraSaldoInicialModelo> lbsi = new ArrayList<>();
                    lbsi = InstruccionMswForm.listBitacoraSaldos(fechaI, fechaF);

                    BigDecimal saldoInicial = new BigDecimal(BigInteger.ZERO);
                    BigDecimal saldoFinal = new BigDecimal(BigInteger.ZERO);

                    for (BitacoraSaldoInicialModelo bsi : lbsi) {
                        saldoInicial = bsi.getSaldo();
                        break;
                    }

                    String clavRastreo = null;
                    String descripcion = "";

                    BigDecimal abonos = new BigDecimal(BigInteger.ZERO);
                    BigDecimal retiros = new BigDecimal(BigInteger.ZERO);

                    List<EstadoCuentaModelo> lecms = new ArrayList<>();
                    saldoFinal = saldoInicial;

                    for (InstruccionModelo im : lim) {

                        List<OrdenModelo> lo = im.getlOrden();
                        for (OrdenModelo o : lo) {
                            abonos = new BigDecimal(BigInteger.ZERO);
                            retiros = new BigDecimal(BigInteger.ZERO);
                            descripcion = "";
                            fechaI = im.getFecha();
                            clavRastreo = o.getClaveRastreo();

                            if (im.isEntrada()) {
                                descripcion = "Pago Deposito";
                                if (o.getM_EstadoPago().getClave() == 4
                                        || o.getM_EstadoPago().getClave() == 7
                                        || o.getM_EstadoPago().getClave() == 14
                                        || o.getM_EstadoPago().getClave() == 16
                                        || o.getM_EstadoPago().getClave() == 18) {
                                    abonos = o.getMonto();
                                    saldoFinal = saldoFinal.add(abonos);
                                } else {
                                    abonos = BigDecimal.ZERO;
                                }
                            } else {
                                descripcion = "Pago Retiro";
                                if (o.getM_EstadoPago().getClave() == 4
                                        || o.getM_EstadoPago().getClave() == 7
                                        || o.getM_EstadoPago().getClave() == 14
                                        || o.getM_EstadoPago().getClave() == 16
                                        || o.getM_EstadoPago().getClave() == 18) {
                                    retiros = o.getMonto();
                                    saldoFinal = saldoFinal.subtract(retiros);
                                } else {
                                    retiros = BigDecimal.ZERO;
                                }
                            }

                        }

                        EstadoCuentaModelo ecm = new EstadoCuentaModelo();

                        ecm.setFecha(fechaI);
                        ecm.setClaveRastreo(descripcion + " " + clavRastreo);
                        ecm.setAbono(Util.formatoMoney(abonos.toString()));
                        ecm.setRetiro(Util.formatoMoney(retiros.toString()));
                        ecm.setSaldoFinal(Util.formatoMoney(saldoFinal.toString()));

                        lecms.add(ecm);
                    }

                    try {

                        parametros = new HashMap<>();
                        response.setHeader("Content-Disposition",
                                "attachment; filename=ReporteEstadoCuenta_" + fechaF + ".xls");
                        /**
                         * parte encargada de definir los parametros que llevara
                         * el reporte
                         */

                        String nombreEntidad;
                        int cuenta;
                        String rfc;
                        nombreEntidad = GeneralForm.getEntidad().getNombre();
                        cuenta = GeneralForm.getEntidad().getNumero();
                        rfc = GeneralForm.getEntidad().getRfc();

                        int noEntradaEc = 0;
                        int noSalidaEc = 0;
                        BigDecimal montoEntradaEc = new BigDecimal(BigInteger.ZERO);
                        BigDecimal montoSalidaEc = new BigDecimal(BigInteger.ZERO);

                        for (InstruccionModelo im : lim) {

                            List<OrdenModelo> lo = im.getlOrden();

                            for (OrdenModelo o : lo) {

                                if (im.isEntrada()) {
                                    if (o.getM_EstadoPago().getClave() == 4
                                            || o.getM_EstadoPago().getClave() == 7
                                            || o.getM_EstadoPago().getClave() == 14
                                            || o.getM_EstadoPago().getClave() == 16
                                            || o.getM_EstadoPago().getClave() == 18) {
                                        noEntradaEc++;
                                        montoEntradaEc = montoEntradaEc.add(o.getMonto());
                                    }
                                } else {
                                    if (o.getM_EstadoPago().getClave() == 4
                                            || o.getM_EstadoPago().getClave() == 7
                                            || o.getM_EstadoPago().getClave() == 14
                                            || o.getM_EstadoPago().getClave() == 16
                                            || o.getM_EstadoPago().getClave() == 18) {
                                        noSalidaEc++;
                                        montoSalidaEc = montoSalidaEc.add(o.getMonto());
                                    }

                                }
                            }
                        }

                        parametros.put("nombreEntidad", nombreEntidad);
                        parametros.put("numeroCuenta", cuenta);
                        parametros.put("rfc", rfc);
                        parametros.put("noEntrada", noEntradaEc);
                        parametros.put("noSalida", noSalidaEc);
                        parametros.put("montoEntrada", Util.formatoMoney(montoEntradaEc.toString()));
                        parametros.put("montoSalida", Util.formatoMoney(montoSalidaEc.toString()));
                        parametros.put("saldoInicio", Util.formatoMoney(saldoInicial.toString()));
                        parametros.put("urlLogoSPEI", urlLogoSPEI);
                        parametros.put("logoReforma", logoReforma);

                        /**
                         * Parte encargada de compilar y tranformar los byte's
                         * en reportes
                         */
                        inputStream = context.getResourceAsStream("/reportes/ReporteEstadoCuenta.jasper");
                        JasperReport jr = (JasperReport) JRLoader.loadObject(inputStream);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                                parametros, new JRBeanCollectionDataSource(lecms));

                        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                        JRXlsExporter exporter = new JRXlsExporter();

                        exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
                        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                        exporter.exportReport();

                        response.setContentLength(arrayOutputStream.toByteArray().length);
                        out.write(arrayOutputStream.toByteArray());

                    } catch (JRException e) {
                        Logger.getLogger(ReportesServlet.class.getName()).
                                log(Level.SEVERE, null, e);
                    } finally {
                        out.flush();
                        out.close();
                    }
                    break;
                case 7:
                    /**
                     * Se einicializan las variables
                     */
                    String rFechaI = request.getParameter("fechaInicio");
                    String rFechaF = request.getParameter("fechaFin");
                    int cont = 1;
                    List l = new ArrayList<>();
                    /**
                     * variables de armado para el nombre del archivo a generar.
                     */
                    String fecha = getFechaActual_yyyymmdd();
                    String hora = getHoraActual();
                    /**
                     * se manda llamar la informacion del reporte y se itera
                     * para armar el archivo
                     */
                    ReportesForm form = new ReportesForm();
                    List<ReporteResultadosModelo> lrr;

                    lrr = form.reporteResultados(rFechaI, rFechaF);
                    for (ReporteResultadosModelo o : lrr) {
                        List a = new ArrayList<>();
                        a.add(cont++);
                        a.add(o.getIdentificador());
                        a.add(o.getClaverastreo());
                        a.add(o.getTipoPago());
                        a.add(o.getCausaDevolucion());
                        a.add(o.getEstadoPago());
                        a.add(o.getFecha());
                        a.add(o.getMonto());
                        String s = a.toString();
                        l.add(s.substring(1) + " \n  ");

                    }

                    /**
                     * comienza la estructura del reporte CSV
                     */
                    response.setContentType("text/csv");
                    response.setHeader("Content-Disposition", "attachment; "
                            + "filename=\"RESULTADOS_SPEI_" + fecha + "_" + hora + "_CONFIA.csv\"");

                    OutputStream outputStream = response.getOutputStream();
                    String replaceString = l.toString().replace(']', ' ');//replaces all occurrences of ']' to ' '  
                    replaceString = replaceString.replace("  , ", " "); // Replace '  , ' with ' '  
                    String outputResult = replaceString.substring(1);
                    outputStream.write(outputResult.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    break;
                default:
            }
        } catch (Exception ex) {
            Logger.getLogger(ReportesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     *
     * @return
     */
    public String getFechaActual_yyyymmdd() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
        return formateador.format(ahora);
    }

    /**
     * Obtiene la hora actual de lsistema.
     *
     * @return
     */
    public String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("HHmmss");
        return formateador.format(ahora);
    }
}
