/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.form;

import dao.OrdenDAO;
import form.catalogo.EstadoOrdenForm;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.general.CatalogoModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.CuentahabienteSucursalModelo;
import modelo.general.DevolucionModelo;
import modelo.general.InstruccionModelo;
import modelo.general.OrdenModelo;
import modelo.general.TerceroTerceroModelo;
import util.Util;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 13/10/2015
 * @hora 01:47:25 PM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 *
 */
public class OrdenMswForm {

    /**
     * Contiene una lista de instrucciones para llenar la tabla.
     */
    private List<InstruccionModelo> lInstruccion;

    public OrdenMswForm(List<InstruccionModelo> lInstruccion) {
        this.lInstruccion = lInstruccion;
    }

    public List<Object[]> contenidoTabla() {

        List<Object[]> lObj = new ArrayList<Object[]>();

        if (lInstruccion == null) {
            return lObj;
        }

        int cont = 1;
        for (InstruccionModelo im : lInstruccion) {
            List<OrdenModelo> lo = im.getlOrden();

            for (OrdenModelo o : lo) {

                List<Object> aloe = new ArrayList<Object>();

                aloe.add(cont++);
                aloe.add(o.getTipoPago().getDescripcion());//tipo_pago
                aloe.add(o.getFolioOrden());            //folio_orden
                aloe.add(im.getFolioInstruccion());     //folio_instruccion     
                aloe.add(o.getClaveRastreo());          //clave_rastreo
                aloe.add(im.getPrioridad());            //prioridad
                aloe.add(im.getTopologia());            //topologia
                String montoD = Util.formatoMoney(o.getMonto().toString());

                aloe.add(montoD);                 //monto
                String ivaD;

                if (o.getIva() != null) {
                    ivaD = Util.formatoMoney(o.getIva().toString());
                } else {
                    ivaD = Util.formatoMoney("0.00");
                }
                aloe.add(ivaD);//iva
                CatalogoModelo est = EstadoOrdenForm.buscaCatalogoClave(
                        o.getM_EstadoPago().getClave());

                aloe.add(
                        est.getDescripcion());     //esattusordenid

                String nombre_o = "";
                int tipo_cuenta_o = 0;
                String cuenta_o = "";
                String rfc_o = "";

                String nombre_b = "";
                int tipo_cuenta_b = 0;
                String cuenta_b = "";
                String rfc_b = "";

                String ref_cobranza1 = "";

                String causaDevolucion = "";
                String claveRastreoDev = "";

                int ordenId_ben = 0;
                int ordenId_ord = 0;
                String identificador = "";
                int clavePart = 0;
                /**
                 * Llena la Orden con todos los datos de todos los tipos de
                 * pagos.
                 */
                int tipoPago = o.getTipoPago().getClave();

                switch (tipoPago) {
                    case 0:
                        /**
                         * DEVOLUCION
                         */
                        DevolucionModelo dev = o.getP_devolucion();

                        causaDevolucion
                                = dev.getCausaDevolucion().getDescripcion();

                        InstruccionModelo iOrg = OrdenDAO.getPagosSalida(
                                im.getFecha(),
                                dev.getDevFolioInstruccion(),
                                dev.getDevFolioOrden());

                        if (iOrg != null) {
                            OrdenModelo oOrg = iOrg.getlOrden().get(0);

                            TerceroTerceroModelo ttOrg = oOrg.getP_terceroTercero();

                            if (ttOrg == null) {

                                nombre_o = "";
                                tipo_cuenta_o = 0;
                                cuenta_o = "";
                                rfc_o = "";

                                nombre_b = "";
                                tipo_cuenta_b = 0;
                                cuenta_b = "";
                                rfc_b = "";
                            } else {
                                CuentahabienteModelo ben = ttOrg.getBeneficiario();
                                CuentahabienteModelo ord = ttOrg.getOrdenante();

                                nombre_o = ben.getNombre();
                                tipo_cuenta_o = ben.getCuenta().getTipoCuenta().getClave();
                                cuenta_o = ben.getCuenta().getNumero();
                                rfc_o = ben.getRfc();

                                nombre_b = ord.getNombre();
                                tipo_cuenta_b = ord.getCuenta().getTipoCuenta().getClave();
                                cuenta_b = ord.getCuenta().getNumero();
                                rfc_b = ord.getRfc();
                            }

                        }

                        claveRastreoDev = dev.getClaveRastreo();

                        break;
                    case 1:
                        /**
                         * TERCERO A TERCERO
                         */
                        TerceroTerceroModelo tt = o.getP_terceroTercero();
                        nombre_o = tt.getOrdenante().getNombre();
                        tipo_cuenta_o
                                = tt.getOrdenante().getCuenta().
                                getTipoCuenta().getClave();

                        cuenta_o = tt.getOrdenante().getCuenta().getNumero();
                        rfc_o = tt.getOrdenante().getRfc();

                        nombre_b = tt.getBeneficiario().getNombre();
                        tipo_cuenta_b
                                = tt.getBeneficiario().getCuenta().getTipoCuenta().getClave();
                        cuenta_b = tt.getBeneficiario().getCuenta().getNumero();
                        rfc_b = tt.getBeneficiario().getRfc();

                        ref_cobranza1 = tt.getRefCobranza1();

                        ordenId_ben = tt.getBeneficiario().getPersonaId();
                        ordenId_ord = tt.getOrdenante().getPersonaId();

                        CuentahabienteSucursalModelo chsm = CuentahabienteOrdenanteForm.buscaOrdenante(ordenId_ord);
                        if (chsm != null) {
                            identificador = chsm.getIdentificador();

                            CuentahabienteModelo chm = CuentahabienteBeneficiarioForm.buscaBeneficiario(ordenId_ben);
                            clavePart = chm.getCuenta().getParticipante().getClave();

                        }

                        break;
                    case 3:
                        /**
                         * TERCERO A TERCERO VOSTRO
                         */
                        break;
                    case 4:
                        /**
                         * TERCERO A PARTICIPANTE
                         */
                        break;
                    case 5:
                        /**
                         * PARTICIPANTE A TERCERO
                         */
                        break;
                    case 6:
                        /**
                         * PARTICIPANTE A TECERO VOSTRO
                         */
                        break;
                    case 7:
                        /**
                         * PARTICIPANTE A PARTICIPANTE
                         */
                        break;
                }

                /**
                 * ORDENANTE
                 */
                aloe.add(nombre_o);//nombre_o
                aloe.add(tipo_cuenta_o);//tipo_cuenta_o
                aloe.add(cuenta_o);//cuenta_o
                aloe.add(rfc_o);//rfc_o
                /**
                 * BENEFICIARIO
                 */
                aloe.add(nombre_b);//nombre_b
                aloe.add(tipo_cuenta_b);//tipo_cuenta_b
                aloe.add(cuenta_b);//cuenta_b
                aloe.add(rfc_b);//rfc_b
                /**
                 * BENEFICIARIO 2
                 */
                aloe.add("");//nombre_b2
                aloe.add("");//tipo_cuenta_b2
                aloe.add("");//cuenta_b2
                aloe.add("");//rfc_ b2

                aloe.add(o.getConcepto());//concepto
                aloe.add("");//concepto2
                aloe.add(o.getReferenciaNumerica());//ref_numerica 
                aloe.add(ref_cobranza1);//referencia_cobranza
                aloe.add("0");//tipo_operacion
                aloe.add(causaDevolucion);//causa_devolucion
                aloe.add(claveRastreoDev);//clave_rastreo_dev
                aloe.add(
                        im.getParticipanteOrdenante().
                        getDescripcion());//clave_part_emisor
                aloe.add(
                        im.getParticipanteBeneficiario().
                        getDescripcion());//clave_part_receptor
                aloe.add(im.getFecha());//fecha

                DateFormat fechaHora = new SimpleDateFormat("HH:mm:ss");
                String convertido = fechaHora.format(o.getHoraInsercion());

                aloe.add(convertido);//hora
                String entrada;
                if (im.isEntrada() == false) {
                    entrada = "Salida";
                } else {
                    entrada = "Entrada";
                }
                aloe.add(entrada);//entrada
                aloe.add("");//firma_electronica
                aloe.add(o.getCde());//cde_orden
                aloe.add(im.getId());//instruccionid
                aloe.add(o.getOrdenId());//ordengeneralid
                aloe.add(0);//entidadid
                aloe.add(0);//ordenoriginalid

                if (o.getSucursal() != null) {
                    aloe.add(o.getSucursal().getNombre());//ordenoriginalid

                } else {
                    aloe.add("SIN DEFINIR");//ordenoriginalid
                }
                aloe.add(im.getSaldo().getNombre());

                aloe.add(ordenId_ben);
                aloe.add(ordenId_ord);
                aloe.add(identificador);
                aloe.add(clavePart);

                lObj.add(Util.convArregloObj(aloe));

            }
        }

        return lObj;
    }
}
