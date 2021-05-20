package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.*;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.repositories.*;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.StudentDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.StudentCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.StudentQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.BankUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.StudentUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.CoordinatorResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ManagerResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StudentResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.*;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.CoordinatorService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ManagerService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StudentService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.utilities.PasswordUtil;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ProcessViolationException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceAlreadyExistsException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentServiceImpl implements StudentService {

    CoordinatorRepository coordinatorRepository;
    ManagerRepository managerRepository;
    StateRepository stateRepository;
    StudentRepository studentRepository;
    StudentQueryService studentQueryService;
    SchoolRepository schoolRepository;
    LogBookRepository logBookRepository;
    SignatureRepository signatureRepository;
    GlobalUserService globalUserService;
    ManagerService managerService;
    CoordinatorService coordinatorService;
    PasswordUtil passwordUtil;


    @Override
    public String updateCoordinatorRemark(String remark, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException("Student"));
        studentEntity.setCoordinatorRemarks(remark);
        studentRepository.save(studentEntity);
        return remark;
    }

    @Override
    public boolean signLogBook(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException("Student"));
        studentEntity.setLogBookSigned(Boolean.TRUE);
        studentRepository.save(studentEntity);
        return true;
    }

    @Override
    public boolean payStudent(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException("Student"));
        studentEntity.setPaid(Boolean.TRUE);
        studentRepository.save(studentEntity);
        return true;
    }

    @Override
    public StudentResponse saveStudent(StudentCreationRequest creationRequest) {
        if(globalUserService.emailExists(creationRequest.getUserRequest().getEmail()).isPresent())
            throw new ResourceAlreadyExistsException("Student Email");

        CoordinatorEntity coordinatorEntity = coordinatorRepository.findById(creationRequest.getCoordinatorId())
                .orElseThrow(()->new ResourceNotFoundException("Coordinator"));

        ManagerEntity managerEntity = managerRepository.findById(creationRequest.getManagerId())
                .orElseThrow(()->new ResourceNotFoundException("Manager"));

        if(studentRepository.findByRegNoAndCoordinator_School(creationRequest.getRegNo(),coordinatorEntity.getSchool()).isPresent())
            throw new ResourceAlreadyExistsException("Student Reg. No");

        StudentEntity studentEntity = StudentEntity.builder()
                .regNo(creationRequest.getRegNo())
                .startDate(creationRequest.getStartDate())
                .coordinator(coordinatorEntity)
                .manager(managerEntity)
                .build();
        studentEntity.setEmail(creationRequest.getUserRequest().getEmail());
        studentEntity.setFirstName(creationRequest.getUserRequest().getFirstName());
        studentEntity.setLastName(creationRequest.getUserRequest().getLastName());
        studentEntity.setPhone(creationRequest.getUserRequest().getPhone());
        String hash = passwordUtil.getHash(creationRequest.getUserRequest().getPassword());
        studentEntity.setPassword(hash);
        return convertEntityToModel(studentRepository.save(studentEntity));
    }

    @Override
    public StudentResponse updateStudent(StudentUpdateRequest updateRequest, Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Student"));

        CoordinatorEntity coordinatorEntity = coordinatorRepository.findById(updateRequest.getCoordinatorId())
                .orElseThrow(()->new ResourceNotFoundException("Coordinator"));

        ManagerEntity managerEntity = managerRepository.findById(updateRequest.getManagerId())
                .orElseThrow(()->new ResourceNotFoundException("Manager"));

        if(userExistsByEmail(updateRequest.getUpdateRequest().getEmail()) &&
            !updateRequest.getUpdateRequest().getEmail().equals(studentEntity.getEmail()))
            throw new ResourceAlreadyExistsException("Student email");

        studentEntity.setCoordinator(coordinatorEntity);
        studentEntity.setManager(managerEntity);
        studentEntity.setPhone(updateRequest.getUpdateRequest().getPhone());
        studentEntity.setEmail(updateRequest.getUpdateRequest().getEmail());
        return convertEntityToModel(studentRepository.save(studentEntity));
    }

    @Override
    public BankDetails updateBank(BankUpdateRequest updateRequest, Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException("Student"));

        //student cannot change account details after payment
        if(studentEntity.getPaid())
            throw new ProcessViolationException("Student is already paid");

        studentEntity.setBankName(updateRequest.getBankName());
        studentEntity.setBankSortCode(updateRequest.getSortCode());
        studentEntity.setAccountName(updateRequest.getAccountName());
        studentEntity.setAccountNumber(updateRequest.getAccountNumber());
        studentEntity = studentRepository.save(studentEntity);
        return getBankDetails(studentEntity);
    }

    @Override
    public PagedData<StudentResponse> getStudents(StudentQueryRequest queryRequest, int page, int size) {
        ManagerEntity managerEntity = managerRepository.findByEmail(queryRequest.getManagerEmail()).orElse(null);
        CoordinatorEntity coordinatorEntity = coordinatorRepository.findByEmail(queryRequest.getManagerEmail()).orElse(null);
        SchoolEntity schoolEntity = schoolRepository.findByName(queryRequest.getSchoolName()).orElse(null);
        StateEntity stateEntity = stateRepository.findByName(queryRequest.getStateName()).orElse(null);

        StudentDTO studentDTO = StudentDTO.builder()
                .email(queryRequest.getEmail())
                .school(schoolEntity)
                .coordinator(coordinatorEntity)
                .manager(managerEntity)
                .state(stateEntity)
                .paid(queryRequest.getPaid())
                .logBookSigned(queryRequest.getLogBookSigned())
                .build();
        Page<StudentEntity> studentEntityPage = studentQueryService.getStudents(studentDTO,page,size);
        return new PagedData<>(studentEntityPage.stream().map(this::convertEntityToModel).collect(Collectors.toList()),
                studentEntityPage.getTotalElements(),
                studentEntityPage.getTotalPages());
    }

    @Override
    public StudentResponse updateTask(Long studentId, String task, LocalDate taskDate) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException("Student"));

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+1"));//Lagos Time
        if(now.getHour() < 7)
            throw  new ProcessViolationException("Logs cannot be entered before 7 AM");

        //LocalDate taskDate = LocalDate.now();
        LogBookEntity logEntry = logBookRepository.findByStudentAndTaskDate(studentEntity,taskDate).orElse(null);
        if(logEntry == null){
            logEntry = LogBookEntity.builder()
                    .task(task)
                    .student(studentEntity)
                    .taskDate(taskDate)
                    .build();
        }else{
            //if it already exists, update the task instead
            logEntry.setTask(task);
        }
        logBookRepository.save(logEntry);
        return convertEntityToModel(studentEntity);
    }

    @Override
    public Optional<AppUserDetails> getAppUserDetailsByEmail(String email) {
        Optional<StudentEntity> optionalStudentEntity = studentRepository.findByEmail(email);
        return optionalStudentEntity.map(this::getUserDetails);
    }

    @Override
    public StudentResponse convertEntityToModel(StudentEntity entity) {
        //there is always a manager
        ManagerResponse manager  = managerService.getUserById(entity.getManager().getId());
        CompanyDetails companyDetails = CompanyDetails.builder()
                .companyAddress(manager.getCompanyAddress())
                .companyName(manager.getCompanyName())
                .companyType(manager.getCompanyType())
                .companyState(manager.getCompanyState())
                .managerPhone(manager.getPersonalDetail().getPhone())
                .managerEmail(manager.getPersonalDetail().getEmail())
                .managerName(manager.getPersonalDetail().getLastName()+" "+manager.getPersonalDetail().getFirstName())
                .build();

        CoordinatorResponse coordinator = coordinatorService.getUserById(entity.getCoordinator().getId());
        SchoolDetails schoolDetails = SchoolDetails.builder()
                .schoolName(coordinator.getSchool().getName())
                .schoolAddress(coordinator.getSchool().getAddress())
                .schoolState(coordinator.getSchool().getState())
                .coordinatorPhone(coordinator.getAppUserDetails().getPhone())
                .department(coordinator.getDepartment())
                .coordinatorEmail(coordinator.getAppUserDetails().getEmail())
                .coordinatorName(coordinator.getAppUserDetails().getLastName()+" "+coordinator.getAppUserDetails().getFirstName())
                .coordinatorRemark(entity.getCoordinatorRemarks())
                .build();

        return StudentResponse.builder()
                .id(entity.getId())
                .paid(entity.getPaid())
                .personalDetail(getUserDetails(entity))
                .regNo(entity.getRegNo())
                .startDate(entity.getStartDate().format(DateTimeFormatter.ISO_DATE))
                .bank(getBankDetails(entity))
                .logBook(getLogBookDetails(entity))
                .companyDetails(companyDetails)
                .schoolDetails(schoolDetails)
                .build();
    }
    private BankDetails getBankDetails(StudentEntity entity){
        return BankDetails.builder()
                .bankName(entity.getBankName())
                .accountName(entity.getAccountName())
                .accountNumber(entity.getAccountNumber())
                .bankSortCode(entity.getBankSortCode())
                .build();
    }
    private LogBookDetails getLogBookDetails(StudentEntity entity){
        Sort sort = Sort.by("createdAt");
        List<LogBookEntity> logBookEntities = logBookRepository.findAllByStudent(entity,sort);
        Map<String, LogBookEntity> map = new HashMap<>();

        LogBookEntity emptyLogBookEntity = LogBookEntity.builder()
                .task("")
                .build();
        for(LogBookEntity logBookEntity: logBookEntities){
            map.put(logBookEntity.getTaskDate().format(DateTimeFormatter.ISO_DATE), logBookEntity);
        }

        //start date might not be Monday, in that, we need to get Monday
        LocalDate curDate = getStartDay(entity.getStartDate());
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<WeeklyTaskDetails> weeklyTasks = new ArrayList<>();
        List<DailyTaskDetails> dailyTasks = new ArrayList<>();
        DailyTaskDetails dailyTask = null;
        WeeklyTaskDetails weeklyTask = null;
        while(curDate.isBefore(tomorrow)){

            String curDateStr = curDate.format(DateTimeFormatter.ISO_DATE);
            boolean editable = (curDate.isAfter(entity.getStartDate().minusDays(1)) )
                                    && curDate.getDayOfWeek().getValue() < 6;//saturday and sunday are not editable

            dailyTask = DailyTaskDetails.builder()
            .editable(editable)
            .task(map.getOrDefault(curDateStr,emptyLogBookEntity).getTask())
            .date(curDateStr)
            .build();
            dailyTasks.add(dailyTask);

            if(curDate.getDayOfWeek().getValue() == 7 || curDate.isEqual(today)){//week end
                boolean signed = signatureRepository.existsByStudentAndStartTaskDateAndEndTaskDate(entity,getStartDay(curDate), curDate);
                weeklyTask = WeeklyTaskDetails.builder()
                        .weekNo(weeklyTasks.size())
                        .dailyTasks(dailyTasks)
                        .signed(signed)
                        .build();
                weeklyTasks.add(weeklyTask);
                dailyTasks = new ArrayList<>();
            }
            curDate = curDate.plusDays(1);
        }

        return LogBookDetails.builder()
                .signed(entity.getLogBookSigned())
                .weeklyTaskDetails(weeklyTasks)
                .build();
    }
    //If start date is not Monday, get Monday of the week program started
    private LocalDate getStartDay(LocalDate startDate){
        int startDayNo = startDate.getDayOfWeek().getValue();//1 -> Monday through 7->Sunday
        if(startDayNo > 1)
            return LocalDate.from(startDate).minusDays(startDayNo-1);
        return LocalDate.from(startDate);
    }

    @Override
    public StudentResponse updateUserPassword(String password, Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student"));
        String hash = passwordUtil.getHash(password);
        studentEntity.setPassword(hash);
        return convertEntityToModel(studentRepository.save(studentEntity));
    }


    @Override
    public StudentResponse signWeekLog(Long student, Long managerId, LocalDate startDate, LocalDate endDate) {

        StudentEntity studentEntity = studentRepository.findById(student)
                .orElseThrow(()->new ResourceNotFoundException("Student"));

        if(signatureRepository.existsByStudentAndStartTaskDateAndEndTaskDate(studentEntity,startDate,endDate))
            throw new ResourceAlreadyExistsException("Logbook is already signed for this week.","");

        ManagerEntity managerEntity = managerRepository.findById(managerId)
                .orElseThrow(()->new ResourceNotFoundException("Manager"));

        if(logBookRepository.countAllByStudentAndTaskDateIsBetween(studentEntity,startDate,endDate) == 0)
            throw new ProcessViolationException("Student does not have any log this week");

        LocalDate lastDay = startDate.plusDays(6);
        if(lastDay.isAfter(endDate)){
            throw new ProcessViolationException("Current week is not due for signing");
        }
        SignatureEntity signatureEntity = SignatureEntity.builder()
                .manager(managerEntity)
                .student(studentEntity)
                .startTaskDate(startDate)
                .endTaskDate(endDate)
                .build();
        signatureRepository.save(signatureEntity);
        return getUserById(student);
    }

    @Override
    public  boolean userExistsById(Long id){
        return studentRepository.existsById(id);
    }

    @Override
    public boolean userExistsByEmail(String email){
        return studentRepository.existsByEmail(email);
    }

    @Override
    public StudentResponse getUserByEmail(String email){
        Optional<StudentEntity> user = studentRepository.findByEmail(email);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    @Override
    public StudentResponse getUserById(Long id){
        Optional<StudentEntity> user = studentRepository.findById(id);
        return user.map(this::convertEntityToModel).orElseThrow(()-> new ResourceNotFoundException("User"));
    }

    public AppUserDetails getUserDetails(StudentEntity userEntity){
        return AppUserDetails.builder()
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userType(userEntity.getUserType().toString())
                .phone(userEntity.getPhone())
                .build();
    }
}
