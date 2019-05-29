package com.ori.rest;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface StatRepository extends CrudRepository<Stat, Long> {
 
    @Query("from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%')")
    public Iterable<Stat> findByName(@Param("name") String name);
     
}