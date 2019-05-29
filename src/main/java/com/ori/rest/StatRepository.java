package com.ori.rest;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
@RepositoryRestResource(collectionResourceRel = "stats", path = "stats")
public interface StatRepository extends CrudRepository<Stat, Long> {
  
	    
	    	public String findByNameStr = "from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') order by instime asc";
	        public String findByNameAndTimeBeforeStr ="from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime < :time";
	        public String findByNameAndTimeSinceStr = "from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime >= :time";
	        public String findByNameOfDateStr = "from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime between :date and :nextday order by instime asc ";       
	        public String findByTimeSinceStr = "from Stat h where  h.instime >= :time";
	        
	        public String findNamesStr = "select max(name) as name from Stat h group by h.name";
	        	        
	        @Query(findByNameStr)
	        public Iterable<Stat> findByName(@Param("name") String name);
	        
	        @Query(findByNameAndTimeBeforeStr)
	        public Iterable<Stat> findByNameAndTimeBefore(@Param("name") String name, @Param("time") String time);

	        @Query(findByNameAndTimeSinceStr)
	        public Iterable<Stat> findByNameAndTimeSince(@Param("name") String name, @Param("time") String time);
	        
	        @Query(findByNameOfDateStr)
	        public Iterable<Gps> findByNameOfDate(@Param("name") String name, @Param("date") String date,  @Param("nextday") String nextDay );
	              
	        @Query(findByTimeSinceStr)
	        public Iterable<Stat> findByTimeSince( @Param("time") String time); 
	        
	        @Query(findNamesStr)
	        public Iterable<String> findAllNames();	   

//		    @Query("from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%')")
//		    public Iterable<Stat> findByName(@Param("name") String name);
//
//		    @Query("from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime < :time")
//		    public Iterable<Stat> findByNameAndTimeBefore(@Param("name") String name, @Param("time") String time);
//
//		    @Query("from Stat h where lower(h.name) like CONCAT('%', lower(:name), '%') and h.instime >= :time")
//		    public Iterable<Stat> findByNameAndTimeSince(@Param("name") String name, @Param("time") String time);
//
//		    @Query("from Stat h where  h.instime >= :time")
//		    public Iterable<Stat> findByTimeSince( @Param("time") String time); 
}