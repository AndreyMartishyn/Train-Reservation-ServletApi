package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.constants.StationServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StationService {
    private final StationDao stationDao;
    private final DataInputValidator dataInputValidator;

    public StationService() {
        stationDao = new StationDaoImpl();
        dataInputValidator = new DataInputValidatorImpl();
    }

    public List<Station> getAllStationsPaginated(int offSet, int entriesPerPage) {
        return stationDao.getAllStationsPaginated(offSet, entriesPerPage).orElse(Collections.emptyList());
    }

    public List<Station> getAllStations() {
        return stationDao.getAllStations().orElse(Collections.emptyList());
    }

    public Optional<Station> getStationByName(String station) {
        return stationDao.getByName(station);
    }

    public boolean deleteStationById(int stationId) {
        return stationDao.delete(stationId);
    }


    public Optional<Station> getStationById(int stationId) {
        return stationDao.getById(stationId);
    }

    public boolean isStationExist(int stationId) {
        return stationDao.getById(stationId).isPresent();
    }

    public boolean createStation(HttpServletRequest request) {
        Station newStation = getStationForCreate(request);
        return stationDao.createStation(newStation);
    }

    public boolean updateStationFromRequest(HttpServletRequest request) {
        Station updatedStation = getStationForUpdate(request);
        return stationDao.update(updatedStation);
    }

    public Station getStationForCreate(HttpServletRequest request) {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        return Station.builder()
                .name(name)
                .code(code)
                .build();
    }

    public Station getStationForUpdate(HttpServletRequest request) {
        int stationId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        return Station.builder()
                .id(stationId)
                .name(name)
                .code(code)
                .build();
    }

    public boolean isStationDataValid(HttpServletRequest request) {
        String name = request.getParameter("name").trim();
        if (!dataInputValidator.isValidStationNameInput(name)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, StationServiceConstants.STATION_NAME_INVALID_MESS);
            return false;
        }
        String code = request.getParameter("code").trim();
        if (!dataInputValidator.isValidStationCodeInput(code)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, StationServiceConstants.STATION_CODE_INVALID_MESS);
            return false;
        }
        return true;
    }


}
