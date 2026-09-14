package com.example.projectjava.Controller;

import com.example.projectjava.DTO.TeacherRequest;
import com.example.projectjava.DTO.TeacherResponse;
import com.example.projectjava.Model.Teacher;
import com.example.projectjava.Repository.TeacherRepository;
import com.example.projectjava.Service.TeacherService;
import com.example.projectjava.response.SuccessResponse;
import com.example.projectjava.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/v-3")
@RequiredArgsConstructor
public class TeacherController3 {

    private final TeacherService teacherService;
//    public TeacherController3(TeacherService teacherService) {
//        this.teacherService = teacherService;
//    }


    @GetMapping("/list")
    public ResponseEntity<List<TeacherResponse>> list(){
        return ResponseEntity.ok(teacherService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getById(@PathVariable int id){
        return ResponseEntity.ok(teacherService.listOne(id));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, teacherService.filter(page, size, name, direction))
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> save(@Valid @RequestBody TeacherRequest teacherRequest){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.CREATED, teacherService.create(teacherRequest))
        );
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> update(@Valid @RequestBody TeacherRequest teacherRequest, @PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.OK, teacherService.update(teacherRequest, id))
        );
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id){
        teacherService.delete(id);
        return ResponseEntity.ok(
                ApiResponseUtil.success(HttpStatus.NO_CONTENT)
        );
    }

}
