package com.example.projectjava.Service;

import com.example.projectjava.DTO.MajorRequest;
import com.example.projectjava.DTO.MajorResponse;
import com.example.projectjava.Model.Major;
import com.example.projectjava.Repository.MajorRepository;
import com.example.projectjava.util.ApiResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service

public interface MajorService {
    Page<MajorResponse> list(int page, @Max(100) @Min(1) int size, Sort.Direction direction);

    MajorResponse create(MajorRequest major);

    MajorResponse update(Long id, @Valid MajorRequest major);

    void delete(Long id);

    MajorResponse getById(Long id);
}
