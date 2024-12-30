package ba.edu.ibu.inventorymanagementwebapp.rest.controller;

import ba.edu.ibu.inventorymanagementwebapp.core.model.User;
import ba.edu.ibu.inventorymanagementwebapp.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> userExists(@PathVariable String email) {
        return ResponseEntity.ok(userService.userExists(email));
    }
}
