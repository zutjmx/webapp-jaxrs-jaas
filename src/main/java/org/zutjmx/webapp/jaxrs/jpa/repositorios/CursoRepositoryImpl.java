package org.zutjmx.webapp.jaxrs.jpa.repositorios;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.zutjmx.webapp.jaxrs.jpa.models.Curso;

import java.util.List;

@RequestScoped
public class CursoRepositoryImpl implements CursoRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<Curso> listar() {
        return entityManager
                .createQuery("select c from Curso c left outer join fetch c.instructor",Curso.class)
                .getResultList();
    }

    @Override
    public Curso guardar(Curso curso) {
        if(curso.getId() != null && curso.getId() > 0) {
            entityManager.merge(curso);
        } else {
            entityManager.persist(curso);
        }

        return curso;
    }

    @Override
    public Curso porId(Long id) {
        return entityManager
                .createQuery("select c from Curso c left outer join fetch c.instructor where c.id = :id",Curso.class)
                .setParameter("id",id)
                .getSingleResult();
        //return entityManager.find(Curso.class,id);
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = porId(id);
        entityManager.remove(curso);
    }
}
