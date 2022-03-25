/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import form.GeneralForm;
import soma.msw.form.CuentahabienteBeneficiarioForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoCuentaForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.CatalogoModelo;
import modelo.general.CuentaModelo;
import modelo.general.CuentahabienteModelo;

import soma.msw.dao.CuentahabienteBeneficiarioDAO;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 24/07/2015
 * @hora 11:09:31 AM
 * @encoding UTF-8
 * @empresa SOMA
 * @version 1.0
 * @author LUNA
 *
 */
@WebServlet(name = "AddBeneficiarioServlet", urlPatterns = {"/AddBeneficiarioServlet"})
public class AddBeneficiarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    CuentahabienteBeneficiarioForm chf = new CuentahabienteBeneficiarioForm();

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
        int accion = Integer.parseInt(request.getParameter("accion"));
        boolean bandera = false;
        String json = null;
        switch (accion) {
            /**
             * CASE 1: Permite insertar a un cuentahabiente beneficiario
             */
            case 1:
                String nombre = request.getParameter("nombre");
                String rfc = request.getParameter("rfc");

                int claveParticipante = Integer.parseInt(
                        request.getParameter("clavePart"));
                int claveTipoCuenta = Integer.parseInt(
                        request.getParameter("tipoCuenta"));
                String numero = request.getParameter("numero");
                boolean activo = Boolean.parseBoolean(
                        request.getParameter("activo"));

                CuentahabienteModelo cuentahabiente
                        = new CuentahabienteModelo();
                CuentaModelo cuenta = new CuentaModelo();
                CatalogoModelo dtoPa = new CatalogoModelo();
                CatalogoModelo dtoTc = new CatalogoModelo();

                dtoPa = ParticipanteForm.buscaCatalogoClave(claveParticipante);
                dtoTc = TipoCuentaForm.buscaCatalogoClave(claveTipoCuenta);

                cuenta.setNumero(numero);
                cuenta.setTipoCuenta(dtoTc);
                cuenta.setParticipante(dtoPa);

                cuentahabiente.setCuenta(cuenta);
                cuentahabiente.setNombre(nombre);
                cuentahabiente.setRfc(rfc);
                cuentahabiente.setEstado(activo);
                this.chf.guardarBeneficiario(cuentahabiente);
                break;
            /**
             * CASE 2: Permite actualizar a un cuentahabiente Beneficiario de la
             * BD
             */
            case 2:
                String personaIds = request.getParameter("personaId");
                String nombres = request.getParameter("nombre");
                String rfcs = request.getParameter("rfc");

                int claveParticipantes = Integer.parseInt(
                        request.getParameter("clavePart"));
                int claveTipoCuentas = Integer.parseInt(
                        request.getParameter("tipoCuenta"));
                String numeros = request.getParameter("numero");
                int cuentaId = Integer.parseInt(
                        request.getParameter("cuentaId"));
                boolean activos = Boolean.parseBoolean(
                        request.getParameter("activo"));

                CuentahabienteModelo cuentahabientes
                        = new CuentahabienteModelo();
                CuentaModelo cuentas = new CuentaModelo();

                dtoPa = ParticipanteForm.buscaCatalogoClave(claveParticipantes);
                dtoTc = TipoCuentaForm.buscaCatalogoClave(claveTipoCuentas);
                cuentahabientes.setPersonaId(
                        Integer.parseInt(personaIds));

                cuentas.setCuentaId(cuentaId);
                cuentas.setNumero(numeros);
                cuentas.setTipoCuenta(dtoTc);
                cuentas.setParticipante(dtoPa);

                cuentahabientes.setCuenta(cuentas);

                cuentahabientes.setNombre(nombres);
                cuentahabientes.setRfc(rfcs);
                cuentahabientes.setEstado(activos);

                this.chf.guardarBeneficiario(cuentahabientes);
                break;
            /**
             * CASE 3: Realiza la validacion paera verificar que el numero de
             * cuenta no se repita dentro de la base de datos
             */
            case 3:
                String numeroCuenta = request.getParameter("numero");
                CuentahabienteBeneficiarioDAO cbd
                        = new CuentahabienteBeneficiarioDAO();
                /**
                 * Obtenemos el numero si es que existe
                 */
                CuentaModelo num = cbd.obtenerNumero(numeroCuenta);

                if (num != null) {
                    if (num.getNumero().equals(numeroCuenta)) {
                        int idCuenta = Integer.parseInt(
                                request.getParameter("cuentaId"));
                        if (num.getCuentaId() == idCuenta) {
                            System.out.println("Participante: " + num.getParticipante().getClave());
                            bandera = true;
                        } else {
                            bandera = false;
                        }
                    }
                } else {
                    System.out.println("Nuevo Numero: " + numeroCuenta);
                    bandera = true;
                }
                json = new Gson().toJson(bandera);
                break;
            /**
             * CASE 4: Se encarga de validar que el participante Reforma no sea
             * el beneficiario
             */
            case 4:
                String cp = request.getParameter("clavePart");
                CatalogoModelo part = ParticipanteForm.buscaCatalogoClave(
                        GeneralForm.getClaveParticipante());
                String participante = String.valueOf(part.getClave());
                if (cp == participante) {
                    bandera = false;
                } else {
                    bandera = true;
                }
                json = new Gson().toJson(bandera);
                break;
            /**
             * CASE 5: Se encarga de realizar un listado de todos los
             * beneficiarios de la base de datos
             */
            case 5:
                List listaBeneficiario
                        = CuentahabienteBeneficiarioForm.listaCuentahabiente();
                json = new Gson().toJson(listaBeneficiario);

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
