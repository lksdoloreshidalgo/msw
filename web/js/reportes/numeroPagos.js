

$(document).ready(function () {
    document.getElementById("rango").style.display = "none";
    document.getElementById("mesAnio").style.display = "none";

    $("#limpiarMes").click(function () {
        limpiarRnp();
    });
    $("#limpiarRango").click(function () {
        limpiarRnp2();
    });
});

/**
 * Funcion encargada de validar que los compos no vallan vacios y 
 * permitir generar el reporte
 * @param {type} f
 * @returns {Boolean}
 */
function formulario(f) {
    if (f.mes.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar un Mes para poder generar el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.mes.focus();
        return false;
    }
    if (f.anio.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar un anio para poder generar el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.anio.focus();
        return false;
    }
    $.bootstrapGrowl("Reporte generado correctamente!", {
        type: 'success',
        delay: 5000
    });
    return true;

}

$(function () {
    $('#datetimepicker11').datetimepicker({
        locale: 'es',
        viewMode: 'years',
        format: 'YYYY'
    });
    $('#datetimepicker10').datetimepicker({
        locale: 'es',
        viewMode: 'months',
        format: 'MM'
    });
    $('#datetimepicker6').datetimepicker({
        locale: 'es',
        format: 'YYYY/MM/DD'
    });
    $('#datetimepicker7').datetimepicker({
        locale: 'es',
        format: 'YYYY/MM/DD'
    });
    $("#datetimepicker6").on("dp.change", function (e) {
        $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker7").on("dp.change", function (e) {
        $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
    });
});


function selectType() {
    var tipo = $("#tipo").val();
    if (tipo === "1") {
        document.getElementById("rango").style.display = "block";
        document.getElementById("mesAnio").style.display = "none";
        limpiarRnp();
        limpiarRnp2();
    } else if (tipo === "2") {
        document.getElementById("rango").style.display = "none";
        document.getElementById("mesAnio").style.display = "block";
        limpiarRnp();
        limpiarRnp2();
    } else {
        document.getElementById("rango").style.display = "none";
        document.getElementById("mesAnio").style.display = "none";
        limpiarRnp();
        limpiarRnp2();
    }
}
/**
 * 
 * @returns {undefined}
 */
function limpiarRnp() {
    document.getElementById("fechaInicio").value = "";
    document.getElementById("fechaFin").value = "";
}

/**
 * 
 * @returns {undefined}
 */
function limpiarRnp2() {
    document.getElementById("mes").value = "";
    document.getElementById("anio").value = "";


}
