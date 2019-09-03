package by.task.ontravelsolutions.repository;

import by.task.ontravelsolutions.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    @Query("SELECT c FROM City c WHERE c.city=:city")
    City getByCityName(@Param("city") String city);
}
