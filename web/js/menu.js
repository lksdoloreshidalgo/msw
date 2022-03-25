/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "vistas/ordenPagos/listarOrdenPago.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});


/**
 * Funcion para la anavegacion del menu
 * Agregar Beneficiario
 * @param {type} param
 */
$("#addBeneficiario").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/cuentahabientes/agregarBeneficiario.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

/**
 * Funcion para la anavegacion del menu
 * Agregar Orden de Pago
 * @param {type} param
 */
$("#addOrdenPago").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/ordenPagos/agregarOrdenPago.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

/**
 * Funcion para la anavegacion del menu
 * Agregar Ordenante
 * @param {type} param
 */
$("#addOrdenante").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/cuentahabientes/agregarOrdenante.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
/**
 * Funcion para la anavegacion del menu
 * Lista de Ordenes de Pago
 * @param {type} param
 */
$("#listOrden").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/ordenPagos/listarOrdenPago.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
/**
 * Funcion para la anavegacion del menu
 * CATALOGOS
 * @param {type} param
 */

$("#causaDevolucion").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/causaDevolucion.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#estadoOrden").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/estadoOrden.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#estadoPeticion").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/estadoPeticion.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#participante").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/participante.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#tipoCuentas").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/tipoCuenta.jsp",
        success: function (a) {
            $("#contenido").html(a);

        }
    });
});

$("#tipoOperacion").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/tipoOperacion.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#tipoPagos").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/tipoPagos.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#tipoPagoes").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/tipoPagoes.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

$("#tipoTraspaso").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/catalogos/tipoTraspaso.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
$("#registrarAdmin").click(function () {
    $.ajax({
        url: "vistas/sistema/registrarUsuario.jsp",
        success: function (p) {
            $("#contenido").html(p);
        }
    });
});
$("#logout").click(function () {

    $.ajax({
        type: "POST",
        url: "CerrarSesionServlet",
        success: function () {
            setTimeout(redireccionar(), 6000);
        }
    });
});

function redireccionar() {
    var accesoCorrecto = "vistas/inicioSesion/login.jsp";
    window.location = accesoCorrecto;
}
/**
 * Funcion para la anavegacion del menu
 * Bitacora Ordenes de Pago
 * @param {type} param
 */
$("#bitacoraOrden").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/ordenPagos/bitacoraOrdenPago.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

/**
 * Funcion para la anavegacion del menu
 * Reporte Cuentas CLABE
 * @param {type} param
 */
$("#repCtaCLABE").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/reportes/reporteCuentaCLABE.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});

/**
 * Funcion para la anavegacion del menu
 * Reporte Numero de Pagos
 * @param {type} param
 */
$("#repNumPagos").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/reportes/numeroPagos.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
/**
 * Funcion para la anavegacion del menu
 * Agregar Orden de Pago
 */
$("#cargarArchivoOrden").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/ordenPagos/cargaArchivoOrden.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
/**
 * Funcion para la anavegacion del menu
 * Reporte Estado Cuenta
 */
$("#rEstadoCuenta").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/reportes/estadoCuenta.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
/**
 * Funcion para la anavegacion del menu
 * Reporte Estado Cuenta
 */
$("#reporteResultados").click(function () {
    $.ajax({
        type: "POST",
        url: "vistas/reportes/reporteResultados.jsp",
        success: function (a) {
            $("#contenido").html(a);
        }
    });
});
