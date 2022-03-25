/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import constantes.EstadoPeticionConstante;
import constantes.ParametrosNombre;
import soma.msw.dao.CuentahabienteOrdenanteDAO;
import form.catalogo.TipoCuentaForm;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.general.CatalogoCLABEEntidadModelo;
import modelo.general.CatalogoModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.CuentahabienteSucursalModelo;
import modelo.general.IndiceModelo;
import util.Util;
import ws.cliente.RecibePaqueteEnviarWS;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 31/07/2015
 * @hora 12:03:15 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class CuentahabienteOrdenanteForm {

    private static List<CuentahabienteSucursalModelo> lCuentahabieteSucursal;
    private final CuentahabienteOrdenanteDAO dao = CuentahabienteOrdenanteDAO.getInstacia();

    /**
     *
     * @return
     */
    public static List<CuentahabienteSucursalModelo> lOrdenante() {
        CuentahabienteOrdenanteDAO dao = new CuentahabienteOrdenanteDAO();
        return dao.lOrdenante();
    }

    /**
     * Metodo que se encarga de generar el indice correspondiente a la cuenta
     * clabe
     *
     * @param im
     * @return string indice para cuenta clabe
     */
    public String calculaNumeroClabeIndice(IndiceModelo im) {
        //Agrega 0 faltantes
        String numeroStr = "00000000" + im.getContador();
        numeroStr = Util.invierteCadena(numeroStr);
        numeroStr = numeroStr.substring(0, 8);
        numeroStr = Util.invierteCadena(numeroStr);
        return numeroStr;
    }

    /**
     * Metodo que se encarga de calcular el numero validador de una cuenta
     *
     * @param clabe
     * @return
     */
    public String generaDigitoV(String clabe) {
        String respuesta = "";
        // Parametro en la operacion de calculo de digito
        int resta = ParametrosNombre.RESTA_DIGITO_VERIFICADOR;
        String[] aux = new String[clabe.length()];
        String[] ponderacion = new String[clabe.length()];
        String[] resultado = new String[clabe.length()];
        int sumaResultado = 0;
        int contadorPon = 1;
        //Llenamos arreglos
        for (int i = 0; i < clabe.length(); i++) {
            //Llenamos arreglo auxiliar 
            aux[i] = String.valueOf(clabe.charAt(i));
            //Llenamos arreglo ponderacion 
            switch (contadorPon) {
                case 1:
                    ponderacion[i] = String.valueOf(3);
                    contadorPon++;
                    break;
                case 2:
                    ponderacion[i] = String.valueOf(7);
                    contadorPon++;
                    break;
                case 3:
                    ponderacion[i] = String.valueOf(1);
                    contadorPon = 1;
                    break;
            }
            //Multiplicamos valores del arreglo Clabe y la ponderacion
            int digClabe = Integer.parseInt(aux[i]);
            int digPonde = Integer.parseInt(ponderacion[i]);
            resultado[i] = String.valueOf((digClabe * digPonde));
        }
        //Invertimos las cadenas y sumamos
        for (int j = 0; j < resultado.length; j++) {
            //En caso de ser dos digitos tomar el ultimo 
            if (resultado[j].length() > 1) {
                String auxRes = resultado[j];
                auxRes = Util.invierteCadena(auxRes);
                auxRes = auxRes.substring(0, 1);
                resultado[j] = auxRes;
            }
            //Suma los digitos del arreglo resultado
            sumaResultado += Integer.parseInt(resultado[j]);
        }

        //Verificamos si tiene mas de un digito la suma        
        if (String.valueOf(sumaResultado).length() > 1) {
            String auxSuma = String.valueOf(sumaResultado);
            auxSuma = Util.invierteCadena(auxSuma);
            sumaResultado = Integer.parseInt(auxSuma.substring(0, 1));
        }
        //Restamos el resultado a 10
        sumaResultado = resta - sumaResultado;

        //Verificamos si tiene mas de un digito la suma        
        String auxSuma = String.valueOf(sumaResultado);
        auxSuma = Util.invierteCadena(auxSuma);
        sumaResultado = Integer.parseInt(auxSuma.substring(0, 1));
        respuesta = String.valueOf(sumaResultado);
        //Retorna respuesta
        return respuesta;
    }

    /**
     * Valida la informacion de un Cuentahabiente Ordenante.
     *
     * @param ch
     * @return
     */
    public int validaOrdenante(CuentahabienteModelo ch) {

        if (ch.getNombre() == null || ch.getNombre().trim().length() < 1) {
            return EstadoPeticionConstante.EP_11;
        }
        if (!Util.validaMaxCarac(ch.getNombre(),
                RecibePaqueteEnviarWS.NO_CARACTERES_NOMBRE)) {
            return EstadoPeticionConstante.EP_10;
        }

        CatalogoModelo tc = TipoCuentaForm.buscaCatalogoClave(
                ch.getCuenta().getTipoCuenta().getClave());

        if (tc == null) {

            return EstadoPeticionConstante.EP_12;

        }

        if (!Util.validaMaxCarac(tc.getClave(), 2)) {
            return EstadoPeticionConstante.EP_13;
        }

        if (ch.getRfc() == null) {
            return EstadoPeticionConstante.EP_70;
        }

        return EstadoPeticionConstante.EP_0;
    }

    /**
     * bsdfhbksdfbhksvbkhsdhbksvdkhbsv
     */
    /**
     * Metodo que busca un cuentahabiente por personaId
     *
     * @param ordenanteId
     * @return
     */
    public static CuentahabienteSucursalModelo buscarCuentahabienteId(int ordenanteId) {
        cargarBeneficiario(ordenanteId);
        for (int i = 0; i < lCuentahabieteSucursal.size(); i++) {
            CuentahabienteSucursalModelo chm = lCuentahabieteSucursal.get(i);
            if (chm.getId() == ordenanteId) {
                return chm;
            }
        }
        return null;
    }

    /**
     * Metodo que carga los beneficiarios
     *
     * @param ordenanteId
     */
    static public void cargarBeneficiario(int ordenanteId) {
        lCuentahabieteSucursal
                = CuentahabienteOrdenanteForm.cargaCuentahabienteSucursalModelo(
                        ordenanteId);
    }

    /**
     * Metodo que carga los beneficiarios por id:
     *
     * @param ordenanteId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<CuentahabienteSucursalModelo>
            cargaCuentahabienteSucursalModelo(int ordenanteId) {
        return CuentahabienteOrdenanteDAO.cargaCuentahabienteSucursal(
                ordenanteId);
    }

    /**
     * Metodo para buscar un ordenante por id
     *
     * @param ordenanteId
     * @return
     */
    public static CuentahabienteSucursalModelo buscaOrdenante(int ordenanteId) {
        CuentahabienteOrdenanteDAO dao = new CuentahabienteOrdenanteDAO();
        return dao.buscaOrdenante(ordenanteId);
    }

    /**
     * Metodo encargado de listar cuenta habientes ordenantes
     *
     * @return
     */
    public List<CuentahabienteSucursalModelo> listaCuentaHabienteOrdenantes() {

        List<CuentahabienteSucursalModelo> lu = new ArrayList<>();
        try {
            lu = dao.listarCuentaHabienteOrdenante();
        } catch (Exception ex) {
            Logger.getLogger(CuentahabienteOrdenanteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lu;
    }

    /*
     * Metodo encargado de obtener un arreglo con lod datos de la tabla 
     * catalogo Clabe
     */
    public static List<CatalogoCLABEEntidadModelo> listaClabeEntidad() {
        CuentahabienteOrdenanteDAO dao1 = new CuentahabienteOrdenanteDAO();
        List<CatalogoCLABEEntidadModelo> lcb = new ArrayList<>();
        try {
            lcb = dao1.listarClabeEntidad();
        } catch (Exception ex) {
            Logger.getLogger(CuentahabienteOrdenanteForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lcb;
    }

    /*
     * Metodo encargado de obtener un arreglo con lod datos de la tabla 
     * catalogo Clabe
     */
    public static CatalogoCLABEEntidadModelo listaClabeEntidad(int identificador) throws Exception {
        CuentahabienteOrdenanteDAO dao1 = new CuentahabienteOrdenanteDAO();
        List<CatalogoCLABEEntidadModelo> lcbe = new ArrayList<>();

        lcbe = dao1.listarClabeEntidad(identificador);

        for (int i = 0; i < lcbe.size(); i++) {
            CatalogoCLABEEntidadModelo cm = lcbe.get(i);

            if (cm.getIdentificador() == identificador) {
                return cm;
            }
        }
        return null;
    }

    /*
     * Metodo encargado de obtener un arreglo con los datos del cuentahabiente 
     * Sucursal 
     */
    public static CuentahabienteSucursalModelo listaCuentahabienteSucursal(String numClabe) throws Exception {
        CuentahabienteOrdenanteDAO dao1 = new CuentahabienteOrdenanteDAO();
        List<CuentahabienteSucursalModelo> lcbe = new ArrayList<>();

        lcbe = dao1.listaCuentahabienteSucursal(numClabe);

        for (int i = 0; i < lcbe.size(); i++) {
            CuentahabienteSucursalModelo cm = lcbe.get(i);

            if (cm.getNumeroCuentahabiente() == null ? numClabe == null : cm.getNumeroCuentahabiente().equals(numClabe)) {
                return cm;
            }
        }
        return null;
    }

    /**
     *
     * @param sucursal
     * @return
     */
//    public String[] cargarCuentahabienteSucursal(String sucursal) {
//        String[] datos = new String[7];
//        try {
//            List<Object[]> list = dao.lCuentahabienteSucursal(sucursal);
//            for (Object[] obj : list) {
//                datos[0] = (obj[0].toString());
//                datos[1] = (obj[1].toString());
//                datos[2] = (obj[2].toString());
//                datos[3] = (obj[3].toString());
//                datos[4] = (obj[4].toString());
//                datos[5] = (Integer.parseInt((obj[5])));
//                datos[6] = (Integer.parseInt((obj[6])));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CuentahabienteOrdenanteForm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return datos;
//    }

}
