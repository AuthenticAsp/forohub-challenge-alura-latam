package com.challenge.alura.forohub.domain.topico;

import com.challenge.alura.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha_creacion;
    private Boolean status;
    @ManyToOne
    private Usuario autor;
    private String curso;

    public Topico() {

    }

    public Topico(DatosTopico datosTopico, Usuario autor) {
        this.titulo = datosTopico.titulo();
        this.mensaje = datosTopico.mensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.status = true;
        this.autor = autor;
        this.curso = datosTopico.curso();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public Boolean getStatus() {
        return status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
