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
import jakarta.validation.Valid;
import java.util.Arrays;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract class BaseService<E extends BaseEntity, I extends Serializable, D extends Object> {

   protected final BaseRepository<E, I> repository;
   protected final BaseMapper<E, D> mapper;

   protected BaseService(BaseRepository<E, I> repository, BaseMapper<E, D> mapper) {
      this.repository = repository;
      this.mapper = mapper;
   }

   public D findById(I id) {
      try {
         // obtenemos el objeto Optional del id
         Optional<E> optionalTask = repository.findById(id);
         if (optionalTask.isPresent()) {
            return mapper.toDto(optionalTask.get());
         } else {
            throw new DataException(HttpStatus.NOT_FOUND, "ENTITY_ID_NOT_FOUND: " + id);
         }

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public D save(@Valid D dto) {
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

   public List<D> saveAll(@Valid List<D> dtos) {
      try {

         if (dtos.isEmpty()) {
            throw new DataException(HttpStatus.BAD_REQUEST, "LIST_IS_NULL");
         }

         List<E> list = dtos.stream() // convierte la lista dtos en un flujo (stream) de elementos
                .map(dto -> mapper.toEntity(dto)) // convierte cada elemento de la lista en un objeto 
                .collect(Collectors.toList()); // crea una lista de objetos

         List<E> listE = repository.saveAll(list);
         return convertToDtoList(listE);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public void deleteById(I id) {
      try {
         repository.deleteById(id);
//      } catch (DataException ex) {
//         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public D update(I id, @Valid D dto) {
      try {
         repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, "ENTITY_ID_NOT_FOUND: " + id));

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

   public List<D> findAll() {
      try {
         List<E> list = repository.findAll();
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public Page<D> findAllPaged(Pageable page) {
      try {
         Page<E> pageE = repository.findAll(page);
         return pageE // se utiliza map directamente con 'taskPage' para convertirla en una página de DTO.
                .map(mapper::toDto);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   public List<D> findAllSorted(SortField sortBy, Sort.Direction sortOrder) {
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

   public Page<D> findAllSortedAndPaged(SortField sortBy, Sort.Direction sortOrder, Pageable pageable) {
      try {

         validateEntitySortBy(sortBy);
         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

         Page<E> pageE = repository.findAll(pageable);
         return pageE // se utiliza map directamente para convertirla en una página de DTO.
                .map(mapper::toDto);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   private void validateEntitySortBy(SortField sortBy) {
      final String ENTITY_NOT_FOUND = "ENTITY_SORT_BY_NOT_FOUND";

      if (sortBy == null) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }

      if (!Arrays.asList(SortField.values()).contains(sortBy)) {
         throw new DataException(HttpStatus.BAD_REQUEST, ENTITY_NOT_FOUND);
      }
   }

   // Método auxiliar para convertir una lista de entidades a DTOs
   private List<D> convertToDtoList(List<E> list) {
      return list.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(entity -> mapper.toDto(entity))
             .collect(Collectors.toList());
   }

}
