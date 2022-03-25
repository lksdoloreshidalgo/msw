/* 
 Created on : 11/09/2015, 12:52:32 AM
 Author     : LUNA
 */


$(document).ready(function () {
    cargarCombos();


    $("#limpiar").click(function () {
        limpiarBop();
    });
    $("#cargar").click(function () {
        iniTabla();
    });
});

/**
 * Funcion encargada de inicializar el listado de la bitacora de ordenes
 * @returns {undefined}
 */
function iniTabla() {
    block();
    var datosP = {
        entrada: document.getElementById("entrada").checked,
        salida: document.getElementById("salida").checked,
        partReceptor: $("#partReceptor").val(),
        partEmisor: $("#partEmisor").val(),
        cveRastreo: $("#cveRastreo").val(),
        folioIns: $("#folioIns").val(),
        folioOrd: $("#folioOrd").val(),
        estado: $("#estado").val(),
        sucursal: $("#sucursal").val(),
        tipoPago: $("#tipoPago").val(),
        fechaInicio: $("#fechaInicio").val(),
        fechaFin: $("#fechaFin").val(),
        accion: 1,
        usuarioId: $("#usuarioId").val(),
        sucursalF: $("#sucursalF").val()
    };
    $.ajax({
        type: "POST",
        url: "ListarOrdenServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblBitacoraOrdenPago").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        "columns": [
                            {"data": 0}, {"data": 1}, {"data": 2}, {"data": 3}, {"data": 4},
                            {"data": 5}, {"data": 6}, {"data": 7}, {"data": 8}, {"data": 9},
                            {"data": 10}, {"data": 11}, {"data": 12}, {"data": 13}, {"data": 14},
                            {"data": 15}, {"data": 16}, {"data": 17}, {"data": 18}, {"data": 19},
                            {"data": 20}, {"data": 21}, {"data": 23}, {"data": 22}, {"data": 24},
                            {"data": 25}, {"data": 26}, {"data": 27}, {"data": 28}, {"data": 29},
                            {"data": 30}, {"data": 31}, {"data": 32}, {"data": 33}, {"data": 34},
                            {"data": 36}, {"data": 37}, {"data": 38}, {"data": 39}, {"data": 40},
                            {"data": 41}
                        ],
                        "columnDefs": [
                            {
                                "searchable": false,
                                "orderable": false,
                                "targets": 0
                            },
                            {
                                "targets": 33,
                                "data": "33", // La inclusión de datos
                                'searchable': false,
                                'orderable': false,
                                'className': 'dt-body-center',
                                "render": function (data, type, full, meta) {
                                    var input = '<input type="checkbox" id="entrada" value="' + data + '" disabled="true">';
                                    if (data === true) {
                                        input = '<input type="checkbox" id="entrada" value="' + data + '" disabled="true" checked="checked">';
                                    }
                                    return input;
                                }
                            }
                        ],
                        "order": [[1, 'asc']],
                        /**
                         * inician los filtros
                         * @returns {undefined}
                         */
                        initComplete: function () {
                            this.api().columns().every(function () {
                                var column = this;
                                var select = $('<select><option value=""></option></select>')
                                        .appendTo($(column.footer()).empty())
                                        .on('change', function () {
                                            var val = $.fn.dataTable.util.escapeRegex(
                                                    $(this).val()
                                                    );
                                            column
                                                    .search(val ? '^' + val + '$' : '', true, false)
                                                    .draw();
                                        });
                                column.data().unique().sort().each(function (d, j) {
                                    select.append('<option value="' + d + '">' + d + '</option>');
                                });
                            });
                        }
                        /**
                         * FIN DE LA FUNCION FILTROS
                         */

                    });
            unBlock();

            table.on('order.dt search.dt', function () {
                table.column(0, {
                    search: 'applied',
                    order: 'applied'
                }).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();

            $("#tblBitacoraOrdenPago tbody").on('dblclick', 'tr', function (evento) {
                evento.preventDefault();
                modal();
            });

            //configuramos la tabla de beneficiarios
            $('#tblBitacoraOrdenPago tbody').on('click', 'tr', function () {

                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
                $('#tblBitacoraOrdenPago tbody').on('click', 'tr', function (evento) {
                    evento.preventDefault();
                    var instruccionId = $('td', this).eq(35).text();
                    $("#instruccionId").val(instruccionId);
                    var instruccionIdRep = $('td', this).eq(35).text();
                    $("#instruccionIdRep").val(instruccionIdRep);
                    var rastreo = $('td', this).eq(4).text();
                    $("#rastreo").val(rastreo);
                });
            });
        }
    });

}


/**
 * Funcion para mostrar los estados de la orden de pago
 * @returns {undefined}
 */
function modal() {

    $("#myModal").modal();
    mostrarEstados();

}

/**
 * Funcion encargada de mostrar los estados de la orden de pago
 * @returns {undefined}
 */
function mostrarEstados() {
    var datos = {
        instruccionId: $("#instruccionId").val(),
        accion: 4
    };
    $.ajax({
        type: 'POST',
        url: "ListarOrdenServlet",
        data: datos,
        success: function (data, textStatus, jqXHR) {
            var dataset = JSON.parse(data);
            var tableD = $("#tblDetalleOrden").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        'order': [[2, 'asc']]
                    }
            );
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $.bootstrapGrowl("Error, ocurrio un error interno Consulte al Administrador ", {
                type: 'danger',
                delay: 8000
            });
        }
    });
}
/**
 * Funcion encargada de validar que se cargue el ID para poder generar el 
 * reporte correscpondiente
 * @param {type} f
 * @returns {Boolean}
 */
function formulario(f) {
    if (f.instruccionIdRep.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar una Orden de la lista para poder Imprimir el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.instruccionIdRep.focus();
        return false;
    }
    return true;
}

$(function () {

    $('#datetimepicker6').datetimepicker({
        locale: 'es',
        format: 'YYYY-MM-DD'
    });
    $('#datetimepicker7').datetimepicker({
        locale: 'es',
        format: 'YYYY-MM-DD'
    });
    $("#datetimepicker6").on("dp.change", function (e) {
        $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker7").on("dp.change", function (e) {
        $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
    });
});

/**
 * limpia los campos encargados de generar el reporte
 * @returns {undefined}
 */
function limpiarBop() {
    document.getElementById("rastreo").value = "";
    document.getElementById("instruccionIdRep").value = "";
}

/**
 * 
 * @param {type} n
 * @param {type} currency
 * @returns {String}
 */
function format2(n, currency) {
    return currency + "" + n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
}

/**
 * funcion encargada de cargar el combo de los participantes Receptores
 * @returns {undefined}
 */
function comboPartReceptor() {
    var datosP = {
        accion: 1
    };
    $select = $('#partReceptor');
    $select = $('#partEmisor');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
//            console.log(JSON.stringify(data1));
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#partReceptor").append('<option value="' + v.descripcion + '">' + v.descripcion + '</option>');
            });
            $.each(data1, function (k, v) {
                $("#partEmisor").append('<option value="' + v.descripcion + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}

/**
 * funcion encargada de cargar el combo de los tipos de pago
 * @returns {undefined}
 */
function comboEstadoOrden() {
    var datosP = {
        accion: 3
    };
    $select = $('#estado');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
//            console.log(JSON.stringify(data1));
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#estado").append('<option value="' + v.descripcion + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}

/**
 * funcion encargada de cargar el combo de los tipos de pago
 * @returns {undefined}
 */
function comboTipoPago() {
    var datosP = {
        accion: 8
    };
    $select = $('#tipoPago');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
//            console.log(JSON.stringify(data1));
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#tipoPago").append('<option value="' + v.descripcion + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}

/**
 * funcion encargada de cargar todas la sucursales 
 * @returns {undefined}
 */
function comboSucursales() {
    var datosP = {
        accion: 10
    };
    $select = $('#sucursal');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
//            console.log(JSON.stringify(data1));
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#sucursal").append('<option value="' + v.nombre + '">' + v.nombre + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}

/**
 * funcion encargada de inicializar todo los combos de la pantralla
 * @returns {undefined}
 */
function cargarCombos() {
    comboEstadoOrden();
    comboSucursales();
    comboTipoPago();
    comboPartReceptor();
    diaActual();
}

/**
 * funcion encargada de generar el dia actual
 * @returns {undefined}
 */
function diaActual() {
    var f = new Date();
    var hoy = f.getFullYear() + "-" + (f.getMonth() + 1) + "-" + f.getDate();
    document.getElementById("fechaInicio").value = hoy;
}

/**
 * funcion encargada de obtener todos los datos de los filtro de la bitacora
 * @returns {undefined}
 */
function obtenerDatos() {


    var entradaR = document.getElementById("entrada").checked;
    var salidaR = document.getElementById("salida").checked;
    var partReceptorR = $("#partReceptor").val();
    var partEmisorR = $("#partEmisor").val();
    var folioInsR = $("#folioIns").val();
    var folioOrdR = $("#folioOrd").val();
    var cveRastreoR = $("#cveRastreo").val();
    var estadoR = $("#estado").val();
    var sucursalR = $("#sucursal").val();
    var tipoPagoR = $("#tipoPago").val();
    var fechaInicioR = $("#fechaInicio").val();
    var fechaFinR = $("#fechaFin").val();

    document.getElementById("entradaR").value = entradaR;
    document.getElementById("salidaR").value = salidaR;
    document.getElementById("partReceptorR").value = partReceptorR;
    document.getElementById("partEmisorR").value = partEmisorR;
    document.getElementById("folioInsR").value = folioInsR;
    document.getElementById("folioOrdR").value = folioOrdR;
    document.getElementById("cveRastreoR").value = cveRastreoR;
    document.getElementById("estadoR").value = estadoR;
    document.getElementById("sucursalR").value = sucursalR;
    document.getElementById("tipoPagoR").value = tipoPagoR;
    document.getElementById("fechaInicioR").value = fechaInicioR;
    document.getElementById("fechaFinR").value = fechaFinR;
}
