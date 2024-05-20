package de.danoeh.antennapod.playback.service

import android.content.Context
import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import de.danoeh.antennapod.model.playback.MediaType

import de.danoeh.antennapod.storage.preferences.PlaybackPreferences
import de.danoeh.antennapod.ui.appstartintent.MainActivityStarter
import de.danoeh.antennapod.ui.appstartintent.VideoPlayerActivityStarter
import de.danoeh.antennapod.model.playback.Playable
import de.danoeh.antennapod.playback.base.PlaybackServiceMediaPlayer
import de.danoeh.antennapod.playback.service.internal.LocalPSMP

/**
 * Controls the MediaPlayer that plays a FeedMedia-file
 */
class PlaybackService : MediaLibraryService() {
    private lateinit var player: Player
    private lateinit var session: MediaLibrarySession

    override fun onCreate() {
        super.onCreate()
        player = with (ExoPlayer.Builder(this)) {
            build()
        }
        val callback = object : MediaLibrarySession.Callback {
        }
        session = with (MediaLibrarySession.Builder(this, player, callback)) {
            build()
        }
    }

    override fun onDestroy() {
        session.release()
        player.release()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = session
}
