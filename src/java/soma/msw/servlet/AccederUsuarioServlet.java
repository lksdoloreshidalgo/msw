/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.servlet;

import com.google.gson.Gson;
import form.GeneralForm;
import form.SucursalForm;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.general.EntidadModeloNuevo;
import modelo.general.SucursalModelo;
import modelo.general.UsuarioModelo;
import soma.msw.dao.UsuarioMswDAO;
import util.Seguridad;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 30/10/2015
 * @hora 06:41:37 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 * @author SOMA-LUNA
 *
 */
@WebServlet(name = "AccederUsuarioServlet", urlPatterns = {"/AccederUsuarioServlet"})
public class AccederUsuarioServlet extends HttpServlet {

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

        HttpSession sesion = request.getSession();

        String usuario = request.getParameter("nickname");
        String contrasenia = Seguridad.encripta(
                request.getParameter("contrasenia"));
        SucursalModelo sm = SucursalForm.buscaSucursalNumeroClabe(
                Integer.parseInt(request.getParameter("sucursal")));
        boolean bandera;

        UsuarioMswDAO uDao = new UsuarioMswDAO();
        UsuarioModelo um = new UsuarioModelo();
        //se obtienen los valores de los campos

        um.setNickname(usuario);
        um.setContrasenia(contrasenia);
        um.setSucursal(sm);

        UsuarioModelo usm = uDao.buscaExisteUsuario(usuario, contrasenia, sm);
        UsuarioModelo bum = uDao.buscarUsuario(usuario);

        EntidadModeloNuevo entidad = GeneralForm.getEntidad();
        int numeroEntidad = entidad.getNumero();
        int cantidad = 3;
        String json;

        if (usm != null) {
            bandera = true;
            sesion.setAttribute("usuario", um.getNickname());
            sesion.setAttribute("sucursal", sm.getNumeroClabe());
            sesion.setAttribute("entidad", Util.colocaCero(numeroEntidad,
                    cantidad));
            sesion.setAttribute("rol", bum.getCatalogorol().getIdentificador());
            sesion.setAttribute("usuarioId", bum.getUsuarioId());
            json = new Gson().toJson(bandera);

        } else {
            bandera = false;
            json = new Gson().toJson(bandera);
        }
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    } // </editor-fold>

}
