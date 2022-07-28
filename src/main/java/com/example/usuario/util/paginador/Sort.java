package com.example.usuario.util.paginador;

public class Sort {
	private  boolean sorted;
	private  boolean unsorted;
	private  boolean empty;
	
	public boolean isSorted() {
		return sorted;
	}
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}
	public boolean isUnsorted() {
		return unsorted;
	}
	public void setUnsorted(boolean unsorted) {
		this.unsorted = unsorted;
	}
	public boolean isEmpty() {
		return empty;
	}
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	public Sort(boolean sorted, boolean unsorted, boolean empty) {
		this.sorted = sorted;
		this.unsorted = unsorted;
		this.empty = empty;
	}
	public Sort() {
	}
	
	
	
	
}
