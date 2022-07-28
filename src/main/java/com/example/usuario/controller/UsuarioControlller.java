package com.example.usuario.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.usuario.model.Usuario;
import com.example.usuario.service.usuarioService;
import com.example.usuario.util.paginador.PageRender;

@Controller
@RequestMapping("/USER")
@SessionAttributes({ "Usuario", "EstadisticoListadoUSU", "EstadisticoListadoUSUPage", "PageRender", "idUsuario" })
public class UsuarioControlller {

	@ModelAttribute("Usuario")
	public Usuario getUsuario() {
		return null;
	}

	@ModelAttribute("EstadisticoListadoUSU")
	public List<Usuario> getEstadisticoListadoUSU() {
		return null;
	}

	@ModelAttribute("EstadisticoListadoUSUPage")
	public Page<Usuario> getEstadisticoListadoUSUPage() {
		return null;
	}

	@ModelAttribute("PageRender")
	public PageRender<Usuario> getpageRender() {
		return null;
	}

	@ModelAttribute("idUsuario")
	public Long getIdUsuario() {
		return null;
	}

	@Autowired
	private usuarioService UsuarioService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicio() {
		return "redirect:panel";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String inicio2() {
		return "redirect:panel";
	}

	@RequestMapping(value = "/editarUsu", method = RequestMethod.GET)
	public String EditUsu(Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario Usu = (Usuario) session.getAttribute("Usuario");
		Long idUsuario = (Long) session.getAttribute("idUsuario");
		
		if (Usu != null) {
			model.put("usuario", Usu);
			model.put("idusu", idUsuario);
			return "editarUsu";
		} else {
			model.put("usuario", null);
			model.put("ListUSU", null);
			model.put("usuario", null);
			return "redirect:/USER/editarUsu";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	public String panel(Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario Usu = (Usuario) session.getAttribute("Usuario");
		Usuario NewUsu = new Usuario();
		Long idUsuario = (Long) session.getAttribute("idUsuario");
		Page<Usuario> ListUSU = (Page<Usuario>) session.getAttribute("EstadisticoListadoUSUPage");
		PageRender<Usuario> pageRender = (PageRender<Usuario>) session.getAttribute("PageRender");
		if (ListUSU != null) {
			model.put("ResultUSU", ListUSU);
			model.put("page", pageRender);
			model.put("usuario", Usu);
			model.put("NewUsu", NewUsu);
			model.put("idusu", idUsuario);
			return "panel";
		} else if (Usu != null) {
			model.put("usuario", Usu);
			model.put("idusu", idUsuario);
			return "panel";
		} else {
			model.put("usuario", null);
			model.put("ListUSU", null);
			model.put("usuario", null);
			return "panel";
		}
	}

	@RequestMapping(value = "/NewUser", method = RequestMethod.POST)
	public String NewUser(Map<String, Object> model, RedirectAttributes flash, HttpSession session, Usuario user,
			@RequestParam("file") MultipartFile imagen) {

		if (!imagen.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources//static/images");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

			try {
				byte[] bytesImg = imagen.getBytes();

				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				user.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String response = UsuarioService.postNewUser(user);

		if (response == "Listo") {
			flash.addFlashAttribute("MensajeOK", "Usuario Registrado");
			return "redirect:/USER/ListUSU";
		} else if (response == "SinEditar") {
			flash.addFlashAttribute("MensajeNo", "Usuario ya registrado");
			return "redirect:/USER/ListUSU";
		} else {

			flash.addFlashAttribute("MensajeNo", "Sin Usuario que registrar");
			return "redirect:/USER/ListUSU";
		}
	}

	@RequestMapping(value = "/ListUSU", method = RequestMethod.GET)
	public String ListadoUSU(Map<String, Object> model, RedirectAttributes flash, HttpSession session,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		List<Usuario> Estadistico;
		PageRender<Usuario> pageRender;
		Page<Usuario> EstadisticoPag;
		Pageable pageRequest = PageRequest.of(page, 10);

		Estadistico = UsuarioService.getListUser();
		if (Estadistico != null) {
			EstadisticoPag = listConvertToPage1(Estadistico, pageRequest);
			pageRender = new PageRender<Usuario>("/USER/ListUSU", EstadisticoPag);
			session.removeAttribute("EstadisticoListadoUSUPage");
			session.removeAttribute("PageRender");
			session.removeAttribute("EstadisticoListadoUSU");
			model.remove("EstadisticoListadoUSUPage");
			model.remove("PageRender");
			model.remove("EstadisticoListadoUSU");
			session.setAttribute("EstadisticoListadoUSUPage", EstadisticoPag);
			session.setAttribute("PageRender", pageRender);
			session.setAttribute("EstadisticoListadoUSU", Estadistico);
			return "redirect:panel";
		} else {
			EstadisticoPag = null;
			pageRender = null;
			return "redirect:panel";
		}

	}

	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
	public String ListadoUSUVer(Map<String, Object> model, RedirectAttributes flash, HttpSession session,
			@PathVariable(value = "id") Long id) {
		Usuario usuario = UsuarioService.getBuscaUsuario(id);
		if (usuario != null) {
			model.remove("Usuario");
			model.remove("usuario");
			model.remove("idusuario");
			session.setAttribute("Usuario", usuario);
			session.setAttribute("idUsuario", usuario.getId());
			return "redirect:/USER/editarUsu";
		} else {
			model.remove("Usuario");
			model.remove("usuario");
			model.remove("idusuario");
			session.setAttribute("Usuario", null);
			session.setAttribute("idusuario", null);
			return "redirect:/USER/editarUsu";
		}
	}

	@RequestMapping(value = "/elimina/{id}", method = RequestMethod.GET)
	public String ListadoUSUelimina(Map<String, Object> model, RedirectAttributes flash, HttpSession session,
			@PathVariable(value = "id") Long id) {
		String rep = UsuarioService.deleteUsuario(id);
		if (rep != null) {
			model.remove("Usuario");
			model.remove("usurio");
			model.remove("idusuario");
			session.setAttribute("Usuario", null);
			session.setAttribute("idusuario", null);
			return "redirect:/USER/ListUSU";
		} else {
			model.remove("Usuario");
			model.remove("usurio");
			model.remove("idusuario");
			session.setAttribute("Usuario", null);
			session.setAttribute("idusuario", null);
			return "redirect:/USER/ListUSU";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editUser(Map<String, Object> model, RedirectAttributes flash, HttpSession session, Usuario user,
			@RequestParam("file") MultipartFile imagen) {
		Usuario Usu = (Usuario) session.getAttribute("Usuario");
		if (!imagen.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources//static/images");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			try {
				byte[] bytesImg = imagen.getBytes();

				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				user.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			user.setImagen(Usu.getImagen());
		}
		user.setId(Usu.getId());

		String response = UsuarioService.putUsuarioEdit(user);
		if (response == "Listo") {
			flash.addFlashAttribute("MensajeOK2", "Usuario Editado");
			return "redirect:/USER/ListUSU";
		} else if (response == "SinEditar") {
			flash.addFlashAttribute("MensajeNo2", "Usuario no existe");
			return "redirect:/USER/ListUSU";
		} else {
			flash.addFlashAttribute("MensajeNo2", "Sin Usuario que registrar");
			return "redirect:/USER/ListUSU";
		}
	}


	/* Metodos */
	public static <T> Page<T> listConvertToPage1(List<T> list, Pageable pageable) {
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
		return new PageImpl<T>(list.subList(start, end), pageable, list.size());
	}
}
