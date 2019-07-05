package com.breja.breja_br.Models;

public class Comentarios {
    private String userFoto;
    private String email;
    private String fotopromocao;
    private String comentario;
    public Comentarios(){}
    public Comentarios(String userFoto, String email, String fotopromocao, String comentario) {
        this.userFoto = userFoto;
        this.email = email;
        this.fotopromocao = fotopromocao;
        this.comentario = comentario;
    }
    public String getUserFoto() {
        return userFoto;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotopromocao() {
        return fotopromocao;
    }

    public void setFotopromocao(String fotopromocao) {
        this.fotopromocao = fotopromocao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}
