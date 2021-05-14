package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.*;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.enums.RecordStatusConstant;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.StudentRepository;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.StudentDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentQueryService {
    StudentRepository studentRepository;

    public Page<StudentEntity> getStudents(StudentDTO studentDTO, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Specification<StudentEntity> specification = withActiveStatus(RecordStatusConstant.ACTIVE);
        if(StringUtils.isNotBlank(studentDTO.getEmail())){
            specification = specification.and(withEmail(studentDTO.getEmail()));
        }
        if(studentDTO.getPaid() != null){
            specification = specification.and(withPaid(studentDTO.getPaid()));
        }
        if(studentDTO.getLogBookSigned() != null){
            specification = specification.and(withLogBookSigned(studentDTO.getLogBookSigned()));
        }
        if(studentDTO.getSchool() != null){
            specification = specification.and(withSchool(studentDTO.getSchool()));
        }
        if(studentDTO.getState() != null){
            specification = specification.and(withState(studentDTO.getState()));
        }
        if(studentDTO.getManager() != null){
            specification = specification.and(withManager(studentDTO.getManager()));
        }
        if(studentDTO.getCoordinator() != null){
            specification = specification.and(withCoordinator(studentDTO.getCoordinator()));
        }

        return studentRepository.findAll(specification,pageable);
    }
    private Specification<StudentEntity> withActiveStatus(RecordStatusConstant status){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("recordStatus"),status));
    }

    private static Specification<StudentEntity> withPhone(String phone){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"),phone));
    }
    private static Specification<StudentEntity> withLogBookSigned(Boolean logBookSigned){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("logBookSigned"),logBookSigned));
    }
    private static Specification<StudentEntity> withPaid(Boolean paid){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("paid"),paid));
    }
    private static Specification<StudentEntity> withEmail(String email){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"),email));
    }
    private static Specification<StudentEntity> withState(StateEntity state){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("state"),state.getId()));
    }
    private static Specification<StudentEntity> withSchool(SchoolEntity school){
        return ((root, criteriaQuery, criteriaBuilder) -> {
            Join<StudentEntity,CoordinatorEntity> coordinatorEntityJoin = root.join("coordinator");
            return criteriaBuilder.equal(coordinatorEntityJoin.get("school"),school.getId());
        });
    }
    private static Specification<StudentEntity> withCoordinator(CoordinatorEntity coordinator){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("coordinator"),coordinator.getId()));
    }
    private static Specification<StudentEntity> withManager(ManagerEntity manager){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("manager"),manager.getId()));
    }
}
