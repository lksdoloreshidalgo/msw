<%-- 
    Document   : edoSaldos
    Created on : 26/08/2015, 11:27:33 AM
    Author     : Estadias8
--%>

<%@page import="modelo.ws.mensaje.entrada.ACUSESALDOENTIDADWSModelo"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.math.BigDecimal"%>

<%@page import="ws.cliente.SaldosWS"%>
<%@page import="modelo.general.SaldosModelo"%>
<%@page import="form.SaldoForm"%>
<%@page import="java.util.List"%>
<%@page import="util.Util"%>

<%
    BigDecimal saldoGeneral = new BigDecimal(0.00);
    BigDecimal saldoPorDevolver = new BigDecimal(0.00);
    String saldo1 = null;
    String saldo2 = null;
    try {
        ACUSESALDOENTIDADWSModelo saldoEntidad = SaldosWS.getSaldoEntidad( );

        saldoGeneral = saldoEntidad.getSaldoEntidad().getSaldoGeneral();
        saldoPorDevolver = saldoEntidad.getSaldoEntidad().getSaldoPorDevolver();
        saldo1 = Util.formatoMoney(saldoGeneral.toString());
        saldo2 = Util.formatoMoney(saldoPorDevolver.toString());

        if (saldo1 == null && saldo2 == null) {
            saldo1 = "No existe conexion, consulta al administrador";
            saldo2 = "No existe conexion, consulta al administrador";
        }

    } catch (Exception ex) {
        ex.getMessage();
        saldo1 = "No existe conexion, consulta al administrador";
        saldo2 = "No existe conexion, consulta al administrador";
    }
%>
<div>
    <div class="row">
        <div class="col-md-3">
            <a href="javascript:location.reload()" class="btn btn-success">
                <span class="glyphicon glyphicon-refresh">
                </span>
                Actualizar
            </a>
        </div>
        <div class="col-md-9">
            <div class="row">
                <div class="col-md-5">
                    <h4>General: </h4>
                </div>
                <div class="col-md-4">
                    <h4><span class="label label-primary"><%=saldo1%></span></h4>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <h4>Por Devolver: </h4>
                </div>
                <div class="col-md-4">
                    <h4><span class="label label-danger"><%=saldo2%></span></h4>
                </div>
            </div>
            <input type="hidden" id="saldoGeneral" name="saldoGeneral" 
                   value="<%=saldo1%>">
        </div>
    </div>
</div>
