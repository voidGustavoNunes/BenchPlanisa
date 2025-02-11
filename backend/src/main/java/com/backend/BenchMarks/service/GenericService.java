package com.backend.BenchMarks.service;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.backend.BenchMarks.util.RegistroNotFoundException;
public interface GenericService<T> {

    List<T> listar();

    T buscarPorId(@NotNull @Positive Long id);

    T criar(@Valid @NotNull T entity);

    T atualizar(@NotNull @Positive Long id, @Valid @NotNull T entity);

    void excluir(@NotNull @Positive Long id);
    
    public abstract void saveValidation(T entity) throws RegistroNotFoundException;

}