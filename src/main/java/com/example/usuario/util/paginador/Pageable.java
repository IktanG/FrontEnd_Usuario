package com.example.usuario.util.paginador;

public class Pageable {
	private Sort sort;
	private Integer offset;
	private Integer pageNumber;
	private Integer pageSize;
	private Boolean unpaged;
	private Boolean paged;
	
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getUnpaged() {
		return unpaged;
	}
	public void setUnpaged(Boolean unpaged) {
		this.unpaged = unpaged;
	}
	public Boolean getPaged() {
		return paged;
	}
	public void setPaged(Boolean paged) {
		this.paged = paged;
	}
	public Pageable(Sort sort, Integer offset, Integer pageNumber, Integer pageSize, Boolean unpaged, Boolean paged) {
		this.sort = sort;
		this.offset = offset;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.unpaged = unpaged;
		this.paged = paged;
	}
	public Pageable() {
	}
	
}
