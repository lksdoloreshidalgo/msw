/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import form.SucursalForm;
import form.catalogo.CausaDevolucionForm;
import form.catalogo.EstadoOrdenForm;
import form.catalogo.EstadoPeticionForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoCuentaForm;
import form.catalogo.TipoOperacionForm;
import form.catalogo.TipoPagoESForm;
import form.catalogo.TipoPagoForm;
import form.catalogo.TipoTraspadoForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 22/02/2016
 * @hora 10:27:29 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
@WebServlet(name = "CatalogosServlet", urlPatterns = {"/CatalogosServlet"})
public class CatalogosServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CatalogosServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CatalogosServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        String json = null;
        /**
         * Se define que catalogo es el encargado de ser mostrado
         */
        switch (accion) {
            case 1:
                List l = ParticipanteForm.getlParticipante();
                json = new Gson().toJson(l);
                break;
            case 2:
                List lcd = CausaDevolucionForm.getlCausaDevolucion();
                json = new Gson().toJson(lcd);
                break;
            case 3:
                List eo = EstadoOrdenForm.getlEstadoOrden();
                json = new Gson().toJson(eo);
                break;
            case 4:
                List ep = EstadoPeticionForm.getlCatalogo();
                json = new Gson().toJson(ep);
                break;
            case 5:
                List ltc = TipoCuentaForm.getlTipoCuenta();
                json = new Gson().toJson(ltc);
                break;
            case 6:
                List lto = TipoOperacionForm.getlTipoOperacion();
                json = new Gson().toJson(lto);
                break;
            case 7:
                List lpes = TipoPagoESForm.getlTipoPagoES();
                json = new Gson().toJson(lpes);
                break;
            case 8:
                List ltp = TipoPagoForm.getlTipoPago();
                json = new Gson().toJson(ltp);
                break;
            case 9:
                List ltt = TipoTraspadoForm.getlTipoTraspaso();
                json = new Gson().toJson(ltt);
                break;
            case 10:
                List ls = SucursalForm.getlSucursal();
                json = new Gson().toJson(ls);
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
