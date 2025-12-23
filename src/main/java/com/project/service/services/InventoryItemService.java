package com.project.service.services;

import com.project.entity.InventoryItem;
import com.project.repository.IRepository;
import com.project.service.interfaces.IInventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryItemService extends com.project.service.services.Service<InventoryItem> implements IInventoryItemService {
    
    @Autowired
    public InventoryItemService(IRepository<InventoryItem> repository) {
        super(repository);
    }
    
    
}

