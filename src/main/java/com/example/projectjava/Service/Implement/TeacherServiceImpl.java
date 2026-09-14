package com.example.projectjava.Service.Implement;

import com.example.projectjava.DTO.TeacherRequest;
import com.example.projectjava.DTO.TeacherResponse;
import com.example.projectjava.Model.Teacher;
import com.example.projectjava.Repository.TeacherRepository;
import com.example.projectjava.Service.TeacherService;
import com.example.projectjava.exception.CustomException;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Predicate;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

//    public TeacherServiceImpl(TeacherRepository teacherRepository) {
//        this.teacherRepository = teacherRepository;
//    }

    @Override
    public List<TeacherResponse> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return teacherRepository.findAll(sort).stream().map(Teacher::toResponse).toList();
    }

    @Override
    public TeacherResponse listOne(int id) {
        Teacher teacher = teacherRepository.findById((long) id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Teacher not found"));
        return teacher.toResponse();
    }

    @Override
    public Page<TeacherResponse> filter(int page, int size, String name, Sort.Direction direction) {
        Sort sort = Sort.by(direction, "id").and(Sort.by(direction, "name"));

        PageRequest pageable = PageRequest.of(page - 1, size, sort);

        return teacherRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(Teacher::toResponse);
//        if(StringUtils.hasText(name)){
//            return teacherRepository.searchTeacherByNameContainingIgnoreCase(name, pageable).map(Teacher::toResponse);
//        }
//
//        return teacherRepository.searchTeacherByNameContainingIgnoreCase(name, pageable).map(Teacher::toResponse);
    }

    @Override
    public TeacherResponse create(TeacherRequest teacherRequest) {
        if(teacherRequest.getCardRequest() == null){
            throw  new CustomException(HttpStatus.BAD_REQUEST, "Card is required");
        }

        String code = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12);
        Teacher teacher = teacherRequest.toEntity(code);
        return teacherRepository.save(teacher).toResponse();


    }

    @Override
    public TeacherResponse update(TeacherRequest teacherRequest, Long id) {
        Teacher teacher = teacherRepository.findById((long) Math.toIntExact(id)).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Teacher not found"));
        teacher.setName(teacherRequest.getName());
        teacher.setGender(teacherRequest.getGender());
        teacher.setAge(teacherRequest.getAge());
        teacher.setClassTeach(teacherRequest.getClassTeach());
        teacher.getCard().setExpiryDate(teacherRequest.getCardRequest().getExpiryDate());
        teacher.getCard().setIssueDate(teacherRequest.getCardRequest().getIssueDate());
        return teacherRepository.save(teacher).toResponse();
    }

    @Override
    public void delete(Long id) {
        Teacher teacher = teacherRepository.findById((long) Math.toIntExact(id)).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Teacher not found"));
        teacherRepository.delete(teacher);
    }
}
