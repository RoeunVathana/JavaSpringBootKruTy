package com.example.projectjava.Service.Implement;

import com.example.projectjava.DTO.StudentRequest;
import com.example.projectjava.DTO.StudentResponse;
import com.example.projectjava.Model.Card;
import com.example.projectjava.Model.Student;
import com.example.projectjava.Repository.StudentRepository;
import com.example.projectjava.Service.StudentService;
import com.example.projectjava.exception.CustomException;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private ModelMapper mapper;


//    public StudentServiceImpl(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }

    @Override
    public List<StudentResponse> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return studentRepository.findAll(sort).stream().map(Student::toResponse).toList();
    }

    @Override
    public StudentResponse create(StudentRequest request) {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (request.getCard() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Card is required");
        }

        String code = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        Student student = mapper.map(request, Student.class);
        student.getCard().setCode(code);

        return mapper.map(studentRepository.save(student), StudentResponse.class);
//        return studentRepository.save(student)
//        Student studentRe = request.toEntity(code);
//        return studentRepository.save(student).toResponse();
//        return studentRepository.save(studentRe).toResponse();

    }

    @Override
    public StudentResponse update(StudentRequest studentRequest, long id) {
        Student stuUp = studentRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Student not found"));
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
//        stuUp.setName(studentRequest.getName());
//        stuUp.setAge(studentRequest.getAge());
//        stuUp.setGender(studentRequest.getGender());
//        stuUp.getCard().setIssueDate(studentRequest.getCard().getIssueDate());
//        stuUp.getCard().setExpiryDate(studentRequest.getCard().getExpiryDate());
//        if (studentRequest.getCardRequest() != null) {
//            String code = stuUp.getCard() != null ? stuUp.getCard().getCode() : UUID.randomUUID().toString().replace("-", "").substring(0, 12);
//            Card card = new Card(
//                    studentRequest.getCardRequest().getIssueDate(),
//                    studentRequest.getCardRequest().getExpiryDate(),
//                    code
//            );
//            stuUp.setCard(card);
//            card.setStudent(stuUp);
//        }

        mapper.map(studentRequest, stuUp);
        return mapper.map(studentRepository.save(stuUp), StudentResponse.class);

//        return studentRepository.save(stuUp).toResponse();
    }

    @Override
    public void delete(long id) {
        Student stu = studentRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );

        studentRepository.delete(stu);
    }

    @Override
    public StudentResponse listOne(long id) {
        return studentRepository.findById(id)
                .map(src -> mapper.map(src, StudentResponse.class))
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @Override
    public Page<StudentResponse> filter(
            int page,
            int size,
            String name,
            Sort.Direction direction
    ) {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Sort sort = Sort.by(direction, "id").and(Sort.by(direction, "name"));
        PageRequest pageable = PageRequest.of(page - 1, size, sort);
        return studentRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                String keyword = "%" + name.trim().toLowerCase() + "%";
                predicates.add( cb.like(cb.lower(root.get("name")), keyword));
            }
            return cb.and(predicates.toArray(new Predicate[0]));

        }, pageable).map(src -> mapper.map(src, StudentResponse.class));
//                    map(Student::toResponse)
    }
}
