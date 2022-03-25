/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
//    initControls();
    $("#acceder").click(function () {
        validaLogin();
    });

});


//function initControls() {
//    window.location.hash = "msw";
//    window.location.hash = "MSW"; //chrome
//    window.onhashchange = function () {
//        window.location.hash = "msw";
//    };
//}

/**
 * Valida que los campos no esten vacios
 * @returns {Boolean}
 */
function validaLogin() {
    var nickname = document.getElementById("txtNickname").value;
    if (nickname.length === 0) {
        $.bootstrapGrowl("Campo Usuario, Vacio!", {
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
    var cboSucursal = document.getElementById("cboSucursal").value;
    if (cboSucursal.length === 0) {
        $.bootstrapGrowl("Campo Sucursal, Vacio!", {
            type: 'danger',
            delay: 5000
        });
        return false;
    }
    submit();
    return true;
}

/**
 * Funcion encargada de gestionar ie inicio de sesion para los usuarios
 * @returns {undefined}
 */
function submit() {

    var datos = {
        nickname: $("#txtNickname").val(),
        contrasenia: $("#txtContrasenia").val(),
        sucursal: $("#cboSucursal").val()
    };
    $.ajax({
        type: "POST",
        url: "../../AccederUsuarioServlet",
        data: datos,
        success: function (data) {
            var estado = data;
            if (estado.trim() === "true") {
                dirIndex();
                $.bootstrapGrowl("Datos correctos!.", {
                    type: 'success',
                    delay: 5000
                });
            } else {
                $.bootstrapGrowl("Error, Usuario, Contraseña o Sucursal Incorrecta, Verifica los datos.", {
                    type: 'danger',
                    delay: 5000
                });
            }
        }
    });
}

/**
 * funcion que permite redireccionar al index
 * @returns {undefined}
 */
function dirIndex() {
    var accesoCorrecto = "../../";
    window.location = accesoCorrecto;
}
