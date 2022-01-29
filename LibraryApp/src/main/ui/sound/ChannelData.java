package ui.sound;


import javax.sound.midi.MidiChannel;

/*
 * From SimpleDrawingPlayer
 * Represents a midi channel
 */
public class ChannelData {

    private final MidiChannel channel;

    private final int velocity;
    private final int pressure;
    private final int bend;
    private final int reverb;
    private final int num;

    /*
     * Constructor
     * EFFECTS: sets a MidiChannel
     */
    public ChannelData(MidiChannel channel, int num) {
        this.channel = channel;
        this.num = num;
        velocity = 64;
        pressure = 64;
        bend = 64;
        reverb = 64;
    }

    // getters
    public MidiChannel getChannel() {
        return channel;
    }
}

