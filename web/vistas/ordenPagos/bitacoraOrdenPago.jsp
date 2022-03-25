<%-- 
    Author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : buscarOrdenPago
    Created on : 11/09/2015, 11:38:06 AM
    Author     : SOMA_LUNA
    empresa    : SOMA
    

--%>



<%
    String sucursalF = request.getSession().getAttribute("sucursal").toString();
    String usuarioId = request.getSession().getAttribute("usuarioId").toString();
%>
<input type="hidden" id="usuarioId" value="<%=usuarioId%>">
<input type="hidden" id="sucursalF" value="<%=sucursalF%>">


<link href="css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="librerias/moment.js" type="text/javascript"></script>
<script src="librerias/moment-with-locales.min.js" type="text/javascript"></script>
<script src="librerias/bootstrap-datetimepicker.js" type="text/javascript"></script>

<!--<script src="librerias/range_dates.js" type="text/javascript"></script>-->


<script src="js/ordenPagos/bitacoraOrdenPago.js" type="text/javascript"></script>


<div class="panel panel-default panelPantalla">
    <div class="panel-heading">
        <div class="row">
            <h2 class="tituloPantalla">Bitacora Ordenes de Pago</h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <!--<div class="checkbox col-xs-2">-->
                    <input type="checkbox" id="entrada">
                    <label>
                        Entrada
                    </label>
                    <!--</div>-->
                </div>
                <div class="col-md-2">
                    <label>
                        Part. Receptor: 
                    </label>
                    <select class="form-control" 
                            id="partReceptor">
                        <option value="0">--Seleccione uno--</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>
                        Folio Ins:
                    </label>
                    <input type="text" id="folioIns" class="form-control">
                </div>
                <div class="col-md-2">
                    <label>
                        Estado: 
                    </label>
                    <select class="form-control" 
                            id="estado">
                        <option value="0">--Seleccione uno--</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>Fecha Inicio:</label> 
                    <div class="form-group">
                        <div class="input-group date" id="datetimepicker6">
                            <input type="text" class="form-control" 
                                   id="fechaInicio" name="fechaInicio"
                                   />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar">
                                </span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="btn-toolbar" role="toolbar">
                        <button type="button" class="btn btn-success"
                                id="cargar">
                            <span class="glyphicon glyphicon-refresh">
                            </span> Cargar Bitacora
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <!--<div class="checkbox col-xs-2">-->
                    <input type="checkbox" id="salida">
                    <label>
                        Salida
                    </label>
                    <!--</div>-->
                </div>
                <div class="col-md-2">
                    <label>
                        Part. Emisor:
                    </label>
                    <select class="form-control" 
                            id="partEmisor">
                        <option value="0">--Seleccione uno--</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>
                        Folio Ord: 
                    </label>
                    <input type="text" id="folioOrd" class="form-control">
                </div>
                <div class="col-md-2">
                    <label>
                        Sucursal: 
                    </label>
                    <select class="form-control" 
                            id="sucursal">
                        <option value="0">--Seleccione uno--</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <label>Fecha Fin:</label>
                    <div class="form-group">
                        <div class="input-group date" id="datetimepicker7">
                            <input type="text" class="form-control" 
                                   id="fechaFin" name="fechaFin"
                                   />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar">
                                </span>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <label for="folioOrden">
                        Clave de Rastreo:
                    </label>
                    <input id="rastreo" 
                           name="rastreo" 
                           type="text" 
                           class="form-control" 
                           maxlength="50"
                           disabled
                           />

                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-2">
                    <label>
                        Cve. Rastreo: 
                    </label>
                    <input type="text" id="cveRastreo" class="form-control">
                </div>
                <div class="col-md-2">
                </div>
                <div class="col-md-2">
                    <label>
                        Tipo Pago: 
                    </label>
                    <select class="form-control" 
                            id="tipoPago">
                        <option value="0">--Seleccione uno--</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <form method="post" action="ReportesServlet"
                          target="_blank" onsubmit="return formulario(this)" >
                        <div class="btn-group">
                            <div class="row">
                                <div class="col-md-6">
                                    <br>
                                    <button  class="btn btn-primary"
                                             id="imprimir"
                                             name="imprimir"
                                             >
                                        <span 
                                            class="glyphicon glyphicon-print"
                                            >
                                        </span>
                                        Imprimir Comprobante
                                    </button>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="accion" 
                               name="accion" value="1"  
                               >
                        <input type="hidden" id="instruccionIdRep" 
                               name="instruccionIdRep" 
                               class="form-control" 
                               maxlength="50"
                               >
                    </form>
                </div>
                <div class="col-md-2">
                    <br>
                    <form method="post" action="ReportesServlet"
                          target="_blank" 
                          onsubmit="obtenerDatos();
                                  return formulario(this)">
                        <div class="btn-group">
                            <button  class="btn btn-primary"
                                     id="imprimir"
                                     name="imprimir"
                                     >
                                <span class="glyphicon glyphicon-print"></span>
                                Imprimir Consolidado
                            </button>
                        </div>
                        <input type="hidden" id="accion" 
                               name="accion" value="5">

                        <input type="hidden" id="entradaR" 
                               name="entradaR">
                        <input type="hidden" id="salidaR" 
                               name="salidaR">
                        <input type="hidden" id="partReceptorR" 
                               name="partReceptorR">
                        <input type="hidden" id="partEmisorR" 
                               name="partEmisorR">
                        <input type="hidden" id="folioInsR" 
                               name="folioInsR">
                        <input type="hidden" id="folioOrdR" 
                               name="folioOrdR">
                        <input type="hidden" id="cveRastreoR" 
                               name="cveRastreoR">
                        <input type="hidden" id="estadoR" 
                               name="estadoR">
                        <input type="hidden" id="sucursalR" 
                               name="sucursalR">
                        <input type="hidden" id="tipoPagoR" 
                               name="tipoPagoR">
                        <input type="hidden" id="fechaInicioR" 
                               name="fechaInicioR">
                        <input type="hidden" id="fechaFinR" 
                               name="fechaFinR">
                    </form>
                </div>
            </div>
        </div>
        <br>

        <!--Inicia el div del listado de las ordenes-->
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <input type="hidden" id="instruccionId"  disabled="true">
                    <table id="tblBitacoraOrdenPago" 
                           class="display table-bordered 
                           table-hover" 
                           >
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Tipo Pago</th>
                                <th>Folio Orden</th>
                                <th>Folio Instrucción</th>
                                <th>Clave de Rastreo</th>
                                <th>Prioridad</th>
                                <th>Topología</th>
                                <th>Monto</th>
                                <th>IVA</th>
                                <th>Estado</th>
                                <th>Nombre Cliente</th>
                                <th>Tipo Cuenta Cliente</th>
                                <th>Cuenta Cliente</th>
                                <th>RFC Cliente</th>
                                <th>Nombre Cliente</th>
                                <th>Tipo Cuenta Beneficiario</th>
                                <th>Cuenta Cliente</th>
                                <th>RFC Beneficiario</th>
                                <th>Nombre Cliente 2</th>
                                <th>Tipo Cuenta 2</th>
                                <th>Cuenta Cliente 2</th>
                                <th>RFC Beneficiario 2</th>
                                <th>Concepto 2</th>
                                <th>Concepto</th>
                                <th>Referencia Numérica</th>
                                <th>Referencia de Cobranza</th>
                                <th>Tipo Operación</th>
                                <th>Causa Devolución</th>
                                <th>Clave Rastreo Devolución</th>
                                <th>Clave Participante Emisor</th>
                                <th>Clave Participante Receptor</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Entrada</th>
                                <th>Firma Electrónica</th>
                                <th>Instrucción ID</th>
                                <th>Orden ID</th>
                                <th>entidad</th>
                                <th>orden original</th>
                                <th>Nombre Sucursal </th>
                                <th>nombre</th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <th>#</th>
                                <th>Tipo Pago</th>
                                <th>Folio Orden</th>
                                <th>Folio Instrucción</th>
                                <th>Clave de Rastreo</th>
                                <th>Prioridad</th>
                                <th>Topología</th>
                                <th>Monto</th>
                                <th>IVA</th>
                                <th>Estado</th>
                                <th>Nombre Cliente</th>
                                <th>Tipo Cuenta Cliente</th>
                                <th>Cuenta Cliente</th>
                                <th>RFC Cliente</th>
                                <th>Nombre Cliente</th>
                                <th>Tipo Cuenta Beneficiario</th>
                                <th>Cuenta Cliente</th>
                                <th>RFC Beneficiario</th>
                                <th>Nombre Cliente 2</th>
                                <th>Tipo Cuenta 2</th>
                                <th>Cuenta Cliente 2</th>
                                <th>RFC Beneficiario 2</th>
                                <th>Concepto2</th>
                                <th>Concepto</th>
                                <th>Referencia Numérica</th>
                                <th>Referencia de Cobranza</th>
                                <th>Tipo Operación</th>
                                <th>Causa Devolución</th>
                                <th>Clave Rastreo Devolución</th>
                                <th>Clave Participante Emisor</th>
                                <th>Clave Participante Receptor</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Entrada</th>
                                <th>Firma Electrónica</th>
                                <th>Instrucción ID</th>
                                <th>Orden ID</th>
                                <th>entidad</th>
                                <th>orden original</th>
                                <th>Nombre Sucursal </th>
                                <th>nombre</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<!--Parte del codigo encargada de mostrar los estatus de la orden de pago-->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Modal Header</h4>
            </div>
            <div class="modal-body">

                <div class="table-responsive">
                    <div class="container">
                        <table id="tblDetalleOrden" class="display table-bordered table-hover" >
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Estado</th>
                                    <th>Fecha y Hora</th>
                                    <th>Usuario</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>#</th>
                                    <th>Estado</th>
                                    <th>Fecha y Hora</th>
                                    <th>Usuario</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
