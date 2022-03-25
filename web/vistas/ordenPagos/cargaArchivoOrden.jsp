<%-- 
    Author     : Victor Hugo Luna Cortes
    Correo     : hugo.luna@somatecnologia.com.mx
    Document   : cargaArchivoOrden
    Created on : 20/01/2016, 05:18:19 PM
    Author     : Luna
    Empresa    : SOMA
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>
<!--<script src="librerias/dataTablesTool/dataTables.tableTools.js" type="text/javascript"></script>-->
<link href="css/jquery-confirm.css" rel="stylesheet" type="text/css"/>
<a href="css/jquery-confirm.less"></a>
<script src="librerias/jquery-confirm.js" type="text/javascript"></script>

<script src="js/ordenPagos/cargaArchivoOrden.js" type="text/javascript"></script>
<script src="librerias/jquery.csv-0.71.min.js" type="text/javascript"></script>

<div class="panel panel-default panelPantalla">
    <div class="panel-heading">
        <div class="row">
            <h2 class="tituloPantalla">Cargar Ordenes de Pago</h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-4">
                        <label for="correctos">No. Pagos Correctos:
                        </label> 
                        <input type="text" id="correctos" size="5" disabled="true"/>
                        <span class="label label-success">
                            <span class="glyphicon glyphicon-ok"></span> 
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <label for="incorrectos">No. Pagos incorrectos:
                        </label> 
                        <input type="text" id="incorrectos"  size="5" disabled="true"/>
                        <span class="label label-danger">
                            <span class="glyphicon glyphicon-remove"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="btn-group">
                    <span class="btn btn-primary btn-file">
                        <span class="glyphicon glyphicon-cloud-upload"></span>
                        Subir Archivo&hellip; 
                        <input type="file" id="archivo" name="archivo">
                    </span>
                    <a href="#" class="btn btn-primary" id="insertar"
                       name="insertar">
                        <span class="glyphicon glyphicon-save"></span>
                        Insertar
                    </a>
                </div>
            </div>
        </div>
        <br><br>
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <table id="tblCargarArchivoOrden" 
                           class="display table-bordered table-hover" >
                        <thead>
                            <tr>
                                <th></th>
                                <th>Estatus</th>
                                <th>Nombre Ordenante</th>
                                <th>Cuenta Ordenante</th>
                                <th>RFC o CURP Ordenante</th>
                                <th>Clave Part Ordante</th>
                                <th>Nombre Beneficiario</th>
                                <th>Cuenta Beneficiario</th>
                                <th>RFC o CURP Beneficiario</th>
                                <th>Clave Part Beneficiario</th>
                                <th>Monto</th>
                                <th>Clave de Rastreo</th>
                                <th>Concepto</th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>Estatus</th>
                                <th>Nombre Ordenante</th>
                                <th>Cuenta Ordenante</th>
                                <th>RFC o CURP Ordenante</th>
                                <th>Clavedd Part Ordante</th>
                                <th>Nombre Beneficiario</th>
                                <th>Cuenta Beneficiario</th>
                                <th>RFC o CURP Beneficiario</th>
                                <th>Clave Part Beneficiario</th>
                                <th>Monto</th>
                                <th>Clave de Rastreo</th>
                                <th>Concepto</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
