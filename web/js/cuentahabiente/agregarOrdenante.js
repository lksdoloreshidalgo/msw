/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 Created on : 31/07/2015, 11:43:50 AM
 Author     : LUNA
 */

$(document).ready(function () {
    iniTabla();

    $("#submit").click(function () {
        document.getElementById("estadoOculto").value = 0;
        validaCampos();

    });
    limpiarDatos();
    datosEntidad();

    $("#alta").hide();
    $("#baja").hide();

    $("#alta").click(function () {
        document.getElementById("estadoOculto").value = 0;
        validaCampos();
    });

    $("#baja").click(function () {
        document.getElementById("estadoOculto").value = "1";
        validaCampos();
    });
//    comboDigitoClabe();
});

/**
 * Created on : 02/09/2016, 11:43:50 AM
 * Author     : Anayeli Torres
 * Función que carga el combo de los tipo de cuentas.
 * @param {type} numDebito
 * @returns {undefined}
 */
function comboTipoCuenta(numDebito) {
    var datosTC = {
        accion: 3
    };
    //$select = $('#tipoCuenta');
    $.ajax({
        type: "POST",
        url: "AddOrdenanteServlet",
        data: datosTC,
        success: function (data) {
            var data1 = JSON.parse(data);
            $.each(data1, function (k, v) {
                $("#tipoCuenta").append('<option value="' + v.clave + '">'
                        + '' + v.descripcion + '</option>');
            });
            $("#tipoCuenta").change(function () {
                var tipoCuenta = $("#tipoCuenta").val();
                if (tipoCuenta === "40") {
                    $("#numero").attr("disabled", "disabled");
                    $("#numero").val("");
                } else if (tipoCuenta === "3" && numDebito === 0) {
                    $("#numero").attr("disabled", "disabled");
                    $("#numero").val("NO APLICA");
                } else {
                    $("#numero").attr("disabled", false);
                    $("#numero").val("");
                }

            });
            unBlock();
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
            $.bootstrapGrowl("ERROR DE CONEXION AL CARGAR EL COMBO TIPO DE CUENTA.", {
                type: 'danger'
            });
            unBlock();
        }
    });
}

/**
 * Created on : 05/09/2016, 11:43:50 AM
 * Author     : Anayeli Torres
 * Función obtiene los datos de la entidad.
 */
function datosEntidad() {
    var datosE = {
        accion: 4
    };
    $.ajax({
        type: "POST",
        url: "AddOrdenanteServlet",
        data: datosE,
        success: function (data) {
            var data1 = JSON.parse(data);
            var numeroDebito = data1.numeroDebito;
            $("#numeroDebito").val(numeroDebito);
            comboTipoCuenta(numeroDebito);
            unBlock();
        },
        error: function () {
            $.bootstrapGrowl("ERROR DE CONEXION AL CARGAR DATOS ENTIDAD.", {
                type: 'danger',
                delay: 8000
            });
            unBlock();
        }
    });
}

function iniTabla() {
    var datosOrdenante = {
        accion: 5,
        sucursal: $("#sucursal").val()
    };

    $.ajax({
        type: "POST",
        url: "AddOrdenanteServlet",
        data: datosOrdenante,
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
                        "columnDefs": [{
                                "targets": 5,
                                "data": 4, // La inclusión de datos
                                'searchable': false,
                                'orderable': false,
                                'className': 'dt-body-center',
                                "render": function (data) {
                                    var imagen;
                                    if (data === true) {
                                        imagen = "correcto.png";
                                    } else {
                                        imagen = "incorrecto.png";
                                    }
                                    return '<img src="imagenes/iconos/' + imagen + '">';
                                }
                            }],
                        "order": [[1, 'asc']]
                    }
            );

            table.on('order.dt search.dt', function () {
                table.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();

            $('#tblOrdenante tbody').on('click', 'tr', function (evento) {
                document.getElementById("accion").value = 2;
                $("#submit span").text("Actualizar");
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }

                var fila = table.row(this).index();

                evento.preventDefault();
                var nombre = $('td', this).eq(1).text().trim();
                $("#nombre").val(nombre);

                var numero = $('td', this).eq(2).text().trim();
                $("#numero").val(numero);
                $("#numerotel").val(numero);
                var rfc = $('td', this).eq(3).text().trim();
                $("#rfc").val(rfc);
                var identificador = $('td', this).eq(4).text().trim();
                $("#identificador").val(identificador);
                $("#rep").val(numero);

                var personaId = dataset[fila][5];
                $("#personaId").val(personaId);
                var ordenanteId = dataset[fila][6];
                $("#ordenanteId").val(ordenanteId);
                var estado = dataset[fila][4];

                var tipoCuenta = dataset[fila][8];
                $("#tipoCuenta").val(tipoCuenta);
//                var digitoClabe = dataset[fila][7];
//                $("#digitoClabe").val(digitoClabe);

                if (numero.length === 10 && estado === true) {
//                    $("#tipoCuenta").val("10");
                    $("#tipoCuenta").attr("disabled", false);
//                    $("#digitoClabe").attr("disabled", false);
                    $("#numero").attr("disabled", false);
                    $("#nombre").attr("disabled", false);
                    $("#rfc").attr("disabled", false);
                    $("#identificador").attr("disabled", false);
                    $("#estadoCHO").val("0");
                    $("#estadoCHO").attr("disabled", true);
                    $("#baja").show();
                    $("#alta").hide();
                    $("#submit").show();
                } else if (numero.length === 18 && estado === true) {
//                    $("#tipoCuenta").val("40");
                    $("#tipoCuenta").attr("disabled", true);
//                    $("#digitoClabe").attr("disabled", true);
                    $("#numero").attr("disabled", "disabled");
                    $("#nombre").attr("disabled", false);
                    $("#rfc").attr("disabled", false);
                    $("#identificador").attr("disabled", false);
                    $("#estadoCHO").val("0");
                    $("#estadoCHO").attr("disabled", true);
                    $("#baja").show();
                    $("#alta").hide();
                    $("#submit").show();
                } else if (numero.length === 16 && estado === true) {
//                    $("#tipoCuenta").val("3");
                    $("#tipoCuenta").attr("disabled", false);
//                    $("#digitoClabe").attr("disabled", false);
                    $("#numero").attr("disabled", false);
                    $("#nombre").attr("disabled", false);
                    $("#rfc").attr("disabled", false);
                    $("#identificador").attr("disabled", false);
                    $("#estadoCHO").val("0");
                    $("#estadoCHO").attr("disabled", true);
                    $("#baja").show();
                    $("#alta").hide();
                    $("#submit").show();
                } else if (estado === false) {
                    $("#estadoCHO").val("1");
                    $("#estadoCHO").attr("disabled", true);
                    $("#alta").show();
                    $("#baja").hide();
                    $("#submit").hide();
                    $("#nombre").attr("disabled", true);
                    $("#rfc").attr("disabled", true);
                    $("#identificador").attr("disabled", true);
//                    if (numero.length === 10) {
//                        $("#tipoCuenta").val("10");
//                    } else if (numero.length === 18) {
//                        $("#tipoCuenta").val("40");
//                    } else if (numero.length === 16) {
//                        $("#tipoCuenta").val("3");
//                    }
                    $("#tipoCuenta").attr("disabled", true);
//                    $("#digitoClabe").attr("disabled", true);
                    $("#numero").attr("disabled", true);
                }
            });
            unBlock();
        },
        error: function () {
            MensajeError("ERROR AL CARGAR LA TABLA ENTIDADES.");
            unBlock();
        }
    });
}


/**
 * funcion que permite  Insertar un cuentahabiente Ordenante
 * @returns {undefined}
 */
function  submit() {
    var estado = $("#estadoOculto").val();
    if (estado === "0") {
        estado = "true";
    } else {
        estado = "false";
    }
    block();
    var datos = {
        nombre: $("#nombre").val(),
        rfc: $("#rfc").val(),
        identificador: $("#identificador").val(),
        ordenanteId: $("#ordenanteId").val(),
        accion: $("#accion").val(),
        sucursal: $("#sucursal").val(),
        numero: $("#numero").val(),
        numero1: $("#numerotel").val(),
        personaId: $("#personaId").val(),
        tipoCuenta: $("#tipoCuenta").val(),
        estado: estado
//        digitoClabe: $("#digitoClabe").val()
    };
    $.ajax({
        type: 'POST',
        url: "AddOrdenanteServlet",
        data: datos,
        success: function (res) {
            var res1 = JSON.parse(res);
            if (res1.estado === 0 || res1 === 0) {
                if (datos.accion === "1") {
                    $.bootstrapGrowl("Datos de Ordenante Insertados Correctamente.", {
                        type: 'success',
                        delay: 5000
                    });
                    limpiarDatos();
                    unBlock();
                } else {
                    $.bootstrapGrowl("Datos de Ordenante Actualizados Correctamente.", {
                        type: 'success',
                        delay: 5000
                    });
                    limpiarDatos();
                    unBlock();
                }
                $.ajax({
                    type: "POST",
                    url: "vistas/cuentahabientes/agregarOrdenante.jsp",
                    success: function (a) {
                        $("#contenido").html(a);
                        setTimeout(4000);
                        unBlock();
                    },
                    error: function (textStatus) {
                        alert(textStatus + res1 + " Consulta al administrador1.");
                        unBlock();
                    }
                });
            } else {
                $.bootstrapGrowl("Error, Se esta tratando de ingresar una cuenta \n\
CLABE duplicada. ", {
                    type: 'danger',
                    delay: 7000});
                limpiarDatos();
                unBlock();
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            $.bootstrapGrowl("Consulta al administrador.", {
                type: 'danger',
                delay: 5000});
            limpiarDatos();
            unBlock();
        }
    });
}
/**
 * Funcion encargada de validar los campos del formulario
 * @returns {Boolean}
 */
function validaCampos() {
    var nombre = document.getElementById("nombre").value;
    if (nombre.length === 0) {
        $.bootstrapGrowl("Campo Nombre, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        unBlock();
        return false;
    }
    var rfcStr = document.getElementById("rfc").value;
    if (rfcStr.length > 0) {
        var strCorrecta;
        strCorrecta = rfcStr;
        if (rfcStr.length === 12) {
            var valid = '^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
        } else {
            var valid = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
        }
        var validRfc = new RegExp(valid);
        var matchArray = strCorrecta.match(validRfc);
        if (matchArray === null) {
            $.bootstrapGrowl("Error, formato Sugerido  ABCD-987654-1ER ", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else {
        $.bootstrapGrowl("Campo RFC, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        unBlock();
        return false;
    }
    var identificador = document.getElementById("identificador").value;
    if (identificador.length === 0) {

        $.bootstrapGrowl("Campo Numero de Cliente, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    } else if (identificador.length !== 7) {
        $.bootstrapGrowl("El numero de cliente Debe ser de 7 caracteres!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
//    var digitoClabe = document.getElementById("digitoClabe").value;
//    if (digitoClabe.length === 0) {
//        $.bootstrapGrowl("Campo Digito CLABE, Vacio!", {
//            type: 'danger',
//            delay: 5000
//        });
//        return false;
//    }

    //Autor: Anayeli Torres
    var tipoCuenta = document.getElementById("tipoCuenta").value;
    if (tipoCuenta.length === 0) {
        $.bootstrapGrowl("Campo Tipo Cuenta, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    } else if (tipoCuenta === "40") {
        submit();
        return true;
    } else if (tipoCuenta === "10") {
        var numero = document.getElementById("numero").value;
        if (numero.length === 0) {
            $.bootstrapGrowl("Campo Numero, Vacio!", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
        if (numero.length === 10) {
            submit();
            return true;
        } else {
            $.bootstrapGrowl("Error, Verifica que el campo numero contenga 10 digitos !", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else if (tipoCuenta === "3") {
        var numDebito = $("#numeroDebito").val();
        var numero = document.getElementById("numero").value;
        if (numDebito !== "0") {
            if (numero.length === 0) {
                $.bootstrapGrowl("Campo Numero, Vacio!", {
                    type: 'danger',
                    delay: 5000
                });
                return false;
            }
            if (numero.length === 16) {
                submit();
                return true;
            } else {
                $.bootstrapGrowl("Error, Verifica que el campo numero contenga 16 digitos !", {
                    type: 'danger',
                    delay: 5000
                });
                return false;
            }
        } else {
            $.bootstrapGrowl("Error, No se cuenta con servicio de Debito en esta sucursal.", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else if (tipoCuenta !== "10" && tipoCuenta !== "3" && tipoCuenta !== "40") {
        $.bootstrapGrowl("Error Tipo de cuenta Deshabilitado!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }

}

/**
 * Funcion que limpia el formulario del beneficiario ordenante
 * @returns {undefined}
 */
function limpiarDatos() {
    document.getElementById("accion").value = 1;
    document.getElementById("personaId").value = "";
    document.getElementById("nombre").value = "";
    document.getElementById("rfc").value = "";
    document.getElementById("identificador").value = "";
    document.getElementById("numero").value = "";
    document.getElementById("rep").value = "";
    document.getElementById("tipoCuenta").value = "";
//    document.getElementById("digitoClabe").value = "";
    document.getElementById("estadoCHO").value = "0";
    document.getElementById("estadoOculto").value = "";
    document.getElementById("numerotel").value = "";
    $("#estadoCHO").attr("disabled", true);
    $("#tipoCuenta").attr("disabled", false);
//    $("#digitoClabe").attr("disabled", false);
    $("#numero").attr("disabled", false);
    $("#submit span").text("Guardar");
    $("#baja").hide();
    $("#alta").hide();

}

/**
 * Funcion encargada de validar los datos para poder generar un reporte
 * @param {type} f
 * @returns {Boolean}
 */
function formulario(f) {
    if (f.identificador.value === '') {
        $.bootstrapGrowl("Error, debe seleccionar un cuentahabiente \n\
Ordenante de la lista para poder Generar Reporte!", {
            type: 'danger',
            delay: 5000
        });
        f.identificador.focus();
        return false;
    }
    return true;
}

/**
 * Autor: Anayeli Guadalupe Torres
 * Fecha: 30-09-2016
 * Esta es una funcion para que solo acepte números.
 * @param {type} e
 * @returns {undefined}
 */
function OnlyNumeros(e) {
    var key = window.Event ? e.which : e.keyCode;
    return ((key >= 48 && key <= 57) || (key == 8));
}

/**
 * Funcion que carga las clabes para el digito en un combo
 * @returns {undefined}
 */
//function comboDigitoClabe() {
//    var datosP = {
//        accion: 6
//    };
//    $select = $('#digitoClabe');
//    $.ajax({
//        type: "POST",
//        url: "AddOrdenanteServlet",
//        data: datosP,
//        success: function (data) {
//            var data1 = JSON.parse(data);
//
//            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
//            //iterate over the data and append a select option
//            $.each(data1, function (k, v) {
//                $("#digitoClabe").append('<option value="' + v.identificador + '">' + v.descripcion + '</option>');
//            });
//        },
//        error: function () {
//            //if there is an error append a 'none available' option
//            $select.html('<option id="-1">none available</option>');
//        }
//    });
//
//}
