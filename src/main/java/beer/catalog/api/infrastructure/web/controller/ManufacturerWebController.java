package beer.catalog.api.infrastructure.web.controller;

import beer.catalog.api.application.services.manufacturer.ManufacturerService;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.infrastructure.web.dto.CreateManufacturerDTO;
import beer.catalog.api.infrastructure.web.dto.ManufacturerDTO;
import beer.catalog.api.infrastructure.web.mapper.ManufacturerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerWebController {
    private final ManufacturerService service;

    public ManufacturerWebController(ManufacturerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody CreateManufacturerDTO dtoRequest){
        Manufacturer created = service.createOrUpdateManufacturer(null, dtoRequest.name(), dtoRequest.country());
        return ResponseEntity.status(HttpStatus.CREATED).body(ManufacturerMapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturer(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(ManufacturerMapper.toDto(service.getManufacturer(id)));
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> listManufacturers(){
        List<ManufacturerDTO> manufacturersList = service.getAllManufacturers().stream()
                .map(ManufacturerMapper::toDto)
                .toList();
        return ResponseEntity.ok(manufacturersList);
    }
}
