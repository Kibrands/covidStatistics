package es.rigel.covid.service.impl;

import static org.mockito.Mockito.*;

import es.rigel.covid.exception.CovidRuntimeException;
import es.rigel.covid.mapper.CovidRegistryMapper;
import es.rigel.covid.model.Registry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

public class CovidServiceImplTest {

    private static CovidServiceImpl covidServiceImpl;
    private static final CovidRegistryMapper covidRegistryMapper = mock(CovidRegistryMapper.class);
    private final Registry registry = mock(Registry.class);
    private static final Date DATE = new Date();
    private static final String COUNTRY_CODE = "es";
    private static final PlatformTransactionManager transactionManager = mock(PlatformTransactionManager.class);

    @BeforeClass
    public static void setUp() throws Exception {
        covidServiceImpl = new CovidServiceImpl(covidRegistryMapper);
        covidServiceImpl.setTransactionManager(transactionManager);
    }

    @Test
    public void getAllRegistries() {
        covidServiceImpl.getAllRegistries();
        verify(covidRegistryMapper, times(1)).getAllRegistries();
    }

    @Test
    public void getRegistry() {
        covidServiceImpl.getRegistry(DATE, COUNTRY_CODE);
        verify(covidRegistryMapper, times(1))
                .getRegistry(new java.sql.Date(DATE.getTime()), COUNTRY_CODE);
    }

    @Test
    public void saveRegistry() {
        covidServiceImpl.saveRegistry(registry);
        verify(covidRegistryMapper, times(1))
                .saveRegistry(registry);
    }

    @Test
    public void updateRegistry() {
        when(covidRegistryMapper.updateRegistry(registry)).thenReturn(true);
        covidServiceImpl.updateRegistry(registry);
        verify(covidRegistryMapper, times(1))
                .updateRegistry(registry);
    }

    @Test(expected = CovidRuntimeException.class)
    public void shouldExpcetionWhenUpdateRegistry() {
        when(covidRegistryMapper.updateRegistry(registry)).thenReturn(false);
        covidServiceImpl.updateRegistry(registry);
        verify(covidRegistryMapper, times(1))
                .updateRegistry(registry);
    }

    @Test
    public void deleteRegistry() {
        when(covidRegistryMapper.deleteRegistry(new java.sql.Date(DATE.getTime()), COUNTRY_CODE)).thenReturn(true);
        covidServiceImpl.deleteRegistry(DATE, COUNTRY_CODE);
        verify(covidRegistryMapper, times(2))
                .deleteRegistry(new java.sql.Date(DATE.getTime()), COUNTRY_CODE);
    }

    @Test(expected = CovidRuntimeException.class)
    public void shouldExceptionWhenDeleteRegistry() {
        when(covidRegistryMapper.deleteRegistry(new java.sql.Date(DATE.getTime()), COUNTRY_CODE)).thenReturn(false);
        covidServiceImpl.deleteRegistry(DATE, COUNTRY_CODE);
        verify(covidRegistryMapper, times(1))
                .deleteRegistry(new java.sql.Date(DATE.getTime()), COUNTRY_CODE);
    }
}