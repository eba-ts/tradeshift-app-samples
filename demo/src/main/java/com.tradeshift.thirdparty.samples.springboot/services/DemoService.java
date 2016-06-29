package com.tradeshift.thirdparty.samples.springboot.services;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.GridDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DemoService {
    List<GridDTO> getGridDTO();
}
