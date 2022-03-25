<%-- 
    author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : agregarBeneficiario
    Created on : 24/07/2015, 09:34:08 AM
    Author     : LUNA
    empresa    : SOMA
--%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/cuentahabiente/agregarBeneficiario.js" type="text/javascript"></script>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default panelPantalla">
            <div class="panel-heading ">
                <div class="row tituloPantalla">
                    <h2> SECCIÓN DE CUENTAHABIENTE BENEFICIARIO</h2>
                </div>
            </div>
            <div class="panel-body">
                <!--Se supone que aqui devera de ir el estilo de mi formulario sab-->
                <div class="row">
                    <div class="col-md-5">
                        <br> <br>
                        <div class="panel panel-default">
                            <div class="panel panel-heading subpanelPantalla">
                                <div class="row subtituloPantalla">
                                    <h6>ADMINISTRAR C.BENEFICIARIO</h6>
                                </div>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="hidden" id="personaId" 
                                               name="personaId"
                                               disabled="true"/>
                                        <input type="hidden" id="accion" 
                                               name="accion"
                                               disabled="true"/>
                                        <label>Nombre:</label>
                                    </div>
                                    <div class="col-md-6">
                                        <input id="nombre" name="nombre" 
                                               type="text" class="form-control" 
                                               onkeyup="javascript:this.value = this.value.toUpperCase();" 
                                               required/>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>RFC:</label>
                                    </div>
                                    <div class="col-md-6">
                                        <input id="rfc" name="rfc" type="text" 
                                               class="form-control" maxlength="18"
                                               onkeyup="javascript:this.value = this.value.toUpperCase();"
                                               /> 
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Activo: 
                                        </label>
                                        <input type="checkbox" id="activo" 
                                               name="activo">
                                    </div>
                                    <div class="col-md-6">
                                        <button  type="button" class="btn btn-info btn-link" 
                                                 data-toggle="modal" 
                                                 data-target="#myModal" onclick="iniTablaParticipante();">
                                            <span class="glyphicon glyphicon-plus" ></span>Agregar Cuenta
                                        </button>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-4">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-primary" 
                                                onclick="limpiarBene()"
                                                >Limpiar</button>
                                    </div>
                                    <div class="col-md-2">
                                        <button id="submit" name="submit" 
                                                class="btn btn-primary" 
                                                >
                                            <span class="ui-button-text"> Guardar</span>
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-7">
                        <div class="table-responsive">
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
                </div>
                <div class="modal fade" id="myModal" role="dialog">
                    <div class="modal-dialog">
                        <!-- Modal content-->
                        <div class="modal-content">

                            <div class="modal-header">
                                <button type="button" class="close" 
                                        data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Agregar Cuenta a un Cuentahabiente</h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <br><br>
                                        <div class="panel panel-default subpanelPantalla">
                                            <div class="panel panel-heading subtituloPantalla">
                                                <div class="row subtituloPantalla">
                                                    <h6>AGREGAR PARTICIPANTE</h6>
                                                </div>
                                            </div>
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label for="tipoCuenta">
                                                            Tipo Cuenta:
                                                        </label>
                                                        <input type="hidden" id="cuentaId" 
                                                               name="cuentaId"
                                                               disabled="true"/>
                                                    </div>
                                                    <div class="col-md-7">
                                                        <select class="form-control" 
                                                                id="tipoCuenta">
                                                            <option value="0">--Seleccione uno--</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label for="descripcion">
                                                            Entidad:
                                                        </label>
                                                    </div>
                                                    <div class="col-md-7">
                                                        <input id="descripcion" 
                                                               name="descripcion" 
                                                               type="text"
                                                               disabled="true"
                                                               class="form-control" 
                                                               maxlength="50"
                                                               />
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label for="clavePart">
                                                            Clave:
                                                        </label>
                                                    </div>
                                                    <div class="col-md-7">
                                                        <input id="clavePart" 
                                                               name="clavePart" 
                                                               type="number"
                                                               disabled="true"
                                                               class="form-control" 
                                                               maxlength="50"
                                                               />
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-5">
                                                        <label for=numero">Numero
                                                        </label>
                                                    </div>
                                                    <div class="col-md-7">
                                                        <input id="numero" 
                                                               name="numero"
                                                               type="text"
                                                               maxlength="18"
                                                               class="form-control"   
                                                               onkeypress="return validaSoloNumeros(event)"
                                                               required 
                                                               />
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <div class="col-md-4">

                                                    </div>

                                                    <div class="col-md-2">
                                                        <button type="button" 
                                                                class="btn btn-primary" 
                                                                onclick="limpiarCuenta()"
                                                                >Limpiar</button>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <button 
                                                            id="saveCuenta"
                                                            name="saveCuenta"
                                                            class="btn btn-primary" 
                                                            >
                                                            <span class="ui-button-text"> Guardar</span>
                                                        </button>
                                                    </div>
                                                    <div class="col-md-4">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="table-responsive">
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
                            <div class="modal-footer">
                                <button type="button" class="btn 
                                        btn-default" data-dismiss="modal">
                                    Close</button>
                            </div>                           
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
