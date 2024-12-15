package com.candido.server.service.base.geo;

import com.candido.server.domain.v1.geo.*;
import com.candido.server.dto.v1.response.geo.*;
import com.candido.server.service.base.mapper.TerritoryMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GeoServiceImpl implements GeoService {

    private final TerritoryService territoryService;
    private final XrefTerritorySubdivisionService xrefTerritorySubdivisionService;

    @Autowired
    GeoServiceImpl(
            TerritoryService territoryService,
            XrefTerritorySubdivisionService xrefTerritorySubdivisionService
    ) {
        this.territoryService = territoryService;
        this.xrefTerritorySubdivisionService = xrefTerritorySubdivisionService;
    }

    @Override
    public List<GeoTerritoryDto> getTerritories(String territoryKey) {
        return territoryService.findAllByCategoryId(territoryKey)
                .stream()
                .map(TerritoryMapperService::mapTerritoryToGeoTerritoryDto)
                .toList();
    }

    @Override
    public GeoTerritoryListDto getTerritoryChildren(int territoryId) {
        Optional<Territory> territoryOptional = territoryService.findById(territoryId);
        if(territoryOptional.isEmpty()) return GeoTerritoryListDto.builder().build();

        List<Integer> xrefTerritorySubdivisionList = xrefTerritorySubdivisionService
                .findByTerritoryMotherId(territoryId)
                .stream()
                .map(XrefTerritorySubdivision::getTerritorySonId)
                .toList();

        List<GeoTerritoryDto> territoryList = territoryService.findAllTerritoriesBySonListId(xrefTerritorySubdivisionList)
                .stream()
                .map(TerritoryMapperService::mapTerritoryToGeoTerritoryDto)
                .toList();

        AtomicReference<TerritoryCategory> territoryCategory = new AtomicReference<>();
        if(!territoryList.isEmpty()) {
            territoryService.findById(territoryList.getFirst().getTerritoryId())
                    .ifPresent(territory -> territoryCategory.set(territory.getTerritoryCategory()));
        }

        return GeoTerritoryListDto
                .builder()
                .labelId(territoryCategory.get().getTerritoryCategoryId())
                .labelName(territoryCategory.get().getTerritoryCategoryKey())
                .territoryList(territoryList)
                .build();
    }

}
