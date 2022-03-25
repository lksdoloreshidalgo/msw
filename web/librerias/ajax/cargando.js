/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 Created on : 5/08/2015, 12:46:14 PM
 Author     : LUNA
 */


$(document).ready(function () {

});
$("#submit").click(function () {
    $.blockUI({
        message: "<i class='fa fa-refresh fa-spin'>" +
                "Cargando, por favor espere.",
        css: {
            border: 'none',
            padding: '150px',
            backgroundColor: '#000',
            'wibkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#fff'
        }

    });
    setTimeout("unBlock, 10000");
});
