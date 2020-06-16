package org.spring.data.example.app;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource(path = "student", collectionResourceRel = "student")
public interface StudentRepository  extends CrudRepository<Student,Long> {

    //@Query
    @RestResource(path = "name", rel = "name")
    public List<Student> getByName(@RequestParam(name = "program")String name);

    //@Query
    @RestResource(path = "program", rel = "program")
    public List<Student> getByProgram(@RequestParam(name = "program")String program);
}
