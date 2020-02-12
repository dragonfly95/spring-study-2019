package com.example.kokomong.repository;

import com.example.kokomong.model.SampleTable;
import org.springframework.data.repository.CrudRepository;

public interface YourSampleRepository extends CrudRepository<SampleTable, Long> {
    SampleTable findOne(Long id);
}
