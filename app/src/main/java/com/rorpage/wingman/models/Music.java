package com.rorpage.wingman.models;

import com.rorpage.wingman.common.Comparator;

import java.util.Locale;

public class Music {
    public enum MusicSource {
        BROADCAST_RECEIVER,
        NOTIFICATION_PARSE,
        MEDIACONTROLLER,
        UNKNOWN
    }

    public static class MusicMeta {
        public String artist = ""; // f7983a
        public String album = ""; // f7984b
        public String track = ""; // f7985c
        public long trackLength = 0;
        public int f7987e = 0;
        public int f7988f = 0;
        public String fromPackage = "";
        public MusicSource musicSource = MusicSource.UNKNOWN;

        public String toString() {
            return "Artist: " + this.artist + ", Album: " + this.album + ", Track: " + this.track +
                    ", Source: " + this.musicSource.toString() + ", Length: " +
                    Music.convertTrackLengthToString(this.trackLength) + ", Package: " + this.fromPackage;
        }

        public boolean compare(MusicMeta musicMeta) {
            if (this == musicMeta) {
                return true;
            }

            if (Comparator.compare(this.artist, musicMeta.artist) &&
                    Comparator.compare(this.album, musicMeta.album) &&
                    Comparator.compare(this.track, musicMeta.track)) {
                return true;
            }

            return false;
        }
    }

    private static String convertTrackLengthToString(long j) {
        if (j < 0) {
            return "Unknown";
        }

        long j2 = (j / 1000) % 60;
        long j3 = (j / 60000) % 60;
        long j4 = (j / 3600000) % 24;
        return String.format(Locale.US, "%02d:%02d:%02d", new Object[]{Long.valueOf(j4), Long.valueOf(j3), Long.valueOf(j2)});
    }
}
