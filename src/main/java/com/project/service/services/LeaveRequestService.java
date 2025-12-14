package com.project.service.services;

import com.project.entity.LeaveRequest;
import com.project.repository.IRepository;
import com.project.service.interfaces.ILeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LeaveRequest Entity için Service Implementation
 * İzin işlemleri için business logic
 */
@Service
public class LeaveRequestService extends com.project.service.services.Service<LeaveRequest> implements ILeaveRequestService {
    
    @Autowired
    public LeaveRequestService(IRepository<LeaveRequest> repository) {
        super(repository);
    }
    
    // LeaveRequest'e özel metodlar buraya eklenebilir
    // Örneğin: findByPersonelId, checkLeaveBalance, approveLeave, etc.
}

