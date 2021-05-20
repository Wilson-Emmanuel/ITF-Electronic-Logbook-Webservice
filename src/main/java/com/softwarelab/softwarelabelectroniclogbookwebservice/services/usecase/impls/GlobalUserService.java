package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.impls;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.UserAuthDTO;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GlobalUserService {
    EntityManager entityManager;

    /**
     * This return the usertype of the owner of the email if it exists
     * @param email
     * @return empty if not exist
     */
    public Optional<UserAuthDTO> emailExists(String email){

        Query query = entityManager.createNativeQuery("SELECT ST.EMAIL, ST.USER_TYPE, ST.PASSWORD, ST.ID FROM STUDENTS ST WHERE ST.EMAIL = ? OR ST.REG_NO = ? UNION " +
                "SELECT ITF.EMAIL, ITF.USER_TYPE, ITF.PASSWORD, ITF.ID FROM ITF_ADMINS ITF WHERE ITF.EMAIL = ? OR ITF.STAFF_NO = ? UNION " +
                "SELECT C.EMAIL, C.USER_TYPE, C.PASSWORD, C.ID FROM COORDINATORS C WHERE C.EMAIL = ? UNION " +
                "SELECT M.EMAIL, M.USER_TYPE, M.PASSWORD, M.ID FROM MANAGERS M WHERE M.EMAIL = ?");
        query.setParameter(1,email);
        query.setParameter(2,email);
        query.setParameter(3,email);
        query.setParameter(4,email);
        query.setParameter(5,email);
        query.setParameter(6,email);

        Object[] user = null;
        try{
            user = (Object[])query.getSingleResult();
        }catch (Exception ex){
            return Optional.empty();
        }
        return Optional.of(UserAuthDTO.builder()
                .email(String.valueOf(user[0]))
                .userType(String.valueOf(user[1]))
                .hashedPassword(String.valueOf(user[2]))
                .userId(String.valueOf(user[3]))
                .build());
    }


}
