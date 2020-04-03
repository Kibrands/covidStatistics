package es.rigel.covid.service.impl;

import es.rigel.covid.exception.CovidRuntimeException;
import es.rigel.covid.mapper.CovidRegistryMapper;
import es.rigel.covid.model.Registry;
import es.rigel.covid.service.CovidService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service("covidServiceImpl")
@Getter
@Setter
public class CovidServiceImpl implements CovidService {

    private final CovidRegistryMapper covidRegistryMapper;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public CovidServiceImpl(CovidRegistryMapper covidRegistryMapper) {
        this.covidRegistryMapper = covidRegistryMapper;
    }

    @Override
    public List<Registry> getAllRegistries() {
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        List<Registry> registries;
        try {
            registries = new ArrayList<>(this.covidRegistryMapper.getAllRegistries());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        transactionManager.commit(txStatus);
        return registries;
    }

    @Override
    public Registry getRegistry(Date date, String countryCode) {
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        Registry registry;
        try {
            registry = this.covidRegistryMapper.getRegistry(new java.sql.Date(date.getTime()), countryCode);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return registry;
    }

    @Override
    public void saveRegistry(Registry reg) {
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            this.covidRegistryMapper.saveRegistry(reg);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
    }

    @Override
    public boolean updateRegistry(Registry reg) {
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        boolean bool;
        try {
            bool = this.covidRegistryMapper.updateRegistry(reg);
            if (!bool) throw new CovidRuntimeException("err.registry.not.updated");
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return bool;
    }

    @Override
    public boolean deleteRegistry(Date date, String countryCode) {
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        boolean bool;
        try {
            bool = this.covidRegistryMapper.deleteRegistry(new java.sql.Date(date.getTime()), countryCode);
            if (!bool) throw new CovidRuntimeException("err.registry.not.deleted");
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw e;
        }
        return bool;
    }
}
