<%-- 
    Author     : Victor Hugo Luna Cortes}
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : numeroPagos
    Created on : 6/10/2015, 06:28:01 PM
    Author     : SOMA_LUNA    
    empresa    : SOMA

--%>
<link href="css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>
<script src="librerias/moment.js" type="text/javascript"></script>
<script src="librerias/moment-with-locales.min.js" type="text/javascript"></script>
<script src="librerias/bootstrap-datetimepicker.js" type="text/javascript"></script>


<script src="js/reportes/numeroPagos.js" type="text/javascript"></script>


<div class="row">
    <div class="col-md-3">
    </div>
    <div class="col-md-6">
        <div class="panel panel-default panelPantalla">
            <div class="panel-heading">
                <div class="row tituloPantalla">
                    <h2>REPORTE NUEMRO DE PAGOS</h2> 
                </div>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6">
                        <label> Tipo de Reporte: </label>
                    </div>
                    <div class="col-md-6">
                        <select id="tipo" name="tipo" 
                                class="form-control"
                                onchange="selectType();"
                                >
                            <option value=0>--Elige opcion--</option>
                            <option value=1>Rango de fechas</option>
                            <option value=2>Mes/Año</option>
                        </select>


                    </div>
                </div>

                <br>                <br>

                <div id="rango" name="rango" >
                    <form  method="post" action="ReportesServlet"
                           target="_blank"  onsubmit="">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Fecha Inicio:</label>
                            </div>
                            <div class="col-md-6">
                                <label>Fecha Fin:</label>
                            </div>
                        </div>

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
                            <div class="col-md-12">

                                <div class="btn-group">
                                    <button  class="btn btn-primary"
                                             id="imprimir"
                                             name="imprimir"
                                             >Imprimir
                                    </button>
                                    <input type="button"  class="btn btn-primary"
                                           id="limpiarMes"
                                           name="limpiarMes"
                                           value="Limpiar"
                                           >
                                </div>
                                <input type="hidden" id="accion" 
                                       name="accion" value="4"  
                                       >
                            </div>
                        </div>
                    </form>
                </div>
                <div id="mesAnio" name="mesAnio">
                    <form  method="post" action="ReportesServlet"
                           target="_blank"  onsubmit="return formulario(this)">
                        <div class="row">
                            <div class="col-lg-6">
                                <label for="mes">
                                    Selecciona Mes:
                                </label>
                            </div>
                            <div class="col-lg-6">
                                <label for="anio">
                                    Selecciona Año
                                </label>
                            </div>                      
                        </div>
                        <div class="row">
                            <div class="col-sm-6" style="height:100px;">
                                <div class="form-group">
                                    <div class='input-group date' id='datetimepicker10'>
                                        <input type='text' class="form-control" 
                                               id="mes"
                                               name="mes"
                                               />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar">
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6" style="height:100px;">
                                <div class="form-group">
                                    <div class='input-group date' id='datetimepicker11'>
                                        <input type='text' class="form-control"
                                               id="anio"
                                               name="anio"
                                               />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar">
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="btn-group">
                                    <button  class="btn btn-primary"
                                             id="imprimir"
                                             name="imprimir"
                                             >Imprimir
                                    </button>
                                    <input type="button"  class="btn btn-primary"
                                           id="limpiarRango"
                                           name="limpiarRango"
                                           value="Limpiar"
                                           >
                                </div>
                                <input type="hidden" id="accion" 
                                       name="accion" value="3"  
                                       >
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
    </div>
</div>