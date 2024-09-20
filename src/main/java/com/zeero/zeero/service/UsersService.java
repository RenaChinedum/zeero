package com.zeero.zeero.service;


import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.exceptions.ErrorStatus;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService extends BaseService {
    private final UsersRepository usersRepository;

    public Users updatePatronDetail(UserDetailRequest request) {
        Users users = loggedInUser();
        updateFieldIfNotNullOrEmpty(request.getFirstName(), users::setFirstName);
        updateFieldIfNotNullOrEmpty(request.getLastName(), users::setLastName);
        updateFieldIfNotNullOrEmpty(request.getEmail(), users::setEmail);
        return usersRepository.save(users);
    }


    @Cacheable(value = "userCache")
    public Page<Users> getAllPatron(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @Cacheable(value = "userCache", key = "#id")
    public Users getAPatron(Long id) {
        return usersRepository.findById(id).orElseThrow(()
                -> new TodoAppException(ErrorStatus.USER_NOT_FOUND_ERROR, "Patron not found"));
    }


}
