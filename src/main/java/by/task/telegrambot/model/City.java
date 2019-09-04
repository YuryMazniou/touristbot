package by.task.telegrambot.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private long id;
    @Column(name = "city", unique = true, length = 80, nullable = false)
    private String city;
    @Column(name = "info", nullable = false)
    private String info;

    public City() {
    }

    public City(String city, String info){
        this(0L, city, info);
    }

    public City(long id, String city, String info) {
        this.id = id;
        this.city = city;
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City cities = (City) o;
        return id == cities.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cities{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
