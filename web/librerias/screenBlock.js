function block() {
    $.blockUI({
        message: "<img src='css/loader2.gif' alt='Cargando'/><br/>Cargando, por favor espere.",
        css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: .5,
            color: '#fff'
        }
    });
}
function unBlock() {
    $.unblockUI();
}