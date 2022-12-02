package com.example.gymdb;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GymController {

    // route for inserting
    @GetMapping("/insert")
    public void gym(@RequestParam(value = "gym_name") String gym_name) {
        // performs a sql insert based on gym_name passed in URL
        InsertSelectStatements.insert(gym_name);
    }

    // route for select
    @GetMapping("/select")
    public ArrayList<Gym> gym() {
        return InsertSelectStatements.select();
    }
}
