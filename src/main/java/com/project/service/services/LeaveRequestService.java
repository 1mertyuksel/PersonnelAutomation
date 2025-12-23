package com.project.service.services;

import com.project.entity.LeaveRequest;
import com.project.repository.IRepository;
import com.project.service.interfaces.ILeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LeaveRequestService extends com.project.service.services.Service<LeaveRequest> implements ILeaveRequestService {
    
    @Autowired
    public LeaveRequestService(IRepository<LeaveRequest> repository) {
        super(repository);
    }
    
   
}

