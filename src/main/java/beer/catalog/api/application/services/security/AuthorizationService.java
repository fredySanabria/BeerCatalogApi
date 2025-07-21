package beer.catalog.api.application.services.security;

import beer.catalog.api.domain.exceptions.ManufacturerNotFoundException;
import beer.catalog.api.domain.exceptions.NoAccessAuthorizationException;
import beer.catalog.api.domain.model.Manufacturer;
import beer.catalog.api.domain.port.out.IManufacturerCRUDRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final IManufacturerCRUDRepository repository;

    public AuthorizationService(IManufacturerCRUDRepository repository) {
        this.repository = repository;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    public void validateManufacturerAccess(Long idManufacturer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String[] parts = username.split("-");
        Long manufacturerIdFromUsername = Long.valueOf(parts[0]);

        Manufacturer manufacturer = repository.getManufacturer(idManufacturer)
                .orElseThrow(() -> new ManufacturerNotFoundException(idManufacturer.toString()));

        if (!manufacturer.belongsTo(manufacturerIdFromUsername)) {
            throw new NoAccessAuthorizationException("You can't modify Manufacturer data from other Manufacturer");
        }
    }
}
