package com.example.demo.controler;

import com.example.demo.dto.request.AddSpotRequest;
import com.example.demo.dto.response.AddSpotResponse;
import com.example.demo.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    private SpotService spotService;

    @PostMapping(path = "/spot/add")
    public AddSpotResponse addSpot(@RequestBody AddSpotRequest addSpotRequest) {
        var spot = spotService.addSpot(addSpotRequest.getDescription());
        return new AddSpotResponse(spot);
    }
}
