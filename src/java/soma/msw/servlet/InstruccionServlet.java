/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import constantes.EstadoPeticionConstante;
import constantes.ParametrosNombre;
import dao.OrdenDAO;
import soma.msw.form.CuentahabienteBeneficiarioForm;
import form.GeneralForm;
import form.InstruccionForm;
import form.OrdenCSVForm;
import form.OrdenForm;
import form.PeticionForm;
import soma.msw.form.InstruccionMswForm;
import form.SaldoForm;
import form.SucursalForm;
import form.catalogo.EstadoOrdenForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoPagoForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.acuse.servlet.AcuseOrdenCSVModelo;
import modelo.general.CatalogoModelo;
import modelo.general.CatalogoSaldoModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.InstruccionModelo;
import modelo.general.OrdenModelo;
import modelo.general.SucursalModelo;
import modelo.general.TerceroTerceroModelo;
import modelo.general.UsuarioModelo;
import modelo.ws.entrada.ACUSECANCELAPAGOWSModelo;
import modelo.ws.entrada.ACUSEORDENWSModelo;
import modelo.ws.entrada.OrdenWSModelo;
import org.json.JSONArray;
import org.json.JSONException;
import soma.msw.dao.UsuarioMswDAO;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileItemFactory;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import soma.msw.ws.OrdenesMswWS;
import util.Util;
import util.WebServicesCliente;
import ws.Servidor;
import ws.cliente.GeneralWS;
import ws.cliente.OrdenesWS;
import ws.servidor.ServidorWS;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 10/08/2015
 * @hora 10:02:02 AM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 * @author LUNA
 *
 */
@WebServlet(name = "InstruccionServlet", urlPatterns = {"/InstruccionServlet"})
public class InstruccionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private InstruccionForm imf = new InstruccionForm();
    InstruccionMswForm inf = new InstruccionMswForm();
    ServidorWS sws = new ServidorWS();
    private List<InstruccionModelo> lInstruccion;

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

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = request.getParameter("accionRep");
        }
        String sucursal = request.getParameter("sucursal");
        if (sucursal == null) {
            sucursal = request.getParameter("sucursalRep");

        }
        String nickname;
        UsuarioModelo usuario = null;
        if (request.getParameter("usuario") != null) {
            nickname = request.getParameter("usuario");
            UsuarioMswDAO uDao = new UsuarioMswDAO();
            usuario = uDao.buscarUsuario(nickname);
        }

        String json = null;

        String datosReq = null;
        if (request.getParameter("lordenid") != null) {
            datosReq = request.getParameter("lordenid");
        }
        boolean status = false;
        if (request.getParameter("status") != null) {
            status = Boolean.parseBoolean(request.getParameter("status"));
        }
        String instruccionId = null;
        if (!"".equals(request.getParameter("instruccionId")) || request.getParameter("instruccionId").isEmpty()) {
            instruccionId = request.getParameter("instruccionId");
        }

        /**
         * Opcion que permite insertar la instruccion y la orden de pago
         */
        switch (Integer.parseInt(accion)) {
            case 1:
                int noPagos = Integer.parseInt(request.getParameter("numeroPagos"));
                int ordenanteId = Integer.parseInt(request.getParameter("ordenanteId"));
                int claveBeneficiario = Integer.parseInt(
                        request.getParameter("beneficiarioId"));
                int claveParticipanate = Integer.parseInt(
                        request.getParameter("claveParicipante"));
                float monto = Float.parseFloat(request.getParameter("monto"));
                String concepto = request.getParameter("concepto");
                String numeroRastreo = request.getParameter("numeroRastreo");
                float iva = Float.parseFloat(request.getParameter("iva"));
                String referenciaCo = request.getParameter("referenciaCo");
                int referenciaNum = Integer.parseInt(request.getParameter("referenciaNum"));

//                UsuarioMswDAO uDao = new UsuarioMswDAO();
//                UsuarioModelo usuario = uDao.buscarUsuario(nickname);
                int claveEstado = ParametrosNombre.PARAENVIAR;

                InstruccionModelo im = new InstruccionModelo();

                im.setFolioInstruccion(0);
                im.setPrioridad(0);
                im.setEntrada(false);
                im.setTopologia('T');
                im.setFecha(Util.fechaSistema('-', 1));

                CatalogoSaldoModelo se = new CatalogoSaldoModelo();
//                se = SaldoForm.buscaSaldoEntrada();
                se = SaldoForm.getSaldoEntrada();
                im.setSaldo(se);

                CatalogoModelo cm_po = new CatalogoModelo();
                cm_po = ParticipanteForm.
                        buscaCatalogoClave(GeneralForm.getClaveParticipante());

                im.setParticipanteOrdenante(cm_po);

                CatalogoModelo cm_pb = new CatalogoModelo();
                cm_pb = ParticipanteForm.
                        buscaCatalogoClave(claveParticipanate);

                im.setParticipanteBeneficiario(cm_pb);

                /**
                 * Se llena la lista de orden de pago
                 */
                List<OrdenModelo> lOrden = new ArrayList<>();

                for (int i = 0; i < noPagos; i++) {

                    CuentahabienteModelo ord = new CuentahabienteModelo();
                    ord = CuentahabienteBeneficiarioForm.
                            buscarCuentahabienteId(ordenanteId);

                    CuentahabienteModelo ben = new CuentahabienteModelo();
                    ben = CuentahabienteBeneficiarioForm.
                            buscarCuentahabienteId(claveBeneficiario);

                    /**
                     * Se llena terceroTercero
                     */
                    TerceroTerceroModelo ttm = new TerceroTerceroModelo();

                    ttm.setOrdenante(ord);
                    ttm.setBeneficiario(ben);
                    ttm.setRefCobranza1(referenciaCo);
                    /**
                     * Se llena la orden
                     */
                    OrdenModelo o = new OrdenModelo();

                    o.setFolioOrden(0);

                    o.setConcepto(concepto);
                    o.setClaveRastreo(numeroRastreo);

                    BigDecimal montoB = new BigDecimal(monto);
                    o.setMonto(montoB);

                    BigDecimal ivaB = new BigDecimal(iva);
                    o.setIva(ivaB);

                    CatalogoModelo tpm = new CatalogoModelo();
                    tpm = TipoPagoForm.buscaCatalogoClave(1);
                    o.setTipoPago(tpm);

                    o.setReferenciaNumerica(referenciaNum);
                    o.setHora(new Date());

                    CatalogoModelo cm_ep = EstadoOrdenForm.buscaCatalogoClave(claveEstado);
                    o.setM_EstadoPago(cm_ep);

                    SucursalModelo sm = new SucursalModelo();
                    sm = SucursalForm.buscaSucursalNumeroClabe(Integer.parseInt(sucursal));
                    o.setSucursal(sm);

                    o.setP_terceroTercero(ttm);
                    lOrden.add(o);
                }

                im.setlOrden(lOrden);

                im.setSaldo(SaldoForm.buscaSaldoId(im.getSaldo().getSaldoid()));
                OrdenesWS.llenaCatalogosInstruccion(im);

                InstruccionForm.definaUsuario(usuario);
                this.inf.guardaInstruccionWeb(im);
                break;
            /**
             * CASE 2: permite enviar el pago de la instruccion/Orden
             * seleccionada
             */
            case 2:

//                 datosReq = request.getParameter("lordenid");
//                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                List<Integer> lOrdenid = new ArrayList<>();

                try {
                    JSONArray arr = new JSONArray(datosReq);
                    for (int i = 0; i < arr.length(); i++) {
                        lOrdenid.add(Integer.parseInt(arr.get(i).toString()));
                    }

                } catch (JSONException ex) {
                    Logger.getLogger(InstruccionServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                OrdenCSVForm form = new OrdenCSVForm();
                AcuseOrdenCSVModelo acuse = form.enviaListaOrden(lOrdenid, status);

                json = new Gson().toJson(acuse);

                break;
            /**
             * Opcion que permite editar la orden seleccionada
             */

            case 3:

                int noPagoss = Integer.parseInt(request.getParameter("numeroPagos"));
                int ordenanteIds = Integer.parseInt(request.getParameter("ordenanteId"));
                int claveBeneficiarios = Integer.parseInt(
                        request.getParameter("beneficiarioId"));
                int claveParticipanates = Integer.parseInt(
                        request.getParameter("claveParicipante"));
                float montos = Float.parseFloat(request.getParameter("monto"));
                String conceptos = request.getParameter("concepto");
                String numeroRastreos = request.getParameter("numeroRastreo");
                float ivas = Float.parseFloat(request.getParameter("iva"));
                String referenciaCos = request.getParameter("referenciaCo");
                int referenciaNums = Integer.parseInt(request.getParameter("referenciaNum"));
                int claveEstados = ParametrosNombre.ACTUALIZADA;
                int instruccionIds = Integer.parseInt(request.getParameter("instruccionId"));
                int idOrden = Integer.parseInt(request.getParameter("idOrden"));

//                uDao = new UsuarioMswDAO();
//                usuario = uDao.buscarUsuario(nickname);

                OrdenModelo ordm = InstruccionMswForm.buscaInstruccion(instruccionIds).getlOrden().get(0);
                int terceroId = ordm.getP_terceroTercero().getId();

                InstruccionModelo imm = new InstruccionModelo();

                imm.setId(instruccionIds);
                imm.setFolioInstruccion(0);
                imm.setPrioridad(0);
                imm.setEntrada(false);
                imm.setTopologia('T');
                imm.setFecha(Util.fechaSistema('-', 1));

                se = SaldoForm.getSaldoEntrada();
                imm.setSaldo(se);

                CatalogoModelo cmpo = new CatalogoModelo();
                cmpo = ParticipanteForm.
                        buscaCatalogoClave(GeneralForm.getClaveParticipante());

                imm.setParticipanteOrdenante(cmpo);

                CatalogoModelo cmpb = new CatalogoModelo();
                cmpb = ParticipanteForm.
                        buscaCatalogoClave(claveParticipanates);

                imm.setParticipanteBeneficiario(cmpb);

                /**
                 * Se llena la lista de orden de pago
                 */
                List<OrdenModelo> lOrdenn = new ArrayList<>();

                for (int i = 0; i < noPagoss; i++) {

                    CuentahabienteModelo ord = new CuentahabienteModelo();
                    ord = CuentahabienteBeneficiarioForm.
                            buscarCuentahabienteId(ordenanteIds);

                    CuentahabienteModelo ben = new CuentahabienteModelo();
                    ben = CuentahabienteBeneficiarioForm.
                            buscarCuentahabienteId(claveBeneficiarios);

                    /**
                     * Se llena terceroTercero
                     */
                    TerceroTerceroModelo ttm = new TerceroTerceroModelo();
                    OrdenModelo o = new OrdenModelo();

                    ttm.setId(terceroId);
                    ttm.setOrdenante(ord);
                    ttm.setBeneficiario(ben);
                    ttm.setRefCobranza1(referenciaCos);
                    /**
                     * Se llena la orden
                     */

                    o.setOrdenId(idOrden);
                    o.setFolioOrden(0);

                    o.setConcepto(conceptos);

                    OrdenModelo om = InstruccionMswForm.buscaInstruccion(instruccionIds).getlOrden().get(0);
                    int ordenante = om.getP_terceroTercero().getOrdenante().getPersonaId();

                    if (ordenante == ordenanteIds) {
                        o.setClaveRastreo(om.getClaveRastreo());
                    } else {
                        o.setClaveRastreo(numeroRastreos);

                    }

                    BigDecimal montoB = new BigDecimal(montos);
                    o.setMonto(montoB);

                    BigDecimal ivaB = new BigDecimal(ivas);
                    o.setIva(ivaB);

                    CatalogoModelo tpm = new CatalogoModelo();
                    tpm = TipoPagoForm.buscaCatalogoClave(1);
                    o.setTipoPago(tpm);

                    o.setReferenciaNumerica(referenciaNums);
                    o.setHora(new Date());

                    CatalogoModelo cm_ep = EstadoOrdenForm.buscaCatalogoClave(claveEstados);
                    o.setM_EstadoPago(cm_ep);

                    SucursalModelo smm = new SucursalModelo();
                    smm = SucursalForm.buscaSucursalNumeroClabe(Integer.parseInt(sucursal));
                    o.setSucursal(smm);

                    o.setP_terceroTercero(ttm);
                    lOrdenn.add(o);
                }

                imm.setlOrden(lOrdenn);

                imm.setSaldo(SaldoForm.buscaSaldoId(1));
                OrdenesMswWS.llenaCatalogosInstruccion(imm);

                InstruccionForm.definaUsuario(usuario);
                this.inf.guardaInstruccionWeb(imm);

                break;
            /**
             * CASE 4: Permitira declinar la orden de pago siempre y cuendo la
             * orden se encuentre en estado "Para enviar"
             */
            case 4:

                final InstruccionModelo instruccion4
                        = InstruccionMswForm.buscaInstruccion(Integer.parseInt(instruccionId));
                imf = new InstruccionForm();
                //String fechaOperacion = fecha;
                int claveEstado4 = ParametrosNombre.DECLINADO;
                CatalogoModelo cm = EstadoOrdenForm.buscaCatalogoClave(
                        claveEstado4);
                //Asignamos el estado a cada orden de la instruccion
                for (int i = 0; i < instruccion4.getlOrden().size(); i++) {
                    OrdenModelo o = instruccion4.getlOrden().get(i);
                    if (o.getM_EstadoPago() != null) {
                        o.setM_EstadoPago(cm);
                    }
                    instruccion4.getlOrden().set(i, o);
                }
                lInstruccion = new ArrayList<>();
                lInstruccion.add(instruccion4);
                //Creamos el archivo xml
                this.imf.guardaInstruccion(lInstruccion);
                break;
            /**
             * CASE 5: Permite cancelar una Instruccion/Orden
             */
            case 5:
                int folioInstruccion = Integer.parseInt(request.getParameter("folioInstruccion"));
                int folioOrden = Integer.parseInt(request.getParameter("folioOrden"));
                SucursalModelo sucu = SucursalForm.buscaSucursalNumeroClabe(Integer.parseInt(sucursal));

                ACUSECANCELAPAGOWSModelo acp = new ACUSECANCELAPAGOWSModelo();
                int res = GeneralForm.validaSucursal(sucu);

                if (res > 0) {
                    acp.setEstado(res);
                }

                GeneralWS.imprimePeticion(false, "cancelOrden(int folioOrden, int folioInstruccion)");

                acp.setFolioInstruccion(folioOrden);
                acp.setFolioOrden(folioInstruccion);
                if (GeneralWS.isActivoServidor()) {

//                    PeticionForm.guardaImprimePeticion(7,
//                            ParametrosNombre.ME_A_MCS, 1, 0, null);
//                    Servidor s = WebServicesCliente.getServidor();
//                    s.cancelaPago(folioOrden, folioInstruccion, GeneralForm.getEntidadWS());
//                    acp.setEstado(EstadoPeticionConstante.EP_0);

                } else {

                    acp.setEstado(EstadoPeticionConstante.EP_55);

                }

                break;
            /**
             * CASE 6: Permite devolder una orden de pago
             */
            case 6:

                int ordengeneralid = Integer.parseInt(request.getParameter("ordenId"));
                int causa = Integer.parseInt(request.getParameter("causa"));
                String entrada = request.getParameter("entrada");
                String estado = request.getParameter("estado");
                OrdenForm of = new OrdenForm();

                if (estado.equals("LIQUIDADO")) {
                    of.devuelvePagoLIQUIDADO(ordengeneralid, causa);

                } else {
                    of.devuelvePagoPORDEVOLVER(ordengeneralid, causa);

                }

                break;
            /**
             * CASE 7: Se encarga de agregar el estado ABONADO a los pagos.
             */
            case 7:

                final InstruccionModelo instruccion7
                        = InstruccionMswForm.buscaInstruccion(Integer.parseInt(instruccionId));
                imf = new InstruccionForm();
                int claveEstado7 = ParametrosNombre.ABONADO;
                CatalogoModelo cm4 = EstadoOrdenForm.buscaCatalogoClave(
                        claveEstado7);
                //Asignamos el estado a cada orden de la instruccion
                for (int i = 0; i < instruccion7.getlOrden().size(); i++) {
                    OrdenModelo o = instruccion7.getlOrden().get(i);
                    if (o.getM_EstadoPago() != null) {
                        o.setM_EstadoPago(cm4);
                    }
                    instruccion7.getlOrden().set(i, o);
                }
                lInstruccion = new ArrayList<>();
                lInstruccion.add(instruccion7);
                //Creamos el archivo xml
                InstruccionForm.definaUsuario(usuario);
                this.imf.guardaInstruccion(lInstruccion);
                break;
            default:
        }

        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    static int contador = 1;

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
