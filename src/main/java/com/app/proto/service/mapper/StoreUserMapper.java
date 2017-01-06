package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.StoreUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity StoreUser and its DTO StoreUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreUserMapper {

    @Mapping(source = "store.id", target = "storeId")
    StoreUserDTO storeUserToStoreUserDTO(StoreUser storeUser);

    List<StoreUserDTO> storeUsersToStoreUserDTOs(List<StoreUser> storeUsers);

    @Mapping(source = "storeId", target = "store")
    StoreUser storeUserDTOToStoreUser(StoreUserDTO storeUserDTO);

    List<StoreUser> storeUserDTOsToStoreUsers(List<StoreUserDTO> storeUserDTOs);

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
