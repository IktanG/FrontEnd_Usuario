package com.example.usuario.model;

public class Usuario {
	
	private Long id;
	private String nombre;
	private String email;
	private String gender;
	private Integer estatus;
	private String imagen;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Usuario(Long id, String nombre, String email, String gender, Integer estatus, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.gender = gender;
		this.estatus = estatus;
		this.imagen = imagen;
	}
	public Usuario() {
		super();
	}

	

}
