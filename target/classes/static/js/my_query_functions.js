/**
 * 
 */
function actualizar(opcion){
        console.log("Actualizando datos",opcion.value);
        document.getElementById("sucursales").value=opcion.value;
        $.ajax({
            type: 'GET',
            url: '/modulos',
            data: {
            	id:opcion.value
            },
            success: function(resp) {
            //Aqui cambias el valor de tus inputs
            	 $("#modulos").html(resp);
            }
        });
    }