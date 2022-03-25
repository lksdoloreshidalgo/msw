/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 Created on : 06/10/2015, 12:07:50 AM
 Author     : LUNA
 */
$(document).ready(function () {
    iniTabla();
    $("#limpiarCLABE").click(function () {
        limpiarCLABE();
    });
});

function iniTabla() {
    var table = $("#tblRepOrdenante").DataTable(
            {
                dom: 'T<"clear">lfrtip',
                tableTools: {
                    "sRowSelect": "single"
                },
                "columnDefs": [{
                        "searchable": false,
                        "orderable": false,
                        "targets": 0
                    }],
                "order": [[1, 'asc']]
            });
    table.on('order.dt search.dt', function () {
        table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
    //configuramos la seleccion de los datos mediantre la tabla
    $('#tblRepOrdenante tbody').on('click', 'tr', function () {
        document.getElementById("accion").value = 2;

        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        $('#tblRepOrdenante tbody').on('click', 'tr', function (evento) {
            evento.preventDefault();

            var identificador = $('td', this).eq(2).text().trim();
            $("#identificador").val(identificador);
            var nombreO = $('td', this).eq(1).text().trim();
            $("#nombreO").val(nombreO);

        });
    });
}
function formulario(f) {
    if (f.identificador.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar un Ordenante de la lista\n\
 para poder generar el Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.identificador.focus();
        return false;
    }

    $.bootstrapGrowl("Reporte generado correctamente!", {
        type: 'success',
        delay: 5000
    });
    return true;
}
function limpiarCLABE() {
    document.getElementById("nombreO").value = "";
    document.getElementById("identificador").value = "";


}