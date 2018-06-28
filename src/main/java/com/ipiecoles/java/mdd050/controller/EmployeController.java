package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.exception.ConflictException;
import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/employes")
public class EmployeController {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";


    @Autowired
    private EmployeService employeService;

    @RequestMapping(value="/count", method = RequestMethod.GET)
        public Long count() {
            return employeService.countAllEmploye();
        }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_CHARSET_UTF_8 )
    public Employe detail(@PathVariable(value = "id") Long id) throws EntityNotFoundException{
        Employe employe = employeService.findById(id);
        return employe;
    }

    /*@ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return "ERREUR ! ";
    }*/

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"matricule"}, produces = APPLICATION_JSON_CHARSET_UTF_8)
    public Employe findByMatricule(@RequestParam("matricule") String matricule)
            throws EntityNotFoundException{
        Employe employe = employeService.findMyMatricule(matricule);
        if (employe == null){
            throw new EntityNotFoundException("L'employé de matricule :  " + matricule + "n'a pas été trouvé.");
        }
        else {
            return employe;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"page","size","sortProperty","sortDirection"},
            produces = APPLICATION_JSON_CHARSET_UTF_8)
    public Page<Employe> getAllEmployes(@RequestParam("page") Integer page,//Numéro de page
                                 @RequestParam("size") Integer size,// Taille de la page
                                 @RequestParam("sortProperty") String sortProperty,//nom du champ
                                 @RequestParam("sortDirection") String sortDirection//ASC ou DESC
                                 ) throws EntityNotFoundException{
        return employeService.findAllEmployes( page, size, sortProperty, sortDirection);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_CHARSET_UTF_8, produces = APPLICATION_JSON_CHARSET_UTF_8,
    value="")
    public Employe createEmploye(@RequestBody Employe employe) throws ConflictException {
        try{
            return this.employeService.creerEmploye(employe);
        }
        catch (DataIntegrityViolationException e){
            if(e.getMessage().contains("matricule_unique")){
                throw new ConflictException("L'employé de matricule " + employe.getMatricule() + "existe déjà ! ");
            }
            throw new IllegalArgumentException("Une erreur technique est survenue");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value="/{id}")
    public Employe updateEmploye(@PathVariable("id")Long id, @RequestBody Employe employe) throws EmployeException {
       return this.employeService.updateEmploye(id,employe);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable("id")Long id, @RequestBody Employe employe)throws EmployeException{
                    this.employeService.deleteEmploye(id);
    }



}
