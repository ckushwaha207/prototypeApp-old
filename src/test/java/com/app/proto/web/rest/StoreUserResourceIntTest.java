package com.app.proto.web.rest;

import com.app.proto.PrototypeApp;

import com.app.proto.domain.StoreUser;
import com.app.proto.repository.StoreUserRepository;
import com.app.proto.service.StoreUserService;
import com.app.proto.service.dto.StoreUserDTO;
import com.app.proto.service.mapper.StoreUserMapper;

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
 * Test class for the StoreUserResource REST controller.
 *
 * @see StoreUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class StoreUserResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private StoreUserMapper storeUserMapper;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStoreUserMockMvc;

    private StoreUser storeUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StoreUserResource storeUserResource = new StoreUserResource();
        ReflectionTestUtils.setField(storeUserResource, "storeUserService", storeUserService);
        this.restStoreUserMockMvc = MockMvcBuilders.standaloneSetup(storeUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreUser createEntity(EntityManager em) {
        StoreUser storeUser = new StoreUser()
                .login(DEFAULT_LOGIN)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .activated(DEFAULT_ACTIVATED)
                .password(DEFAULT_PASSWORD);
        return storeUser;
    }

    @Before
    public void initTest() {
        storeUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreUser() throws Exception {
        int databaseSizeBeforeCreate = storeUserRepository.findAll().size();

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);

        restStoreUserMockMvc.perform(post("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeCreate + 1);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
        assertThat(testStoreUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testStoreUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStoreUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStoreUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStoreUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testStoreUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createStoreUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeUserRepository.findAll().size();

        // Create the StoreUser with an existing ID
        StoreUser existingStoreUser = new StoreUser();
        existingStoreUser.setId(1L);
        StoreUserDTO existingStoreUserDTO = storeUserMapper.storeUserToStoreUserDTO(existingStoreUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreUserMockMvc.perform(post("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingStoreUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeUserRepository.findAll().size();
        // set the field null
        storeUser.setLogin(null);

        // Create the StoreUser, which fails.
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);

        restStoreUserMockMvc.perform(post("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isBadRequest());

        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeUserRepository.findAll().size();
        // set the field null
        storeUser.setEmail(null);

        // Create the StoreUser, which fails.
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);

        restStoreUserMockMvc.perform(post("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isBadRequest());

        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeUserRepository.findAll().size();
        // set the field null
        storeUser.setActivated(null);

        // Create the StoreUser, which fails.
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);

        restStoreUserMockMvc.perform(post("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isBadRequest());

        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStoreUsers() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        // Get all the storeUserList
        restStoreUserMockMvc.perform(get("/api/store-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);

        // Get the storeUser
        restStoreUserMockMvc.perform(get("/api/store-users/{id}", storeUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeUser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStoreUser() throws Exception {
        // Get the storeUser
        restStoreUserMockMvc.perform(get("/api/store-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();

        // Update the storeUser
        StoreUser updatedStoreUser = storeUserRepository.findOne(storeUser.getId());
        updatedStoreUser
                .login(UPDATED_LOGIN)
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .email(UPDATED_EMAIL)
                .activated(UPDATED_ACTIVATED)
                .password(UPDATED_PASSWORD);
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(updatedStoreUser);

        restStoreUserMockMvc.perform(put("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isOk());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate);
        StoreUser testStoreUser = storeUserList.get(storeUserList.size() - 1);
        assertThat(testStoreUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testStoreUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStoreUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStoreUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStoreUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testStoreUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreUser() throws Exception {
        int databaseSizeBeforeUpdate = storeUserRepository.findAll().size();

        // Create the StoreUser
        StoreUserDTO storeUserDTO = storeUserMapper.storeUserToStoreUserDTO(storeUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStoreUserMockMvc.perform(put("/api/store-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeUserDTO)))
            .andExpect(status().isCreated());

        // Validate the StoreUser in the database
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStoreUser() throws Exception {
        // Initialize the database
        storeUserRepository.saveAndFlush(storeUser);
        int databaseSizeBeforeDelete = storeUserRepository.findAll().size();

        // Get the storeUser
        restStoreUserMockMvc.perform(delete("/api/store-users/{id}", storeUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreUser> storeUserList = storeUserRepository.findAll();
        assertThat(storeUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
