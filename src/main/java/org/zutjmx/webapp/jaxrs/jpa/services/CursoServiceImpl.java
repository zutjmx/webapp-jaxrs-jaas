package org.zutjmx.webapp.jaxrs.jpa.services;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.zutjmx.webapp.jaxrs.jpa.models.Curso;
import org.zutjmx.webapp.jaxrs.jpa.repositorios.CursoRepository;

import java.util.List;
import java.util.Optional;

@Stateless
@DeclareRoles({"ADMIN","USER"})
public class CursoServiceImpl implements CursoService {

    @Inject
    private CursoRepository cursoRepository;

    @Override
    @RolesAllowed({"ADMIN","USER"})
    public List<Curso> listar() {
        return cursoRepository.listar();
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public Curso guardar(Curso curso) {
        return cursoRepository.guardar(curso);
    }

    @Override
    @RolesAllowed({"ADMIN","USER"})
    public Optional<Curso> porId(Long id) {
        return Optional
                .ofNullable(cursoRepository.porId(id));
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public void eliminar(Long id) {
        cursoRepository.eliminar(id);
    }
}
