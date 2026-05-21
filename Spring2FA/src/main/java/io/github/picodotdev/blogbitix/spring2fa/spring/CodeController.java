package io.github.picodotdev.blogbitix.spring2fa.spring;

import java.util.ArrayList;
import java.util.List;

import io.github.picodotdev.blogbitix.spring2fa.account.AccountRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String showCodeForm() {
        return "code";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String verifyCode(String code) {
        try {
            UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Totp totp = new Totp(userDetailsAdapter.getAccount().getSecret());
            if (!isValidLong(code) || !totp.verify(code)) {
                throw new VerificationCodeException("invalid key");
            }

            Utils.setAuthentication();
            return "redirect:/home";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (VerificationCodeException e) {
            e.printStackTrace();
        }
        return "redirect:/code?error";
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
