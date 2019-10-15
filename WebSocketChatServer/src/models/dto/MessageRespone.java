package models.dto;

public class MessageRespone<T> {
	public Integer code;
	public String typeRequest;
	public T content;
}
