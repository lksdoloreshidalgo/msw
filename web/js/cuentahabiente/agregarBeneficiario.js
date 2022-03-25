


$(document).ready(function () {

    iniTablaBen();
    comboTipoCuenta();
    $("#submit").click(function () {
        validaCampos();
    });
    $("#saveCuenta").click(function () {
        validaParticipante();
    });
    limpiarBene();
});

/**
 * funcion que permite  realizar un a inserciuon de un cuentahabiente 
 * beneficiario
 * @returns {undefined}
 */
function  submit() {
    block();
    var datosCB = {
        nombre: $("#nombre").val(),
        rfc: $("#rfc").val(),
        tipoCuenta: $("#tipoCuenta").val(),
        clavePart: $("#clavePart").val(),
        numero: $("#numero").val(),
        personaId: $("#personaId").val(),
        accion: $("#accion").val(),
        cuentaId: $("#cuentaId").val(),
        activo: document.getElementById("activo").checked
    };
    $.ajax({
        type: 'POST',
        url: "AddBeneficiarioServlet",
        data: datosCB,
        success: function () {
            if (datosCB.accion === "1") {
                $.bootstrapGrowl("Datos Insertados Correctamente.", {
                    type: 'success',
                    delay: 5000
                });
            } else {
                $.bootstrapGrowl("Datos Actualizados Correctamente.", {
                    type: 'success',
                    delay: 5000
                });
            }
            $.ajax({
                type: "POST",
                url: "vistas/cuentahabientes/agregarBeneficiario.jsp",
                success: function (a) {
                    $("#contenido").html(a);
                    setTimeout(4000);
                    unBlock();
                }
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $.bootstrapGrowl("Ocurrio un error interno, verifique con el Administrador.", {
                type: 'danger',
                delay: 8000
            });
        }
    });
}

/**
 * Funcion que valida que los campos sean los correctos
 * @returns {Boolean}
 */
function validaCampos() {
    var tipoCuenta = document.getElementById("tipoCuenta").value;
    if (tipoCuenta.length === 0) {
        $.bootstrapGrowl("Campo Tipo Cuenta, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var descripcion = document.getElementById("descripcion").value;
    if (descripcion.length === 0) {

        $.bootstrapGrowl("Campo Entidad, Vacio!, Seleccione uno de la tabla", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var clavePart = document.getElementById("clavePart").value;
    if (clavePart.length === 0) {

        $.bootstrapGrowl("Campo Clave, Vacio!, Seleccione uno de la tabla", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var numero = document.getElementById("numero").value;
    if (numero.length === 0) {

        $.bootstrapGrowl("Campo Numero, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var nombre = document.getElementById("nombre").value;
    if (nombre.length === 0) {

        $.bootstrapGrowl("Campo Nombre, Vacio!", {
            type: 'danger'
        });
        return false;
    }
    var rfcStr = document.getElementById("rfc").value;
    if (rfcStr.length > 0) {
        if (rfcStr !== "ND") {
            var strCorrecta;
            strCorrecta = rfcStr;
            var valid = "";
            if (rfcStr.length === 12) {
                valid = '^(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
            } else {
                valid = '^(([A-Z]|[a-z]|\s){1})(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
            }
            var validRfc = new RegExp(valid);
            var matchArray = strCorrecta.match(validRfc);
            if (matchArray === null) {
                $.bootstrapGrowl("Error, formato Sugerido  ABCD-987654-1ER ", {
                    type: 'danger',
                    delay: 5000
                });
                return false;
            } else {
                submit();
                return true;
            }
        } else {
            document.getElementById("rfc").value = "ND";
            submit();
            return true;
        }
    } else {
        document.getElementById("rfc").value = "ND";
        submit();
        return true;
    }

    submit();
    return true;
}

/**
 * Funcion que valida los datos del formulario agregra cuenta a un 
 * cuentahabiente
 * @returns {Boolean}
 */
function saveCuenta() {
    var tipoCuenta = document.getElementById("tipoCuenta").value;
    if (tipoCuenta.length === 0) {

        $.bootstrapGrowl("Campo Tipo Cuenta, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var descripcion = document.getElementById("descripcion").value;
    if (descripcion.length === 0) {

        $.bootstrapGrowl("Campo Entidad, Vacio!, Seleccione uno de la tabla", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var clavePart = document.getElementById("clavePart").value;
    if (clavePart.length === 0) {

        $.bootstrapGrowl("Campo Clave, Vacio!, Seleccione uno de la tabla", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var tc = $("#tipoCuenta").val();
    if (tc === "40") {
        var numero = document.getElementById("numero").value;
        if (numero.length === 0) {
            $.bootstrapGrowl("Campo Numero, Vacio!", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
        if (numero.length === 18) {
            var clabe = 0;
            var digito = 0;
            clabe = clavePart.substr(-3);
            digito = numero.substr(0, 3);
            if (clabe === digito) {
                validarNumCuenta();
                return true;
            } else {
                $.bootstrapGrowl("Error, Verifica que el numero de cuenta coinsida con la Clave de la Entidad!", {
                    type: 'danger',
                    delay: 5000
                });
                return false;
            }
        } else {
            $.bootstrapGrowl("Error, Verifica que el campo numero contenga 18 digitos !", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else if (tc === "3") {
        var numero = document.getElementById("numero").value;
        if (numero.length === 0) {
            $.bootstrapGrowl("Campo Numero, Vacio!", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
        if (numero.length === 16) {
            var clabe = 0;
            var digito = 0;
            if (clavePart.length === 3) {
                clabe = clavePart.substr(-3);
                digito = numero.substr(0, 3);
            } else if (clavePart.length === 4) {
                clabe = clavePart.substr(-4);
                digito = numero.substr(0, 4);
            } else if (clavePart.length === 5) {
                clabe = clavePart.substr(-5);
                digito = numero.substr(0, 5);
            }
            if (clabe === digito) {
                validarNumCuenta();
                $('#myModal').modal('hide');
                return true;
            } else {
                $.bootstrapGrowl("Error, Verifica que el numero de cuenta coinsida con la Clave de la Entidad!", {
                    type: 'danger',
                    delay: 5000
                });
                return false;
            }
        } else {
            $.bootstrapGrowl("Error, Verifica que el campo numero contenga 16 digitos !", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else if (tc === "10") {
        var numero = document.getElementById("numero").value;
        if (numero.length === 0) {
            $.bootstrapGrowl("Campo Numero, Vacio!", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
        if (numero.length === 10) {
            validarNumCuenta();
            $('#myModal').modal('hide');
            return true;
        } else {
            $.bootstrapGrowl("Error, Verifica que el campo numero contenga 10 digitos !", {
                type: 'danger',
                delay: 5000
            });
            return false;
        }
    } else if (tc !== "40" && tc !== "3" && tc !== "10") {
        $.bootstrapGrowl("Error Tipo de cuenta Deshabilitado!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    $('#myModal').modal('hide');
    return true;
}

function validaParticipante() {
    block();
    var datosParticipante = {
        clavePart: $("#clavePart").val(),
        descripcion: $("#descripcion").val(),
        accion: 4
    };
    $.ajax({
        type: 'POST',
        url: "AddBeneficiarioServlet",
        data: datosParticipante,
        success: function (data) {
            var dataset = JSON.parse(data);
            if (dataset === true) {
                saveCuenta();
            } else {
                $.bootstrapGrowl("Error, El Participante: "
                        + datosParticipante.descripcion +
                        " no puede estar como Beneficiario.", {
                            type: 'danger',
                            delay: 5000
                        });
            }
            setTimeout(4000);
            unBlock();
        }
    });
}

/**
 * Funcion que limpia los campos del beneficiario
 * @returns {undefined}
 */
function limpiarBene() {
    document.getElementById("nombre").value = "";
    document.getElementById("rfc").value = "";
    document.getElementById("personaId").value = "";
    document.getElementById("accion").value = "1";
    document.getElementById("tipoCuenta").value = "";
    document.getElementById("clavePart").value = "";
    document.getElementById("numero").value = "";
    document.getElementById("descripcion").value = "";
    document.getElementById("cuentaId").value = "";
    $("#submit span").text("Guardar");
    $("#saveCuenta span").text("Guardar");
    $("#activo").prop("checked", "checked");
}

/**
 * funcion que limpia los campos de agregra cuenta aun cuentahabiente
 * @returns {undefined}
 */
function limpiarCuenta() {
    document.getElementById("tipoCuenta").value = "";
    document.getElementById("clavePart").value = "";
    document.getElementById("numero").value = "";
    document.getElementById("descripcion").value = "";
    document.getElementById("cuentaId").value = "";
}

/**
 * Funcion que permite validar que solo se ingresen numeros en la caja de texto
 * @param {type} e
 * @returns {Boolean}
 */
function validaSoloNumeros(e) {
    tecla = (document.all) ? e.keyCode : e.which; // 2
    if (tecla === 8)
        return true;
    if (tecla === 46)
        return true;
    var patron = /^([0-9])*$/;
    te = String.fromCharCode(tecla); // 5
    return patron.test(te); // 6
}

/**
 * funcion que permite validar que no se repita un mismo numero de cuenta 
 * @returns {undefined}
 */
function validarNumCuenta() {
    var cuentaId = 0;
    cuentaId = document.getElementById("cuentaId").value;
    if (cuentaId === null || cuentaId === "") {
        cuentaId = 0;
    }
    document.getElementById("cuentaId").value = cuentaId;
    block();
    var datos = {
        numero: $("#numero").val(),
        cuentaId: $("#cuentaId").val(),
        accion: 3
    };
    $.ajax({
        type: 'POST',
        url: "AddBeneficiarioServlet",
        data: datos,
        success: function (data) {
            var dataset = JSON.parse(data);
            if (dataset === true) {
                $.bootstrapGrowl("Cuenta validada correctamente.", {
                    type: 'success',
                    delay: 5000
                });
                $('#myModal').modal('hide');
            } else {
                $.bootstrapGrowl("Error, Favor de verificar numero de cuenta ya existe.", {
                    type: 'danger',
                    delay: 5000
                });
            }
            setTimeout(4000);
            unBlock();
        }
    });
}

/**
 * Funcion encargada de listar la informacion de los beneficiarios
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
             * parte encargada del display de la tabla
             */
            $('#tblBeneficiario tbody').on('click', 'tr', function (evento) {
                document.getElementById("accion").value = 2;
                $("#submit span").text("Actualizar");
                $("#saveCuenta span").text("Actualizar");


                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
                var fila = table.row(this).index();

                evento.preventDefault();
                var personaId = dataset[fila].personaId;
                $("#personaId").val(personaId);
                var nombre = $('td', this).eq(1).text().trim();
                $("#nombre").val(nombre);
                var rfc = $('td', this).eq(3).text().trim();
                $("#rfc").val(rfc);
                var numero = $('td', this).eq(2).text().trim();
                $("#numero").val(numero);
                var tipoCuenta = dataset[fila].cuenta.tipoCuenta.clave;
                $("#tipoCuenta").val(tipoCuenta);
                var descripcion = $('td', this).eq(5).text().trim();
                $("#descripcion").val(descripcion);
                var clavePart = dataset[fila].cuenta.participante.clave;
                $("#clavePart").val(clavePart);
                var cuentaId = dataset[fila].cuenta.cuentaId;
                $("#cuentaId").val(cuentaId);
                var activo = dataset[fila].estado;
                if (activo === true) {
                    $("#activo").prop("checked", "checked");
                } else {
                    $("#activo").prop("checked", "");
                }
            });
        }
    });
}

/**
 * Funcion encargada obtener i visualizar los datos del participante en el 
 * componete DataTable
 * @returns {undefined}
 */
function iniTablaParticipante() {
    var datosP = {
        accion: 1
    };
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tblParticipantes").DataTable(
                    {
                        destroy: true,
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

            /**
             * configuramos la tabla de agregar cuentas a un cuentahabiente
             */
            $('#tblParticipantes tbody').on('click', 'tr', function (evento) {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
                evento.preventDefault();
                var descripcion = $('td', this).eq(2).text().trim();
                $("#descripcion").val(descripcion);
                var clavePart = $('td', this).eq(1).text().trim();
                $("#clavePart").val(clavePart);
            });
        }
    });
}

/**
 * Funcion que carga los tipos de cuenta en un combo
 * @returns {undefined}
 */
function comboTipoCuenta() {
    var datosP = {
        accion: 5
    };
    $select = $('#tipoCuenta');
    $.ajax({
        type: "POST",
        url: "CatalogosServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#tipoCuenta").append('<option value="' + v.clave + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}
