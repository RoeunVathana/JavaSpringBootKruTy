package com.example.projectjava.Controller;

import com.example.projectjava.DTO.SubjectRequest;
import com.example.projectjava.DTO.SubjectResponse;
import com.example.projectjava.Service.SubjectService;
import com.example.projectjava.response.PaginationResponse;
import com.example.projectjava.response.SuccessResponse;
import com.example.projectjava.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subject/v-3")
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<PaginationResponse<List<SubjectResponse>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
            ){
            return ResponseEntity.ok(
                    ApiResponseUtil.pagination(HttpStatus.OK, subjectService.list(page, size, direction))
            );
    }

    @GetMapping("/getListAll")
    public ResponseEntity<SuccessResponse<List<SubjectResponse>>> getAll(){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, subjectService.getAll())
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<SubjectResponse>> create(@Valid @RequestBody SubjectRequest request){
        return ResponseEntity.ok(
            ApiResponseUtil.success(HttpStatus.CREATED, subjectService.create(request))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<SubjectResponse>> getOne(@PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, subjectService.getById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<SubjectResponse>> update(@Valid @PathVariable Long id, @Valid @RequestBody SubjectRequest request){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, subjectService.update(request, id))
        );
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id){
        subjectService.delete(id);
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.NO_CONTENT)
        );
    }


}

