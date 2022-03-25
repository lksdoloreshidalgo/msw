<%-- 
    Author     : Victor Hugo Luna Cortes
    Correo     : hugo.luna@somatecnologia.com.mx
    Document   : estadoCuenta
    Created on : 6/04/2016, 11:59:41 AM
    Author     : Luna
    Empresa    : SOMA
--%>
<link href="css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="librerias/moment.js" type="text/javascript"></script>
<script src="librerias/moment-with-locales.min.js" type="text/javascript"></script>
<script src="librerias/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script src="js/reportes/estadoCuenta.js" type="text/javascript"></script>

<div class="row">
    <div class="col-md-3">
    </div>
    <div class="col-md-6">
        <div class="panel panel-default panelPantalla">
            <div class="panel-heading">
                <div class="row tituloPantalla">
                    <h2>REPORTE ESTADO DE CUENTA</h2> 
                </div>
            </div>
            <div class="panel-body">
                <form  method="post" action="ReportesServlet"
                       target="_blank"  onsubmit="return formulario(this)">
                    <div class="row">
                        <div class="col-md-6">
                            <label>Fecha Inicio:</label>
                        </div>
                        <div class="col-md-6">
                            <label>Fecha Fin:</label>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class='col-md-6'>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker6'>
                                    <input type='text' class="form-control" 
                                           id="fechaInicio" name="fechaInicio"
                                           />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class='col-md-6'>
                            <div class="form-group">
                                <div class='input-group date' id='datetimepicker7'>
                                    <input type='text' class="form-control" 
                                           id="fechaFin" name="fechaFin"
                                           />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-3">
                            <div class="btn-group">
                                <button  class="btn btn-primary"
                                         id="imprimir"
                                         name="imprimir"
                                         >
                                    <span class="glyphicon glyphicon-print">
                                    </span>
                                    Imprimir
                                </button>
                            </div>
                            <input type="hidden" id="accion" 
                                   name="accion" value="6"  
                                   >
                        </div>
                        <div class="col-md-5">
                            <div class="btn-group">
                                <button type="button" class="btn btn-success"
                                        id="limpiarMes" name="limpiarMes"
                                        > 
                                    <span class="glyphicon glyphicon-trash">
                                    </span>
                                    Limpiar
                                </button>
                            </div>
                        </div>
                        <div class="col-md-1">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-3">
    </div>
</div>
