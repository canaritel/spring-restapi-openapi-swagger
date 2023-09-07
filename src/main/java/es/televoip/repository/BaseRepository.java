package es.televoip.repository;

import es.televoip.model.BaseEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

// En este método declaramos los tipos de datos que vamos a permitir < Base, Serializable>
@NoRepositoryBean // no requerimos este repositorio se pueda instanciar
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID> {

}
