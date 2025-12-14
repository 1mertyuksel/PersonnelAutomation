package com.project.service.interfaces;

import com.project.entity.InventoryItem;

/**
 * InventoryItem Entity için Service Interface
 * Zimmet (Envanter) işlemleri için business logic interface
 */
public interface IInventoryItemService extends IService<InventoryItem> {
    
    // InventoryItem'a özel metodlar buraya eklenebilir
    // Örneğin: findByPersonelId, assignToPersonel, etc.
}

