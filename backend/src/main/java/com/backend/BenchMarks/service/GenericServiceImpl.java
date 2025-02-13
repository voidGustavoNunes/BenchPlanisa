package com.backend.BenchMarks.service;

import java.util.List;
import java.lang.reflect.Field;


import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.BenchMarks.handler.RegistroNotFoundException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public abstract class GenericServiceImpl<T, R extends JpaRepository<T, Long>> implements GenericService<T> {

    protected R repository;

    protected GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> listar() {
        return repository.findAll();
    }

    @Override
    public T buscarPorId(@NotNull @Positive Long id) {
        return repository.findById(id).orElseThrow(() -> new RegistroNotFoundException(id));
    }

    @Override
    public T criar(@Valid @NotNull T entity) {
        this.saveValidation(entity);
        return repository.save(entity);
    }

    @Override
    public void excluir(@NotNull @Positive Long id) {
        repository.delete(this.repository.findById(id).orElseThrow(() -> new RegistroNotFoundException(id)));
    }

    @Override
    public T atualizar(@NotNull @Positive Long id, @Valid @NotNull T entityAtualizada) {
        this.saveValidation(entityAtualizada);
        T entityExistente = repository.findById(id)
                .orElseThrow(() -> new RegistroNotFoundException(id));
        
        if (entityExistente != null) {
            try {
                Class<?> clazz = entityExistente.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entityAtualizada);
                    if (value != null) {
                        field.set(entityExistente, value);
                    }
                }
                return repository.save(entityExistente);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Erro ao atualizar a entidade: " + e.getMessage());
            }
        } else {
            throw new RegistroNotFoundException(id);
        }
    }

}
