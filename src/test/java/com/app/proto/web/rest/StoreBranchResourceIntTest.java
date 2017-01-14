package com.app.proto.web.rest;

import com.app.proto.PrototypeApp;

import com.app.proto.domain.StoreBranch;
import com.app.proto.repository.StoreBranchRepository;
import com.app.proto.service.StoreBranchService;
import com.app.proto.service.dto.StoreBranchDTO;
import com.app.proto.service.mapper.StoreBranchMapper;

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
 * Test class for the StoreBranchResource REST controller.
 *
 * @see StoreBranchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class StoreBranchResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private StoreBranchRepository storeBranchRepository;

    @Inject
    private StoreBranchMapper storeBranchMapper;

    @Inject
    private StoreBranchService storeBranchService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoreBranchMockMvc;

    private StoreBranch storeBranch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreBranchResource storeBranchResource = new StoreBranchResource();
        ReflectionTestUtils.setField(storeBranchResource, "storeBranchService", storeBranchService);
        this.restStoreBranchMockMvc = MockMvcBuilders.standaloneSetup(storeBranchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreBranch createEntity(EntityManager em) {
        StoreBranch storeBranch = new StoreBranch()
                .name(DEFAULT_NAME);
        return storeBranch;
    }

    @Before
    public void initTest() {
        storeBranch = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreBranch() throws Exception {
        int databaseSizeBeforeCreate = storeBranchRepository.findAll().size();

        // Create the StoreBranch
        StoreBranchDTO storeBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(storeBranch);

        restStoreBranchMockMvc.perform(post("/api/store-branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeBranchDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreBranch in the database
        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeCreate + 1);
        StoreBranch testStoreBranch = storeBranchList.get(storeBranchList.size() - 1);
        assertThat(testStoreBranch.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStoreBranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeBranchRepository.findAll().size();

        // Create the StoreBranch with an existing ID
        StoreBranch existingStoreBranch = new StoreBranch();
        existingStoreBranch.setId(1L);
        StoreBranchDTO existingStoreBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(existingStoreBranch);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreBranchMockMvc.perform(post("/api/store-branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStoreBranchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeBranchRepository.findAll().size();
        // set the field null
        storeBranch.setName(null);

        // Create the StoreBranch, which fails.
        StoreBranchDTO storeBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(storeBranch);

        restStoreBranchMockMvc.perform(post("/api/store-branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeBranchDTO)))
            .andExpect(status().isBadRequest());

        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStoreBranches() throws Exception {
        // Initialize the database
        storeBranchRepository.saveAndFlush(storeBranch);

        // Get all the storeBranchList
        restStoreBranchMockMvc.perform(get("/api/store-branches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeBranch.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getStoreBranch() throws Exception {
        // Initialize the database
        storeBranchRepository.saveAndFlush(storeBranch);

        // Get the storeBranch
        restStoreBranchMockMvc.perform(get("/api/store-branches/{id}", storeBranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeBranch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStoreBranch() throws Exception {
        // Get the storeBranch
        restStoreBranchMockMvc.perform(get("/api/store-branches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreBranch() throws Exception {
        // Initialize the database
        storeBranchRepository.saveAndFlush(storeBranch);
        int databaseSizeBeforeUpdate = storeBranchRepository.findAll().size();

        // Update the storeBranch
        StoreBranch updatedStoreBranch = storeBranchRepository.findOne(storeBranch.getId());
        updatedStoreBranch
                .name(UPDATED_NAME);
        StoreBranchDTO storeBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(updatedStoreBranch);

        restStoreBranchMockMvc.perform(put("/api/store-branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeBranchDTO)))
            .andExpect(status().isOk());

        // Validate the StoreBranch in the database
        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeUpdate);
        StoreBranch testStoreBranch = storeBranchList.get(storeBranchList.size() - 1);
        assertThat(testStoreBranch.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreBranch() throws Exception {
        int databaseSizeBeforeUpdate = storeBranchRepository.findAll().size();

        // Create the StoreBranch
        StoreBranchDTO storeBranchDTO = storeBranchMapper.storeBranchToStoreBranchDTO(storeBranch);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStoreBranchMockMvc.perform(put("/api/store-branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeBranchDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreBranch in the database
        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStoreBranch() throws Exception {
        // Initialize the database
        storeBranchRepository.saveAndFlush(storeBranch);
        int databaseSizeBeforeDelete = storeBranchRepository.findAll().size();

        // Get the storeBranch
        restStoreBranchMockMvc.perform(delete("/api/store-branches/{id}", storeBranch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreBranch> storeBranchList = storeBranchRepository.findAll();
        assertThat(storeBranchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
