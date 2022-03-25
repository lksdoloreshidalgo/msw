<%-- 
    author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : participante
    Created on : 23/07/2015, 05:15:05 PM
    Author     : LUNA
    empresa    : SOMA
--%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/catalogos/participante.js" type="text/javascript"></script>

<div class="panel panel-default panelPantalla">
    <div class="panel-heading">
        <div class="row">
            <h2 class="tituloPantalla"> PARTICIPANTES </h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-12">
                <div class="table-responsive">
                    <div class="container">
                        <table id="tblParticipantes" class="display table-bordered table-hover" >
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Clave</th>
                                    <th>Descripción</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>#</th>
                                    <th>Clave</th>
                                    <th>Descripción</th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
