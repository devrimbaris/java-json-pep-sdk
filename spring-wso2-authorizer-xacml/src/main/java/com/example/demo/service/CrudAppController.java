package com.example.demo.service;

import com.example.demo.service.AbstractAppController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class CrudAppController<E> extends AbstractAppController {


  protected final Class<E> entityClass;

  /// constructors

  public CrudAppController(final Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  /// methods

  protected E prepareForRead(final E entity) {
    return entity;
  }

  protected E prepareForSave(final E entity) {
    return entity;
  }
  public Class<E> getEntityClass() {
    return entityClass;
  }

//  @ReadMapping("/{id}")
//  public ResponseEntity<E> read(@PathVariable("id") long id) {
//    final E entity = service.read(id);
//    if (entity == null) {
//      throw new ObjectNotFoundException(ObjectLabels.getLabel(entityClass), Long.toString(id));
//    }
//
//    final E preparedEntity = prepareForRead(entity);
//    return ResponseEntity.ok(preparedEntity);
//  }
//
//  @CreateMapping("")
//  public ResponseEntity<E> create(RequestEntity<E> entity) {
//    final E preparedEntity = prepareForSave(entity.getBody());
//    final E returnedEntity = service.create(preparedEntity);
//    return ResponseEntity.ok(returnedEntity);
//  }
//
//  @UpdateMapping("/{id}")
//  public ResponseEntity<E> update(@PathVariable("id") long id, RequestEntity<E> entity) {
//    final E preparedEntity = prepareForSave(entity.getBody());
//    final E returnedEntity = service.update(id, preparedEntity);
//    return ResponseEntity.ok(returnedEntity);
//  }
//
//  @DeleteMapping("/{id}")
//  public ResponseEntity<GenericResponse> delete(@PathVariable("id") long id, RequestEntity<E> entity) {
//    final E preparedEntity = prepareForSave(entity.getBody());
//    service.delete(id, preparedEntity);
//    return ResponseEntity.ok(GenericResponse.createEmpty());
//  }
}
