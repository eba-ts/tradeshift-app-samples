package com.tradeshift.thirdparty.samples.springboot.controllers;


import com.tradeshift.thirdparty.samples.springboot.services.TaskService;
import io.swagger.client.ApiException;
import io.swagger.client.model.ExternalTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    static Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;


    @RequestMapping(method = RequestMethod.POST)
    public ExternalTaskDTO createTask() throws ApiException, ParserConfigurationException {
        LOGGER.info("create task", TaskController.class);

        return taskService.createTask();
    }

    @RequestMapping(value = "/complete/{id}", method = RequestMethod.PUT)
    public void completeTask(@PathVariable("id") String Id, @RequestParam(value = "action") String action)
                                throws ApiException, IOException, SAXException, ParserConfigurationException {

        LOGGER.info("complete task", TaskController.class);

        taskService.completeTask(Id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ExternalTaskDTO> getListOfTasks(@RequestParam Integer limit, @RequestParam Integer pageNumber)
                                                                                            throws ApiException {
        LOGGER.info("get list of tasks", TaskController.class);

        return taskService.getTaskList(limit, pageNumber);
    }

}
