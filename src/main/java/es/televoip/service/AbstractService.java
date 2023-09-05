package es.televoip.service;

import es.televoip.exceptions.DataException;
import es.televoip.model.BaseEntity;
import es.televoip.model.enums.SortField;
import es.televoip.repository.BaseRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import es.televoip.model.mapper.BaseMapper;

public abstract class AbstractService<E extends BaseEntity, I extends Serializable, D extends Object> {

   protected final BaseRepository<E, I> repository;
   protected final BaseMapper<E, D> mapper;

   protected AbstractService(BaseRepository<E, I> repository, BaseMapper<E, D> mapper) {
      this.repository = repository;
      this.mapper = mapper;
   }

   public D getById(I id) {
      try {
         // obtenemos el objeto Optional del id
         Optional<E> optionalTask = repository.findById(id);
         if (optionalTask.isPresent()) {
            return mapper.toDto(optionalTask.get());
         } else {
            throw new DataException(HttpStatus.NOT_FOUND, "OBJECT_ID_NOT_FOUND" + id);
         }

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public D setSave(D dto) {
      try {
         // Convertimos el objeto DTO a su entidad
         E entity = mapper.toEntity(dto);
         // Grabamos en la base de datos
         E savedEntity = repository.save(entity);
         // devolvermos el objeto Dto
         return mapper.toDto(savedEntity);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public void setDeleteById(I id) {
      try {
         repository.deleteById(id);
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public D setUpdate(I id, D dto) {
      try {
         repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, "OBJECT_ID_NOT_FOUND" + id));

         E entity = mapper.toEntity(dto);
         entity.setId((Long) id); // Asegurar que el ID se mantenga igual
         repository.save(entity);
         return mapper.toDto(entity);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public List<D> getAll() {
      try {
         List<E> list = repository.findAll();
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public List<D> getAllSort(SortField sortBy, Sort.Direction sortOrder) {
      try {
         validateEntitySortBy(sortBy);

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         List<E> list = repository.findAll(sort);
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public Page<D> getAllPageable(Pageable page) {
      try {
         Page<E> pageE = repository.findAll(page);
         return pageE.map(mapper::toDto);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   //    Método auxiliar para convertir una lista de entidades a DTOs
   private List<D> convertToDtoList(List<E> list) {
      return list.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(mapper::toDto)
             .collect(Collectors.toList());
   }

   private void validateEntitySortBy(SortField sortBy) {
      final String ENTITY_NOT_FOUND = "ENTITY_SORT_BY_NOT_FOUND";

      if (sortBy == null) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }

      if (!SortField.values().equals(sortBy)) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }

      Class<?> entityClass = this.getClass();
      if (sortBy.getEntityClass() != entityClass) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }

      if (!isValueValid(sortBy.getFieldName())) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }
   }

   private boolean isValueValid(String value) {
      try {
         Enum.valueOf(SortField.class, value);
         return true;
      } catch (IllegalArgumentException e) {
         return false;
      }
   }

}
