package com.project.controller.controllers;

import com.project.entity.LeaveRequest;
import com.project.service.interfaces.ILeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * LeaveRequest Entity için Controller
 * İzin işlemleri için REST API endpoint'leri
 */
@RestController
@RequestMapping("/api/leave")
public class LeaveRequestController {
    
    @Autowired
    private ILeaveRequestService leaveRequestService;
    
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAll() {
        List<LeaveRequest> requests = leaveRequestService.findAll();
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<LeaveRequest>> getById(@PathVariable Long id) {
        Optional<LeaveRequest> request = leaveRequestService.getByIdAsync(id).join();
        if (request.isPresent()) {
            return ResponseEntity.ok(request);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<LeaveRequest> create(@RequestBody LeaveRequest leaveRequest) {
        LeaveRequest created = leaveRequestService.addAsync(leaveRequest).join();
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> update(@PathVariable Long id, @RequestBody LeaveRequest leaveRequest) {
        leaveRequest.setId(id);
        LeaveRequest updated = leaveRequestService.updateAsync(leaveRequest).join();
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leaveRequestService.removeAsync(id).join();
        return ResponseEntity.noContent().build();
    }
}

