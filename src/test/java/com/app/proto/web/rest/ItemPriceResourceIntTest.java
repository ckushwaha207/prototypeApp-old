package com.app.proto.web.rest;

import com.app.proto.PrototypeApp;

import com.app.proto.domain.ItemPrice;
import com.app.proto.repository.ItemPriceRepository;
import com.app.proto.service.ItemPriceService;
import com.app.proto.service.dto.ItemPriceDTO;
import com.app.proto.service.mapper.ItemPriceMapper;

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
 * Test class for the ItemPriceResource REST controller.
 *
 * @see ItemPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class ItemPriceResourceIntTest {

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_LIST_PRICE = 1D;
    private static final Double UPDATED_LIST_PRICE = 2D;

    private static final Double DEFAULT_SALE_PRICE = 1D;
    private static final Double UPDATED_SALE_PRICE = 2D;

    @Inject
    private ItemPriceRepository itemPriceRepository;

    @Inject
    private ItemPriceMapper itemPriceMapper;

    @Inject
    private ItemPriceService itemPriceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restItemPriceMockMvc;

    private ItemPrice itemPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemPriceResource itemPriceResource = new ItemPriceResource();
        ReflectionTestUtils.setField(itemPriceResource, "itemPriceService", itemPriceService);
        this.restItemPriceMockMvc = MockMvcBuilders.standaloneSetup(itemPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPrice createEntity(EntityManager em) {
        ItemPrice itemPrice = new ItemPrice()
                .productId(DEFAULT_PRODUCT_ID)
                .listPrice(DEFAULT_LIST_PRICE)
                .salePrice(DEFAULT_SALE_PRICE);
        return itemPrice;
    }

    @Before
    public void initTest() {
        itemPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPrice() throws Exception {
        int databaseSizeBeforeCreate = itemPriceRepository.findAll().size();

        // Create the ItemPrice
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);

        restItemPriceMockMvc.perform(post("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrice in the database
        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPrice testItemPrice = itemPriceList.get(itemPriceList.size() - 1);
        assertThat(testItemPrice.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testItemPrice.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testItemPrice.getSalePrice()).isEqualTo(DEFAULT_SALE_PRICE);
    }

    @Test
    @Transactional
    public void createItemPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPriceRepository.findAll().size();

        // Create the ItemPrice with an existing ID
        ItemPrice existingItemPrice = new ItemPrice();
        existingItemPrice.setId(1L);
        ItemPriceDTO existingItemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(existingItemPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPriceMockMvc.perform(post("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingItemPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPriceRepository.findAll().size();
        // set the field null
        itemPrice.setProductId(null);

        // Create the ItemPrice, which fails.
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);

        restItemPriceMockMvc.perform(post("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkListPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPriceRepository.findAll().size();
        // set the field null
        itemPrice.setListPrice(null);

        // Create the ItemPrice, which fails.
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);

        restItemPriceMockMvc.perform(post("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalePriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPriceRepository.findAll().size();
        // set the field null
        itemPrice.setSalePrice(null);

        // Create the ItemPrice, which fails.
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);

        restItemPriceMockMvc.perform(post("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemPrices() throws Exception {
        // Initialize the database
        itemPriceRepository.saveAndFlush(itemPrice);

        // Get all the itemPriceList
        restItemPriceMockMvc.perform(get("/api/item-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].salePrice").value(hasItem(DEFAULT_SALE_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getItemPrice() throws Exception {
        // Initialize the database
        itemPriceRepository.saveAndFlush(itemPrice);

        // Get the itemPrice
        restItemPriceMockMvc.perform(get("/api/item-prices/{id}", itemPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPrice.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.salePrice").value(DEFAULT_SALE_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemPrice() throws Exception {
        // Get the itemPrice
        restItemPriceMockMvc.perform(get("/api/item-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPrice() throws Exception {
        // Initialize the database
        itemPriceRepository.saveAndFlush(itemPrice);
        int databaseSizeBeforeUpdate = itemPriceRepository.findAll().size();

        // Update the itemPrice
        ItemPrice updatedItemPrice = itemPriceRepository.findOne(itemPrice.getId());
        updatedItemPrice
                .productId(UPDATED_PRODUCT_ID)
                .listPrice(UPDATED_LIST_PRICE)
                .salePrice(UPDATED_SALE_PRICE);
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(updatedItemPrice);

        restItemPriceMockMvc.perform(put("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ItemPrice in the database
        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeUpdate);
        ItemPrice testItemPrice = itemPriceList.get(itemPriceList.size() - 1);
        assertThat(testItemPrice.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testItemPrice.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testItemPrice.getSalePrice()).isEqualTo(UPDATED_SALE_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPrice() throws Exception {
        int databaseSizeBeforeUpdate = itemPriceRepository.findAll().size();

        // Create the ItemPrice
        ItemPriceDTO itemPriceDTO = itemPriceMapper.itemPriceToItemPriceDTO(itemPrice);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemPriceMockMvc.perform(put("/api/item-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemPrice in the database
        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteItemPrice() throws Exception {
        // Initialize the database
        itemPriceRepository.saveAndFlush(itemPrice);
        int databaseSizeBeforeDelete = itemPriceRepository.findAll().size();

        // Get the itemPrice
        restItemPriceMockMvc.perform(delete("/api/item-prices/{id}", itemPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPrice> itemPriceList = itemPriceRepository.findAll();
        assertThat(itemPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
