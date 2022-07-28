/**
 * Ejemplo 4 de html2canvas para convertir el HTML de una web
 * a un elemento canvas - Descargar la captura como imagen PNG
 * 
 * @author Erick Gumesindo
 */
//Definimos el botón para escuchar su click
const $boton = document.querySelector("#btnCapturar"), // El botón que desencadena
	$objetivo = document.getElementById('Ticket'); // A qué le tomamos la fotocanvas
// Nota: no necesitamos contenedor, pues vamos a descargarla

// Agregar el listener al botón
$boton.addEventListener("click", () => {
	html2canvas($objetivo) // Llamar a html2canvas y pasarle el elemento
		.then(canvas => {
			// Cuando se resuelva la promesa traerá el canvas
			// Crear un elemento <a>
			var codigos = document.getElementById('codigos').innerText;
			let enlace = document.createElement('a');
			enlace.download = "Ticket-" + codigos + ".png";
			// Convertir la imagen a Base64
			enlace.href = canvas.toDataURL();
			// Hacer click en él
			enlace.click();
		});
});