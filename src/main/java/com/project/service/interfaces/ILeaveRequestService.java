package com.project.service.interfaces;

import com.project.entity.LeaveRequest;

/**
 * LeaveRequest Entity için Service Interface
 * İzin işlemleri için business logic interface
 */
public interface ILeaveRequestService extends IService<LeaveRequest> {
    
    // LeaveRequest'e özel metodlar buraya eklenebilir
    // Örneğin: findByPersonelId, checkLeaveBalance, approveLeave, etc.
}

