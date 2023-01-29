package com.umcreligo.umcback.domain.church.repository;

import com.umcreligo.umcback.domain.church.domain.ChurchIndexDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ChurchIndexDocumentRepository extends ElasticsearchRepository<ChurchIndexDocument, String> {
}
