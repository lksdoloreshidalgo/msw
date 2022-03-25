/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    var table2 = $("#tblDetalleOrden").DataTable(
            {
                "bPaginate": false,
                "info": false,
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
    table2.on('order.dt search.dt', function () {
        table2.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
    $('#tblDetalleOrden tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table2.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        $('#tblDetalleOrden tbody').on('click', 'tr', function (evento) {
            evento.preventDefault();
            var beneficiarioId = $('td', this).eq(1).text().trim();
            $("#beneficiarioId").val(beneficiarioId);

        });
    });


});