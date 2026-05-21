package io.github.picodotdev.blogbitix.javasound;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        printSupportedFileTypes();

        String format = args[0];
        switch (format) {
            case "midi":
                playMidi();
                break;
            case "wav":
                playWav();
                break;
            case "mp3":
                playMp3();
                break;
            case "ogg":
                playOgg();
                break;
        }
    }

    private static void printSupportedFileTypes() {
        String audioFileTypes = Arrays.stream(AudioSystem.getAudioFileTypes()).map(it -> it.getExtension()).collect(Collectors.joining(", "));
        String midiFileTypes = Arrays.stream(MidiSystem.getMidiFileTypes()).mapToObj(it -> String.valueOf(it)).collect(Collectors.joining(", "));

        System.out.printf("Audio file types: %s%n", audioFileTypes);
        System.out.printf("Midi file types: %s%n", midiFileTypes);
        System.out.printf("Mixers info: %s%n", Arrays.stream(AudioSystem.getMixerInfo()).map(it -> it.toString()).collect(Collectors.joining(", ")));
    }

    private static void playMidi() throws Exception {
        InputStream is = Main.class.getResourceAsStream("/midi.mid");

        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.setSequence(is);

        System.out.printf("Midi duration: %d seconds%n", sequencer.getMicrosecondLength() / 1000000);

        sequencer.start();

        do  {
            Thread.sleep(100);
        } while (sequencer.isRunning());

        sequencer.close();
        is.close();
    }

    private static void playWav() throws Exception {
        InputStream is = Main.class.getResourceAsStream("/wav.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(is);

        Clip clip = AudioSystem.getClip();
        clip.open(ais);

        System.out.printf("Audio format: %s%n", ais.getFormat());
        System.out.printf("Sampled duration: %d seconds%n", clip.getMicrosecondLength() / 1000000);

        clip.start();

        do  {
            Thread.sleep(100);
        } while (clip.isRunning());

        clip.close();
        ais.close();
    }

    private static void playMp3() throws Exception {
        ProcessBuilder builder = new ProcessBuilder().command("ffmpeg", "-i", "src/main/resources/mp3.mp3", "-acodec", "pcm_s16le", "-ar", "44100", "-ac", "2", "-f", "wav", "pipe:1");
        Process process = builder.start();

        InputStream is = process.getInputStream();
        AudioInputStream ais = new AudioInputStream(is, AudioSystem.getAudioInputStream(is).getFormat(), AudioSystem.NOT_SPECIFIED);

        Clip clip = AudioSystem.getClip();
        clip.open(ais);

        System.out.printf("Audio format: %s%n", ais.getFormat());
        System.out.printf("Sampled duration: %d seconds%n", clip.getMicrosecondLength() / 1000000);

        clip.start();

        do  {
            Thread.sleep(100);
        } while (clip.isRunning());

        clip.close();
        ais.close();
    }

    private static void playOgg() throws Exception {
        ProcessBuilder builder = new ProcessBuilder().command("ffmpeg", "-i", "src/main/resources/ogg.ogg", "-acodec", "pcm_s16le", "-ar", "44100", "-ac", "2", "-f", "wav", "pipe:1");
        Process process = builder.start();

        InputStream is = process.getInputStream();
        AudioInputStream ais = new AudioInputStream(is, AudioSystem.getAudioInputStream(is).getFormat(), AudioSystem.NOT_SPECIFIED);

        Clip clip = AudioSystem.getClip();
        clip.open(ais);

        System.out.printf("Audio format: %s%n", ais.getFormat());
        System.out.printf("Sampled duration: %d seconds%n", clip.getMicrosecondLength() / 1000000);

        clip.start();

        do  {
            Thread.sleep(100);
        } while (clip.isRunning());

        clip.close();
        ais.close();
    }
}
