package ddd.usermanagement.xport.rest;


import ddd.usermanagement.domain.model.User;
import ddd.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserResource {

    @Autowired
    private UserService userService;

    public static String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping("/{username}")
    ResponseEntity<User> details(@PathVariable String username) {
        try {
            User user = this.userService.loadUserByUsername(username);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}