package beer.catalog.api.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Beer")
public class BeerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double ABV;
    private String type;
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    public BeerEntity(Long id, String name, double ABV, String type, String description, ManufacturerEntity manufacturer) {
        this.id = id;
        this.name = name;
        this.ABV = ABV;
        this.type = type;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    protected BeerEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getABV() {
        return ABV;
    }

    public void setABV(double ABV) {
        this.ABV = ABV;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }
}
