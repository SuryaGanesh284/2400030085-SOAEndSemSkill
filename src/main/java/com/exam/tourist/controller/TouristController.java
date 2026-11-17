package com.exam.tourist.controller;

import com.exam.tourist.model.Tourist;
import com.exam.tourist.repository.TouristRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tourists")
@Tag(name = "Tourist Management", description = "APIs for managing tourist records")
public class TouristController {

    @Autowired
    private TouristRepository touristRepository;

    // Define valid token value (can be tested with "secret123" or customized)
    public static final String VALID_TOKEN = "secret123";

    /**
     * Requirement 1 & 2:
     * - Validates "Token: <Token_value>" header in incoming PUT request.
     * - PUT endpoint /api/tourists/{id} to fetch existing record, modify fields, and save to DB.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Tourist Record", description = "Validates Token header, fetches existing record by id, updates fields, and saves to database.")
    public ResponseEntity<?> updateTourist(
            @PathVariable Long id,
            @Parameter(
                name = "Token",
                description = "Header token for authorization (e.g., secret123)",
                in = ParameterIn.HEADER,
                required = true
            )
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody Tourist updatedData) {

        // 1. Validate request token header
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Missing 'Token' header. Please supply 'Token: secret123'.");
        }

        if (!VALID_TOKEN.equals(token.trim())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized: Invalid Token value provided.");
        }

        // 2. Fetch existing tourist record from database
        Optional<Tourist> existingOptional = touristRepository.findById(id);
        if (existingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tourist record with ID " + id + " not found.");
        }

        // 3. Modify fields
        Tourist existingTourist = existingOptional.get();
        if (updatedData.getName() != null) {
            existingTourist.setName(updatedData.getName());
        }
        if (updatedData.getEmail() != null) {
            existingTourist.setEmail(updatedData.getEmail());
        }
        if (updatedData.getDestination() != null) {
            existingTourist.setDestination(updatedData.getDestination());
        }
        if (updatedData.getBudget() != null) {
            existingTourist.setBudget(updatedData.getBudget());
        }

        // 4. Save modified data to database
        Tourist savedTourist = touristRepository.save(existingTourist);

        return ResponseEntity.ok(savedTourist);
    }

    // Helper GET endpoint to view all tourists
    @GetMapping
    @Operation(summary = "Get All Tourists", description = "Fetch all tourist records from database")
    public List<Tourist> getAllTourists() {
        return touristRepository.findAll();
    }

    // Helper GET endpoint to view a specific tourist
    @GetMapping("/{id}")
    @Operation(summary = "Get Tourist By ID", description = "Fetch single tourist record by id")
    public ResponseEntity<?> getTouristById(@PathVariable Long id) {
        return touristRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tourist not found with ID: " + id));
    }

    // Helper POST endpoint to create a new tourist
    @PostMapping
    @Operation(summary = "Create Tourist", description = "Add a new tourist record to database")
    public ResponseEntity<Tourist> createTourist(@RequestBody Tourist tourist) {
        Tourist saved = touristRepository.save(tourist);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
