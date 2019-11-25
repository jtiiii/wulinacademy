package com.funeral.wulinacademy.web.converter;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;

import javax.persistence.AttributeConverter;

/**
 * @author FuneralObjects
 * Create date: 2019/11/22 5:27 PM
 */
public class StandardStatusConverter implements AttributeConverter<StandardStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StandardStatus attribute) {
        return attribute == null? null: attribute.getNum();
    }

    @Override
    public StandardStatus convertToEntityAttribute(Integer dbData) {
        return StandardStatus.of(dbData);
    }
}
