/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import soma.msw.dao.CuentahabienteBeneficiarioDAO;
import java.util.List;
import modelo.general.CuentaModelo;
import modelo.general.CuentahabienteModelo;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 1/07/2015
 * @hora 10:49:47 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class CuentahabienteBeneficiarioForm {

    private static List<CuentahabienteModelo> lCuentahabiete;
    //private static int personaId;

    /**
     * Metodo que lista todos los beneficiarios de la base de datos.
     *
     * @return
     */
    public static List<CuentahabienteModelo> listaCuentahabiente() {
        CuentahabienteBeneficiarioDAO dao = new CuentahabienteBeneficiarioDAO();
        return dao.listaCuentahabientes();
    }

    /**
     * Metodo que carga los beneficiarios por id:
     *
     * @param personaId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<CuentahabienteModelo> cargaCuentahabienteModelo(int personaId) {
        return CuentahabienteBeneficiarioDAO.cargaCuentahabiente(personaId);
    }

    /**
     * Metodo que carga los beneficiarios
     *
     * @param personaId
     */
    static public void cargarBeneficiario(int personaId) {
        lCuentahabiete = CuentahabienteBeneficiarioForm.cargaCuentahabienteModelo(personaId);
    }

    /**
     *
     * @param personaId
     * @return
     */
    public static List<CuentahabienteModelo> getCuentahabiente(int personaId) {
        if (lCuentahabiete == null) {
            cargarBeneficiario(personaId);
        }
        return lCuentahabiete;
    }

    /**
     * Metodo que busca un cuentahabiente por personaId
     *
     * @param personaId
     * @return
     */
    public static CuentahabienteModelo buscarCuentahabienteId(int personaId) {
        cargarBeneficiario(personaId);
        for (int i = 0; i < lCuentahabiete.size(); i++) {
            CuentahabienteModelo chm = lCuentahabiete.get(i);
            if (chm.getPersonaId() == personaId) {
                return chm;
            }
        }
        return null;
    }

    /**
     * Metodo que busca un cuentahabiente por personaId
     *
     * @param personaId
     * @return
     */
    public static CuentahabienteModelo buscarCuentahabienteCuenta(int personaId) {
        cargarBeneficiario(personaId);
        for (int i = 0; i < lCuentahabiete.size(); i++) {
            CuentahabienteModelo chm = lCuentahabiete.get(i);
            if (chm.getPersonaId() == personaId) {
//                for (CuentaModelo cue : chm.getL_cuenta()) {
//                    if (cue.getCuentaId() == cuentaId) {
//                        return chm;
//                    }
//                }
                return chm;
            }
        }
        return null;
    }

    /**
     * Metodo que guarda a un Cuentahabiente Beneficiario
     *
     * @param chm
     * @return
     */
    public synchronized boolean guardarBeneficiario(CuentahabienteModelo chm) {
        CuentahabienteBeneficiarioDAO dao = new CuentahabienteBeneficiarioDAO();
        boolean bandera = dao.guardarBeneficiario(chm);
        return bandera;
    }

    /**
     * Metodo que guarda n Cuentas a un Beneficiario
     *
     * @param cm
     * @return
     */
    public synchronized boolean guardarCuentaBeneficiario(CuentahabienteModelo cm) {
        CuentahabienteBeneficiarioDAO dao = new CuentahabienteBeneficiarioDAO();
        boolean bandera = dao.guardarCuentaBeneficiario(cm);
        return bandera;
    }

    /**
     * Metodo que lista todos los beneficiarios de la base de datos.
     *
     * @param cuentaId
     * @return
     */
    public static CuentaModelo listaCuenta(int cuentaId) {
        CuentahabienteBeneficiarioDAO dao = new CuentahabienteBeneficiarioDAO();
        return dao.listaCuenta(cuentaId);
    }

    /**
     * busca beneficiario ID
     *
     * @param benficiarioId
     * @return
     */
    public static CuentahabienteModelo buscaBeneficiario(int benficiarioId) {
        CuentahabienteBeneficiarioDAO dao = new CuentahabienteBeneficiarioDAO();
        return dao.buscaBeneficiario(benficiarioId);
    }

}
