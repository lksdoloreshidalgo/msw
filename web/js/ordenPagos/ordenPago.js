/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    iniTablaBen();
    var sucursal = $("#sucursal").val();
    iniTablaOrd(sucursal);
    getRefNum();
    $("#submit").click(function () {
        validaCampos();
    });
//    limpiarDatosOp();

});

/**
 * Funcion que calcula el IVA
 * @returns {undefined}
 */
function calcularIva() {

    var importe = $("#importe").val();
    var iva = (parseFloat(0));
    var ivaD = iva.toFixed(2);
    document.getElementById("iva").value = ivaD;
    document.getElementById("importeTotal").value = importe;
}

function submit() {
    createClaveR();
    block();
    var idOrden = $("#idOrden").val();
    if (!idOrden) {
        document.getElementById("accion").value = 1;
    } else {
        document.getElementById("accion").value = 3;
    }
    var datosOrden = {
        ordenanteId: $("#ordenanteId").val(),
        beneficiarioId: $("#beneficiarioId").val(),
        numeroRastreo: $("#numeroRastreo").val(),
        claveParicipante: $("#clave").val(),
        referenciaNum: $("#referenciaNum").val(),
        referenciaCo: $("#referenciaCo").val(),
        concepto: $("#concepto").val(),
        monto: $("#importe").val(),
        iva: $("#iva").val(),
        importeTotal: $("#importeTotal").val(),
        numeroPagos: 1,
        accion: $("#accion").val(),
        idOrden: $("#idOrden").val(), //83
        sucursal: $("#sucursal").val(),
        instruccionId: $("#instruccionId").val(),
        usuario: $("#usuario").val()
    };
    $.ajax({
        type: 'POST',
        url: "InstruccionServlet",
        data: datosOrden,
        success: function () {
            if (datosOrden.accion === 1) {
                $.bootstrapGrowl("Orden Insertada Correctamente", {
                    type: 'success',
                    delay: 5000
                });
            }
            if (datosOrden.accion === 3) {
                $.bootstrapGrowl("Orden Editada Correctamente", {
                    type: 'success',
                    delay: 5000
                });
            }
            $.ajax({
                type: "POST",
                url: "vistas/ordenPagos/listarOrdenPago.jsp",
                success: function (a) {
                    $("#contenido").html(a);
                    setTimeout(4000);
                    unBlock();
                },
                error: function () {
                    $.bootstrapGrowl("Ocurrio un error consulta al administrador", {
                        type: 'danger',
                        delay: 5000
                    });
                    setTimeout(4000);
                    unBlock();
                }
            });
        }
    });
}

/**
 * funcion que valida que los compos no vallan vacios
 * @returns {Boolean}
 */
function validaCampos() {

    var nombreOrdenante = document.getElementById("nombreOrdenante").value;
    if (nombreOrdenante.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Agregar a un Cuentahabiente-Ordenante de la Lista!"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    var nombreBeneficiario = document.getElementById("nombreBeneficiario").value;
    if (nombreBeneficiario.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Agregar a un Cuentahabiente-Beneficiario de la Lista!"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    var importe = document.getElementById("importe").value;
    if (importe.length !== 0) {
        if (importe <= 0) {
            $.bootstrapGrowl(
                    "Error, El importe debe ser mayor a 0"
                    , {
                        type: 'danger',
                        delay: 5000
                    });
            return false;
        } else if (importe > 9999999999999999.99) {
            $.bootstrapGrowl(
                    "Error, El importe no puede ser mayor a 9,999,999,999,999,999.99"
                    , {
                        type: 'danger',
                        delay: 5000
                    });
            return false;
        }
    } else {
        $.bootstrapGrowl(
                "Se debe de Ingresar el Importe de la Orden"
                , {
                    type: 'danger',
                    delay: 5000
                });

        return false;
    }
    var iva = document.getElementById("iva").value;
    if (iva.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Ingresar el Importe de la Orden"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    var importeTotal = document.getElementById("importeTotal").value;
    if (importeTotal.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Ingresar el Importe de la Orden"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    var referenciaNum = document.getElementById("referenciaNum").value;
    if (referenciaNum.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Ingresar la referencia Numerica de la Orden"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    var concepto = document.getElementById("concepto").value;
    if (concepto.length === 0) {
        $.bootstrapGrowl(
                "Se debe de Ingresar el Concepto de la Orden"
                , {
                    type: 'danger',
                    delay: 5000
                });
        return false;
    }
    submit();
    return true;
}

/**
 * Funcion que permite limpiar todos los campos del formulario
 * @returns {undefined}
 */
function limpiarDatosOp() {
    document.getElementById("ordenanteId").value = "";
    document.getElementById("beneficiarioId").value = "";
    document.getElementById("numeroRastreo").value = "";
    document.getElementById("clave").value = "";
    document.getElementById("referenciaNum").value = "";
    document.getElementById("referenciaCo").value = "";
    document.getElementById("concepto").value = "";
    document.getElementById("importe").value = "";
    document.getElementById("iva").value = "";
    document.getElementById("importeTotal").value = "";
    document.getElementById("accion").value = 1;
    document.getElementById("nombreOrdenante").value = "";
    document.getElementById("identificador").value = "";
    document.getElementById("cuentaOrdenante").value = "";
    document.getElementById("rfcOrdenante").value = "";
    document.getElementById("nombreBeneficiario").value = "";
    document.getElementById("part").value = "";
    document.getElementById("cuentaBeneficiario").value = "";
    document.getElementById("rfcBeneficiario").value = "";
    getRefNum();
}

/**
 * Funcion que permite validar campos de solo numeros
 * @param {type} e
 * @returns {Boolean}
 */
function validaSoloNumeros(e) {
    tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla === 8)
        return true;
    if (tecla === 46)
        return true;
    patron = /^([0-9])*$/;
    te = String.fromCharCode(tecla); // 5
    return patron.test(te); // 6
}

/**
 * Funcion que permite crear la clave de rastreo
 * @returns {undefined}
 */
function createClaveR() {

    var identificador = $("#identificador").val();
    var d = new Date();
    var claveRastreo = "" + d.getDate() + "" + d.getMonth() + "" +
            d.getFullYear() + "" + d.getHours() + "" + d.getMinutes() + "" +
            d.getSeconds() + "" + identificador;
    document.getElementById("numeroRastreo").value = claveRastreo;
}

/**
 * Funcion que permite crear una referencia numerica aleatoriamente
 * @returns {undefined}
 */
function getRefNum() {

    var uno = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var dos = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var tres = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var cuatro = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var cinco = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var seis = Math.floor(Math.random() * (9 - 0) + 0).toString();
    var siete = Math.floor(Math.random() * (9 - 0) + 0).toString();

    var refNumerica = uno + dos + tres + cuatro + cinco + seis + siete;
    document.getElementById("referenciaNum").value = refNumerica.toString();
}

function permite(elEvento, permitidos) {
    // Variables que definen los caracteres permitidos
    var numeros = "0123456789";
    var caracteres = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var numeros_caracteres = numeros + caracteres;
    var teclas_especiales = [8, 37, 39, 46];
    // 8 = BackSpace, 46 = Supr, 37 = flecha izquierda, 39 = flecha derecha


    // Seleccionar los caracteres a partir del parámetro de la función
    switch (permitidos) {
        case 'num':
            permitidos = numeros;
            break;
        case 'car':
            permitidos = caracteres;
            break;
        case 'num_car':
            permitidos = numeros_caracteres;
            break;
    }

    // Obtener la tecla pulsada 
    var evento = elEvento || window.event;
    var codigoCaracter = evento.charCode || evento.keyCode;
    var caracter = String.fromCharCode(codigoCaracter);

    // Comprobar si la tecla pulsada es alguna de las teclas especiales
    // (teclas de borrado y flechas horizontales)
    var tecla_especial = false;
    for (var i in teclas_especiales) {
        if (codigoCaracter === teclas_especiales[i]) {
            tecla_especial = true;
            break;
        }
    }

    // Comprobar si la tecla pulsada se encuentra en los caracteres permitidos
    // o si es una tecla especial
    return permitidos.indexOf(caracter) !== -1 || tecla_especial;
}

/**
 * Funcion que inicializa la tabla del beneficiario
 * @returns {undefined}
 */
function iniTablaBen() {
    var datosP = {
        accion: 5
    };
    $.ajax({
        type: "POST",
        url: "AddBeneficiarioServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblBeneficiario").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        "columns": [
                            {"data": null},
                            {"data": "nombre"},
                            {"data": "cuenta.numero"},
                            {"data": "rfc"},
                            {"data": "cuenta.tipoCuenta.descripcion"},
                            {"data": "cuenta.participante.descripcion"},
                            {"data": "estado"}
                        ],
                        "columnDefs": [
                            /**
                             * El estado de la orden tomara forma de imagen
                             */
                            {
                                "targets": [6], // El objetivo de la columna de posición, desde cero.
                                "data": "estado", // La inclusión de datos
                                'searchable': false, //no podra ser buscado
                                'orderable': false, //no podra ser ordenado //data
                                "render": function (data) {// Devuelve el contenido personalizado
                                    var imagen;
                                    if (data === true) {
                                        imagen = "correcto.png";
                                    } else {
                                        imagen = "incorrecto.png";
                                    }
                                    return '<img src="imagenes/iconos/' + imagen + '">';
                                }
                            }
                        ],
                        "order": [[1, 'asc']]
                    });
            table.on('order.dt search.dt', function () {
                table.column(0,
                        {
                            search: 'applied',
                            order: 'applied'
                        }
                )
                        .nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();
            /**
             * parte encargada del display de la tabla para el beneficiario.
             */
            $('#tblBeneficiario tbody').on('click', 'tr', function (evento) {
                var fila = table.row(this).index();
                var activo = dataset[fila].estado;
                if (activo === true) {
                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                    } else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                    evento.preventDefault();
                    var beneficiarioId = dataset[fila].personaId;
                    $("#beneficiarioId").val(beneficiarioId);
                    var nombreBeneficiario = $('td', this).eq(1).text().trim();
                    $("#nombreBeneficiario").val(nombreBeneficiario);
                    var cuentaBeneficiario = $('td', this).eq(2).text().trim();
                    $("#cuentaBeneficiario").val(cuentaBeneficiario);
                    var rfcBeneficiario = $('td', this).eq(3).text().trim();
                    $("#rfcBeneficiario").val(rfcBeneficiario);
                    var part = $('td', this).eq(5).text().trim();
                    $("#part").val(part);
                    var clave = dataset[fila].cuenta.participante.clave;
                    $("#clave").val(clave);
                    var cuentaId = dataset[fila].cuenta.cuentaId;
                    $("#cuentaId").val(cuentaId);
                    $('#myModal2').modal('hide');
                } else {
                    $.bootstrapGrowl("No puedes seleccionar a un Beneficiario dado de BAJA.", {
                        type: 'danger',
                        delay: 5000
                    });
                }
            });
        }
    });
}

/**
 * Funcion que inicializa la tabla del beneficiario
 * @param {type} su
 * @returns {undefined}
 */
function iniTablaOrd(su) {
    var datosP = {
        accion: 5,
        sucursal: su
    };
    $.ajax({
        type: "POST",
        url: "AddOrdenanteServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblOrdenante").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        "columns": [
                            {"data": null},
                            {"data": 0},
                            {"data": 1},
                            {"data": 2},
                            {"data": 3},
                            {"data": 4}
                        ],
                        "columnDefs": [
                            /**
                             * El estado de la orden tomara forma de imagen
                             */
                            {
                                "targets": [5], // El objetivo de la columna de posición, desde cero.
                                "data": 4, // La inclusión de datos
                                'searchable': false, //no podra ser buscado
                                'orderable': false, //no podra ser ordenado //data
                                "render": function (data) {// Devuelve el contenido personalizado
                                    var imagen;
                                    if (data === true) {
                                        imagen = "correcto.png";
                                    } else {
                                        imagen = "incorrecto.png";
                                    }
                                    return '<img src="imagenes/iconos/' + imagen + '">';
                                }
                            }
                        ],
                        "order": [[1, 'asc']]
                    });
            table.on('order.dt search.dt', function () {
                table.column(0,
                        {
                            search: 'applied',
                            order: 'applied'
                        }
                )
                        .nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();
            /**
             * parte encargada del display de la tabla para el beneficiario.
             */
            $('#tblOrdenante tbody').on('click', 'tr', function (evento) {
                var fila = table.row(this).index();
                var activo = dataset[fila][4];
                if (activo === true) {
                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                    } else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                    evento.preventDefault();
                    var ordenanteId = dataset[fila][5];
                    $("#ordenanteId").val(ordenanteId);
                    var nombreOrdenante = $('td', this).eq(1).text();
                    $("#nombreOrdenante").val(nombreOrdenante);
                    var rfcOrdenante = $('td', this).eq(3).text();
                    $("#rfcOrdenante").val(rfcOrdenante);
                    var cuentaOrdenante = $('td', this).eq(2).text();
                    $("#cuentaOrdenante").val(cuentaOrdenante);
                    var identificador = $('td', this).eq(4).text();
                    $("#identificador").val(identificador);
                    $('#myModal').modal('hide');
                } else {
                    $.bootstrapGrowl("No puedes seleccionar a un Ordenante dado de BAJA.", {
                        type: 'danger',
                        delay: 5000
                    });
                }
            });
        }
    });
}
