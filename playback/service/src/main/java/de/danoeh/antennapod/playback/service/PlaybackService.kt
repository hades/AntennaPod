package de.danoeh.antennapod.playback.service

import android.content.Context
import android.content.Intent
import androidx.media3.common.Player
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
    private lateinit var player: LocalPSMP
    private lateinit var session: MediaLibrarySession

    override fun onCreate() {
        super.onCreate()
        player = LocalPSMP(this, object : PlaybackServiceMediaPlayer.PSMPCallback {
            override fun statusChanged(newInfo: PlaybackServiceMediaPlayer.PSMPInfo?) {
                TODO("Not yet implemented")
            }

            override fun shouldStop() {
                TODO("Not yet implemented")
            }

            override fun onMediaChanged(reloadUI: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onPostPlayback(media: Playable, ended: Boolean, skipped: Boolean, playingNext: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onPlaybackStart(playable: Playable, position: Int) {
                TODO("Not yet implemented")
            }

            override fun onPlaybackPause(playable: Playable?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun getNextInQueue(currentMedia: Playable?): Playable {
                TODO("Not yet implemented")
            }

            override fun findMedia(url: String): Playable? {
                TODO("Not yet implemented")
            }

            override fun onPlaybackEnded(mediaType: MediaType?, stopPlaying: Boolean) {
                TODO("Not yet implemented")
            }

            override fun ensureMediaInfoLoaded(media: Playable) {
                TODO("Not yet implemented")
            }
        })
        val callback = object : MediaLibrarySession.Callback {

        }
        session = with (MediaLibrarySession.Builder(this, player.player, callback)) {
            build()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? {
        TODO("Not yet implemented")
        return null
    }
}
