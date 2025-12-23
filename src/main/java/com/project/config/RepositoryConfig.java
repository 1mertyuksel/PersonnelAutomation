package com.project.config;

import com.project.entity.*;
import com.project.repository.IRepository;
import com.project.repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Repository Configuration
 * Her entity için IRepository<Entity> bean'lerini oluşturur
 * Service'ler IRepository<User>, IRepository<Personel> gibi generic şekilde inject edebilir
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public IRepository<Personel> personelRepository() {
        return new Repository<>(Personel.class);
    }

    @Bean
    public IRepository<User> userRepository() {
        return new Repository<>(User.class);
    }

    @Bean
    public IRepository<InventoryItem> inventoryItemRepository() {
        return new Repository<>(InventoryItem.class);
    }

    @Bean
    public IRepository<LeaveRequest> leaveRequestRepository() {
        return new Repository<>(LeaveRequest.class);
    }

    @Bean
    public IRepository<Payroll> payrollRepository() {
        return new Repository<>(Payroll.class);
    }

    @Bean
    public IRepository<Department> departmentRepository() {
        return new Repository<>(Department.class);
    }
}


