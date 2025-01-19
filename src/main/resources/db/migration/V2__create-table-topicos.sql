CREATE TABLE topicos (
    id INT AUTO_INCREMENT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status BOOLEAN NOT NULL DEFAULT 1,
    autor_id INT NOT NULL,
    curso VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);