<%-- 
    Document   : registrarUsuario
    Created on : 27/07/2015, 09:47:44 AM
    Author     : Estadias8
--%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/acceso/registrarUsuario.js" type="text/javascript"></script>


<div class="row">
    <div class="col-md-5 panelPantalla">
        <div class="row">
            <h2 class="tituloPantalla">REGISTRAR USUARIO</h2>
        </div>
        <br>
        <div class="row">
            <div class="col-md-6">
                <label>Nombre de Usuario:</label>
            </div>
            <div class="col-md-6">
                <input type="text" name="txtNombreU" id="txtNombreU" class="form-control">
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md-6">
                <label>Usuario (NickName):</label>
            </div>
            <div class="col-md-6">
                <input type="text" name="txtNickname" id="txtNickname" class="form-control">
            </div>
        </div>        <br>

        <div class="row">
            <div class="col-md-6">
                <label>Contraseña:</label>
            </div>
            <div class="col-md-6">
                <input type="password" name="txtContrasenia" id="txtContrasenia" class="form-control">
            </div>
        </div>        <br>

        <div class="row">
            <div class="col-md-6">
                <label>Sucursal:</label>
            </div>
            <div class="col-md-6">
                <select id="sucursal" 
                        required
                        class="form-control"
                        >
                    <option selected 
                            value="">
                        --Seleccione una opcion--
                    </option>
                </select> 
            </div>
        </div>  <br>
        <div class="row">
            <div class="col-md-6">
                <label>Rol:</label>
            </div>
            <div class="col-md-6">
                <select id="rol" 
                        required
                        class="form-control"
                        >
                    <option selected 
                            value="">
                        --Seleccione una opcion--
                    </option>
                </select> 
            </div>
        </div>
        <input type="hidden" name="accion" id="accion">
        <input type="hidden" name="usuarioId" id="usuarioId">
        <br/>
        <button id="submit" name="submit" 
                class="btn btn-default btn-primary" 
                >
            <span class="ui-button-text">Crear Cuenta</span>
        </button>
        <button id="clear" name="clear" class="btn btn-danger" 
                onclick="limpiarRegistro()"
                >
            <span class="ui-button-text">Cancelar</span>
        </button>
        <button id="update" name="update" class="btn btn-success" disabled="true"
                >
            <span class="ui-button-text">Cambiar contraseña</span>
        </button>
    </div>
    <div  class="col-md-7 ">
        <div class="table-responsive">
            <table id="tablaUsuarios" class="display compact" >
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Nombre del Usuario</th>       
                        <th>Usuario</th>
                        <th>Sucursal</th>
                        <th>Rol</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th>#</th>
                        <th>Nombre del Usuario</th>       
                        <th>Usuario</th>
                        <th>Sucursal</th>
                        <th>Rol</th>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="modalPass" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Cambiar Contraseña</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <label for="saldoActual">
                            Contraseña:
                        </label>
                    </div>
                    <div class="col-md-6">
                        <input type="password" id="pass" name="pass" 
                               class="form-control">
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-6">
                        <label for="saldoActual">
                            Confirmar Contraseña:
                        </label>
                    </div>
                    <div class="col-md-6">
                        <input type="password" id="pass2" name="pass2" 
                               class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                <button class="btn btn-primary" id="actualizarPass">Actualizar</button>
            </div>
        </div>
    </div>
</div>
