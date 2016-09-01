package com.tradeshift.thirdparty.samples.springboot.services;

import io.swagger.client.ApiException;
import io.swagger.client.model.ExternalTaskDTO;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
public interface TaskService {

    ExternalTaskDTO createTask() throws ApiException, ParserConfigurationException;

    void completeTask(String taskId) throws ParserConfigurationException, SAXException, IOException, ApiException;

    List<ExternalTaskDTO> getTaskList(Integer limit, Integer pageNumber) throws ApiException;

}
