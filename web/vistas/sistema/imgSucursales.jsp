<%-- 
    Document   : imgSucursales
    Created on : 25/08/2015, 12:06:24 PM
    Author     : Estadias8
--%>

<%@page import="form.GeneralForm"%>
<% String entity = GeneralForm.getEntidad().getNombreCorto();
                                     String imgEntity = "MSW";
                                        if(entity.equals(imgEntity)){
                                        out.println("<img class='imgSucursal' src='imagenes/imgSucursales/entidadMsw.png'/>");
                                        }else{
                                        out.println("<img class='imgSucursal' src='imagenes/imgSucursales/entidadMsw.png'");
                                        }
%>