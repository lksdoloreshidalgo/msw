<%-- 
    Document   : index
    Created on : 22/07/2015, 10:32:46 AM
    Author     : LUNA
--%>


<%@page session="true" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html id="hola">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <link href="css/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap/main.css" rel="stylesheet" type="text/css"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <link href="css/registrar.css" rel="stylesheet" type="text/css"/>
        <link href="css/modulo.png" rel="icon"/>

        <title>Modulo Sucursal Web SPEI</title>

    </head>
    <body>
        <%
            HttpSession sesion = request.getSession();
            String nombreU = (String) sesion.getAttribute("usuario");

            if (nombreU == null) {

                String site = new String("vistas/inicioSesion/login.jsp");
                response.setStatus(response.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", site);
            }
        %>
        <!--<script src="librerias/jquery-1.12.0.min.js" type="text/javascript"></script>-->
        <script src="librerias/jquery-3.2.1.min.js" type="text/javascript"></script>
        <script src="librerias/jquery.validate.js" type="text/javascript"></script>

        <%
            String rol = "";
            if (request.getSession().getAttribute("rol") != null) {
                rol = request.getSession()
                        .getAttribute("rol").toString();
            }
            if (rol.equals("1")) {
        %>
        <%@include file="menu/menuAdmin.jsp" %>
        <%
        } else if (rol.equals("2")) {
        %>
        <%@include file="menu/menu.jsp" %>
        <%
        } else if (rol.equals("3")) {
        %>
        <%@include file="menu/menuVentanilla.jsp" %>
        <%
        } else if (rol.equals("4")) {
        %>
        <%@include file="menu/menuTesoreria.jsp" %>
        <%
        } else if (rol.equals("5")) {
        %>
        <%@include file="menu/menuAdmonUser.jsp" %>
        <%
            }
        %>
        <div id="contenido">

        </div>
        <script src="librerias/bootstrap.js" type="text/javascript"></script>
        <!--<script src="librerias/dataTable/dataTables.bootstrap.js" type="text/javascript"></script>-->
        <script src="librerias/jquery.blockUI.js" type="text/javascript"></script>
        <script src="librerias/screenBlock.js" type="text/javascript"></script>
        <script src="librerias/jquery.bootstrap-growl.min.js" type="text/javascript"></script>

        <script src="js/menu.js" type="text/javascript"></script>
        <script src="librerias/ajaxupload.3.6.js" type="text/javascript"></script>

    </body>
</html>
