package ru.nsu.mkorovkina.rubics_cube.service;

import org.springframework.stereotype.Component;
import org.worldcubeassociation.tnoodle.scrambles.Puzzle;
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry;

import java.util.Random;

@Component
public class ScrambleGenerator {

    private final Puzzle cube = PuzzleRegistry.THREE.getScrambler();
    private final Random random = new Random();

    public String generate() {
        return cube.generateWcaScramble(random);
    }
}