package pl.wiktor.learning.api.dto.request;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import pl.wiktor.learning.infrastructure.security.SecurityContextUtils;

import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {


//    @GetMapping(path = "/roles/admin")
////    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ADMINISTRATOR')")
//    public ResponseEntity<Set<String>> getAuthorizedUserRoles() {
//        System.out.println(SecurityContextUtils.getUserName());
//        return ResponseEntity.ok(SecurityContextUtils.getUserRoles());
//    }
//
//    @GetMapping(path = "/roles/regular")
//    @PreAuthorize("hasAnyAuthority('ROLE_REGULAR')")
//    public ResponseEntity<Set<String>> getAuthorizedUserRolesRegular() {
//        return ResponseEntity.ok(SecurityContextUtils.getUserRoles());
//    }
}
