<%-- 
    Document   : menu
    Created on : 22/07/2015, 10:56:32 AM
    Author     : LUNA
--%>


<%@page import="java.math.BigDecimal"%>
<%@page import="ws.cliente.SaldosWS"%>
<%@page import="form.GeneralForm"%>
<div  class="container" id="cabecera">
    <nav class="navbar navbar-default navbar-fixed-top">
        <!-- Sección donde se muestra el logotipo, un mensaje de 
        bienvenida y el estado de los saldos, de forma estática.-->
        <div class="row">
            <div class="col-md-4">
                <%--<%@include file='/vistas/sistema/imgSucursales.jsp' %>--%>
            </div>
            <div class="col-md-4">
                <br>
                <h2 style="text-align: center;">
                    BIENVENIDO ${sessionScope.usuario}
                </h2>
            </div>
            <div class="col-md-4">
                <h4 class="text-center">
                    <span class="label label-default">ESTADOS SALDOS: </span>
                </h4>
                <div class="estado">
                    <%@include file='/vistas/sistema/edoSaldos.jsp' %>
                </div>
            </div>
        </div> 
        <nav class="navbar navbar-default" id="cabecera2">
            <div class="container-fluid">    
                <!-- El logotipo y el icono que despliega el menú se agrupan
                     para mostrarlos mejor en los dispositivos móviles -->
                <div class="navbar-header" >
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Desplegar navegación</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>

                <!-- Agrupar los enlaces de navegación, los formularios y cualquier
                     otro elemento que se pueda ocultar al minimizar la barra -->
                <div class="collapse navbar-collapse" 
                     id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav" >
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" 
                               data-toggle="dropdown" role="button" 
                               aria-haspopup="true">
                                INICIO<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><h6 class="text-center titulos">
                                        Pantalla Principal
                                    </h6>
                                </li>
                                <li class="divider"></li>
                                <li><a  style="color:#269abc; 
                                        text-align:left;"
                                        href="#"
                                        id="listOrden">
                                        Enviar Orden de Pago</a>
                                </li>
                                <li><a  style="color:#269abc; 
                                        text-align:left;"
                                        href="#"
                                        id="cargarArchivoOrden">
                                        Cargar Ordenes</a>
                                </li>

                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" 
                               data-toggle="dropdown" role="button" 
                               aria-haspopup="true">
                                ORDEN<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><h6 class="text-center titulos">
                                        Información de la Orden</h6>
                                </li>
                                <li class="divider"></li>
                                <li><a href="#" style="color:#269abc;
                                       text-align:left;" id="addOrdenPago">
                                        Agregar Orden de Pago</a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" 
                               data-toggle="dropdown" role="button" 
                               aria-haspopup="true" >
                                CUENTAHABIENTE<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><h6 class="text-center titulos">
                                        Formulario-Lista</h6>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="addBeneficiario" 
                                       >
                                        Cuentahabiente-Beneficiario</a>
                                </li>
                                <li>
                                    <a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="addOrdenante" 
                                       >
                                        Cuentahabiente-Ordenante</a>
                                </li>
                            </ul>    
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" 
                               data-toggle="dropdown" role="button" 
                               aria-haspopup="true" >
                                REPORTE<span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><h6 class="text-center titulos">
                                        Mostrar-Imprimir</h6>
                                </li>
                                <li class="divider"></li>
                                <li><a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="bitacoraOrden"
                                       >
                                        Bitácoras de Órdenes</a>
                                </li>
<!--                                <li><a style="color:#269abc;
                                       text-align:left;" href="#">
                                        Monto a Pagar a Ent.Apadrinadora</a>
                                </li>-->
                                <li><a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="repNumPagos"
                                       >
                                        Número de Pagos</a>
                                </li>
                                <li><a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="repCtaCLABE"
                                       >
                                        Cuentas CLABE</a>
                                </li>
                                <li><a style="color:#269abc;
                                       text-align:left;" 
                                       href="#"
                                       id="rEstadoCuenta"
                                       >
                                        Estado de Cuenta</a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" 
                               data-toggle="dropdown">
                                CATÁLOGOS <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><h6 class="text-center titulos">
                                        Tablas</h6>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="tipoPagos" >
                                        Tipo Pago 
                                    </a>
                                </li>
                                <li ng-click="toggleModal1()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="tipoCuentas" >
                                        Tipo Cuenta 
                                    </a>
                                </li>
                                <li ng-click="toggleModal2()">
                                    <a style="color:#269abc;
                                       text-align:left;"  href="#"
                                       id="tipoOperacion">
                                        Tipo Operacion 
                                    </a>
                                </li>
                                <li ng-click="toggleModal3()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="causaDevolucion">
                                        Causa Devolucion 
                                    </a>
                                </li>
                                <li ng-click="toggleModal4()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="tipoTraspaso">
                                        Tipo Traspaso 
                                    </a>
                                </li>
                                <li ng-click="toggleModal5()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="participante">
                                        Participante 
                                    </a>
                                </li>
                                <li ng-click="toggleModal6()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="tipoPagoes">
                                        Tipo Pago ES 
                                    </a>
                                </li>
                                <li ng-click="toggleModal7()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="estadoPeticion">
                                        Estado Peticion 
                                    </a>
                                </li>
                                <li ng-click="toggleModal8()">
                                    <a style="color:#269abc;
                                       text-align:left;" href="#"
                                       id="estadoOrden">
                                        Estado Orden 
                                    </a>
                                </li>
                            </ul>
                        </li>

                    </ul>
                    <a style="color:#1BC9B1;
                       text-align:left;" href="#"
                       id="logout">
                        <span class="glyphicon glyphicon-lock" ></span> <b>CERRAR SESIÓN</b></a>

                </div>
            </div>
        </nav>
    </nav>

</div>
