package by.task.telegrambot.repository;

import by.task.telegrambot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.city=:city")
    City getByCityName(@Param("city") String city);

    @Modifying
    @Transactional
    @Query("DELETE FROM City c WHERE c.id=:id")
    int delete(@Param("id") long id);
}
