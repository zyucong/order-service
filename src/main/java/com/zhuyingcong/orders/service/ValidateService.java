package com.zhuyingcong.orders.service;

import com.zhuyingcong.orders.entity.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ValidateService {

    private final int TWO = 2;
    private final double MIN_LATITUDE = -90;
    private final double MAX_LATITUDE = 90;
    private final double MIN_LONGITUDE = -180;
    private final double MAX_LONGITUDE = 180;

    public void validateCoordinates(List<String> coordinates) throws InvalidDataException {
        if (coordinates == null) {
            throw new InvalidDataException();
        }
        if (coordinates.size() != TWO) {
            throw new InvalidDataException();
        }
        if (coordinates.stream()
                .filter(Objects::nonNull)
                .filter(ValidateService::isNumeric)
                .count() != TWO) {
            throw new InvalidDataException();
        }
        validateLatitude(coordinates.get(0));
        validateLongitude(coordinates.get(1));
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private void validateLatitude(String latitude) throws InvalidDataException {
        double lat = Double.parseDouble(latitude);
        if (lat < MIN_LATITUDE || lat > MAX_LATITUDE) {
            throw new InvalidDataException();
        }
    }

    private void validateLongitude(String longitude) throws InvalidDataException {
        double lon = Double.parseDouble(longitude);
        if (lon < MIN_LONGITUDE || lon > MAX_LONGITUDE) {
            throw new InvalidDataException();
        }
    }
}
