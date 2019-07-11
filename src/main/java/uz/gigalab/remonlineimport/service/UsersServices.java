package uz.gigalab.remonlineimport.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import uz.gigalab.remonlineimport.domain.Users;
import uz.gigalab.remonlineimport.repository.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersServices {

    private final UsersRepository usersRepository;

    public Long getUsersId(String fullName) {
        String sn, sl;
        String[] splited = fullName.split("\\s+");
        if (splited.length == 2) {
            sn = splited[0];
            sl = splited[1];
        } else {
            sn = fullName;
            sl = "";
        }

        final String name = sn;
        final String lastname = sl;

        Users users = usersRepository.findFirstByNameAndLastname(name, lastname)
                .orElseGet(() -> {
                    Users entity = Users.builder()
                            .createDate(LocalDateTime.now())
                            .createUid(1L)
                            .name(name)
                            .lastname(lastname)
                            .login(RandomStringUtils.randomAlphanumeric(40))
                            .passwd(RandomStringUtils.randomAlphanumeric(32))
                            .salt(RandomStringUtils.randomAlphanumeric(12))
                            .isAdmin(false)
                            .authsGroupsId(1L)
                            .servicesId(2L)
                            .statusId(51L)
                            .rostersPostsId(19L)
                            .localesId(1L)
                            .careerStart(LocalDate.now())
                            .note("Перенос базы из RemOnline")
                            .build();

                    usersRepository.save(entity);
                    return entity;
                });

        return users.getId();
    }
}

















