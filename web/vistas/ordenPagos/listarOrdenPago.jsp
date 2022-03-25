<%-- 
    author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : listarOrdenPago
    Created on : 12/08/2015, 10:40:57 AM
    Author     : LUNA
    empresa    : SOMA
--%>

<%
    String sucursalF = request.getSession().getAttribute("sucursal").toString();
    String usuarioId = request.getSession().getAttribute("usuarioId").toString();
    String usuario = request.getSession().getAttribute("usuario").toString();
%>

<input type="hidden" id="usuarioId" value="<%=usuarioId%>">
<input type="hidden" id="sucursalF" value="<%=sucursalF%>">
<input type="hidden" id="usuario" value="<%=usuario%>">

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<link href="css/jquery-confirm.css" rel="stylesheet" type="text/css"/>
<a href="css/jquery-confirm.less"></a>
<script src="librerias/jquery-confirm.js" type="text/javascript"></script>

<script src="js/ordenPagos/listOrdenPago.js" type="text/javascript"></script>

<div class="panel panel-default panelPantalla">
    <div class="panel-heading ">
        <div class="row tituloPantalla">
            <h1> Listado de Ordenes de Pago</h1>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-6">

                <div class="col-md-1">
                </div>
                <div class="col-md-5">
                    <label for="folioOrden">
                        Clave de Rastreo Seleccionada:
                    </label>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-md-12">
                            <input id="claveRastreo" 
                                   name="claveRastreo" 
                                   type="text" 
                                   class="form-control" 
                                   maxlength="50"
                                   disabled="true"
                                   />
                        </div>
                        <br>                    <br>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="btn-group ">
                    <a href="#" class="btn btn-primary" id="enviar"
                       name="enviar">Enviar</a>
                    <a href="#" class="btn btn-primary" id="actualizar"
                       name="actualizar">Actualizar</a>
                    <a href="#" class="btn btn-primary" id="declinar"
                       name="declinar">Declinar</a>
                    <a href="#" class="btn btn-primary" id="cancelar"
                       name="cancelar">Cancelar</a>
                    <a href="#" class="btn btn-primary" id="devolver"
                       name="devolver">Devolver</a>
                    <a href="#" class="btn btn-primary" id="abonar"
                       name="abonar">Abonar Pago</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="col-md-6">
                </div>
                <div class="col-md-6">
                    <div class="col-md-6">
                        <form method="post" action="ReportesServlet"
                              target="_blank" onsubmit="return formulario(this)" >
                            <div class="btn-group">
                                <div class="row">
                                    <div class="col-md-6">
                                        <button  class="btn btn-primary"
                                                 id="imprimir"
                                                 name="imprimir"
                                                 >
                                            <span 
                                                class="glyphicon glyphicon-print"
                                                >
                                            </span>
                                            Imprimir
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
                    <div class="col-md-6">
                        <button  class="btn btn-primary"
                                 id="limpiar"
                                 name="limpiar"
                                 >
                            <span class="glyphicon glyphicon-trash">
                            </span>
                            Limpiar
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-lg-12">

                <!--Comienza el div para el listado de los beneficiarios-->
                <div class="table-responsive ">
                    <div>

                        <input type="hidden" id="saldo"  disabled="true">
                        <input type="hidden" id="instruccionId"  disabled="true">
                        <input type="hidden" id="ordenId"  disabled="true">

                        <input type="hidden" id="estado"  disabled="true"/>
                        <!--atrubutos encargados para la cancelacion de la 
                        orden de pago-->
                        <input type="hidden" id="folioInstruccion"  disabled="true"/>
                        <input type="hidden" id="folioOrden"  disabled="true"/>

                        <input type="hidden" id="fecha"  disabled="true"/>
                        <!--atrubutos encargado para la devolucion de la 
                       orden de pago-->
                        <input type="hidden" id="entrada"  disabled="true"/>
                        <input type="hidden" id="clavePartOrd"  disabled="true"/> 
                        <input type="hidden" id="claveCausaDev"  disabled="true"/>



                    </div>
                    <table id="tblOrdenPago" class="display compact" >
                        <thead>
                            <tr>
                                <th>#</th>
                                <th><input name="select_all" value="1" type="checkbox"></th>
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
                                <th  data-orderable="false"></th>
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
                <h4 class="modal-title">Estados del Pago</h4>
            </div>
            <div class="modal-body">

                <div class="table-responsive">
                    <div class="container">
                        <table id="tblDetalleOrden" class="display compact" >
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
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="modalInfo" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Informacion del Pago</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-3">
                        <label for="folioOrden">
                            No. Pagos:
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label id="noPagos">test</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <label for="noPaquetes">
                            No. Paquetes
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label id="noPaquetes">test</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <label for="montoTotal">
                            Monto Total:
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label id="montoTotal">test</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <label for="saldoActual">
                            Saldo Actual:
                        </label>
                    </div>
                    <div class="col-md-3">
                        <label id="saldoActual">test</label>
                    </div>
                </div>

                <div class="table-responsive">
                    <div class="container">

                        <table id="tblInfo" class="display compact">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Clave</th>
                                    <th>Descripción</th>
                                    <th>No Ordenes</th>
                                    <th>Monto Total</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>#</th>
                                    <th>Clave</th>
                                    <th>Descripción</th>
                                    <th>No Ordenes</th>
                                    <th>Monto Total</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
                <!--</div>-->
            </div>
            <div class="modal-footer">

                <button type="button" class="btn btn-primary" id="enviarOrden">
                    <span class="glyphicon glyphicon-save"></span>
                    Enviar
                </button>
                <button type="button" class="btn btn-danger" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove"></span>
                    Cerrar
                </button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="modalDevolucion" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Causa Devolucion</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <label for="saldoActual">
                            Selecciona una Causa Devolucion:
                        </label>
                    </div>
                    <div class="col-md-6">
                        <select class="form-control" id="causaDev">
                            <option value="0">Seleccione uno...</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-primary" id="aceptar">Aceptar</button>
            </div>
        </div>
    </div>
</div>
