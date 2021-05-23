package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StudentEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.StudentCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.StudentQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.BankUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.StudentUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StudentResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.BankDetails;

import java.time.LocalDate;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
public interface StudentService extends UserService<StudentEntity, StudentResponse, Long>  {
    String updateCoordinatorRemark(String remark, Long studentId,String coordinatorEmail);
    boolean signLogBook(Long studentId);
    boolean payStudent(Long student);
    StudentResponse saveStudent(StudentCreationRequest creationRequest);
    StudentResponse updateStudent(StudentUpdateRequest updateRequest, Long id);
    BankDetails updateBank(BankUpdateRequest updateRequest, Long studentId);
    PagedData<StudentResponse> getStudents(StudentQueryRequest queryRequest, int page, int size);
    StudentResponse updateTask(Long studentId, String task, LocalDate taskDate);
    StudentResponse updateUserPassword(String password, Long id);
    StudentResponse signWeekLog(Long student, Long managerId, LocalDate startDate, LocalDate endDate);
}
