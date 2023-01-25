package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChurchServiceImpl implements ChurchService {
    private final ChurchRepository churchRepository;
}
