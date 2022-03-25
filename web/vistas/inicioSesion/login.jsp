<%-- 
   
    Author     : Victor Hugo Luna Cortes
    Correo     : hugo.luna@somatecnologia.com.mx
    Fecha      : 4/11/2015
    Hora       : 09:44:34 AM
    Encoding   : ISO-8859-1
    Empresa    : SOMA
    Version    : 1.0
    Author     : SOMA-LUNA
--%>


<%@page import="hibernate.HibernateUtilMSW"%>
<%@page import="java.util.List"%>
<%@page import="modelo.general.SucursalModelo"%>
<%@page import="form.SucursalForm"%>

<link href="../../css/modulo.png" rel="icon"/>
<!-- Bootstrap core CSS -->
<link href="../../css/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="../../css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<!-- Custom styles for this template -->
<link href="../../css/signin.css" rel="stylesheet" type="text/css"/>

<script src="../../librerias/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="../../librerias/bootstrap.js" type="text/javascript"></script>
<script src="../../librerias/jquery.blockUI.js" type="text/javascript"></script>
<script src="../../librerias/screenBlock.js" type="text/javascript"></script>
<script src="../../librerias/ajaxupload.3.6.js" type="text/javascript"></script>

<script src="../../js/inicioSesion/login.js" type="text/javascript"></script>

<div class="row">
    <div class="col-lg-12">
        <br> <br>
        <br> <br>
    </div>
</div>
<div class="row">
    <div class="col-lg-6">

        <div class="form-signin">

            <h2 class="form-signin-heading">Acceso al "MSW"</h2>
            <h6 class="form-signin-heading">Módulo Sucursal Web de SPEI</h6>

            <label for="txtNickname">Usuario</label>
            <input type="text" name="txtNickname" id="txtNickname" class="form-control">
            <label for="txtContrasenia">Contraseña</label>
            <input type="password" name="txtContrasenia" id="txtContrasenia" class="form-control" >
            <label for="cboSucursal">Sucursal</label>

            <select class="form-control select" 
                    id="cboSucursal" 
                    name="cboSucursal">
                <option value=""> 
                    --Selecciona una sucursal-- 
                </option>
                <%
                    
                    HibernateUtilMSW.getSession();
                    String clave = "";
                    SucursalForm sm = new SucursalForm();
                    List<SucursalModelo> ts = sm.getlSucursal();
                    for (SucursalModelo lsm : ts) {
                        if ((request.getParameter("cboSucursal") != null)
                                && (request.getParameter("cboSucursal").trim()
                                        .equalsIgnoreCase(String.valueOf(lsm.getSucursalId())))) {
                            out.println("<option value=" + lsm.getSucursalId() + "selected>"
                                    + lsm.getNombre() + "</option>");
                            clave = String.valueOf(lsm.getSucursalId());
                        } else {
                            out.println("<option value=" + lsm.getNumeroClabe() + ">" + lsm.getNombre() + "</option>");
                        }
                    }
                %>
            </select>

            <br/>
            <input type="submit" value="entrar" name="acceder" class="btn btn-lg btn-info"  id="acceder">
        </div>

        <%
            String usuario = "";
            if (request.getSession().getAttribute("usuario") != null) {
                usuario = request.getSession()
                        .getAttribute("usuario").toString();
            }
        %>
        <input type="hidden" id="usuario" name="usuario" 
               value="<%=usuario%>">

    </div> <!-- /container -->
    <div class="col-lg-offset-4">

        <%@include file="SPEI.html"%>

    </div>
</div>

<script src="../../librerias/jquery.bootstrap-growl.min.js" type="text/javascript"></script>


