package beer.catalog.api.domain.model;


public record Beer (Integer id, String name, double ABV, String type, String Description, Manufacturer manufacturer){
    public Beer{
        if(id < 0){
            throw new IllegalArgumentException("Id is mandatory field");
        }
        if(ABV > 100 || ABV < 0) {
            throw new IllegalArgumentException("ABV value should be a percentage");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Beer Name is mandatory field");
        }
        if(manufacturer == null){
            throw new IllegalArgumentException("Manufacturer is mandatory field");
        }
    }
}
