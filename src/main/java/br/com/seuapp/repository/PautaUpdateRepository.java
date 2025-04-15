package br.com.seuapp.repository;

import br.com.seuapp.model.PautaUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PautaUpdateRepository extends JpaRepository<PautaUpdate, Integer> {

    // Alterando de EAN para GTIN
    List<PautaUpdate> findTop10ByGtinIsNullOrderByIdAsc();

}
