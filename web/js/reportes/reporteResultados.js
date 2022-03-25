/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $("#limpiarMes").click(function () {
        limpiarCampos();
    });
});

$(function () {

    $('#datetimepicker6').datetimepicker({
        locale: 'es',
        format: 'YYYY-MM-DD'
    });
    $('#datetimepicker7').datetimepicker({
        locale: 'es',
        format: 'YYYY-MM-DD',
        useCurrent: true
    });
    $("#datetimepicker6").on("dp.change", function (e) {
        $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker7").on("dp.change", function (e) {
        $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
    });
});

/**
 * Funcion encargada de validar que los compos no vallan vacios y 
 * permitir generar el reporte
 * @param {type} f
 * @returns {Boolean}
 */
function formulario(f) {
    if (f.fechaInicio.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar la fecha inicio para poder generar el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.fechaInicio.focus();
        return false;
    }

    $.bootstrapGrowl("Reporte generado correctamente!", {
        type: 'success',
        delay: 5000
    });
    return true;
}

/**
 * Funcion encargada de limpiar los campos del formulario
 * @returns {undefined}
 */
function limpiarCampos() {
    document.getElementById("fechaInicio").value = "";
    document.getElementById("fechaFin").value = "";
}

