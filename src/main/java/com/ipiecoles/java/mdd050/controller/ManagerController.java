package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{idTechnicien}/remove")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTechniciens(@PathVariable("idManager")Long idManager, @PathVariable("idTechnicien")Long idTechnicien){
        this.managerService.deleteTechniciens(idManager,idTechnicien);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{matricule}/add")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTechniciens(@PathVariable("idManager")Long idManager, @PathVariable("matricule")String matricule){
        this.managerService.addTechniciens(idManager, matricule);
    }

}
