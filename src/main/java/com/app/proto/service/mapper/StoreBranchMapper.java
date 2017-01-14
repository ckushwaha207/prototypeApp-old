package com.app.proto.service.mapper;

import com.app.proto.domain.*;
import com.app.proto.service.dto.StoreBranchDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity StoreBranch and its DTO StoreBranchDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StoreBranchMapper {

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "store.id", target = "storeId")
    StoreBranchDTO storeBranchToStoreBranchDTO(StoreBranch storeBranch);

    List<StoreBranchDTO> storeBranchesToStoreBranchDTOs(List<StoreBranch> storeBranches);

    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "storeId", target = "store")
    StoreBranch storeBranchDTOToStoreBranch(StoreBranchDTO storeBranchDTO);

    List<StoreBranch> storeBranchDTOsToStoreBranches(List<StoreBranchDTO> storeBranchDTOs);

    default Location locationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
