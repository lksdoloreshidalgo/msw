$(document).ready(function () {
    document.getElementById("accion").value = 1;
    iniTablas();
    $("#submit").click(function () {
        validaDatos();
    });
    $("#update").click(function () {
        modalPass();
    });
    comboSucursal();
    comboRol();
});

/**
 * 
 * @returns {undefined}
 */
function iniTablas() {

    var datosU = {
        accion: 5
    };
    $.ajax({
        type: "POST",
        url: "ListarOrdenServlet",
        data: datosU,
        success: function (data) {
            var dataset = JSON.parse(data);
            var table = $("#tablaUsuarios").DataTable(
                    {
                        destroy: true,
                        data: dataset,
                        "order": [[1, "asc"]],
                        "columns": [
                            {"data": null},
                            {"data": "persona.nombre"},
                            {"data": "nickname"},
                            {"data": "sucursal.nombre"},
                            {"data": "catalogorol.descripcion"}
                        ],
                        "columnDefs": [{
                                "searchable": false,
                                "orderable": false,
                                "targets": 0
                            }]
                    });
            table.on('order.dt search.dt', function () {
                table.column(0, {
                    search: 'applied',
                    order: 'applied'
                }).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();
            //configuramos la tabla de beneficiarios
            $('#tablaUsuarios tbody').on('click', 'tr', function (evento) {
                document.getElementById("accion").value = 2;
                $('#update').attr("disabled", false);
                $('#txtContrasenia').attr("disabled", true);
                document.getElementById("txtContrasenia").value = "contrasena";
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }

                var fila = table.row(this).index();

                evento.preventDefault();
                var txtNombreU = $('td', this).eq(1).text().trim();
                $("#txtNombreU").val(txtNombreU);
                var txtNickname = $('td', this).eq(2).text().trim();
                $("#txtNickname").val(txtNickname);
                var cboSucursal = dataset[fila].sucursal.numeroClabe;
                $("#sucursal").val(cboSucursal);
                var idUsuario = dataset[fila].usuarioId;
                $("#usuarioId").val(idUsuario);
                var rol = dataset[fila].catalogorol.identificador;
                $("#rol").val(rol);

            });
        }
    });
}

function reset() {
    this.reset();
}

/**
 * 
 * @returns {undefined}
 */
function submit() {
    block();
    var datos = {
        nombreU: $("#txtNombreU").val(),
        nickname: $("#txtNickname").val(),
        contrasenia: $("#txtContrasenia").val(),
        sucursal: $("#sucursal option:selected").val(),
        accion: $("#accion").val(),
        usuarioId: $("#usuarioId").val(),
        rol: $("#rol option:selected").val()
    };
    $.ajax({
        type: 'POST',
        url: "RegistrarUsuarioServlet",
        data: datos,
        success: function () {
            $.bootstrapGrowl("Datos Insertados Correctamente.", {
                type: 'success',
                delay: 5000
            });
            $.ajax({
                type: "POST",
                url: "vistas/sistema/registrarUsuario.jsp",
                success: function (a) {
                    $("#contenido").html(a);
                    setTimeout(4000);
                    unBlock();
                }
            });
        }
    });
}

/**
 * 
 * @returns {Boolean}
 */
function validaDatos() {
    //Se declaran las variables para hacer referencia a los controles a validar 
    //y de inmediatose asigna el control correspondiente del formulario
    var nombreU = document.getElementById("txtNombreU").value;
    if (nombreU.length === 0) {
        $.bootstrapGrowl("Campo Nombre, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var nickname = document.getElementById("txtNickname").value;
    if (nickname.length === 0) {
        $.bootstrapGrowl("Campo Nickname, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var contrasenia = document.getElementById("txtContrasenia").value;
    if (contrasenia.length === 0) {
        $.bootstrapGrowl("Campo Contraseña, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var cboSucursal = document.getElementById("sucursal").value;
    if (cboSucursal.length === 0) {
        $.bootstrapGrowl("Campo Sucursal, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    var rol = document.getElementById("rol").value;
    if (rol.length === 0) {
        $.bootstrapGrowl("Campo Sucursal, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    submit();
    return true;
}//Termina la funcion de validar datos

/**
 * 
 * @returns {undefined}
 */
function limpiarRegistro() {
    document.getElementById("sucursal").selectedIndex = 0;
    document.getElementById("accion").value = 1;
    document.getElementById("txtNombreU").value = "";
    document.getElementById("txtNickname").value = "";
    document.getElementById("txtContrasenia").value = "";
    document.getElementById("pass").value = "";
    document.getElementById("pass2").value = "";
    document.getElementById("usuarioId").value = "";
    $('#update').attr("disabled", true);
    $('#txtContrasenia').attr("disabled", false);


//    uploadAjax = null;
}

function modalPass() {
    $("#modalPass").modal();
    $("#actualizarPass").click(function () {
        var pass = document.getElementById("pass").value;
        var pass2 = document.getElementById("pass2").value;
        if (pass.length !== 0 && pass2 !== 0) {
            if (pass === pass2) {
                updatePass();
            } else {
                $.bootstrapGrowl("La contraseña no coinside verifica nuevamente!", {
                    type: 'danger',
                    delay: 5000
                });
            }
        } else {
            $.bootstrapGrowl("Uno de los campos esta vacio!", {
                type: 'danger',
                delay: 5000
            });
        }
    });
}
/**
 * Funcion encargada de actualizar la contraseña 
 * @returns {undefined}
 */
function updatePass() {
    var datos = {
        nombreU: $("#txtNombreU").val(),
        nickname: $("#txtNickname").val(),
        contrasenia: $("#pass").val(),
        sucursal: $("#sucursal option:selected").val(),
        accion: 3,
        usuarioId: $("#usuarioId").val()
    };
    $.ajax({
        type: 'POST',
        url: "RegistrarUsuarioServlet",
        data: datos,
        success: function (data) {

            var estado = data;
            if (estado.trim() === "true") {
                $('#modalPass').modal('hide');
                $.bootstrapGrowl("Contraseña actualizada correctamente.", {
                    type: 'success',
                    delay: 5000
                });
                limpiarRegistro();
                iniTablas();
            } else {
                $.bootstrapGrowl("Error, El Nickname ya se encuantra registrado!", {
                    type: 'danger',
                    delay: 5000
                });
            }
        }
    });
}

/**
 * 
 * @returns {undefined}
 */
function comboSucursal() {
    var datosP = {
        accion: 5
    };
    $select = $('#sucursal');
    $.ajax({
        type: "POST",
        url: "RegistrarUsuarioServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#sucursal").append('<option value="' + v.numeroClabe + '">' + v.nombre + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });

}

/**
 * Funcion que carga las clabes para el digito en un combo
 * @returns {undefined}
 */
function comboRol() {
    var datosP = {
        accion: 4
    };
    $select = $('#rol');
    $.ajax({
        type: "POST",
        url: "RegistrarUsuarioServlet",
        data: datosP,
        success: function (data) {
            var data1 = JSON.parse(data);

            $select.html(' <option value="0">--Selecciona una Opcion--</option>');
            //iterate over the data and append a select option
            $.each(data1, function (k, v) {
                $("#rol").append('<option value="' + v.identificador + '">' + v.descripcion + '</option>');
            });
        },
        error: function () {
            //if there is an error append a 'none available' option
            $select.html('<option id="-1">none available</option>');
        }
    });
}