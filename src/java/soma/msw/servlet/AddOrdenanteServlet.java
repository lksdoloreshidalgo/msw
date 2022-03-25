/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import constantes.EstadoPeticionConstante;
import dao.ClabeDAO;
import form.DatosEntidadForm;
import soma.msw.form.CuentahabienteBeneficiarioForm;
import soma.msw.form.CuentahabienteOrdenanteForm;
import form.GeneralForm;
import form.IndiceForm;
import form.SucursalForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoCuentaForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.CatalogoModelo;
import modelo.general.CuentaModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.CuentahabienteSucursalModelo;
import modelo.general.EntidadModeloNuevo;
import modelo.general.IndiceModelo;
import modelo.general.SucursalModelo;
import modelo.ws.entrada.ACUSEADMINISTRATELEFONOSWSModelo;
import modelo.ws.entrada.ACUSEGENERACLABEWSModelo;
import soma.msw.dao.CuentahabienteOrdenanteDAO;
import util.Util;
import ws.cliente.AdministraTelefonosWS;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 31/07/2015
 * @hora 11:51:26 AM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 * @author LUNA
 *
 */
@WebServlet(name = "AddOrdenanteServlet", urlPatterns = {"/AddOrdenanteServlet"})
public class AddOrdenanteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    CuentahabienteSucursalModelo cs = new CuentahabienteSucursalModelo();
    ACUSEGENERACLABEWSModelo gcc = new ACUSEGENERACLABEWSModelo();

    int tipo;
    IndiceForm inf = new IndiceForm();
    IndiceModelo im = new IndiceModelo();
    CuentahabienteOrdenanteForm chof = new CuentahabienteOrdenanteForm();

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

        SucursalModelo s = new SucursalModelo();
        int accion = Integer.parseInt(request.getParameter("accion"));

        String json = null;
        String identificador = request.getParameter("identificador");

        String sucursal = "";
        if (request.getParameter("sucursal") != null) {
            sucursal = request.getParameter("sucursal");
            s = SucursalForm.buscaSucursalNumeroClabe(Integer.parseInt(sucursal));
        }
        int tipoCuenta = 0;
        System.out.println("tipocuenta*************" + request.getParameter("tipoCuenta"));
        if (request.getParameter("tipoCuenta") != null) {
            tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
        }

//        int digitoClabe = 0;
//
//        if (request.getParameter("digitoClabe") != null) {
//            digitoClabe = Integer.parseInt(request.getParameter("digitoClabe"));
//        }
        switch (accion) {
            case 1:
//                identificador = request.getParameter("identificador");
//                tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));
                String num = request.getParameter("numero");

                if (identificador == null
                        || identificador.trim().length() < 1) {
                    gcc.setEstadoPeticion(EstadoPeticionConstante.EP_84);
                    gcc.setIdentificador(identificador);
                }
                if (cs.getCuentaHabiente() == null) {
                    gcc.setEstadoPeticion(EstadoPeticionConstante.EP_85);
                    gcc.setIdentificador(identificador);
                }
                CuentahabienteModelo ch_ms = new CuentahabienteModelo();
                CuentaModelo c = new CuentaModelo();

                CatalogoModelo dtoPa = ParticipanteForm.buscaCatalogoClave(
                        GeneralForm.getClaveParticipante());

                CatalogoModelo dtoTc = TipoCuentaForm.buscaCatalogoClave(
                        tipoCuenta);

                c.setTipoCuenta(dtoTc);

                String numeroCliente = "";
                StringBuilder cuenta = new StringBuilder("");
                int tipos = 0;
                if (tipoCuenta == 40) {
                    switch (tipos) {
                        case 0:
                            /**
                             * Genera la cuenta clabe segun un indice que va
                             * incrementando.
                             */
                            im = inf.getIndice(s);

                            if (im == null) {
                                im = new IndiceModelo();
                                im.setContador(1);
                                im.setSucursal(s);
                            } else {
                                im.setContador(im.getContador() + 1);
                            }

                            //Sumamos contador
                            numeroCliente = chof.calculaNumeroClabeIndice(im);
//                            numeroCliente = identificador;

                            break;
                        case 1:

                            break;
                        default:
                    }

                    /**
                     * Obtenemos la clabe de la entidad
                     */
                    EntidadModeloNuevo entidad = GeneralForm.getEntidad();
                    int numeroEntidad = entidad.getNumero();

                    cuenta.append(GeneralForm.numeroCLABE());             // 3 D
                    cuenta.append(Util.colocaCero(numeroEntidad, 3));     // 3 D
                    cuenta.append(s.getNumeroClabe());                    // 3 D
                    cuenta.append(numeroCliente);                         // 7 D
//                    cuenta.append(digitoClabe);                           // 1 D
                    cuenta.append(chof.generaDigitoV(cuenta.toString())); // 1 D

                    c.setNumero(cuenta.toString());
                    c.setTipoCuenta(dtoTc);
                    c.setParticipante(dtoPa);
                    ch_ms.setCuenta(c);

                } else {
                    c.setNumero(num);
                    c.setTipoCuenta(dtoTc);
                    c.setParticipante(dtoPa);
                    ch_ms.setCuenta(c);
                }

                ch_ms.setNombre(request.getParameter("nombre"));
                ch_ms.setRfc(request.getParameter("rfc"));
                ch_ms.setEstado(true);

                gcc.setCuenta(c);
                gcc.setEstadoPeticion(EstadoPeticionConstante.EP_0);
                gcc.setIdentificador(identificador);

                ClabeDAO dao = new ClabeDAO();

//                CatalogoCLABEEntidadModelo lccem = new CatalogoCLABEEntidadModelo();
//                try {
//                    lccem = CuentahabienteOrdenanteForm.listaClabeEntidad(digitoClabe);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddOrdenanteServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
                CuentahabienteSucursalModelo chsm = new CuentahabienteSucursalModelo();

                try {
                    //                chsm = dao.buscaCuentahabienteSucursal(identificador);
                    chsm = CuentahabienteOrdenanteForm.listaCuentahabienteSucursal(ch_ms.getCuenta().getNumero());
                } catch (Exception ex) {
                    Logger.getLogger(AddOrdenanteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (chsm == null) {
                    chsm = new CuentahabienteSucursalModelo();
                    chsm.setCuentaHabiente(ch_ms);
                    chsm.setIdentificador(identificador);
//                    chsm.setCatalogoClabeentidad(lccem);
                    chsm.setNumeroCuentahabiente(cuenta.toString());
                    chsm.setSucursal(s);

                    if (tipoCuenta == 40) {

                        dao.guardaIndice(im);
                        dao.guardaCuenta(ch_ms);
                        dao.guardaCuentahabienteSucursal(chsm);
                        json = new Gson().toJson(0);
                    } else {
                        chsm.setNumeroCuentahabiente(num);

                        AdministraTelefonosWS atws = new AdministraTelefonosWS();
                        ACUSEADMINISTRATELEFONOSWSModelo estado = atws.insertaTelefonos(s, num, null, accion, identificador);
                        if (estado.getEstado() == 0) {
                            dao.guardaCuentahabienteSucursal(chsm);
                            json = new Gson().toJson(estado);
                        } else {
                            json = new Gson().toJson(estado);
                        }

                    }
                } else {
                    json = new Gson().toJson(1);
                }

                break;
            /**
             * CASE 2: Se encarda de gestionar la actualizacion del registro
             * parea el cuentahabeinte ordenante
             */
            case 2:
//                identificador = request.getParameter("identificador");
                ClabeDAO daoU = new ClabeDAO();
                CuentahabienteModelo cuentahabiente
                        = new CuentahabienteModelo();
                CuentaModelo cuentaU = new CuentaModelo();
//                s = SucursalForm.buscaSucursalNumeroClabe(Integer.parseInt(sucursal));
                int personaId = Integer.parseInt(
                        request.getParameter("personaId"));
                String numero = request.getParameter("numero");
                String numerotel = request.getParameter("numero1");
                String nombre = request.getParameter("nombre");
                String rfc = request.getParameter("rfc");
                int ordenanteId = Integer.parseInt(
                        request.getParameter("ordenanteId"));
//                tipoCuenta = Integer.parseInt(request.getParameter("tipoCuenta"));

                boolean estadoBA = Boolean.parseBoolean(request.getParameter("estado"));

                dtoPa = ParticipanteForm.buscaCatalogoClave(
                        GeneralForm.getClaveParticipante());
                dtoTc = TipoCuentaForm.buscaCatalogoClave(
                        tipoCuenta);
                CuentahabienteModelo chmo
                        = CuentahabienteBeneficiarioForm.
                                buscarCuentahabienteCuenta(personaId);

                cuentaU.setCuentaId(chmo.getCuenta().getCuentaId());
                cuentaU.setNumero(numero);
                cuentaU.setTipoCuenta(dtoTc);
                cuentaU.setParticipante(dtoPa);

                cuentahabiente.setCuenta(cuentaU);
                cuentahabiente.setNombre(nombre);
                cuentahabiente.setRfc(rfc);
                cuentahabiente.setEstado(estadoBA);
                cuentahabiente.setPersonaId(personaId);
//                CatalogoCLABEEntidadModelo lccemU = new CatalogoCLABEEntidadModelo();

//                try {
//                    lccemU = CuentahabienteOrdenanteForm.listaClabeEntidad(digitoClabe);
//                } catch (Exception ex) {
//                    Logger.getLogger(AddOrdenanteServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
                CuentahabienteSucursalModelo chsmU
                        = new CuentahabienteSucursalModelo();
                chsmU.setId(ordenanteId);
                chsmU.setCuentaHabiente(cuentahabiente);
                chsmU.setIdentificador(identificador);
                chsmU.setNumeroCuentahabiente(numero);
//                chsmU.setCatalogoClabeentidad(lccemU);
                chsmU.setSucursal(s);
                AdministraTelefonosWS atws = new AdministraTelefonosWS();
                ACUSEADMINISTRATELEFONOSWSModelo estado1 = null;
                if (estadoBA == Boolean.parseBoolean("false")) {
                    if (tipoCuenta == 10) {

                        estado1 = atws.insertaTelefonos(s, numero, null, 3, identificador);
                        if (estado1.getEstado() == 0) {
                            daoU.guardaCuentahabienteSucursal(chsmU);
                            json = new Gson().toJson(estado1);
                        } else {
                            json = new Gson().toJson(estado1);
                        }
                    } else {
                        daoU.guardaCuentahabienteSucursal(chsmU);
                        estado1 = new ACUSEADMINISTRATELEFONOSWSModelo();
                        estado1.setEstado(0);
                        estado1.setDescripcion("");
                        json = new Gson().toJson(estado1);
                    }

                } else {
                    if (numerotel.equals(numero)) {
                        if (tipoCuenta == 10) {
                            estado1 = atws.insertaTelefonos(s, numerotel, null, 1, identificador);

                            if (estado1.getEstado() == 0) {
                                daoU.guardaCuentahabienteSucursal(chsmU);
                                json = new Gson().toJson(estado1);
                            } else {
                                json = new Gson().toJson(estado1);
                            }
                        } else {
                            daoU.guardaCuentahabienteSucursal(chsmU);
                            estado1 = new ACUSEADMINISTRATELEFONOSWSModelo();
                            estado1.setEstado(0);
                            estado1.setDescripcion("");
                            json = new Gson().toJson(estado1);
                        }
                    } else {

                        estado1 = atws.insertaTelefonos(s, numerotel, numero, 2, identificador);
                        if (estado1.getEstado() == 0) {
                            daoU.guardaCuentahabienteSucursal(chsmU);
                            json = new Gson().toJson(estado1);
                        } else {
                            json = new Gson().toJson(estado1);
                        }
                    }
                }
                break;
            case 3:
                List<CatalogoModelo> tp = TipoCuentaForm.getlTipoCuenta();
                json = new Gson().toJson(tp);
                break;
            case 4:
                EntidadModeloNuevo daEntidad = DatosEntidadForm.getEntidad();
                json = new Gson().toJson(daEntidad);
                break;
            case 5:
//                List listaOrdenante
//                        = CuentahabienteOrdenanteForm.lOrdenante();
                CuentahabienteOrdenanteDAO daoC = new CuentahabienteOrdenanteDAO();
                List listaOrdenante = null;
                try {
                    listaOrdenante = daoC.lCuentahabienteSucursal(sucursal);
                } catch (Exception ex) {
                    Logger.getLogger(AddOrdenanteServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                json = new Gson().toJson(listaOrdenante);
                break;
            case 6:
                List listaClabe = CuentahabienteOrdenanteForm.listaClabeEntidad();
                json = new Gson().toJson(listaClabe);
                break;
            default:
        }
        try (PrintWriter out = response.getWriter()) {
            out.println(json);
            out.flush();
            out.close();
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

}
