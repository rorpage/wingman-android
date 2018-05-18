package com.rorpage.wingman.modules.receivers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BadParcelableException;

import com.rorpage.wingman.models.messages.WingmanMessage;
import com.rorpage.wingman.models.messages.MessagePriority;
import com.rorpage.wingman.models.messages.MessageType;
import com.rorpage.wingman.models.Music;
import com.rorpage.wingman.modules.BaseBroadcastReceiverModule;

import java.util.ArrayList;

import timber.log.Timber;

public class MusicModule extends BaseBroadcastReceiverModule {
    public static final String PREFERENCE_KEY_MODULEDATA_MUSICMODULE = "DATA_MusicModule";

    public MusicModule(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
    }

    @Override
    protected ArrayList<WingmanMessage> getMessages() {
        ArrayList<WingmanMessage> wingmanMessages = new ArrayList<>();
        wingmanMessages.add(new WingmanMessage(getMessageType(),
                mSharedPreferences.getString(PREFERENCE_KEY_MODULEDATA_MUSICMODULE, "None"),
                getMessagePriority()));
        return wingmanMessages;
    }

    @Override
    protected MessageType getMessageType() {
        return MessageType.MUSIC;
    }

    @Override
    protected MessagePriority getMessagePriority() {
        return MessagePriority.LOW;
    }

    @Override
    protected void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.amazon.mp3.metachanged");
        intentFilter.addAction("com.andrew.apollo.metachanged");
        intentFilter.addAction("com.android.music.metachanged");
        intentFilter.addAction("com.htc.music.metachanged");
        intentFilter.addAction("com.miui.player.metachanged");
        intentFilter.addAction("com.rdio.android.metachanged");
        intentFilter.addAction("com.real.IMP.metachanged");
        intentFilter.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        intentFilter.addAction("com.sec.android.app.music.metachanged");
        intentFilter.addAction("com.sonyericsson.music.metachanged");
        intentFilter.addAction("fm.last.android.metachanged");
        intentFilter.addAction("com.nullsoft.winamp.metachanged");
        intentFilter.addAction("com.getpebble.action.NOW_PLAYING");
        intentFilter.addAction("com.spotify.music.metadatachanged");
        intentFilter.addAction("com.doubleTwist.androidPlayer.metachanged");
        intentFilter.addAction("com.jrstudio.AnotherMusicPlayer.metachanged");
        intentFilter.addAction("com.rhapsody.metachanged");
        intentFilter.addAction("com.e8tracks.metachanged");
        intentFilter.addAction("com.soundcloud.android.metachanged");

//        intentFilter.addAction("au.com.shiftyjelly.pocketcasts.action.PLAY");

//        intentFilter.addAction("com.amazon.mp3.playstatechanged");
//        intentFilter.addAction("com.andrew.apollo.playstatechanged");
//        intentFilter.addAction("com.android.music.playbackcomplete");
//        intentFilter.addAction("com.android.music.playstatechanged");
//        intentFilter.addAction("com.htc.music.playstatechanged");
//        intentFilter.addAction("com.htc.music.playbackcomplete");
//        intentFilter.addAction("com.miui.player.playstatechanged");
//        intentFilter.addAction("com.rdio.android.playstatechanged");
//        intentFilter.addAction("com.real.IMP.playstatechanged");
//        intentFilter.addAction("com.samsung.sec.android.MusicPlayer.playstatechanged");
//        intentFilter.addAction("com.sec.android.app.music.playstatechanged");
//        intentFilter.addAction("com.sonyericsson.music.playstatechanged");
//        intentFilter.addAction("fm.last.android.playstatechanged");
//        intentFilter.addAction("com.nullsoft.winamp.playstatechanged");
//        intentFilter.addAction("com.spotify.music.playbackstatechanged");
//        intentFilter.addAction("com.doubleTwist.androidPlayer.playstatechanged");
//        intentFilter.addAction("com.jrstudio.AnotherMusicPlayer.playstatechanged");
//        intentFilter.addAction("com.rhapsody.playstatechanged");
//        intentFilter.addAction("com.e8tracks.playstatechanged");
//        intentFilter.addAction("com.soundcloud.android.playstatechanged");

        mContext.registerReceiver(this, intentFilter);
    }

    @Override
    protected void handleReceivedBroadcast(Context context, Intent intent) {
        String str = null;

        try {
            String stringExtra;
            String stringExtra2;

            if (intent.hasExtra("artist")) {
                stringExtra = intent.getStringExtra("artist");
            } else if (intent.hasExtra("ARTIST_NAME")) {
                stringExtra = intent.getStringExtra("ARTIST_NAME");
            } else if (intent.hasExtra("com.amazon.mp3.artist")) {
                stringExtra = intent.getStringExtra("com.amazon.mp3.artist");
            } else {
                stringExtra = null;
            }

            if (intent.hasExtra("track")) {
                stringExtra2 = intent.getStringExtra("track");
            } else if (intent.hasExtra("TRACK_NAME")) {
                stringExtra2 = intent.getStringExtra("TRACK_NAME");
            } else if (intent.hasExtra("com.amazon.mp3.track")) {
                stringExtra2 = intent.getStringExtra("com.amazon.mp3.track");
            } else {
                stringExtra2 = null;
            }

            if (intent.hasExtra("album")) {
                str = intent.getStringExtra("album");
            } else if (intent.hasExtra("ALBUM_NAME")) {
                str = intent.getStringExtra("ALBUM_NAME");
            } else if (intent.hasExtra("com.amazon.mp3.album")) {
                str = intent.getStringExtra("com.amazon.mp3.album");
            }

            if (stringExtra == null) {
                stringExtra = "";
            }

            if (stringExtra2 == null) {
                stringExtra2 = "";
            }

            if (str == null) {
                str = "";
            }

            Music.MusicMeta music = new Music.MusicMeta();
            music.artist = stringExtra;
            music.album = str;
            music.track = stringExtra2;
            music.musicSource = Music.MusicSource.BROADCAST_RECEIVER;

//            final String artist = music.artist.substring(0, Math.min(music.artist.length(), 14));
//            final String track = music.track.substring(0, Math.min(music.track.length(), 16));
            final String artist = music.artist.substring(0, Math.min(music.artist.length(), 18));
            final String track = music.track.substring(0, Math.min(music.track.length(), 20));
            final String musicData = String.format("%s\n%s", artist, track);

            mSharedPreferences.edit()
                    .putString(PREFERENCE_KEY_MODULEDATA_MUSICMODULE, musicData)
                    .apply();
        } catch (BadParcelableException e) {
            Timber.e(e, "Error receiving music broadcast");
        }
    }
}
