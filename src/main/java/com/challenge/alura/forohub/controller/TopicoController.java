package com.challenge.alura.forohub.controller;

import com.challenge.alura.forohub.domain.topico.*;
import com.challenge.alura.forohub.domain.usuario.Usuario;
import com.challenge.alura.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.plaf.SpinnerUI;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping // Listar todos los tópicos
    public ResponseEntity<Page<TopicoDTO>> listarTopicos(Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion)
                .map(topico -> new TopicoDTO(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getFecha_creacion(),
                        topico.getStatus(),
                        topico.getAutor().getUsername(),
                        topico.getCurso()
                )));
    }

    @GetMapping("/{id}") // Detallar un tópico
    public ResponseEntity<TopicoDTO> detallarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new TopicoDTO(
                    topico.get().getId(),
                    topico.get().getTitulo(),
                    topico.get().getMensaje(),
                    topico.get().getFecha_creacion(),
                    topico.get().getStatus(),
                    topico.get().getAutor().getUsername(),
                    topico.get().getCurso()
            ));
        }
    }

    @PostMapping // Registrar un tópico
    public ResponseEntity<TopicoDTO> registrarTopico(@RequestBody @Valid DatosTopico datosTopico, UriComponentsBuilder uriComponentsBuilder) {
        Usuario autor = usuarioRepository.findById(datosTopico.idUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Topico topico = new Topico(datosTopico, autor);
        topicoRepository.save(topico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getStatus(),
                topico.getAutor().getUsername(),
                topico.getCurso()
        ));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDTO> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopico datosTopico) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Topico topico = topicoOptional.get();
            topico.setTitulo(datosTopico.titulo());
            topico.setMensaje(datosTopico.mensaje());
            topico.setCurso(datosTopico.curso());

            return ResponseEntity.ok(new TopicoDTO(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFecha_creacion(),
                    topico.getStatus(),
                    topico.getAutor().getUsername(),
                    topico.getCurso()
            ));
        }

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Topico topicoEncontrado = topico.get();
            topicoEncontrado.setStatus(false); // Para no eliminarlo de la BD, sino desactivarlo
            return ResponseEntity.noContent().build();
        }
    }


}
