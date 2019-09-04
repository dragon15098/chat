/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverchat.model;

/**
 *
 * @author NMQ
 */
public class Response<T> {
    private int code;
    private T object;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
