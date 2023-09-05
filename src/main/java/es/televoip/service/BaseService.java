package es.televoip.service;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/*
public interface BaseService<E extends Object, ID extends Serializable> {

   public E getById(ID id);

   public E setSave(@Valid E entity); // validamos antes de grabar

   public E setUpdate(ID id, @Valid E entity); // validamos antes de actualizar

   public boolean setDelete(ID id);

   public long getCountAll();

   public List<E> getAll();

   public List<E> getAllSort(Sort sort);

   public Page<E> getAllPageable(Pageable page);

}
*/