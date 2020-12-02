package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DomainController;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

}
