package com.bekwam.examples.javafx.tvtable;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Domain object for TVTable demo
 *
 * @author carl
 */
public class TVProgram {

    private final StringProperty programName = new SimpleStringProperty("");
    private final IntegerProperty episodesRecorded = new SimpleIntegerProperty(0);
    private final ObjectProperty<LocalDate> lastRecording = new SimpleObjectProperty<>();

    public TVProgram(String programName, Integer episodesRecorded, LocalDate lastRecording) {
        setProgramName(programName);
        setEpisodesRecorded(episodesRecorded);
        setLastRecording(lastRecording);
    }

    public String getProgramName() {
        return programName.get();
    }

    public StringProperty programNameProperty() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName.set(programName);
    }

    public int getEpisodesRecorded() {
        return episodesRecorded.get();
    }

    public IntegerProperty episodesRecordedProperty() {
        return episodesRecorded;
    }

    public void setEpisodesRecorded(int episodesRecorded) {
        this.episodesRecorded.set(episodesRecorded);
    }

    public LocalDate getLastRecording() {
        return lastRecording.get();
    }

    public ObjectProperty<LocalDate> lastRecordingProperty() {
        return lastRecording;
    }

    public void setLastRecording(LocalDate lastRecording) {
        this.lastRecording.set(lastRecording);
    }
}
