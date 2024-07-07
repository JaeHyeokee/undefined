package com.lec.spring.domain;

import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONENUM_PATTERN = Pattern.compile(
            "^\\d{3}-\\d{4}-\\d{4}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$");

    private static final Pattern NICKNAME_PATTERN = Pattern.compile(
            "^[A-Za-z0-9가-힣_]{1,12}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String nickname = user.getNickname();
        if (nickname == null || nickname.trim().isEmpty()) {
            errors.rejectValue("nickname", "* 닉네임은 필수입니다.");
        } else if (!NICKNAME_PATTERN.matcher(nickname).matches()){
            errors.rejectValue("nickname", "* 닉네임은 1자리 이상 12자리 이하의 영문자, 한글, 숫자, 밑줄(_)만 사용할 수 있습니다.");
        } else if (userService.isExistNickname(nickname)) {
            errors.rejectValue("nickname", "* 이미 존재하는 닉네임입니다.");
        }

        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.rejectValue("email", "* 이메일은 필수입니다.");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.rejectValue("email", "* 올바른 이메일 형식을 입력해 주세요.");
        } else if (userService.emailExists(email)) {
            errors.rejectValue("email", "* 이미 가입된 이메일입니다.");
        }

        String phonenum = user.getPhonenum();
        if (phonenum != null && !phonenum.trim().isEmpty()) {
            if (userService.isExistPhonenum(phonenum)) {
                errors.rejectValue("phonenum", "* 이미 가입된 전화번호입니다.");
            } else if (!PHONENUM_PATTERN.matcher(phonenum).matches()) {
                errors.rejectValue("phonenum", "* 올바른 형식(예: 010-1234-5678)의 전화번호를 입력해주세요.");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "* 이름은 필수입니다.");

        String password = user.getPassword();
        if (password == null || password.trim().isEmpty()){
            errors.rejectValue("password", "* 비밀번호를 입력해 주세요.");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errors.rejectValue("password", "* 비밀번호는 최소 하나 이상의 영문 대소문자, 숫자, 특수문자를 혼합하여 8~20자로 입력하여 주세요.");
        }

        if (!user.getPassword().equals(user.getRe_password())) {
            errors.rejectValue("re_password", "* 비밀번호가 일치하지 않습니다.");
        }


    }
}
