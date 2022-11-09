package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;

@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);//
        return "search";
    }

    @PostMapping ("/results")
    public String displaySearchResults (Model model, @RequestParam String searchTerm, @RequestParam (required = false) String searchType) {
        ArrayList<Job> jobs = new ArrayList<>();

            if (searchTerm.equals ("") || searchTerm.toLowerCase().equals("all")){
                jobs = JobData.findAll();
                model.addAttribute("title", "All Jobs");
                model.addAttribute("jobs", jobs);
        }

             else if (searchTerm.equals ("all")){
                jobs = JobData.findAll();
                 model.addAttribute("title", "All Jobs");
                model.addAttribute("jobs", jobs);
        }

            else {
                jobs = JobData.findByColumnAndValue(searchTerm, searchType);
                model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
                model.addAttribute("jobs", jobs);
                model.addAttribute("columns", columnChoices);
            }

        return "search";
    }


    // TODO #3 - Create a handler to process a search request and render the updated search view.

}


