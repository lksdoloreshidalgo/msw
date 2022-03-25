/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.general.CatalogoRolModelo;
import modelo.general.SucursalModelo;
import modelo.general.UsuarioModelo;
import soma.msw.dao.UsuarioMswDAO;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 30/10/2015
 * @hora 12:08:46 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 * @author SOMA-LUNA
 *
 */
public class UsuarioMswForm {

    private static final UsuarioMswDAO dao = new UsuarioMswDAO();

    /**
     * Metodo que se encarga de cargar una lista de usuarios de la BD
     *
     * @return
     */
    public static List<UsuarioModelo> lUsuarios() {

        return dao.lUsuarios();
    }

    /**
     * Metodo encargado de listar el catalogo de roles
     *
     * @return
     */
    public static List<CatalogoRolModelo> listaCatalogoRol() {
        List<CatalogoRolModelo> lcb = new ArrayList<>();
        try {
            lcb = dao.listaCatalogoRol();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMswForm.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return lcb;
    }

    /**
     * Metodo encargado de optener una lista de sucursales registradas.
     *
     * @return
     */
    public static List<SucursalModelo> listaSucursales() {
        List<SucursalModelo> ls = new ArrayList<>();
        try {
            ls = dao.listaSucursal();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMswForm.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return ls;
    }

    /**
     *
     * @param identificador
     * @return
     * @throws java.lang.Exception
     */
    public static CatalogoRolModelo listaCatalogoRol(int identificador) throws Exception {
        List<CatalogoRolModelo> lcb = new ArrayList<>();
        try {
            lcb = dao.listaCatalogoRol(identificador);
            for (int i = 0; i < lcb.size(); i++) {
                CatalogoRolModelo crm = lcb.get(i);

                if (crm.getIdentificador() == identificador) {
                    return crm;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMswForm.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
