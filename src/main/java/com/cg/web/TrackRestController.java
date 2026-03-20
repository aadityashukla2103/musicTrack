package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.entity.Track;
import com.cg.repo.TrackRepo;

@RestController
@RequestMapping("/music")
public class TrackRestController {

    @Autowired
    private TrackRepo repo;

    @GetMapping("/tracks")
    public ResponseEntity<List<Track>> viewAllMusic() {
        List<Track> tracks = repo.findAll();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @PostMapping("/tracks")
    public ResponseEntity<String> addTrack(@RequestBody Track track) {
        repo.save(track);
        return new ResponseEntity<>("Track Added", HttpStatus.CREATED);
    }

    @GetMapping("/tracks/search")
    public ResponseEntity<List<Track>> getTracksByTitle(@RequestParam String title) {
        List<Track> tracks = repo.findByTitleContainingIgnoreCase(title);
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable Long id) {

        Track track = repo.findById(id).orElse(null);

        if (track != null) {
            return new ResponseEntity<>(track, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Track not found", HttpStatus.NOT_FOUND);
        }
    }
}