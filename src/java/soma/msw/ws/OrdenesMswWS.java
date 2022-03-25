/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soma.msw.ws;

import constantes.ParametrosNombre;
import form.SaldoForm;
import form.catalogo.CausaDevolucionForm;
import form.catalogo.EstadoOrdenForm;
import form.catalogo.ParticipanteForm;
import form.catalogo.TipoCuentaForm;
import form.catalogo.TipoPagoForm;
import modelo.general.CatalogoModelo;
import modelo.general.CatalogoSaldoModelo;
import modelo.general.CuentahabienteModelo;
import modelo.general.DevolucionModelo;
import modelo.general.InstruccionModelo;
import modelo.general.OrdenModelo;
import modelo.general.TerceroTerceroModelo;

/**
 * @author Victor Hugo Luna Cortes
 * @correo hugo.luna@somatecnologia.com.mx
 * @fecha 29/10/2015
 * @hora 11:27:07 AM
 * @encoding ISO-8859-1
 * @empresa SOMA
 * @version 1.0
 * @author SOMA-LUNA
 *
 */
public class OrdenesMswWS {

    /**
     * Llena los catalogos de una orden.
     *
     * @param im
     */
    public static void llenaCatalogosInstruccion(InstruccionModelo im) {

        CatalogoModelo participanteO = new CatalogoModelo();
        participanteO = ParticipanteForm.buscaCatalogoClave(
                im.getParticipanteOrdenante().getClave()
        );
        CatalogoModelo participanteB = new CatalogoModelo();
        participanteB = ParticipanteForm.buscaCatalogoClave(
                im.getParticipanteBeneficiario().getClave()
        );
        im.setParticipanteOrdenante(participanteO);
        im.setParticipanteBeneficiario(participanteB);
        CatalogoSaldoModelo saldo = new CatalogoSaldoModelo();
        CatalogoSaldoModelo saldoDev = new CatalogoSaldoModelo();
        //Obtiene el saldo al que corresponde la instruccion
        if (im.isEntrada()) {
            saldo = SaldoForm.getSaldoEntrada();
        } else {
            saldo = SaldoForm.buscaSaldoId(im.getSaldo().getSaldoid());
        }

        for (OrdenModelo orden : im.getlOrden()) {
            CatalogoModelo estado = new CatalogoModelo();
            estado = EstadoOrdenForm.buscaCatalogoClave(
                    orden.getM_EstadoPago().getClave()
            );
            CatalogoModelo tipoPago = new CatalogoModelo();
            tipoPago = TipoPagoForm.buscaCatalogoClave(
                    orden.getTipoPago().getClave()
            );
//            orden.setOrdenId(0);
            orden.setM_EstadoPago(estado);
            orden.setTipoPago(tipoPago);
            //En el caso de ser de entrada suma al saldo
            if (im.isEntrada()) {
                if (estado.getClave() == ParametrosNombre.PORDEVOLVER) {
                    saldoDev = SaldoForm.buscaSaldoId(ParametrosNombre.SALDO_PORDEVOLVER);
//                    saldoDev = SaldoForm.sumaRestaSaldo(saldoDev, orden.getMonto(), ParametrosNombre.SUMA);
                    saldo = saldoDev;
                } else {
//                    saldo = SaldoForm.sumaRestaSaldo(saldo, orden.getMonto(), ParametrosNombre.SUMA);
                }
            }

            /**
             * Llena la Orden con todos los datos de todos los tipos de pagos.
             */
            switch (tipoPago.getClave()) {
                case 0:
                    /**
                     * DEVOLUCION
                     */

                    DevolucionModelo devolucion = orden.getP_devolucion();
                    //Complemento.
                    devolucion.setCausaDevolucion(
                            CausaDevolucionForm.buscaCatalogoClave(
                                    devolucion.getCausaDevolucion().getClave()
                            )
                    );
                    orden.setP_devolucion(devolucion);
                    break;
                case 1:
                    /**
                     * TERCERO A TERCERO
                     */
                    TerceroTerceroModelo tt = new TerceroTerceroModelo();
                    tt = orden.getP_terceroTercero();
                    CuentahabienteModelo ordenante = tt.getOrdenante();
                    CuentahabienteModelo beneficiario = tt.getBeneficiario();
                    /**
                     * Obtener el tipo de catalogo al que pertenece ya en base
                     * de datos y evitar duplicados
                     */
                    CatalogoModelo tcf = new CatalogoModelo();
                    tcf = TipoCuentaForm.buscaCatalogoClave(
                            ordenante.getCuenta().getTipoCuenta().getClave());
                    ordenante.getCuenta().setTipoCuenta(tcf);

                    ordenante.getCuenta().setParticipante(participanteO);
                    beneficiario.getCuenta().setParticipante(participanteB);

                    CatalogoModelo tcb = new CatalogoModelo();
                    tcb = TipoCuentaForm.buscaCatalogoClave(
                            beneficiario.getCuenta().getTipoCuenta().getClave());
                    beneficiario.getCuenta().setTipoCuenta(tcb);
                    tt.setOrdenante(ordenante);
                    tt.setBeneficiario(beneficiario);
                    orden.setP_terceroTercero(tt);

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
                default:
            }
        }
        im.setSaldo(saldo);
    }
}
