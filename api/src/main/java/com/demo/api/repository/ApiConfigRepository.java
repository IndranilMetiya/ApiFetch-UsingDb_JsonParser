package com.demo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.api.entity.ApiConfig;

public interface ApiConfigRepository extends JpaRepository<ApiConfig, Integer> {
	
	@Query("SELECT a FROM ApiConfig a WHERE a.apiName = :apiName AND a.desiredValue IN :desiredValues")
    List<ApiConfig> findByApiNameAndDesiredValues(
            @Param("apiName") String apiName, 
            @Param("desiredValues") List<String> desiredValues);

}
