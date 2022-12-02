package com.example.gymdb;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GymController {

    // route for inserting
    // performs sql insert based on url param
    @GetMapping("/insert")
    public void gym(@RequestParam(value = "gym_name") String gym_name) {
        InsertSelectStatements.insert(gym_name);
    }

    // route for select
    @GetMapping("/select")
    public ArrayList<Gym> gym() {
        return InsertSelectStatements.select();
    }
}
