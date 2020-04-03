package es.rigel.covid.service;

import es.rigel.covid.model.Registry;

import java.util.Date;
import java.util.List;

public interface CovidService {

    List<Registry> getAllRegistries();

    Registry getRegistry(Date date, String countryCode);

    void saveRegistry(Registry reg);

    boolean updateRegistry(Registry reg);

    boolean deleteRegistry(Date date, String countryCode);
}
