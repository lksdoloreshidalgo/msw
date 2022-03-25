/* 
 Created on : 4/08/2015, 11:03:32 AM
 Author     : LUNA
 */

/**
 * Funcion encargada de iniciar todos los eventos para gestionar
 *  la orden de pago
 */
var lids;
$(document).ready(function () {

    iniTabla();
    $("#enviar").click(function () {
        enviar();
    });
    $("#actualizar").click(function () {
        var instruccionId = $("#instruccionId").val();
        if (instruccionId !== "") {
            actualizar();
        } else {
            $.bootstrapGrowl("Error, Para poder Editar seleccione una Orden de la Lista!", {
                type: 'danger',
                delay: 5000
            });
        }
    });
    $("#declinar").click(function () {
        var estado = $("#estado").val();
        if (estado === "PARA ENVIAR" || estado === "ACTUALIZADA") {
            $.confirm({
                title: 'Confirmacion!',
                content: 'Realmente desea Declinar la Orden de Pago!',
                confirm: function () {
                    declinar();
                },
                cancel: function () {
                }
            });
        } else {
            $.bootstrapGrowl("Error, Estado: " + estado + ", La Orden no puede ser Declinada !", {type: 'danger',
                delay: 5000
            });
            setTimeout(8000);
            unBlock();
        }
    });

    $("#cancelar").click(function () {
        cancelar();
    });
    $("#devolver").click(function () {
        var instruccionId = $("#instruccionId").val();
        var entrada = $("#entrada").val();
        var estado = $("#estado").val();
        if (instruccionId !== "") {
            if (entrada === "Entrada") {
                if (estado === "LIQUIDADO" || estado === "POR DEVOLVER") {
                    $('#modalDevolucion').modal('show');
                    comboDevolucion();
                    $("#aceptar").click(function () {
                        $.confirm({
                            title: 'Confirmacion!',
                            content: 'Realmente desea devolver la Orden de Pago!',
                            confirm: function () {
                                devolver();
                            },
                            cancel: function () {
                            }
                        });
                    });
                } else {
                    $.bootstrapGrowl("Error, La Orden no puede ser Devuelta Estado: " + estado + ".", {
                        type: 'danger'
                    });
                    unBlock();
                }
            } else {
                $.bootstrapGrowl("Error, La Orden de Pago no es de Entrada, no puede ser Devuelta", {
                    type: 'danger'
                });
                unBlock();
            }
        } else {
            $.bootstrapGrowl("Error, debe seleccionar una Orden de la lista para poder Devolverla!", {
                type: 'danger'
            });
            unBlock();
        }
    });
    $("#limpiar").click(function () {
        limpiarLop();
    });
    $("#abonar").click(function () {
        var estado = $("#estado").val();
        if (estado === "LIQUIDADO") {
            $.confirm({
                title: 'Confirmacion!',
                content: 'Realmente desea Abonar la Orden de Pago!',
                confirm: function () {
                    abonar();
                },
                cancel: function () {
                }
            });
        } else {
            $.bootstrapGrowl("Error, Estado: " + estado + ", La Orden no puede ser Abonada !", {type: 'danger',
                delay: 5000
            });
            setTimeout(8000);
            unBlock();
        }
    });
    $("#enviarOrden").click(function (evento) {

        $.confirm({
            title: 'Confirmacion!',
            content: 'Realmente desea Enviar la(s) Orden(es) de Pago!',
            confirm: function () {
                block();
                $('#modalInfo').modal('hide');
                var datosO = {
                    lordenid: JSON.stringify(lids),
                    accion: 2,
                    sucursal: $("#sucursal").val(),
                    status: true,
                    usuario: $("#usuario").val()
                };
                $.ajax({
                    type: 'POST',
                    url: "InstruccionServlet",
                    data: datosO,
                    dataType: "json",
                    success: function (data) {
                        lids = null;
                        var estado = data.acuseEnvio["estadoInstruccion"];
                        var arr = data.acuseEnvio;
                        var claveRastreo = "";
                        if (arr !== null || arr !== 0 || arr !== "undefined") {
                            for (var i = 0; i < arr.length; i++) {
                                var arrArr = arr[i];
                                for (var j in arrArr) {
                                    claveRastreo = arrArr[j];
                                }
                            }
                        }
                        var noPagos = data.estadoGeneral[1];

                        if (estado === 0) {
                            $.bootstrapGrowl("Se enviaron: " + noPagos +
                                    " , Ordenes de pago correctamente ", {
                                        type: 'success',
                                        delay: 5000
                                    });
                            unBlock();
                            iniTabla();
                        } else {
                            if (estado !== 0) {
                                $.bootstrapGrowl("Ocurrio un ERROR numero: '" + estado
                                        + "' Para mayor informacion consultar Catalogos->Estado Peticion ", {
                                            type: 'danger',
                                            delay: 8000
                                        });
                                unBlock();
                            } else {
                                $.bootstrapGrowl("Error '" + estado
                                        + "' en el pago con clave de rastreo '"
                                        + claveRastreo +
                                        "' Consultar Catalogos->Estado Peticion ", {
                                            type: 'danger',
                                            delay: 8000
                                        });
                                unBlock();
                            }
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        lids = null;
                        $.bootstrapGrowl("Error, ocurrio un error interno Consulte al Administrador ", {
                            type: 'danger',
                            delay: 8000
                        });
                        unBlock();
                    }
                });

            },
            cancel: function () {
                lids = null;
                $('#modalInfo').modal('hide');
            }
        });
        evento.stopPropagation();
    });
});

/**
 * funcion encargada de inicializar una lista de checkbox dentro de un dataTable
 * @param {type} table
 * @returns {undefined}
 */
function updateDataTableSelectAllCtrl(table) {
    var $table = table.table().node();
    var $chkbox_all = $('tbody input[type="checkbox"]', $table);
    var $chkbox_checked = $('tbody input[type="checkbox"]:checked', $table);
    var chkbox_select_all = $('thead input[name="select_all"]', $table).get(0);

    // If none of the checkboxes are checked
    if ($chkbox_checked.length === 0) {
        chkbox_select_all.checked = false;
        if ('indeterminate' in chkbox_select_all) {
            chkbox_select_all.indeterminate = false;
        }

        // If all of the checkboxes are checked
    } else if ($chkbox_checked.length === $chkbox_all.length) {
        chkbox_select_all.checked = true;
        if ('indeterminate' in chkbox_select_all) {
            chkbox_select_all.indeterminate = false;
        }

        // If some of the checkboxes are checked
    } else {
        chkbox_select_all.checked = true;
        if ('indeterminate' in chkbox_select_all) {
            chkbox_select_all.indeterminate = true;
        }
    }
}

/**
 * variable global (CheckBox)
 * @type Array
 */
var rows_selected = [];


/**
 * Funcion encargada de inicializar la tabla Ordenes de pago
 * @returns {undefined}
 */
function iniTabla() {
    block();
    var datosP = {
        accion: 2,
        usuarioId: $("#usuarioId").val(),
        sucursalF: $("#sucursalF").val()
    };
    $.ajax({
        type: "POST",
        url: "ListarOrdenServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblOrdenPago").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        "scrollY": 220,
                        "scrollX": true,
                        'order': [[33, 'desc']],
                        "columns": [
                            {"data": 0}, {"data": null}, {"data": 1}, {"data": 2}, {"data": 3},
                            {"data": 4}, {"data": 5}, {"data": 6}, {"data": 7}, {"data": 8},
                            {"data": 9}, {"data": 10}, {"data": 11}, {"data": 12}, {"data": 13},
                            {"data": 14}, {"data": 15}, {"data": 16}, {"data": 17}, {"data": 18},
                            {"data": 19}, {"data": 20}, {"data": 21}, {"data": 23}, {"data": 22},
                            {"data": 24}, {"data": 25}, {"data": 26}, {"data": 27}, {"data": 28},
                            {"data": 29}, {"data": 30}, {"data": 31}, {"data": 32}, {"data": 33},
                            {"data": 34}, {"data": 36}, {"data": 37}, {"data": 38}, {"data": 39},
                            {"data": 40}, {"data": 41}
                        ],
                        "columnDefs": [
                            {
                                "searchable": false,
                                "orderable": false,
                                "targets": 0
                            },
                            {
                                "targets": 1,
                                'searchable': false,
                                'orderable': false,
                                'className': 'dt-body-center',
                                "render": function () {
                                    return '<input type="checkbox">';
                                }
                            }
                        ],
                        'rowCallback': function (row, data, dataIndex) {
                            // Get row ID
                            var rowId = data[1];

                            // If row ID is in the list of selected row IDs
                            if ($.inArray(rowId, rows_selected) !== -1) {
                                $(row).find('input[type="checkbox"]').prop('checked', true);
                                $(row).addClass('selected');
                            }
                        },
                        initComplete: function () {
                            var arr = [2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                                12, 13, 14, 15, 16, 17, 8, 19, 20, 21,
                                22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
                                32, 33, 34, 35, 36, 37, 38, 39, 40, 41];
                            this.api().columns(arr).every(function () {
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
            // Handle click on checkbox
            $('#tblOrdenPago tbody').on('click', 'input[type="checkbox"]', function (e) {
                var $row = $(this).closest('tr');

                // Get row data
                var data = table.row($row).data();

                // Se asigna que valor queremos obtener del check seleccionado
                var rowId = data[37];

                // Determine whether row ID is in the list of selected row IDs 
                var index = $.inArray(rowId, rows_selected);

                // If checkbox is checked and row ID is not in list of selected row IDs
                if (this.checked && index === -1) {
                    rows_selected.push(rowId);

                    // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
                } else if (!this.checked && index !== -1) {
                    rows_selected.splice(index, 1);
                }

                if (this.checked) {
                    $row.addClass('selected');
                } else {
                    $row.removeClass('selected');
                }

                // Update state of "Select all" control
                updateDataTableSelectAllCtrl(table);

                // Prevent click event from propagating to parent
                e.stopPropagation();
            });

            // Handle click on table cells with checkboxes
            $('#tblOrdenPago').on('click', 'tbody td, thead th:first-child', function (e) {
                $(this).parent().find('input[type="checkbox"]').trigger('click');
            });

            // Handle click on "Select all" control
            $('thead input[name="select_all"]', table.table().container()).on('click', function (e) {
                if (this.checked) {
                    $('#tblOrdenPago tbody input[type="checkbox"]:not(:checked)').trigger('click');
                } else {
                    $('#tblOrdenPago tbody input[type="checkbox"]:checked').trigger('click');
                }

                // Prevent click event from propagating to parent
                e.stopPropagation();
            });

            // Handle table draw event
            table.on('draw', function () {
                // Update state of "Select all" control
                updateDataTableSelectAllCtrl(table);
            });

            $("#tblOrdenPago tbody").on('dblclick', 'tr', function (evento) {
                evento.preventDefault();
                modal();
            });
            $('#tblOrdenPago tbody').on('click', 'tr', function (evento) {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
                evento.preventDefault();

                var instruccionId = $('td', this).eq(36).text();
                $("#instruccionId").val(instruccionId);
                var instruccionIdRep = $('td', this).eq(36).text();
                $("#instruccionIdRep").val(instruccionIdRep);
                var rastreo = $('td', this).eq(5).text();
                $("#claveRastreo").val(rastreo);
                var estado = $('td', this).eq(10).text();
                $("#estado").val(estado);
                var entrada = $('td', this).eq(34).text();
                $("#entrada").val(entrada);
                var ordenId = $('td', this).eq(37).text();
                $("#ordenId").val(ordenId);
                var folioOrden = $('td', this).eq(3).text();
                $("#folioOrden").val(folioOrden);
                var folioInstruccion = $('td', this).eq(4).text();
                $("#folioInstruccion").val(folioInstruccion);

            });
        }
    });
}

/**
 * Funcion que permite inicializar la trabla de la informacion del pago
 * @param {type} _ltabla
 * @returns {undefined}
 */
function iniTablaInfo(_ltabla) {
    var ltabla = _ltabla.lPrevew;
    lids = _ltabla.lOrdenId;
    var table = $("#tblInfo").DataTable(
            {
                destroy: true,
                data: ltabla,
                "order": [[4, "asc"]],
                "columns": [
                    {"data": null},
                    {"data": "0"},
                    {"data": "1"},
                    {"data": "2"},
                    {"data": "3"}
                ],
                "columnDefs": [
                    {
                        "searchable": false,
                        "orderable": false,
                        "targets": 0
                    },
                    {
                        "targets": 4, // El objetivo de la columna de posición, desde cero.
                        "data": "3", // La inclusión de datos
                        "render": function (data) {
                            var formato = format2(data, "$");
                            return formato;
                        }
                    }
                ]
            });
    table.on('order.dt search.dt', function () {
        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

}

/**
 * Funcion encargada de gestionar el elnvio de la orden de pago
 * @returns {undefined}
 */
function enviar() {
    block();
    if (rows_selected.length !== 0) {
        var datos = {
            lordenid: JSON.stringify(rows_selected),
            accion: 2,
            sucursal: $("#sucursal").val(),
            status: false
        };
        $.ajax({
            type: 'POST',
            url: "InstruccionServlet",
            data: datos,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                var lEstado = data.lEstadoOrden;
                if (lEstado.length === 0 || lEstado === null || lEstado === "") {

                    $('#modalInfo').modal('show');
                    var noPaquetes = data.estadoGeneral[0];
                    var noPagos = data.estadoGeneral[1];
                    var montoTotal = data.estadoGeneral[2];
                    var saldoActual = data.estadoGeneral[3];

                    var formato1 = format2(montoTotal, "$");
                    var formato2 = format2(saldoActual, "$");


                    document.getElementById('noPagos').innerHTML = noPagos;
                    document.getElementById('noPaquetes').innerHTML = noPaquetes;
                    document.getElementById('montoTotal').innerHTML = formato1;
                    document.getElementById('saldoActual').innerHTML = formato2;

                    var ltabla = data;
                    iniTablaInfo(ltabla);
                    unBlock();

                } else {
                    $.bootstrapGrowl(lEstado, {
                        type: 'danger',
                        delay: 9000
                    });
                    unBlock();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $.bootstrapGrowl("Error, ocurrio un error interno Consulte al Administrador ", {
                    type: 'danger',
                    delay: 8000
                });
                unBlock();
            }
        });
    } else {
        $.bootstrapGrowl("Error, No se ha seleccionado nunguna Orden de Pago de la Lista!", {
            type: 'danger',
            delay: 5000
        });
        unBlock();
    }
}

/**
 * Funcion encargada de cancela una orden de pago
 * @returns {undefined}
 */
function cancelar() {
    block();
    var instruccionId = $("#instruccionId").val();
    if (instruccionId !== "") {
        var datos = {
            instruccionId: $("#instruccionId").val(),
            folioInstruccion: $("#folioInstruccion").val(),
            folioOrden: $("#folioOrden").val(),
            fecha: $("#fecha").val(),
            estado: $("#estado").val(),
            accion: 5,
            sucursal: $("#sucursalF").val()
        };
        if (datos.estado === "POR LIQUIDAR") {
            $.ajax({
                type: 'POST',
                url: "InstruccionServlet",
                data: datos,
                success: function () {
                    $.ajax({
                        type: "POST",
                        url: "vistas/ordenPagos/listarOrdenPago.jsp", success: function (a) {
                            $.bootstrapGrowl("Orden Cancelada Correctamente", {
                                type: 'success',
                                delay: 5000
                            });
                            $("#contenido").html(a);
                            setTimeout(8000);
                            unBlock();
                        }
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $.bootstrapGrowl("Error, ocurrio un error interno Consulte al Administrador ", {
                        type: 'danger',
                        delay: 8000
                    });
                    unBlock();
                }
            });
        } else {
            $.bootstrapGrowl("Error, Estado: " + datos.estado + ", La Orden no puede ser Cancelada !", {type: 'danger',
                delay: 5000
            });
            unBlock();
        }
    } else {
        $.bootstrapGrowl("Error, No se ha seleccionado nunguna Orden de Pago de la Lista!", {
            type: 'danger',
            delay: 5000
        });
        unBlock();
    }
}

/**
 * Funcion encargada de gestionar la devolucion de una orden de pago 
 * @returns {undefined}
 */

function devolver() {
    block();
    var datos = {
        ordenId: $("#ordenId").val(),
        causa: $("#causaDev option:selected").val(),
        entrada: $("#entrada").val(),
        estado: $("#estado").val(),
        accion: 6
    };
    $.ajax({type: 'POST',
        url: "InstruccionServlet",
        data: datos,
        success: function () {
            $('#modalDevolucion').modal('hide');
            $.bootstrapGrowl("Orden Devuelta Correctamente", {
                type: 'success',
                delay: 5000
            });
            unBlock();
            iniTabla();
        },
        error: function () {
            $('#modalDevolucion').modal('hide');
            $.bootstrapGrowl("Ocurrio un Error interno, Verifica con Administrador!", {
                type: 'danger',
                delay: 8000
            });
            setTimeout(8000);
            unBlock();
        }
    });
}

/**
 * Funcion para mostrar los estados de la orden de pago en una ventana emergente
 * @returns {undefined}
 */
function modal() {
    $("#myModal").modal();
    mostrarEstados();
}

/**
 * permite mostrar lo estados de la orden de pago
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
 * Valida que se halla seleccionada una orden de pago para poder generar un 
 * reporte.
 * @param {type} f
 * @returns {Boolean}
 */
function formulario(f) {
    if (f.instruccionIdRep.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar una Orden de Pago de la lista para poder Imprimir el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.instruccionIdRep.focus();
        return false;
    }

    return true;
}

/**
 * limpia los campos encargados de generar el reporte
 * @returns {undefined}
 */
function limpiarLop() {
    document.getElementById("claveRastreo").value = "";
    document.getElementById("instruccionIdRep").value = "";
    document.getElementById("saldo").value = "";
    document.getElementById("instruccionId").value = "";
    document.getElementById("ordenId").value = "";
    document.getElementById("estado").value = "";
    document.getElementById("folioInstruccion").value = "";
    document.getElementById("folioOrden").value = "";
    document.getElementById("fecha").value = "";
    document.getElementById("entrada").value = "";
    document.getElementById("clavePartOrd").value = "";
    document.getElementById("claveCausaDev").value = "";


}

/**
 * Funcion encargada de declinar una orden de pago seleccionada
 * @returns {undefined}
 */
function declinar() {
    block();
    var datos = {
        instruccionId: $("#instruccionId").val(),
        accion: 4
    };
    $.ajax({
        type: 'POST',
        url: "InstruccionServlet",
        data: datos,
        success: function () {
            $.ajax({
                type: "POST",
                url: "vistas/ordenPagos/listarOrdenPago.jsp",
                success: function (a) {
                    $.bootstrapGrowl("Orden Declinada Correctamente", {
                        type: 'success',
                        delay: 5000
                    });
                    $("#contenido").html(a);
                    setTimeout(8000);
                    unBlock();
                },
                error: function () {
                    $.bootstrapGrowl("Ocurrio un error con la conexion consulte al Administrador", {
                        type: 'danger',
                        delay: 5000
                    });
                    setTimeout(8000);
                    unBlock();
                }
            });
        }
    });
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
 * Funcion encargada de Actualizar una orden de pago 
 * @returns {undefined}
 */
function actualizar() {
    block();
    var instruccionId = $("#instruccionId").val();
    var datos = {
        instruccionId: instruccionId,
        accion: 3
    };

    var estado = $("#estado").val();
    if (estado === "PARA ENVIAR") {
        $.ajax({
            type: 'POST',
            url: "ListarOrdenServlet",
            data: datos,
            success: function (data) {
                var dataset = JSON.parse(data);
                var importe = dataset[0][7].replace(/[^a-zA-Z 0-9.]+/g, '');
                var iva = dataset[0][8].replace(/[^a-zA-Z 0-9.]+/g, '');
                $("#contenido").load("vistas/ordenPagos/agregarOrdenPago.jsp",
                        function () {
                            //beneficiario
                            $("#ordenanteId").val(dataset[0][43]);
                            $("#numeroRastreo").val(dataset[0][4]);
                            $("#nombreOrdenante").val(dataset[0][10]);
                            $("#identificador").val(dataset[0][44]);
                            $("#cuentaOrdenante").val(dataset[0][12]);
                            $("#rfcOrdenante").val(dataset[0][13]);
                            $("#beneficiarioId").val(dataset[0][42]);
                            $("#nombreBeneficiario").val(dataset[0][14]);
                            $("#part").val(dataset[0][30]);
                            $("#clave").val(dataset[0][45]);
                            $("#cuentaBeneficiario").val(dataset[0][16]);
                            $("#rfcBeneficiario").val(dataset[0][17]);
                            $("#referenciaNum").val(dataset[0][24]);
                            $("#referenciaCo").val(dataset[0][25]);
                            $("#concepto").val(dataset[0][22]);
                            $("#importe").val(importe);
                            $("#accion").val(datos.accion);
                            $("#iva").val(iva);
                            $("#importeTotal").val(importe);
                            $("#instruccionId").val(dataset[0][36]);
                            $("#idOrden").val(dataset[0][37]);

                        });
                unBlock();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $.bootstrapGrowl("Error, ocurrio un error interno Consulte al Administrador ", {
                    type: 'danger',
                    delay: 8000
                });
                unBlock();
            }
        });
    } else {
        $.bootstrapGrowl(
                "Error, Estado: " + estado + " La Orden no puede ser Editada.", {
                    type: 'danger',
                    delay: 5000
                });
        unBlock();
    }
}

/**
 * funcion para llenar el conbo causa devolucion
 * @returns {undefined}
 */
function comboDevolucion() {
    var datosP = {
        accion: 2
    };
    $select = $('#causaDev');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
            console.log(JSON.stringify(data1));
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#causaDev").append('<option value="' + v.clave + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}


function abonar() {
    block();
    var datos = {
        instruccionId: $("#instruccionId").val(),
        usuario: $("#usuario").val(),
        accion: 7
    };
    $.ajax({
        type: 'POST',
        url: "InstruccionServlet",
        data: datos,
        success: function () {
            $.ajax({
                type: "POST",
                url: "vistas/ordenPagos/listarOrdenPago.jsp",
                success: function (a) {
                    $.bootstrapGrowl("Orden Abonada Correctamente", {
                        type: 'success',
                        delay: 5000
                    });
                    $("#contenido").html(a);
                    setTimeout(8000);
                    unBlock();
                },
                error: function () {
                    $.bootstrapGrowl("Ocurrio un error con la conexion consulte al Administrador", {
                        type: 'danger',
                        delay: 5000
                    });
                    setTimeout(8000);
                    unBlock();
                }
            });
        }
    });
}