package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.GridDTO;
import com.tradeshift.thirdparty.samples.springboot.services.Impl.TokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    static Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    /**
     *
     * @return data for grid view
     */
    @RequestMapping(value = "/get_grid" , method = RequestMethod.GET)
    public @ResponseBody List<GridDTO> getGrid() {
        LOGGER.info("get data for grid view", DemoController.class);
        List<GridDTO> gridDTOs = new ArrayList<GridDTO>();
        gridDTOs.add(new GridDTO(1, "Barbarian Queen", "Neutral Evil"));
        gridDTOs.add(new GridDTO(2, "Global Senior Vice President of Sales", "Chaotic Evil"));
        gridDTOs.add(new GridDTO(3, "Jonathan the Piggy", "Glorious Good"));
        gridDTOs.add(new GridDTO(4, "Paladin Knight", "Lawful Good"));
        gridDTOs.add(new GridDTO(5, "Potato Chip", "Radiant Good"));

        return gridDTOs;
    }

}
