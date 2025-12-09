package com.dev.api_volex.company.usecase;

import com.dev.api_volex.company.entity.CompanyEntity;
import com.dev.api_volex.company.repository.CompanyRepository;
import com.dev.api_volex.shared.exceptions.InvalidApiKeyException;
import org.springframework.stereotype.Service;

@Service
public class GetCompany {

    private final CompanyRepository companyRepository;

    public GetCompany(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyEntity findByApiKey(String apiKey) {
        return companyRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new InvalidApiKeyException("Invalid API Key"));
    }
}
