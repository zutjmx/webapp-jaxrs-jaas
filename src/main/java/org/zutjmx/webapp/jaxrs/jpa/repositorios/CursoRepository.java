package org.zutjmx.webapp.jaxrs.jpa.repositorios;

import org.zutjmx.webapp.jaxrs.jpa.models.Curso;

import java.util.List;

public interface CursoRepository {
    List<Curso> listar();
    Curso guardar(Curso curso);
    Curso porId(Long id);
    void eliminar(Long id);
}
