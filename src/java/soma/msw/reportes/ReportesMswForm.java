/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.reportes;

import form.SucursalForm;
import modelo.general.SucursalModelo;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 6/10/2015
 * @hora 11:33:40 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class ReportesMswForm {

    /**
     * Busca la sucursal a la que pertenece el cuentahabiente de la sucursal.
     *
     * @param numeroCuenta
     * @return
     */
    public SucursalModelo defineSucursalCuenta(String numeroCuenta) {

        SucursalModelo suc = null;

        int numeroCLABE = Integer.parseInt(numeroCuenta.substring(6, 9));

        suc = SucursalForm.buscaSucursalNumeroClabe(numeroCLABE);

        return suc;
    }

}
