package com.funeral.wulinacademy.web.converter;

import com.funeral.wulinacademy.web.util.CollectionUtils;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * Create date: 2019/11/22 5:35 PM
 */
public class FolderPathConverter implements AttributeConverter<List<Integer>,String> {


    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return CollectionUtils.isEmpty(attribute)?  null: Strings.join(attribute,',');
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        return StringUtils.isEmpty(dbData)? Collections.emptyList(): Arrays.asList(dbData.split(",")).parallelStream().map( Integer::valueOf).collect(Collectors.toList());
    }
}
