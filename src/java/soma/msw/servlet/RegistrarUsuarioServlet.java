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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.general.CatalogoRolModelo;
import modelo.general.PersonaModelo;
import modelo.general.SucursalModelo;
import modelo.general.UsuarioModelo;
import soma.msw.dao.UsuarioMswDAO;
import soma.msw.form.UsuarioMswForm;
import util.Seguridad;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 31/10/2015
 * @hora 10:32:12 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 * @author SOMA-LUNA
 *
 */
@WebServlet(name = "RegistrarUsuarioServlet", urlPatterns = {"/RegistrarUsuarioServlet"})
public class RegistrarUsuarioServlet extends HttpServlet {

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

        SucursalModelo sm = new SucursalModelo();

        if (request.getParameter("sucursal") != null) {
            sm = SucursalForm.buscaSucursalNumeroClabe(
                    Integer.parseInt(request.getParameter("sucursal")));
        }

        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombreU");
        String contrasena = request.getParameter("contrasenia");
        int accion = Integer.parseInt(request.getParameter("accion"));
        String usuarioId = request.getParameter("usuarioId");
        String contrasenia;
        boolean error = true;
        CatalogoRolModelo cr = new CatalogoRolModelo();

        if (request.getParameter("rol") != null) {
            try {
                cr = UsuarioMswForm.listaCatalogoRol(
                        Integer.parseInt(request.getParameter("rol")));
            } catch (Exception ex) {
                Logger.getLogger(RegistrarUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        UsuarioMswDAO uDao = new UsuarioMswDAO();
        UsuarioModelo um = new UsuarioModelo();
        PersonaModelo persona = new PersonaModelo();
        UsuarioModelo bui = new UsuarioModelo();
        UsuarioModelo usm = new UsuarioModelo();
        String json = null;

        switch (accion) {
            /**
             * CASE 1: Permite Guardar un usuario en la base de datos
             * verificando que el nickname no se repita.
             */
            case 1:
                persona.setNombre(nombre);

                um.setPersona(persona);
                um.setNickname(nickname);
                um.setContrasenia(Seguridad.encripta(contrasena));
                um.setSucursal(sm);
                um.setCatalogorol(cr);
                usm = uDao.buscaUsuarioRegistrado(nickname);
                if (usm == null) {
                    try {
                        uDao.guardaPersona(persona);
                        uDao.guardaUsuario(um);
                    } catch (Exception ex) {
                        Logger.getLogger(RegistrarUsuarioServlet.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
                break;
            /**
             * CASE 2: Permite Actualizar a un usuario en la base de datos
             * verificando que el nickname no se repita
             */
            case 2:
                bui = uDao.buscaUsuarioId(Integer.parseInt(usuarioId));
                contrasenia = Seguridad.desencripta(bui.getContrasenia());

                persona.setNombre(nombre);
                persona.setPersonaId(bui.getPersona().getPersonaId());
                um.setUsuarioId(Integer.parseInt(usuarioId));
                um.setPersona(persona);
                um.setNickname(nickname);
                um.setContrasenia(Seguridad.encripta(contrasenia));
                um.setSucursal(sm);
                um.setCatalogorol(cr);

                if (bui.getNickname().equals(nickname)) {
                    int id = Integer.parseInt(usuarioId);
                    if (bui.getUsuarioId() == id) {
                        uDao.guardaPersona(persona);
                        uDao.guardaUsuario(um);
                    } else {
                        error = false;
                        json = new Gson().toJson(error);

                    }
                } else {
                    uDao.guardaPersona(persona);
                    uDao.guardaUsuario(um);
                }

                break;
            /**
             * CASE 3: Permite actualizar la contraseña y sus derivados
             */
            case 3:
                bui = uDao.buscaUsuarioRegistrado(nickname);
                UsuarioModelo u = uDao.buscaUsuarioId(Integer.parseInt(usuarioId));
                contrasenia = contrasena;
                persona.setNombre(nombre);
                persona.setPersonaId(u.getPersona().getPersonaId());
                um.setUsuarioId(Integer.parseInt(usuarioId));
                um.setPersona(persona);
                um.setNickname(nickname);
                um.setContrasenia(Seguridad.encripta(contrasenia));
                um.setSucursal(sm);
                um.setCatalogorol(u.getCatalogorol());

                if (bui != null) {
                    if (bui.getNickname().equals(nickname)) {
                        int id = Integer.parseInt(usuarioId);
                        if (bui.getUsuarioId() == id) {
                            uDao.guardaPersona(persona);
                            uDao.guardaUsuario(um);
                            json = new Gson().toJson(error);
                            break;
                        } else {
                            error = false;
                            json = new Gson().toJson(error);

                        }
                    } else {
                        uDao.guardaPersona(persona);
                        uDao.guardaUsuario(um);
                    }
                } else {
                    uDao.guardaPersona(persona);
                    uDao.guardaUsuario(um);
                }
                break;
            /**
             * CASE 4: Lista los roles registrados de la base de datos.
             */
            case 4:
                List listaRoles = UsuarioMswForm.listaCatalogoRol();
                json = new Gson().toJson(listaRoles);
                break;
            /**
             * CASE 5: Lista las Sucursales Registradas.
             */
            case 5:
                List listaSucursales = UsuarioMswForm.listaSucursales();
                json = new Gson().toJson(listaSucursales);
                break;
            default:
                break;
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
    }// </editor-fold>

}
