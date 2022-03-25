<%-- 
    Author     : Victor Hugo Luna Corteser}
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : estadoSaldos
    Created on : 19/09/2015, 10:07:27 AM
    Author     : SOMA_LUNA    
    empresa    : SOMA

--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@page import="soma.msw.form.EstadoPagoMswForm"%>
<%@page import="java.util.List"%>
<%@page import="soma.msw.form.InstruccionMswForm"%>
<%@page import="modelo.general.OrdenModelo"%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>



<!--<script src="js/ordenPagos/listOrdenPago.js" type="text/javascript"></script>-->
<script src="js/ordenPagos/estadoSaldos.js" type="text/javascript"></script>

<input type="hidden" id="instruccionId"  disabled="true">




<%
    List loe = null;
    String instruccionId = request.getParameter("instruccionId");
    if (instruccionId == null || instruccionId == "") {
        instruccionId = "0";
    } else {
        OrdenModelo o = InstruccionMswForm.buscaInstruccion(Integer.parseInt(instruccionId)).getlOrden().get(0);
        loe = EstadoPagoMswForm.contenidoEstados(o);
    }
%>
<table id="tblDetalleOrden" class="display table-bordered table-hover" >
    <thead>
        <tr>
            <th>#</th>
            <th>Estado</th>
            <th>Fecha y Hora</th>

        </tr>
    </thead>
    <tfoot>
        <tr>
            <th>#</th>
            <th>Estado</th>
            <th>Fecha y Hora</th>

        </tr>
    </tfoot>
    <tbody>
        <c:forEach var="det" items="<%=loe%>">
            <tr>
                <td></td>
                <td>${det[1]}</td>
                <td>${det[2]}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
