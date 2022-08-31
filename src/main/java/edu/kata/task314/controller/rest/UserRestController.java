package edu.kata.task314.controller.rest;

import edu.kata.task314.dto.RoleDto;
import edu.kata.task314.dto.UserDto;
import edu.kata.task314.entity.User;
import edu.kata.task314.facade.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserFacade userFacade;

    //------------------------------------------------------------------------------------------------------------------

    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(userFacade.findOneUser(user.getLogin()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/one")
    public ResponseEntity<UserDto> findOneUser(@RequestParam("id") Long userId) {
        return new ResponseEntity<>(userFacade.findOneUser(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return new ResponseEntity<>(userFacade.findAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userFacade.saveUser(userDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> removeUser(@RequestParam("id") Long userId) {
        userFacade.removeUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/roles")
    public ResponseEntity<Set<RoleDto>> findAllRoles(@RequestParam(value = "userId", required = false) Long userId) {
        return new ResponseEntity<>(userId == null ? userFacade.findAllRoles() : userFacade.findAllRoles(userId), HttpStatus.OK);
    }
}
