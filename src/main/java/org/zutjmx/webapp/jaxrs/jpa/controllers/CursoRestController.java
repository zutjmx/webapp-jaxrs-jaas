package org.zutjmx.webapp.jaxrs.jpa.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.zutjmx.webapp.jaxrs.jpa.models.Curso;
import org.zutjmx.webapp.jaxrs.jpa.services.CursoService;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
public class CursoRestController {

    @Inject
    private CursoService cursoService;

    @GET
    public List<Curso> listar() {
        return cursoService.listar();
    }

    /*@GET
    public Response listar() {
        return Response
                .ok(cursoService.listar())
                .build();
    }*/

    @GET
    @Path("/{id}")
    public Response porId(@PathParam("id") Long id) {
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if(cursoOptional.isPresent()) {
            return Response
                    .ok(cursoOptional.get())
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(Curso curso) {
        try {
            Curso nuevoCurso = cursoService.guardar(curso);
            return Response.ok(nuevoCurso).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editar(@PathParam("id") Long id, Curso curso) {
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if(cursoOptional.isPresent()) {
            Curso nuevoCurso = cursoOptional.get();
            nuevoCurso.setDescripcion(curso.getDescripcion());
            nuevoCurso.setDuracion(curso.getDuracion());
            nuevoCurso.setInstructor(curso.getInstructor());
            nuevoCurso.setNombre(curso.getNombre());

            try {
                cursoService.guardar(nuevoCurso);
                return Response.ok(nuevoCurso).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }

        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if(cursoOptional.isPresent()) {
            try {
                cursoService.eliminar(cursoOptional.get().getId());
                return Response.noContent().build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.serverError().build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
