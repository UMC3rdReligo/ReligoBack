package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchProviderImpl implements ChurchProvider {
    private final ChurchRepository churchRepository;

    @Override
    public Optional<Church> findChurch() {
        return Optional.empty();
    }

    @Override
    public Page<Church> searchChurches() {
        return Page.empty();
    }

    @Override
    public Page<Church> recommendChurches() {
        return Page.empty();
    }
}
