/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

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
import modelo.general.OrdenArchivoModelo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 5/02/2016
 * @hora 05:11:39 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 */
@WebServlet(name = "InsertarAordenServlet", urlPatterns = {"/InsertarAordenServlet"})
public class InsertarAordenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    final private int NOCOLUMNAS = 11;

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
            String arregloJSON = request.getParameter("lArreglo");
            System.out.println("hola2: " + arregloJSON);
            JSONArray arr = new JSONArray(arregloJSON);
            int length = arr.length();
            List<OrdenArchivoModelo> oam = new ArrayList<>(length);
            
            for (int i = 0; i < length; i++) {
                JSONObject quote = arr.getJSONObject(i);
                
                OrdenArchivoModelo o = new OrdenArchivoModelo();
                o.setNombreOrdenante(quote.getString("nombreOrdenante"));
                o.setNumeroCuentaOrdenante(quote.getString("numeroCuentaOrdenante"));
                o.setRfcOrdenante(quote.getString("rfcOrdenante"));
                o.setClaveParticipanteOrdenante(quote.getString("claveParticipanteOrdenante"));
                
                o.setNombreBeneficiario(quote.getString("nombreBeneficiario"));
                o.setNumeroCuentaBeneficiario(quote.getString("numeroCuentaBeneficiario"));
                o.setRfcBeneficiario(quote.getString("rfcBeneficiario"));
                o.setClaveParticipanteBneneficiario(quote.getString("claveParticipanteBneneficiario"));
                
                o.setMonto(quote.getString("monto"));
                o.setClaveRastreo(quote.getString("claveRastreo"));
                
                o.setConcepto(quote.getString("concepto"));
                o.setEstado(quote.getBoolean("estado"));
                o.setTopologia('T');
                
                oam.add(o);
            }
            
            OrdenCSVForm form = new OrdenCSVForm();
//            List<Object> res = form.insertaOrden(oam);

            List<Object> res = form.insertaOrden(oam);
            
            PrintWriter printout = response.getWriter();
            
            JSONObject JObjet = new JSONObject();
            JObjet.put("id", res.get(0));
            JObjet.put("mensaje", res.get(1));
            
            printout.print(JObjet);
            printout.flush();
            System.out.println("Llego: ");
        } catch (JSONException ex) {
            Logger.getLogger(InsertarAordenServlet.class.getName()).log(Level.SEVERE, null, ex);
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
