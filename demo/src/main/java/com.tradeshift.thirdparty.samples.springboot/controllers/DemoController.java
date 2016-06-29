package com.tradeshift.thirdparty.samples.springboot.controllers;

import com.tradeshift.thirdparty.samples.springboot.domain.dto.GridDTO;
import com.tradeshift.thirdparty.samples.springboot.services.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping("/get_grid")
    public @ResponseBody
    List<GridDTO> getGrid(final HttpServletRequest request) {
        return demoService.getGridDTO();
    }

}
