package com.example.projectjava.Controller;

import com.example.projectjava.DTO.MajorRequest;
import com.example.projectjava.DTO.MajorResponse;
import com.example.projectjava.Model.Major;
import com.example.projectjava.Repository.MajorRepository;
import com.example.projectjava.Service.MajorService;
import com.example.projectjava.response.PaginationResponse;
import com.example.projectjava.response.SuccessResponse;
import com.example.projectjava.util.ApiResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/major/v-3")
public class MajorController {
    private final MajorService majorService;

    @GetMapping
    public ResponseEntity<PaginationResponse<List<MajorResponse>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") @Max(100) @Min(1) int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return ResponseEntity.ok(
                ApiResponseUtil.pagination(HttpStatus.OK, majorService.list(page, size, direction)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<MajorResponse>> getMajorById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, majorService.getById(id)));
    }


    @PostMapping
    public ResponseEntity<SuccessResponse<MajorResponse>> create(@Valid @RequestBody MajorRequest major) {
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.CREATED, majorService.create(major)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<MajorResponse>> update(@PathVariable  Long id, @Valid @RequestBody MajorRequest major) {
       return ResponseEntity.ok(
               ApiResponseUtil.success(HttpStatus.OK, majorService.update(id, major)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable  Long id) {
        majorService.delete(id);
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.NO_CONTENT));
    }
}
