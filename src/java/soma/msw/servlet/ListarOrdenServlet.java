/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import form.SucursalForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.OrdenModelo;
import modelo.general.SucursalModelo;
import modelo.general.UsuarioModelo;
import soma.msw.dao.UsuarioMswDAO;
import soma.msw.form.EstadoPagoMswForm;
import soma.msw.form.InstruccionMswForm;
import soma.msw.form.UsuarioMswForm;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 23/02/2016
 * @hora 12:19:02 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
@WebServlet(name = "ListarOrdenServlet", urlPatterns = {"/ListarOrdenServlet"})
public class ListarOrdenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        int accion = Integer.parseInt(request.getParameter("accion"));
        String usuarioId = request.getParameter("usuarioId");

        String sucursalF = request.getParameter("sucursalF");
        SucursalModelo sm = new SucursalModelo();
        if (sucursalF != null) {
            sm = SucursalForm.buscaSucursalNumeroClabe(
                    Integer.parseInt(sucursalF));
        }
        String idIntruccion = request.getParameter("instruccionId");

        UsuarioMswDAO uDao = new UsuarioMswDAO();
        UsuarioModelo um = new UsuarioModelo();

        String json = null;
        int id;

        switch (accion) {
            /**
             * CASE 1: Lista el contenido de Bitacora de ordenes segun el rol de
             * los usuarios y por sucursal
             */
            case 1:
                um = uDao.buscaUsuarioId(Integer.parseInt(usuarioId));
                String entrada = request.getParameter("entrada");
                String salida = request.getParameter("salida");
                String partReceptor = request.getParameter("partReceptor");
                String partEmisor = request.getParameter("partEmisor");
                String cveRastreo = request.getParameter("cveRastreo");
                String folioIns = request.getParameter("folioIns");
                String folioOrd = request.getParameter("folioOrd");
                String estado = request.getParameter("estado");
                String sucursal = request.getParameter("sucursal");
                String tipoPago = request.getParameter("tipoPago");
                String fechaInicio = request.getParameter("fechaInicio");
                String fechaFin = request.getParameter("fechaFin");

                String[] array = new String[]{
                    entrada, salida, partReceptor, partEmisor, cveRastreo,
                    folioIns, folioOrd, estado, sucursal, tipoPago, fechaInicio,
                    fechaFin
                };

                if (um.getCatalogorol().getIdentificador() == 1) {
                    List lop = InstruccionMswForm.contenidoTablaOrden(array);
                    json = new Gson().toJson(lop);
                } else {
                    List lop = InstruccionMswForm.contenidoTablaOrden(sm, array);
                    json = new Gson().toJson(lop);
                }

                break;
            /**
             * CASE 2: Lista la pantalla principal de las ordenes de pago con la
             * fecha actual y por suscursal segun el rool del usuario
             */
            case 2:
                um = uDao.buscaUsuarioId(Integer.parseInt(usuarioId));

                List lop;
                switch (um.getCatalogorol().getIdentificador()) {
                    case 1:
                        lop = InstruccionMswForm.contenidoTablaOrden(Util.fechaSistema('-', 1));
                        json = new Gson().toJson(lop);
                        break;
                    case 2:
                        lop = InstruccionMswForm.contenidoTablaOrden(Util.fechaSistema('-', 1), sm);
                        json = new Gson().toJson(lop);
                        break;
                    case 3:
                        lop = InstruccionMswForm.contenidoTablaOrden(Util.fechaSistema('-', 1), sm);
                        json = new Gson().toJson(lop);
                        break;
                    case 4:
                        lop = InstruccionMswForm.contenidoTablaOrden(Util.fechaSistema('-', 1), sm);
                        json = new Gson().toJson(lop);
                        break;
                    default:
                        lop = InstruccionMswForm.contenidoTablaOrden(Util.fechaSistema('-', 1), sm);
                        json = new Gson().toJson(lop);
                        break;
                }
                break;
            /**
             * CASE 3: se obtiene la orden de pagopor ID
             */
            case 3:
                id = Integer.parseInt(idIntruccion);
                List im = InstruccionMswForm.contenidoTablaOrden(id);
                json = new Gson().toJson(im);
                break;
            /**
             * CASE 4: Se obtiene una lista de los estados de la orden de pago
             */
            case 4:
                id = Integer.parseInt(idIntruccion);
                OrdenModelo o = InstruccionMswForm.buscaInstruccion(id).getlOrden().get(0);
                List loe = EstadoPagoMswForm.contenidoEstados(o);
                json = new Gson().toJson(loe);
                break;
            /**
             * CASE 5: Lista los usuarios registrado para ingresar al sistema
             */
            case 5:
                List lu = UsuarioMswForm.lUsuarios();
                json = new Gson().toJson(lu);
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
