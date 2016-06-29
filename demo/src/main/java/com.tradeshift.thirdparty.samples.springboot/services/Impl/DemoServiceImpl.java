package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.GridDTO;
import com.tradeshift.thirdparty.samples.springboot.services.DemoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public List<GridDTO> getGridDTO() {
        List<GridDTO> gridDTOs = new ArrayList<GridDTO>();
        gridDTOs.add(new GridDTO(1, "Barbarian Queen", "Neutral Evil"));
        gridDTOs.add(new GridDTO(2, "Global Senior Vice President of Sales", "Chaotic Evil"));
        gridDTOs.add(new GridDTO(3, "Jonathan the Piggy", "Glorious Good"));
        gridDTOs.add(new GridDTO(4, "Paladin Knight", "Lawful Good"));
        gridDTOs.add(new GridDTO(5, "Potato Chip", "Radiant Good"));

        return gridDTOs;
    }
}

