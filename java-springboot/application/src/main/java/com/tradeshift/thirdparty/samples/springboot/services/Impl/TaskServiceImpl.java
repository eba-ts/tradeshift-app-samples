package com.tradeshift.thirdparty.samples.springboot.services.Impl;

import com.tradeshift.thirdparty.samples.springboot.services.TaskService;
import com.tradeshift.thirdparty.springboot.samples.config.PropertySources;
import com.tradeshift.thirdparty.springboot.samples.services.TokenService;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.ExternalTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    static Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    PropertySources propertySources;

    private DefaultApi defaultApi;


    @Override
    public ExternalTaskDTO createTask() throws ApiException, ParserConfigurationException {
        LOGGER.info("create task", TaskServiceImpl.class);

        ExternalTaskDTO taskDTO = new ExternalTaskDTO();
        taskDTO.setHandler("Tradeshift.NetworkSearch.networkInvitation");

        List<String> assignees = new ArrayList<>();
        assignees.add(tokenService.getCurrentUserId());
        taskDTO.setAssignees(assignees);

        return getDefaultApiInstance().createTask(taskDTO);
    }

    @Override
    public void completeTask(String taskId) throws ParserConfigurationException, SAXException, IOException, ApiException {
        LOGGER.info("complete task", TaskServiceImpl.class);

        getDefaultApiInstance().completeTask(taskId, "outcome", tokenService.getCurrentUserId());
    }

    @Override
    public List<ExternalTaskDTO> getTaskList(Integer limit, Integer pageNumber) throws ApiException {
        LOGGER.info("get list of tasks", TaskServiceImpl.class);

        List<ExternalTaskDTO> apiResponse = getDefaultApiInstance().getTasks(null, null, null, null, limit, pageNumber);

        return apiResponse;
    }

    private DefaultApi getDefaultApiInstance() {
        if (this.defaultApi == null) {
            this.defaultApi = new DefaultApi(new ApiClient());
            this.defaultApi.getApiClient().setBasePath(propertySources.getTradeshiftAPIDomainName() + "/tradeshift/rest");
        }

        this.defaultApi.getApiClient().setAccessToken(tokenService.getAccessTokenFromContext().getValue());
        return this.defaultApi;
    }
}
