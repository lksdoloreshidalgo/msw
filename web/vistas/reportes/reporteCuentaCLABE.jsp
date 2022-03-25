<%-- 
    Author     : Victor Hugo Luna Cortes}
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : reporteCuentaCLABE
    Created on : 6/10/2015, 11:38:09 AM
    Author     : SOMA_LUNA    
    empresa    : SOMA

--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@page import="java.util.List"%>
<%@page import="soma.msw.form.CuentahabienteOrdenanteForm"%>
<%@page import="modelo.general.CuentahabienteSucursalModelo"%>


<%  List lchms = CuentahabienteOrdenanteForm.lOrdenante();%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/reportes/reporteCuentaCLABE.js" type="text/javascript"></script>

<div class="panel panel-default panelPantalla">
    <div class="panel-heading ">
        <div class="row tituloPantalla">
            <h2> GENERAR REPORTE CUENTA CLABE</h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-10">
                        <label>
                            Nombre del Ordenante Seleccionado:
                        </label>
                    </div>
                    <div class="col-md-2">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-1">
                    </div>
                    <div class="col-md-5">
                        <input id="nombreO" 
                               name="nombreO" 
                               type="text" 
                               class="form-control" 
                               maxlength="50"
                               disabled="true"
                               />
                    </div>
                    <div class="col-md-6">
                        <form method="post" action="ReportesServlet"
                              target="_blank" onsubmit="return formulario(this)">
                            <div class="btn-group">
                                <button  class="btn btn-primary"
                                         id="imprimir"
                                         name="imprimir"
                                         >Imprimir
                                </button>
                                <input type="button" 
                                       class="btn btn-primary"
                                       id="limpiarCLABE"
                                       name="limpiarCLABE"
                                       value="Limpiar"
                                       >
                            </div>
                            <input type="hidden" id="identificador" 
                                   name="identificador">
                            <input type="hidden" id="accion" 
                                   name="accion" value="2"  
                                   >
                        </form>
                    </div>
<!--                    <div class="col-md-3">
                    </div>-->
                </div>
            </div>
            <div class="col-md-6">
            </div>
        </div>
        <br><br>
        <div class="row">
            <div class="col-md-12">
                <!--Comienza el div para el listado de los beneficiarios-->
                <div class="">
                    <div class="table-responsive ">
                        <!--<table class="table table-bordered table-hover">-->
                        <table id="tblRepOrdenante" 
                               class="display table-bordered table-hover" >
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nombre de Cliente</th>
                                    <th>Cuenta CLABE</th>
                                    <th>RFC ó CURP</th>
                                    <th>Numero Cliente</th>
                                    <th hidden="ordenanteId"></th>
                                    <th hidden="personaId"></th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>#</th>
                                    <th>Nombre de Cliente</th>
                                    <th>Cuenta CLABE</th>
                                    <th>RFC ó CURP</th>
                                    <th>Numero Cliente</th>
                                    <th hidden="ordenanteId"></th>
                                    <th hidden="personaId"></th>
                                </tr>
                            </tfoot>
                            <tbody>
                                <c:forEach var="chsm" items="<%=lchms%>">
                                    <tr>
                                        <td></td>
                                        <td>
                                            ${chsm.cuentaHabiente.nombre}
                                        </td>
                                        <td>
                                            ${chsm.numeroCuentahabiente}
                                        </td>
                                        <td>
                                            ${chsm.cuentaHabiente.rfc}
                                        </td>
                                        <td>
                                            ${chsm.identificador}
                                        </td>
                                        <td hidden="ordenanteId">
                                            ${chsm.id}
                                        </td>
                                        <td hidden="personaId">
                                            ${chsm.cuentaHabiente.personaId}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>