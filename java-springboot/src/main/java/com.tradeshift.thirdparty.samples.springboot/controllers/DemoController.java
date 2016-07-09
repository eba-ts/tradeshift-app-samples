package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.GridRowDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    /**
     * Get List of Objects for show on the UI client
     *
     *
     * @return data for grid view
     */
    @RequestMapping(value = "/grid-data", method = RequestMethod.GET)
    public @ResponseBody List<GridRowDTO> getGrid() {
        LOGGER.info("get data for grid view", DemoController.class);

        List<GridRowDTO> gridRowDTOs = new ArrayList<GridRowDTO>();
        gridRowDTOs.add(new GridRowDTO(1, "Barbarian Queen", "Neutral Evil"));
        gridRowDTOs.add(new GridRowDTO(2, "Global Senior Vice President of Sales", "Chaotic Evil"));
        gridRowDTOs.add(new GridRowDTO(3, "Jonathan the Piggy", "Glorious Good"));
        gridRowDTOs.add(new GridRowDTO(4, "Paladin Knight", "Lawful Good"));
        gridRowDTOs.add(new GridRowDTO(5, "Potato Chip", "Radiant Good"));

        return gridRowDTOs;
    }

}
