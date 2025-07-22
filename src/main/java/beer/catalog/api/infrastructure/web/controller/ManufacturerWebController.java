package beer.catalog.api.infrastructure.web.controller;

import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.model.ManufacturerSearchCriteria;
import beer.catalog.api.domain.port.in.IManufacturerUseCases;
import beer.catalog.api.infrastructure.web.dto.CreateManufacturerDTO;
import beer.catalog.api.infrastructure.web.dto.ManufacturerDTO;
import beer.catalog.api.infrastructure.web.mapper.ManufacturerMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerWebController {
    private final IManufacturerUseCases service;

    public ManufacturerWebController(IManufacturerUseCases service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANUFACTURER')")
    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody @Valid CreateManufacturerDTO dtoRequest){
        Manufacturer created = service.createManufacturer(dtoRequest.name(), dtoRequest.country());
        return ResponseEntity.status(HttpStatus.CREATED).body(ManufacturerMapper.toDto(created));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ManufacturerMapper.toDto(service.getManufacturer(id)));
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> listManufacturers(ManufacturerSearchCriteria criteria, Pageable pageable){
        List<Manufacturer> manufacturers;
        if(criteria.isEmpty()){
            manufacturers = service.getAllManufacturers(pageable);
        } else{
            manufacturers = service.getAllManufacturers(criteria, pageable);
        }
        List<ManufacturerDTO> manufacturersList = manufacturers.stream()
                .map(ManufacturerMapper::toDto)
                .toList();
        return ResponseEntity.ok(manufacturersList);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('MANUFACTURER')")
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable Long id,@RequestBody @Valid ManufacturerDTO updateManufacturer){
        if(Objects.equals(id, updateManufacturer.id())){
            Manufacturer updatedManufacturer = service.updateManufacturer(updateManufacturer.id(), updateManufacturer.name(), updateManufacturer.country());
            return ResponseEntity.ok(ManufacturerMapper.toDto(updatedManufacturer));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Manufacturer id is different from existing in the body");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer (@PathVariable Long id) {
        service.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}

