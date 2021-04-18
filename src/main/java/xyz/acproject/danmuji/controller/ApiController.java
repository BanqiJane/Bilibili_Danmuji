package xyz.acproject.danmuji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.acproject.danmuji.service.ClientService;
import xyz.acproject.danmuji.service.SetService;

/**
 * @author Jane
 * @ClassName ApiController
 * @Description TODO
 * @date 2021/4/12 16:04
 * @Copyright:2021
 */
@RestController
@RequestMapping("/api")
public class ApiController {


    private SetService checkService;

    private ClientService clientService;








    @Autowired
    public void setCheckService(SetService checkService) {
        this.checkService = checkService;
    }
    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
}
