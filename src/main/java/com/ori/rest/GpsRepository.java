package com.ori.rest;
 
import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
@RepositoryRestResource(collectionResourceRel = "gpses", path = "gpses")
public interface GpsRepository extends CrudRepository<Gps, Long> {
	public String findByNameStr = "from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') order by instime asc";
    public String findByNameAndTimeBeforeStr ="from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime < :time";
    public String findByNameAndTimeSinceStr = "from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime >= :time";
    public String findByNameOfDateStr = "from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime between :date and :nextday order by instime asc ";       
    public String findByTimeSinceStr = "from Gps h where  h.instime >= :time";
    
    public String findNamesStr = "select max(name) as name from Gps h group by h.name";
    
//    @Query("from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%')")
//    public Iterable<Gps> findByName(@Param("name") String name);
//
//    @Query("from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime < :time")
//    public Iterable<Gps> findByNameAndTimeBefore(@Param("name") String name, @Param("time") String time);
//
//    @Query("from Gps h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime >= :time")
//    public Iterable<Gps> findByNameAndTimeSince(@Param("name") String name, @Param("time") String time);
//
//    @Query("from Gps h where  h.instime >= :time")
//    public Iterable<Gps> findByTimeSince( @Param("time") String time);  
    
    @Query(findByNameStr)
    public Iterable<Gps> findByName(@Param("name") String name);
    
    @Query(findByNameAndTimeBeforeStr)
    public Iterable<Gps> findByNameAndTimeBefore(@Param("name") String name, @Param("time") String time);

    @Query(findByNameAndTimeSinceStr)
    public Iterable<Gps> findByNameAndTimeSince(@Param("name") String name, @Param("time") String time);
    
    @Query(findByNameOfDateStr)
    public Iterable<Gps> findByNameOfDate(@Param("name") String name, @Param("date") String date,  @Param("nextday") String nextDay );
          
    @Query(findByTimeSinceStr)
    public Iterable<Gps> findByTimeSince( @Param("time") String time); 
    
    @Query(findNamesStr)
    public Iterable<String> findAllNames();

}