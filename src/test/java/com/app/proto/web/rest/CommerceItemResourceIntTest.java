package com.app.proto.web.rest;

import com.app.proto.PrototypeApp;

import com.app.proto.domain.CommerceItem;
import com.app.proto.repository.CommerceItemRepository;
import com.app.proto.service.CommerceItemService;
import com.app.proto.service.dto.CommerceItemDTO;
import com.app.proto.service.mapper.CommerceItemMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommerceItemResource REST controller.
 *
 * @see CommerceItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class CommerceItemResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Inject
    private CommerceItemRepository commerceItemRepository;

    @Inject
    private CommerceItemMapper commerceItemMapper;

    @Inject
    private CommerceItemService commerceItemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCommerceItemMockMvc;

    private CommerceItem commerceItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommerceItemResource commerceItemResource = new CommerceItemResource();
        ReflectionTestUtils.setField(commerceItemResource, "commerceItemService", commerceItemService);
        this.restCommerceItemMockMvc = MockMvcBuilders.standaloneSetup(commerceItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommerceItem createEntity(EntityManager em) {
        CommerceItem commerceItem = new CommerceItem()
                .quantity(DEFAULT_QUANTITY);
        return commerceItem;
    }

    @Before
    public void initTest() {
        commerceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommerceItem() throws Exception {
        int databaseSizeBeforeCreate = commerceItemRepository.findAll().size();

        // Create the CommerceItem
        CommerceItemDTO commerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(commerceItem);

        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeCreate + 1);
        CommerceItem testCommerceItem = commerceItemList.get(commerceItemList.size() - 1);
        assertThat(testCommerceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createCommerceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commerceItemRepository.findAll().size();

        // Create the CommerceItem with an existing ID
        CommerceItem existingCommerceItem = new CommerceItem();
        existingCommerceItem.setId(1L);
        CommerceItemDTO existingCommerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(existingCommerceItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCommerceItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = commerceItemRepository.findAll().size();
        // set the field null
        commerceItem.setQuantity(null);

        // Create the CommerceItem, which fails.
        CommerceItemDTO commerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(commerceItem);

        restCommerceItemMockMvc.perform(post("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isBadRequest());

        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommerceItems() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        // Get all the commerceItemList
        restCommerceItemMockMvc.perform(get("/api/commerce-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commerceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);

        // Get the commerceItem
        restCommerceItemMockMvc.perform(get("/api/commerce-items/{id}", commerceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commerceItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingCommerceItem() throws Exception {
        // Get the commerceItem
        restCommerceItemMockMvc.perform(get("/api/commerce-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);
        int databaseSizeBeforeUpdate = commerceItemRepository.findAll().size();

        // Update the commerceItem
        CommerceItem updatedCommerceItem = commerceItemRepository.findOne(commerceItem.getId());
        updatedCommerceItem
                .quantity(UPDATED_QUANTITY);
        CommerceItemDTO commerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(updatedCommerceItem);

        restCommerceItemMockMvc.perform(put("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isOk());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeUpdate);
        CommerceItem testCommerceItem = commerceItemList.get(commerceItemList.size() - 1);
        assertThat(testCommerceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCommerceItem() throws Exception {
        int databaseSizeBeforeUpdate = commerceItemRepository.findAll().size();

        // Create the CommerceItem
        CommerceItemDTO commerceItemDTO = commerceItemMapper.commerceItemToCommerceItemDTO(commerceItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommerceItemMockMvc.perform(put("/api/commerce-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commerceItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CommerceItem in the database
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommerceItem() throws Exception {
        // Initialize the database
        commerceItemRepository.saveAndFlush(commerceItem);
        int databaseSizeBeforeDelete = commerceItemRepository.findAll().size();

        // Get the commerceItem
        restCommerceItemMockMvc.perform(delete("/api/commerce-items/{id}", commerceItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommerceItem> commerceItemList = commerceItemRepository.findAll();
        assertThat(commerceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
