package com.example.projectjava.Service;

import com.example.projectjava.DTO.SubjectRequest;
import com.example.projectjava.DTO.SubjectResponse;
import com.example.projectjava.response.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {
    Page<SubjectResponse> list(int page, int size, Sort.Direction direction);

    List<SubjectResponse> getAll();

    SubjectResponse create(@Valid SubjectRequest request);

    SubjectResponse getById(Long id);

    SubjectResponse update(@Valid SubjectRequest request, @Valid Long id);
    void delete(Long id);
}
