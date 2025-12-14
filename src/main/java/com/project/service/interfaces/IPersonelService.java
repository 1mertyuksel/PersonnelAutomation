package com.project.service.interfaces;

import com.project.entity.Personel;

/**
 * Personel Entity için Service Interface
 * Personel işlemleri için business logic interface
 */
public interface IPersonelService extends IService<Personel> {
    
    // Personel'e özel metodlar buraya eklenebilir
    // Örneğin: findByDepartment, calculateSeniority, etc.
}

