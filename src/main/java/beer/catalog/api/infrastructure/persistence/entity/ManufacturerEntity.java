package beer.catalog.api.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Manufacturer")
public class ManufacturerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;

    public ManufacturerEntity(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    protected ManufacturerEntity(){}

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getCountry() { return country; }

}
