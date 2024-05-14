package de.danoeh.antennapod.playback.service

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.util.Pair
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent
import de.danoeh.antennapod.event.playback.PlaybackServiceEvent
import de.danoeh.antennapod.event.playback.SpeedChangedEvent
import de.danoeh.antennapod.model.feed.FeedMedia
import de.danoeh.antennapod.model.feed.FeedPreferences
import de.danoeh.antennapod.model.playback.MediaType
import de.danoeh.antennapod.model.playback.Playable
import de.danoeh.antennapod.playback.base.PlaybackServiceMediaPlayer.PSMPInfo
import de.danoeh.antennapod.playback.base.PlayerStatus
import de.danoeh.antennapod.storage.database.DBReader
import de.danoeh.antennapod.storage.database.DBWriter
import de.danoeh.antennapod.storage.preferences.PlaybackPreferences
import de.danoeh.antennapod.ui.episodes.PlaybackSpeedUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Communicates with the playback service. GUI classes should use this class to
 * control playback instead of communicating with the PlaybackService directly.
 */
abstract class PlaybackController(private val activity: Activity) {
    @Synchronized
    fun init() {
    }

    /**
     * Should be called if the PlaybackController is no longer needed, for
     * example in the activity's onStop() method.
     */
    fun release() {
    }

    /**
     * Should be called in the activity's onPause() method.
     */
    fun pause() {
    }

    open fun onPlaybackEnd() {}


    protected open fun updatePlayButtonShowsPlay(showPlay: Boolean) {}
    abstract fun loadMediaInfo()

    /**
     * Called when connection to playback service has been established or
     * information has to be refreshed
     */
    private fun queryService() {
    }

    fun playPause() {
    }

    val status: PlayerStatus = PlayerStatus.STOPPED
    val position: Int = 0
    val duration: Int = 0

    fun getMedia(): Playable? = null

    fun sleepTimerActive(): Boolean  = false

    fun disableSleepTimer() {
    }

    val sleepTimerTimeLeft: Long = 0

    fun extendSleepTimer(extendTime: Long) {
    }

    fun setSleepTimer(time: Long) {
    }

    fun seekTo(time: Int) {
    }

    fun setVideoSurface(holder: SurfaceHolder?) {
    }

    fun setPlaybackSpeed(speed: Float) {
    }

    fun setSkipSilence(skipSilence: Boolean) {
    }

    val currentPlaybackSpeedMultiplier: Float = 1f
    val currentPlaybackSkipSilence: Boolean = false
    val audioTracks: List<String> = emptyList()
    val selectedAudioTrack: Int = -1

    fun setAudioTrack(track: Int) {
    }

    val isPlayingVideoLocally: Boolean = false
    val videoSize: Pair<Int, Int>? = null

    fun notifyVideoSurfaceAbandoned() {
    }

    val isStreaming: Boolean = false
}
