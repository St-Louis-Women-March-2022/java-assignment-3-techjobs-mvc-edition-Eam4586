package org.launchcode.techjobs.mvc.controllers;


import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**This controller allows users to see either a table w\ all the options for
 different Job fields or a list of details for selected jobs.
 On /list, “All” column doesn’t work.*/
@Controller
@RequestMapping(value = "list")//class is requesting at list
public class ListController {

    //populates columnChoices and tableChoices with values.
    // These HashMaps play the same role as in the console app
    // They provide a collection of List and Search options presented in interface.

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());
    }

//displays table of clickable links for different job categories by implementing JobData class methods.

    @GetMapping(value = "")//at list
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());
        return "list";
    }

//displays information for the jobs in selected category by implementing JobData class methods.
//uses column and value query parameters to determine what to fetch from JobData.
// In the case of "all", fetches all job data, otherwise, renders the list-jobs.html view.
// similar to search by “searching” for a particular value within a field and then displaying jobs that match.
// Different from searching b\c user will arrive at this handler method by clicking on a link in list view, rather than submitting a form.
// The listJobsByColumnAndValue method deals with an “all” scenario differently than if a user clicks the category links.

    @GetMapping(value = "jobs")//the job of this method is to take the logic from the model and make it available to my view at list/jobs
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam(required = false) String value) {
        ArrayList<Job> jobs;// Created this array list.

        if (column.equals("all")){
            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");

        } else {
            jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }

        model.addAttribute("jobs", jobs);//this makes jobs accessible

        return "list-jobs";//inside list-jobs
    }
}
