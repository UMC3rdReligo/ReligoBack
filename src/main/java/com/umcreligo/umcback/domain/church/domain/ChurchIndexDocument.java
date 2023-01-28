package com.umcreligo.umcback.domain.church.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Document(indexName = "religo_church")
@Setting(settingPath = "elastic/religo_church_settings.json")
@Mapping(mappingPath = "elastic/religo_church_mappings.json")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class ChurchIndexDocument {
    @Id
    private String id;
    private String name;
    private String address;
    private String introduction;
    private String hashtags;
    private String locationcode;
    private String platformcode;
}
