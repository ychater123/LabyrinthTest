package com.nespresso.sofa.recruitment.labyrinth;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import com.nespresso.sofa.recruitment.labyrinth.exceptions.*;

public class LabyrinthTest {

    /**
     * each letter represent a room
     * each | represent a gate between two room
     * each $ represent a gate with a sensor on
     * @throws IllegalMoveException 
     * @throws ClosedDoorException 
     */
    @Test
    public void be_Walkable_Till_The_End() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("E");
        walker.walkTo("F");
        walker.walkTo("G");
        assertThat(walker.position()).isEqualTo("G");
    }

    @Test(expected = IllegalMoveException.class)
    public void refuse_Illegal_Move() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "B$D");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("E"); // room E does not exist in the labyrinth
    }

    @Test(expected = IllegalMoveException.class)
    public void refuse_Move_Without_Path() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "B$D");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("C"); // Can not reach C from B
    }

    @Test
    public void allow_Cyclic_Path() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.walkTo("F");
        walker.walkTo("E");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.walkTo("F");
        walker.walkTo("G");
        assertThat(walker.position()).isEqualTo("G");
    }


    @Test
    public void allow_Back_And_Forth() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("C");
        walker.walkTo("A");
        walker.walkTo("B");
        walker.walkTo("D");
        assertThat(walker.position()).isEqualTo("D");
    }


    @Test
    public void allow_Walker_To_Close_Passed_Door() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.walkTo("F");
        walker.closeLastDoor();
        walker.walkTo("G");
        assertThat(walker.position()).isEqualTo("G");
    }

    @Test(expected = DoorAlreadyClosedException.class)
    public void allow_Walker_To_Close_Only_Last_Door() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.walkTo("F");
        walker.closeLastDoor();
        walker.closeLastDoor();
        walker.walkTo("G");
    }

    @Test(expected = ClosedDoorException.class)
    public void not_Allow_Closed_Door_Crossing() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.closeLastDoor();
        walker.walkTo("F");
        walker.walkTo("E");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.walkTo("F");
        walker.walkTo("G");
    }

    @Test(expected = ClosedDoorException.class)
    public void not_Allow_Turn_Back_Through_Closed_Door() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        walker.walkTo("D");
        walker.closeLastDoor();
        walker.walkTo("B");
    }


    @Test
    public void follow_Walker() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "B$D", "D$E", "D$F", "F$H", "D$F");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        assertThat(labyrinth.readSensors()).isEqualTo("AB");
        walker.walkTo("D");
        assertThat(labyrinth.readSensors()).isEqualTo("AB;BD");
        walker.walkTo("F");
        assertThat(walker.position()).isEqualTo("F");
        assertThat(labyrinth.readSensors()).isEqualTo("AB;BD;DF");
    }

    @Test
    public void follow_Walker_Through_Unmonitored_Path() {
        Labyrinth labyrinth = new Labyrinth("A$B", "A$C", "C|E", "B$D", "B|E", "E$F", "D$F", "F|G");
        Walker walker = labyrinth.popIn("A");
        walker.walkTo("B");
        assertThat(labyrinth.readSensors()).isEqualTo("AB");
        walker.walkTo("E");
        walker.walkTo("F");
        assertThat(labyrinth.readSensors()).isEqualTo("AB;EF");
        walker.walkTo("G");
        assertThat(walker.position()).isEqualTo("G");
        assertThat(labyrinth.readSensors()).isEqualTo("AB;EF");
    }


}
