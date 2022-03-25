/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    iniTabla();
});
/**
 * funcion encargada de inicializar el listado de los participantes
 * @returns {undefined}
 */
function iniTabla() {
    var datosP = {
        accion: 3
    };
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblEstadoOrden").DataTable(
                    {
                        data: dataset,
                        "columns": [
                            {"data": null},
                            {"data": "clave"},
                            {"data": "descripcion"}
                        ],
                        "columnDefs": [{
                                "searchable": false,
                                "orderable": false,
                                "targets": 0
                            }],
                        "order": [[1, 'asc']]
                    });
            table.on('order.dt search.dt', function () {
                table.column(0, {
                    search: 'applied',
                    order: 'applied'
                }).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();
        }
    });
}
