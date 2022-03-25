<%-- 
    author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : agregarOrdenPago
    Created on : 29/07/2015, 05:17:58 PM
    Author     : LUNA
    empresa    : SOMA
--%>

<%
    String usuario = request.getSession().getAttribute("usuario").toString();
%>

<input type="hidden" id="usuario" value="<%=usuario%>">
<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/ordenPagos/ordenPago.js" type="text/javascript"></script>

<div class="panel panel-default panelPantalla">
    <div class="panel-heading">
        <div class="row">
            <h2 class="tituloPantalla"> ORDEN DE PAGO </h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="formOrden">
            <div class="row">
                <div class="col-md-4 rastreo">
                    <%
                        String sucursal = "";
                        if (request.getSession().getAttribute("sucursal") != null) {
                            sucursal = request.getSession()
                                    .getAttribute("sucursal").toString();
                        }
                        String entidad = "";
                        if (request.getSession().getAttribute("entidad") != null) {
                            entidad = request.getSession()
                                    .getAttribute("entidad").toString();
                        }
                    %>
                    <input type="hidden" id="entidad" name="entidad" 
                           value="<%=entidad%>">
                    <input type="hidden" id="sucursal" name="sucursal" 
                           value="<%=sucursal%>">

                    <label class="etiquetaImportante">
                        Número de Rastreo:
                    </label>
                    <input type="text" 
                           id="numeroRastreo" 
                           name="numeroRastreo"
                           class="form-control"
                           disabled="true"
                           >
                    <input type="hidden" id="accion" name="accion" 
                           type="text" class="form-control" 
                           disabled="true"
                           />
                    <input type="hidden" id="instruccionId" name="instruccionId" 
                           type="text" class="form-control" 
                           disabled="true"
                           />
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default subpanelPantalla">
                        <div class="panel-heading">
                            <div class="row">
                                <h4 class="subtituloPantalla">
                                    INFORMACIÓN DEL CUENTAHABIENTE ORDENANTE
                                </h4>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <button type="button"
                                        class="btn btn-link botonAgregar"
                                        data-toggle="modal" 
                                        data-target="#myModal">
                                    <span class="glyphicon glyphicon-plus" ></span> 
                                    <b><u>Seleccionar C.Ordenante</u></b>
                                </button>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="nombreOrdenante">
                                        Nombre Ordenante:
                                    </label>
                                    <input type="hidden" id="ordenanteId" 
                                           name="ordenanteId"
                                           disabled="true"
                                           />
                                    <input id="nombreOrdenante" 
                                           name="nombreOrdenante" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="50"
                                           disabled="true"
                                           />
                                </div>
                                <div class="col-md-6">
                                    <label for="identificador">
                                        Número del Cliente:
                                    </label>

                                    <input id="identificador" 
                                           name="identificador" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="50"
                                           disabled="true"
                                           />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <label for="cuentaOrdenante">
                                        Cuenta CLABE:
                                    </label>
                                    <input id="cuentaOrdenante" name="cuentaOrdenante" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="50"
                                           disabled="true"
                                           />
                                </div>
                                <div class="col-md-6">
                                    <label for="rfcOrdenante">
                                        RFC ó CURP:
                                    </label>
                                    <input id="rfcOrdenante" name="rfcOrdenante" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="18"
                                           required 
                                           disabled="true"
                                           />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default subpanelPantalla">
                        <div class="panel-heading">
                            <div class="row">
                                <h4 class="subtituloPantalla">
                                    INFORMACIÓN DEL CUENTAHABIENTE BENEFICIARIO
                                </h4>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <button type="button" 
                                        class="btn btn-link botonAgregar" 
                                        data-toggle="modal" 
                                        data-target="#myModal2">
                                    <span class="glyphicon glyphicon-plus" ></span> 
                                    <b><u>Seleccionar C.Beneficiario</u></b>
                                </button>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="rfc">
                                        Nombre Beneficiario:
                                    </label>
                                    <input type="hidden" id="beneficiarioId" 
                                           name="beneficiarioId"
                                           disabled="true"
                                           />
                                    <input id="nombreBeneficiario" 
                                           name="nombreBeneficiario" 
                                           type="text" 
                                           class="form-control" 
                                           disabled="true"
                                           />
                                </div>
                                <div class="col-md-6">
                                    <label for="participante" >
                                        Institución:
                                    </label>
                                    <input id="part" 
                                           name="part" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="16"
                                           disabled="true"
                                           />
                                    <input id="clave" name="clave" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="16"
                                           disabled="true"
                                           />
                                    <input type="hidden" id="idOrden" 
                                           name="idOrden"
                                           disabled="true"
                                           />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="cuentaBeneficiario">
                                        Número Cuenta:
                                    </label>
                                    <input id="cuentaBeneficiario" 
                                           name="cuentaBeneficiario" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="16"
                                           disabled="true"
                                           />
                                </div>
                                <div class="col-md-6">
                                    <label for="rfcBeneficiario">RFC ó CURP:
                                    </label>
                                    <input id="rfcBeneficiario" 
                                           name="rfcBeneficiario" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="18"
                                           disabled="true"
                                           />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-default subpanelPantalla">
                        <div class="panel-heading">
                            <div class="row">
                                <h4 class="subtituloPantalla">
                                    CONCEPTO/REFERENCIAS    
                                </h4>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="referenciaNum">
                                        Referencia Numérica:
                                    </label>
                                    <input id="referenciaNum" name="referenciaNum" 
                                           type="text" 
                                           class="form-control" 
                                           onkeypress="return validaSoloNumeros(event)"
                                           maxlength="7"
                                           />
                                </div>
                                <div class="col-md-6">
                                    <label for="referenciaCo">
                                        Referencia Cobranza:
                                    </label>
                                    <input id="referenciaCo" 
                                           name="referenciaCo" 
                                           type="text" 
                                           class="form-control" 
                                           onkeypress="return permite(event, 'num_car')"
                                           maxlength="40"
                                           />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6"> 
                                </div>
                                <div class="col-md-6">
                                    <label for="concepto">
                                        Concepto: 
                                    </label>
                                    <input id="concepto" name="concepto" 
                                           type="text" 
                                           class="form-control" 
                                           onkeypress="return permite(event, 'num_car')"
                                           maxlength="40"
                                           required 
                                           />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default subpanelPantalla">
                        <div class="panel-heading">
                            <div class="row">
                                <h4 class="subtituloPantalla">
                                    INFORMACIÓN DE IMPORTE DE LA ORDEN
                                </h4>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="importe">
                                        Importe de Orden: 
                                    </label>
                                    <input id="importe" 
                                           name="importe" 
                                           type="text" 
                                           onchange="calcularIva();"
                                           class="form-control" 
                                           maxlength="19"
                                           onkeypress="return validaSoloNumeros(event)"
                                           required 
                                           >
                                </div>
                                <div class="col-md-6">
                                    <label for="iva">
                                        IVA de Importe: 
                                    </label>
                                    <input id="iva" 
                                           name="iva" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="19"
                                           disabled="true"
                                           >
                                </div>                                        
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                </div>
                                <div class="col-md-6">
                                    <label for="importeTotal">
                                        Importe Total: 
                                    </label>
                                    <input id="importeTotal" 
                                           name="importeTotal" 
                                           type="text" 
                                           class="form-control" 
                                           maxlength="19"
                                           max="19"
                                           disabled="true"
                                           >
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="buttons">
                <button type="button" 
                        class="btn btn-primary" 
                        onclick="limpiarDatosOp();"
                        >
                    LIMPIAR
                </button>
                <button id="submit" name="submit"
                        class="btn btn-primary" 
                        >
                    AGREGAR
                </button>
            </div>
            <!-- Apartado del Modal para seleccionar a un Cuentahabiente 
            Ordenante-->
            <div class="modal fade" id="myModal" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" 
                                    data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">CUENTAHABIENTE ORDENANTE</h4>
                        </div>
                        <div class="modal-body">
                            <div class="table-responsive ">
                                <table id="tblOrdenante" class="display compact" >
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre</th>
                                            <th>Cuenta CLABE</th>
                                            <th>RFC ó CURP</th>
                                            <th>Número Cliente</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre</th>
                                            <th>Cuenta CLABE</th>
                                            <th>RFC ó CURP</th>
                                            <th>Numero Cliente</th>
                                            <th>Estado</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn 
                                    btn-default" data-dismiss="modal">
                                Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Apartado del Modal para seleccionar a un Cuentahabiente 
           Beneficiario-->
            <div class="modal fade" id="myModal2" role="dialog">
                <div class="modal-dialog">
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" 
                                    data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">CUENTAHABIENTE BENIFICIARIO</h4>
                        </div>
                        <div class="modal-body">
                            <!--Comienza el div para el listado de los beneficiarios-->
                            <div class="table-responsive ">
                                <table id="tblBeneficiario" class="display compact" >
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre de Cliente</th>
                                            <th>No. Cuenta</th>
                                            <th>RFC ó CURP</th>
                                            <th>Tipo Cuenta</th>
                                            <th>Participante</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre de Cliente</th>
                                            <th>No. Cuenta</th>
                                            <th>RFC ó CURP</th>
                                            <th>Tipo Cuenta</th>
                                            <th>Participante</th>
                                            <th>Estado</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn 
                                    btn-default" data-dismiss="modal">
                                Cerrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
