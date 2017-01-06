package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.StoreDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Store and its DTO StoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper {

    StoreDTO storeToStoreDTO(Store store);

    List<StoreDTO> storesToStoreDTOs(List<Store> stores);

    @Mapping(target = "branches", ignore = true)
    @Mapping(target = "users", ignore = true)
    Store storeDTOToStore(StoreDTO storeDTO);

    List<Store> storeDTOsToStores(List<StoreDTO> storeDTOs);
}
