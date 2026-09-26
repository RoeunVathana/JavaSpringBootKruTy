package com.example.projectjava.Service.Implement;

import com.example.projectjava.DTO.SubjectRequest;
import com.example.projectjava.DTO.SubjectResponse;
import com.example.projectjava.Model.Subject;
import com.example.projectjava.Repository.SubjectRepository;
import com.example.projectjava.Service.SubjectService;
import com.example.projectjava.exception.CustomException;
import com.example.projectjava.response.SuccessResponse;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ModelMapper mapper;

    @Override
    public Page<SubjectResponse> list(
            int page,
            int size,
            Sort.Direction direction
    ) {

        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by(direction, "id")
        );

        return subjectRepository.findAll((root, query, cb) -> {

            List<Predicate> predicateList = new ArrayList<>();

            return cb.and(
                    predicateList.toArray(new Predicate[0])
            );

        }, pageable).map(src -> mapper.map(src, SubjectResponse.class));
    }

    @Override
    public List<SubjectResponse> getAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return subjectRepository.findAll(sort).stream()
                .map(src -> mapper.map(src, SubjectResponse.class))
                .toList();
    }

    @Override
    public SubjectResponse create(SubjectRequest request) {

        String name = request.getName().trim();

        if (subjectRepository.existsByNameIgnoreCase(name)) {
            throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "Name already exists"
            );
        }

        Subject subject = mapper.map(request, Subject.class);

        Subject savedSubject = subjectRepository.save(subject);

        return mapper.map(savedSubject, SubjectResponse.class);
    }

    @Override
    public SubjectResponse getById(Long id) {
        return subjectRepository.findById(id).map(src -> mapper.map(src, SubjectResponse.class)).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "Subject is not found!")
        );
    }

    @Override
    public SubjectResponse update(SubjectRequest request, Long id) {
        boolean existing = subjectRepository.existsByNameIgnoreCase(request.getName().trim());

        if (existing) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Name already exists!");
        }

        Subject subject = subjectRepository.findById(id).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "Subject is not found!")
        );

        mapper.map(request, subject);
        return mapper.map(subjectRepository.save(subject), SubjectResponse.class);
    }

    @Override
    public Void delete(Long id) {
        return null;
    }
}