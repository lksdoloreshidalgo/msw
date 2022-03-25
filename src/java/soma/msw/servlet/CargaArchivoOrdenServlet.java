/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import form.OrdenCSVForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.ArchivoOrdenCSVModelo;
import modelo.general.OrdenArchivoModelo;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Socket
 */
@WebServlet(name = "CargaArchivoOrdenServlet", urlPatterns = {"/CargaArchivoOrdenServlet"})
public class CargaArchivoOrdenServlet extends HttpServlet {

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
            out.println("<title>Servlet CargaArchivoOrdenServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CargaArchivoOrdenServlet at " + request.getContextPath() + "</h1>");
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
        try {
            String arregloJSON = request.getParameter("arreglo");
            JSONArray arr = new JSONArray(arregloJSON);

            List<List<String>> lObj;
            lObj = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                JSONArray objJason = arr.getJSONArray(i);

                List<String> lFilas = new ArrayList<>();
                for (int j = 0; j < objJason.length(); j++) {

                    lFilas.add(objJason.getString(j));
                }
                lObj.add(lFilas);
            }
            lObj.remove(0);

            OrdenCSVForm form = new OrdenCSVForm();
            ArchivoOrdenCSVModelo res = form.procesaArreglo(lObj);

            /**
             * La lista de objetos se convierte a Json para ser manipulada por
             * el JavaScript
             */
            String json = new Gson().toJson(res);

            PrintWriter out = response.getWriter();
            out.println(json);
            out.flush();

            System.out.println("Termino");
        } catch (JSONException ex) {
            Logger.getLogger(CargaArchivoOrdenServlet.class.getName()).log(Level.SEVERE, null, ex);
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
