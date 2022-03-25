/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Funcion encargada de iniciar todos los eventos para gestionar
 * la orden de pago
 * @param {type} param
 */
$(document).ready(function () {
    cargarArchivo();
    iniTabla1();
});

function format(d) {
    var arr = d.lEstado;

    for (var i = 0; i < arr.length; i++) {
        var arrArr = arr[i];
        for (var j = 0; j < arrArr.length; j++) {
            arrArr[j];
        }
    }
    var imagen0, imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7
            , imagen8, imagen9, imagen10;

    if (arr[0][4] === true) {
        imagen0 = "correcto.png";
    } else {
        imagen0 = "incorrecto.png";
    }
    if (arr[1][4] === true) {
        imagen1 = "correcto.png";
    } else {
        imagen1 = "incorrecto.png";
    }
    if (arr[2][4] === true) {
        imagen2 = "correcto.png";
    } else {
        imagen2 = "incorrecto.png";
    }
    if (arr[3][4] === true) {
        imagen3 = "correcto.png";
    } else {
        imagen3 = "incorrecto.png";
    }
    if (arr[4][4] === true) {
        imagen4 = "correcto.png";
    } else {
        imagen4 = "incorrecto.png";
    }
    if (arr[5][4] === true) {
        imagen5 = "correcto.png";
    } else {
        imagen5 = "incorrecto.png";
    }
    if (arr[6][4] === true) {
        imagen6 = "correcto.png";
    } else {
        imagen6 = "incorrecto.png";
    }
    if (arr[7][4] === true) {
        imagen7 = "correcto.png";
    } else {
        imagen7 = "incorrecto.png";
    }
    if (arr[8][4] === true) {
        imagen8 = "correcto.png";
    } else {
        imagen8 = "incorrecto.png";
    }
    if (arr[9][4] === true) {
        imagen9 = "correcto.png";
    } else {
        imagen9 = "incorrecto.png";
    }
    if (arr[10][4] === true) {
        imagen10 = "correcto.png";
    } else {
        imagen10 = "incorrecto.png";
    }

    return '<table id="tblEstado" class="display table-bordered table-hover">' +
            '<tr>' +
            '<th>Numero</th>' +
            '<th>Estado</th>' +
            '<th>Nombre</th>' +
            '<th>Valor</th>' +
            '<th>Descripcion</th>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[0][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen0 + '"></td>' +
            '<td>' + arr[0][0] + '</td>' +
            '<td>' + arr[0][1] + '</td>' +
            '<td>' + arr[0][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[1][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen1 + '"></td>' +
            '<td>' + arr[1][0] + '</td>' +
            '<td>' + arr[1][1] + '</td>' +
            '<td>' + arr[1][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[2][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen2 + '"></td>' +
            '<td>' + arr[2][0] + '</td>' +
            '<td>' + arr[2][1] + '</td>' +
            '<td>' + arr[2][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[3][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen3 + '"></td>' +
            '<td>' + arr[3][0] + '</td>' +
            '<td>' + arr[3][1] + '</td>' +
            '<td>' + arr[3][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[4][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen4 + '"></td>' +
            '<td>' + arr[4][0] + '</td>' +
            '<td>' + arr[4][1] + '</td>' +
            '<td>' + arr[4][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[5][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen5 + '"></td>' +
            '<td>' + arr[5][0] + '</td>' +
            '<td>' + arr[5][1] + '</td>' +
            '<td>' + arr[5][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[6][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen6 + '"></td>' +
            '<td>' + arr[6][0] + '</td>' +
            '<td>' + arr[6][1] + '</td>' +
            '<td>' + arr[6][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[7][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen7 + '"></td>' +
            '<td>' + arr[7][0] + '</td>' +
            '<td>' + arr[7][1] + '</td>' +
            '<td>' + arr[7][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[8][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen8 + '"></td>' +
            '<td>' + arr[8][0] + '</td>' +
            '<td>' + arr[8][1] + '</td>' +
            '<td>' + arr[8][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[9][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen9 + '"></td>' +
            '<td>' + arr[9][0] + '</td>' +
            '<td>' + arr[9][1] + '</td>' +
            '<td>' + arr[9][3] + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' + arr[10][2] + '</td>' +
            '<td><img src="imagenes/iconos/' + imagen10 + '"></td>' +
            '<td>' + arr[10][0] + '</td>' +
            '<td>' + arr[10][1] + '</td>' +
            '<td>' + arr[10][3] + '</td>' +
            '</tr>' +
            '</table>';
}
function iniTabla1() {
    var table = $("#tblCargarArchivoOrden").DataTable();
}
/**
 * funcion encargada de inicializar el componente para la visualizacion del 
 * listado de las ordenes
 * @param {type} _data
 * @returns {undefined}
 */
function iniTabla(_data) {
    var dataSet = _data;
    var table = $("#tblCargarArchivoOrden").DataTable(
            {
//                dom: 'T<"clear">lfrtip',
//                tableTools: {
//                    "sRowSelect": "multi",
//                    "aButtons": ["select_all", "select_none"]
//                },
                destroy: true,
                data: dataSet,
                "columns": [
                    {
                        "className": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": ''
                    },
                    {"data": "estado"},
                    {"data": "nombreOrdenante"},
                    {"data": "numeroCuentaOrdenante"},
                    {"data": "rfcOrdenante"},
                    {"data": "claveParticipanteOrdenante"},
                    {"data": "nombreBeneficiario"},
                    {"data": "numeroCuentaBeneficiario"},
                    {"data": "rfcBeneficiario"},
                    {"data": "claveParticipanteBneneficiario"},
                    {"data": "monto"},
                    {"data": "claveRastreo"},
                    {"data": "concepto"}
                ],
                "columnDefs": [
                    /**
                     * El estado de la orden tomara forma de imagen
                     */
                    {
                        "targets": [1], // El objetivo de la columna de posición, desde cero.
                        "data": "estado", // La inclusión de datos
                        'searchable': false, //no podra ser buscado
                        'orderable': false, //no podra ser ordenado
                        "render": function (data) {// Devuelve el contenido personalizado
                            var imagen;
                            if (data === true) {
                                imagen = "correcto.png";
                            } else {
                                imagen = "incorrecto.png";
                            }
                            return '<img src="imagenes/iconos/' + imagen + '">';
                        }
                    },
                    {
                        "targets": [10], // El objetivo de la columna de posición, desde cero.
                        "data": "monto", // La inclusión de datos
                        "render": function (data) {
                            var formato = format2(parseInt(data), "$");
                            return formato;
                        }
                    }
                ],
                "order": [[1, 'asc']],
                deferRender: true
            }
    );
    /**
     * Add event listener for opening and closing details
     */
    $('#tblCargarArchivoOrden tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row(tr);

        if (row.child.isShown()) {             /**
         * This row is already open - close it
         */
            row.child.hide();
            tr.removeClass('shown');
        } else {             /**
         * Open this row
         */
            row.child(format(row.data())).show();
            tr.addClass('shown');
        }
    });
    $("#insertar").click(function () {
        if (dataSet.length !== 0 || dataSet.length !== null) {
            var x = document.getElementById("incorrectos").value;
            if (x === "0") {
                $.confirm({
                    title: 'Confirmacion!',
                    content: 'Realmente desea Insertar las Orden de Pago!',
                    confirm: function () {
                        insertarOrden(dataSet);
                    },
                    cancel: function () {
                    }
                }).click(function (e) {
                    e.preventDefault();
                });
            } else {
                $.bootstrapGrowl("Error, Existen: " + x + " pagos Incorrectos. ", {
                    type: 'danger',
                    delay: 5000
                });
            }

        } else {
            $.bootstrapGrowl("Error, Carga un archivo para poder Insertar. ", {
                type: 'danger',
                delay: 5000
            });
        }

    });
    contador(dataSet);
}
/**
 * funcion que permitre cargar un archivo de extencion ".CSV" y procesarlo 
 * en ordenes validas
 * @returns {undefined}
 */
function cargarArchivo() {
// The event listener for the file upload
    document.getElementById('archivo').addEventListener('change', upload, false);
    // Method that checks that the browser supports the HTML5 File API
    function browserSupportFileUpload() {
        var isCompatible = false;
        if (window.File && window.FileReader && window.FileList && window.Blob) {
            isCompatible = true;
        }
        return isCompatible;
    }

    // Method that reads and processes the selected file
    function upload(evt) {
        if (!browserSupportFileUpload()) {
            alert('The File APIs are not fully supported in this browser!');
        } else {
            var archivo = $("#archivo").val();
            if (validarArchivo(archivo)) {
                block();
                var dataArreglo = null;
                var file = evt.target.files[0];
                var reader = new FileReader();
                reader.readAsText(file);
                reader.onload = function (event) {
                    var csvData = event.target.result;
                    dataArreglo = $.csv.toArrays(csvData);
                    if (dataArreglo && dataArreglo.length > 0) {
                        var datos = {
                            arreglo: JSON.stringify(dataArreglo)
                        };
                        $.ajax({
                            processData: true,
                            type: 'POST',
                            url: "CargaArchivoOrdenServlet",
                            dataType: "json",
                            data: datos,
                            success: function (data) {
                                var lOrden = data.lOrden;
                                var estado = data.estado;
                                var error = data.descripcion;

                                if (estado === 0) {
                                    $.bootstrapGrowl("El documento se Cargo Correctamente", {
                                        type: 'success',
                                        delay: 5000
                                    });
                                    unBlock();
                                    iniTabla(lOrden);
                                } else {
                                    unBlock();
                                    $.bootstrapGrowl(error, {
                                        type: 'danger',
                                        delay: 8000
                                    });
                                }

                            }
                        });
                    } else {
                        $.bootstrapGrowl("Error, El archivo no cumple con el formato adecuado. ", {
                            type: 'danger',
                            delay: 5000
                        });
                    }
                    unBlock();
                };
                reader.onerror = function () {
                    alert('Unable to read ' + file.fileName);
                };
                unBlock();
            }
        }
    }
}
/**
 * funcion encargada de insertar las ordenes masivas 
 * @param {type} _data
 * @returns {undefined}
 */
function insertarOrden(_data) {
    block();
    var lo = _data;
    var datosLo = {
        lArreglo: JSON.stringify(lo)
    };
    $.ajax({
        processData: true,
        type: 'POST',
        url: "InsertarAordenServlet",
        dataType: "json",
        data: datosLo,
        success: function (data) {
            if (data.id === 0) {
                $.ajax({
                    type: "POST",
                    url: "vistas/ordenPagos/listarOrdenPago.jsp",
                    success: function (a) {
                        $("#contenido").html(a);
                        unBlock();
                    }
                });
                $.bootstrapGrowl("Exito, " + data.mensaje, {
                    type: 'success',
                    delay: 5000
                });
            } else {
                $.bootstrapGrowl("Error, " + data.mensaje, {
                    type: 'danger',
                    delay: 5000
                });
                unBlock();
            }
        }
    });
}
function validarArchivo(archivo) {
    var extensiones_permitidas = ".csv";
    var mierror = "";
    if (!archivo) {
        //Si no tengo archivo, es que no se ha seleccionado un archivo en el formulario 
        mierror = "No has seleccionado ningún archivo";
    } else {
        //recupero la extensión de este nombre de archivo 
        var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
        //alert (extension); 
        //compruebo si la extensión está entre las permitidas 
        var permitida = false;
//        for (var i = 0; i < extensiones_permitidas.length; i++) {
        if (extensiones_permitidas === extension) {
            permitida = true;
//                break;
        }
//        }
        if (!permitida) {
            mierror = "Solo se pueden cargar Archivos con extencion : "
                    + extensiones_permitidas;
        } else {
            //submito! 
//            alert("Todo correcto. Voy a submitir el formulario.");
            $.bootstrapGrowl("Formato Correcto", {
                type: 'success',
                delay: 5000
            });
            return 1;
        }
    }
    //si estoy aqui es que no se ha podido submitir 
//    alert(mierror);
    $.bootstrapGrowl("Error, " + mierror, {
        type: 'danger',
        delay: 5000
    });
    return 0;
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
 * Funcion encargada de contar el numero de ordenes correctas e incorrectas 
 * y mostrarlas en la pantalla para su facil visualizacion 
 * @param {type} dataSet
 * @returns {undefined}
 */

function contador(dataSet) {
    var lOrden = dataSet;
    var correctas = 0;
    var incorrectas = 0;
    var estados = Boolean("true");
    for (i = 0; i < lOrden.length; i++) {
        estados = lOrden[i].estado;
        if (estados === true) {
            correctas++;
        } else {
            incorrectas++;
        }
    }
    document.getElementById('correctos').value = correctas;
    document.getElementById('incorrectos').value = incorrectas;
}
