package com.app.proto.service;

import com.app.proto.service.dto.ProductDTO;
import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     *  Get all the products.
     *
     *  @return the list of entities
     */
    List<ProductDTO> findAll();

    /**
     *  Get the "id" product.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductDTO findOne(Long id);

    /**
     *  Get the "barcodeId" product.
     *
     *  @param barcodeId the barcodeId of the entity
     *  @return the entity
     */
    ProductDTO findOneByBarcodeId(String barcodeId);

    /**
     *  Delete the "id" product.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
