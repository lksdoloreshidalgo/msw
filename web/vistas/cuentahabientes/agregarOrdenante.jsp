<%-- 
    author     : Victor Hugo Luna Cortes
    correo     : hugo.luna@somatecnologia.com.mx
    Document   : agregarOrdenante
    Created on : 31/07/2015, 10:17:01 AM
    Author     : LUNA
    empresa    : SOMA
--%>

<script src="librerias/dataTable/jquery.dataTables.min.js" type="text/javascript"></script>
<link href="librerias/dataTablesTool/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
<link href="librerias/dataTablesTool/dataTables.tableTools.css" rel="stylesheet" type="text/css"/>

<script src="js/cuentahabiente/agregarOrdenante.js" type="text/javascript"></script>


<div class="panel panel-default panelPantalla">
    <div class="panel-heading">
        <div class="row tituloPantalla">
            <h2> SECCIÓN DE CUENTAHABIENTE ORDENANTE</h2>
        </div>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-12">

                <div class="row">
                    <div class="col-md-6">
                        <br> <br>
                        <div class="panel panel-default subpanelPantalla">
                            <div class="panel panel-heading subtituloPantalla">
                                <div class="row subtituloPantalla">
                                    <h6>ADMINISTRAR C.ORDENANTE</h6>
                                </div>
                            </div>
                            <div class="panel-body">
                                <!--<form id="formOrdenante" name="formOrdenante">-->
                                <div class="row">
                                    <div class="col-md-6">

                                        <label>
                                            Nombre Ordenante:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <%
                                            String sucursal = "";
                                            if (request.getSession().getAttribute("sucursal") != null) {
                                                sucursal = request.getSession()
                                                        .getAttribute("sucursal").toString();
                                            }
                                        %>
                                        <input type="hidden" id="sucursal" name="sucursal" 
                                               value="<%=sucursal%>"
                                               disabled="true">
                                        <input type="hidden" 
                                               id="ordenanteId" 
                                               name="ordenanteId"
                                               disabled="true"
                                               />
                                        <input type="hidden" 
                                               id="personaId" 
                                               name="personaId"
                                               disabled="true"
                                               />
                                        <input type="hidden" 
                                               id="accion" name="accion" 
                                               type="text" 
                                               class="form-control" 
                                               maxlength="50"
                                               disabled="true"/>
                                        <input id="nombre" name="nombre" 
                                               type="text" 
                                               class="form-control"
                                               maxlength="50"
                                               onkeyup="javascript:this.value = this.value.toUpperCase();" 
                                               required/>
                                        <input id="numeroDebito"
                                               type="hidden" 
                                               class="form-control"
                                               maxlength="16"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            RFC:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <input id="rfc" name="rfc" 
                                               type="text" 
                                               class="form-control" 
                                               maxlength="18"
                                               onkeyup="javascript:this.value = this.value.toUpperCase();" 
                                               required /> 
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Numero Cliente:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <input id="identificador" 
                                               name="identificador" 
                                               type="text" 
                                               class="form-control" 
                                               min="0"
                                               maxlength="7"
                                               onkeypress="javascript:return OnlyNumeros(event);"
                                               required > 
                                    </div>
                                </div>
                                <div hidden class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Digito CLABE:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <select id="digitoClabe" 
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
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Tipo Cuenta:
                                        </label>
                                        <input type="hidden" id="cuentaId" 
                                               name="cuentaId"
                                               disabled="true"/>
                                    </div>
                                    <div class="col-md-6">
                                        <select id="tipoCuenta" 
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
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Numero Cuenta:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <input id="numero" name="numero" 
                                               type="text" 
                                               class="form-control"
                                               maxlength="18"
                                               onkeypress="javascript:return OnlyNumeros(event);"
                                               />

                                        <input type="hidden" id="numerotel" 
                                               name="numerotel">
                                        <input type="hidden" id="estadoOculto" 
                                               name="estadoOculto">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <label>
                                            Estado:
                                        </label>
                                    </div>
                                    <div class="col-md-6">
                                        <select id="estadoCHO" 
                                                required
                                                class="form-control">
                                            <option selected 
                                                    value="0">
                                                ALTA
                                            </option>
                                            <option selected 
                                                    value="1">
                                                BAJA
                                            </option>

                                        </select>
                                    </div>
                                </div>
                                <br><br>

                                <div class="row">
                                    <div class="col-md-2"></div>


                                    <div class="col-md-2">
                                        <button id="baja" name="baja" 
                                                class="btn btn-primary" 
                                                >
                                            <span class="ui-button-text"> Baja</span>
                                        </button>
                                    </div>
                                    <div class="col-md-2">
                                        <button id="submit" name="submit" 
                                                class="btn btn-primary" 
                                                >
                                            <span class="ui-button-text"> Guardar</span>
                                        </button>
                                        <button id="alta" name="alta" 
                                                class="btn btn-primary" 
                                                value="1">
                                            <span class="ui-button-text"> Alta</span>
                                        </button>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="buttons">
                                            <form method="post" 
                                                  action="ReportesServlet"
                                                  target="_blank" 
                                                  onsubmit="return formulario(this)">
                                                <div class="btn-group">
                                                    <button  class="btn btn-primary"
                                                             id="imprimir"
                                                             name="imprimir"
                                                             >Imprimir
                                                    </button>
                                                </div>
                                                <input type="hidden" id="rep" 
                                                       name="identificador">
                                                <input type="hidden" id="accion" 
                                                       name="accion" value="2"  
                                                       >
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" 
                                                class="btn btn-primary" 
                                                onclick="limpiarDatos();"
                                                >
                                            Limpiar
                                        </button>
                                    </div>
                                    <div class="col-md-2"></div>
                                </div>


                                <!--</form>-->
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <!--Comienza el div para el listado de los beneficiarios-->
                        <div class="">
                            <div class="table-responsive ">
                                <table id="tblOrdenante" class="display compact" >
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre de Cliente</th>
                                            <th>Cuenta CLABE</th>
                                            <th>RFC ó CURP</th>
                                            <th>Numero Cliente</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>#</th>
                                            <th>Nombre de Cliente</th>
                                            <th>Cuenta CLABE</th>
                                            <th>RFC ó CURP</th>
                                            <th>Numero Cliente</th>
                                            <th>Estado</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                </div>
            </div>
        </div>
    </div>
</div>
