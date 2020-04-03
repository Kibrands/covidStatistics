package es.rigel.covid.mapper;

import es.rigel.covid.model.Registry;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface CovidRegistryMapper {

    @Select("SELECT * FROM registries")
    List<Registry> getAllRegistries();

    @Select("SELECT * FROM registries WHERE date = #{date} AND countryCode = #{countryCode}")
    Registry getRegistry(@Param("date") Date date, @Param("countryCode") String countryCode);

    @Insert("INSERT INTO registries VALUES (#{reg.date}, #{reg.countryCode}, #{reg.countryName}, " +
            "#{reg.newCases}, #{reg.newDeaths}, #{reg.newRecovered})")
    void saveRegistry(@Param("reg") Registry reg);

    @Update("UPDATE registries SET countryName = #{reg.countryName}, newCases = #{reg.newCases}, " +
            "newDeaths = #{reg.newDeaths}, newRecovered = #{reg.newRecovered} WHERE date = " +
            "#{reg.date} AND countryCode = #{reg.countryCode}")
    boolean updateRegistry(@Param("reg") Registry reg);

    @Delete("DELETE FROM registries WHERE date = #{date} AND countryCode = #{countryCode}")
    boolean deleteRegistry(@Param("date") Date date, @Param("countryCode") String countryCode);
}
