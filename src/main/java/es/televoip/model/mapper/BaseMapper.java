package es.televoip.model.mapper;

import es.televoip.model.BaseEntity;
import org.mapstruct.MappingTarget;

public interface BaseMapper<E extends BaseEntity, D extends Object> {

   D toDto(E entity);

   E toEntity(D dto);

   void updateEntity(D dto, @MappingTarget E entity);

}
