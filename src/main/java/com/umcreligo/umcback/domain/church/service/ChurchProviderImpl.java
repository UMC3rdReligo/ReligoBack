package com.umcreligo.umcback.domain.church.service;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.church.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChurchProviderImpl implements ChurchProvider {
    private final ChurchRepository churchRepository;

    @Override
    public Optional<Church> findChurch(Long churchId) {
        return this.churchRepository.findWithAllById(churchId);
    }

    @Override
    public List<Church> searchChurches() {
        return new ArrayList<>();
    }

    @Override
    public List<Church> recommendChurches() {
        return new ArrayList<>();
    }
}
