package com.example.usuario.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.usuario.model.Usuario;
@Service
public class usuarioService {

	// private String ip="159.1.1.35";
	private String ip = "localhost";

	private String URL_SEARCH_ALL = "http://" + ip + ":8081/User/All";
	private String URL_DELETE_USER_ID = "http://" + ip + ":8081/User/Delete/{idusuario}";
	private String URL_NUEVO_USER = "http://" + ip + ":8081/User/New";
	private String URL_BUSCAR_GET = "http://" + ip + ":8081/User/Search/{idusuario}";
	private String URL_UPDATEUSER_PUT = "http://" + ip +":8081/User/Update";

	public String Mensaje = "";

	// Listados para mostrar informacion de usuarios

	public List<Usuario> getListUser() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<Usuario[]> responseEntity = restTemplate.exchange(URL_SEARCH_ALL, HttpMethod.GET, request,
				Usuario[].class);
		Usuario[] Estatus = responseEntity.getBody();
		if (Estatus == null) {
			return null;
		} else {
			List<Usuario> Final = Arrays.asList(Estatus);
			return Final;
		}
	}

	public String postNewUser(Usuario Usu) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Usuario> request = new HttpEntity<>(Usu, headers);
		ResponseEntity<String> response = restTemplate.exchange(URL_NUEVO_USER, HttpMethod.POST, request, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return "Listo";
		} else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			return "SinEditar";
		} else {
			return "Fallo";
		}
	}

	public Usuario getBuscaUsuario(Long idusuario) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Usuario> request = new HttpEntity<>(headers);
		ResponseEntity<Usuario> response = restTemplate.exchange(URL_BUSCAR_GET, HttpMethod.GET, request, Usuario.class,
				idusuario);
		if (response.getStatusCode() == HttpStatus.OK) {
			Usuario user = response.getBody();
			return user;
		} else {
			return null;
		}
	}

	public String deleteUsuario(Long idusuario) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(URL_DELETE_USER_ID, HttpMethod.DELETE, request,
				String.class, idusuario);
		if (response.getStatusCode() == HttpStatus.OK) {
			String user = response.getBody();
			return user;
		} else {
			return null;
		}
	}
	
	public String putUsuarioEdit( Usuario Usu) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Usuario> request = new HttpEntity<>(Usu, headers);
		ResponseEntity<String> response = restTemplate.exchange(URL_UPDATEUSER_PUT, HttpMethod.PUT, request,
				String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return "Listo";
		} else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			return "SinEditar";
		} else {
			return "Fallo";
		}
	}

}
