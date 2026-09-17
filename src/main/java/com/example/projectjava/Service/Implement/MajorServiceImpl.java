package com.example.projectjava.Service.Implement;

import com.example.projectjava.DTO.MajorRequest;
import com.example.projectjava.DTO.MajorResponse;
import com.example.projectjava.Model.Major;
import com.example.projectjava.Repository.MajorRepository;
import com.example.projectjava.Service.MajorService;
import com.example.projectjava.exception.CustomException;
import com.example.projectjava.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {
    private  final MajorRepository majorRepository;
    private ModelMapper mapper;

    @Override
    public Page<MajorResponse> list(int page, int size, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(
                page - 1,size,Sort.by(direction, "name")
        );

        return majorRepository.findAll((root, query, cb) -> {

            List<Predicate> predicateList = new ArrayList<>();

            return cb.and( predicateList.toArray(new Predicate[0]));

        }, pageable).map(src ->mapper.map(src, MajorResponse.class) );
    }

    @Override
    public MajorResponse create(MajorRequest major) {
        if(majorRepository.findByName(major.getName().trim()).isPresent()){
            throw  new CustomException(HttpStatus.BAD_REQUEST, "Major Already Exists");
        }
        Major majorSub = mapper.map(major, Major.class);

        return mapper.map(majorRepository.save(majorSub), MajorResponse.class);
    }

    @Override
    public MajorResponse update(Long id, MajorRequest major) {
        Optional<Major> exist = majorRepository.findByName(major.getName());
        if(exist.isPresent() && !exist.get().getId().equals(id)){
            throw  new CustomException(HttpStatus.NOT_FOUND, "Major Already Exists");
        }

        Major majorSub = majorRepository.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "Major Not Found"));
        mapper.map(major, majorSub);
        return mapper.map(majorRepository.save(majorSub), MajorResponse.class);
    }

    @Override
    public void delete(Long id) {
        Major major = this.majorRepository.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "Major Not Found"));
        majorRepository.delete(major);
    }

    @Override
    public MajorResponse getById(Long id) {
        return majorRepository.findById(id)
                .map(src -> mapper.map(src, MajorResponse.class))
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Major Not Found"));
    }
}
