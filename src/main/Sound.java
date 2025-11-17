package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import javax.sound.sampled.AudioFormat;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound()
    {
        soundURL[0] = getClass().getResource("/sound/A_Minor_Distraction.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/fanfare.wav");
        soundURL[3] = getClass().getResource("/sound/powerup.wav");
        soundURL[4] = getClass().getResource("/sound/unlock.wav");
        soundURL[5] = getClass().getResource("/sound/dooropen.wav");

    }

    public void setFile(int i)           // Java Sound File Opening Format
    {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);

            AudioFormat baseFormat = ais.getFormat();

            // Chuyển WAV về PCM 16-bit, 44.1kHz, Stereo nếu cần
            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100, // Tần số mẫu (Hz)
                    16,    // Độ sâu bit (16-bit)
                    baseFormat.getChannels(), // Số kênh (Mono/Stereo)
                    baseFormat.getChannels() * 2, // Frame size
                    44100, // Sample rate
                    false  // Little Endian
            );

            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(targetFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(decodedAis);

            // Kiểm tra xem có thể chỉnh âm lượng không
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
            } else {
                System.out.println("Không hỗ trợ điều chỉnh âm lượng");
            }
//          pass value for clip // -80f to 6f // 6 is max. -80f = 0
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        checkVolume();
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void checkVolume() {
        if (fc != null) {
            switch (volumeScale) {
                case 0:
                    volume = -80f;
                case 1:
                    volume = -20f;
                case 2:
                    volume = -12f;
                case 3:
                    volume = -5f;
                case 4:
                    volume = 1f;
                case 5:
                    volume = 6f;
            }
            fc.setValue(volume);
        }
    }
}