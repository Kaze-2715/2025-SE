package com.agri.platform.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.agri.platform.DTO.UserUpdateDTO;
import com.agri.platform.service.user.UserUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/update")
public class UserUpdateController {
    private final UserUpdateService updateService;

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDTO updateDTO) {
        updateService.updateUser(updateDTO);
        return ResponseEntity.noContent().build();
    }
}
