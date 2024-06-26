package de.danoeh.antennapod.ui.screen.onlinefeedview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.playback.MediaType;
import de.danoeh.antennapod.net.common.NetworkUtils;
import de.danoeh.antennapod.model.playback.RemoteMedia;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.playback.service.PlaybackService;
import de.danoeh.antennapod.playback.service.PlaybackServiceStarter;
import de.danoeh.antennapod.ui.common.DateFormatter;
import de.danoeh.antennapod.model.playback.Playable;
import de.danoeh.antennapod.ui.cleaner.HtmlToPlainText;
import de.danoeh.antennapod.ui.StreamingConfirmationDialog;

import java.util.List;

/**
 * List adapter for showing a list of FeedItems with their title and description.
 */
public class FeedItemlistDescriptionAdapter extends ArrayAdapter<FeedItem> {
    private static final int MAX_LINES_COLLAPSED = 2;

    public FeedItemlistDescriptionAdapter(Context context, int resource, List<FeedItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;

        FeedItem item = getItem(position);

        // Inflate layout
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.itemdescription_listitem, parent, false);
            holder.title = convertView.findViewById(R.id.txtvTitle);
            holder.pubDate = convertView.findViewById(R.id.txtvPubDate);
            holder.description = convertView.findViewById(R.id.txtvDescription);
            holder.preview = convertView.findViewById(R.id.butPreview);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.title.setText(item.getTitle());
        holder.pubDate.setText(DateFormatter.formatAbbrev(getContext(), item.getPubDate()));
        if (item.getDescription() != null) {
            String description = HtmlToPlainText.getPlainText(item.getDescription())
                    .replaceAll("\n", " ")
                    .replaceAll("\\s+", " ")
                    .trim();
            holder.description.setText(description);
            holder.description.setMaxLines(MAX_LINES_COLLAPSED);
        }
        holder.description.setTag(Boolean.FALSE); // not expanded
        holder.preview.setVisibility(View.GONE);
        holder.preview.setOnClickListener(v -> {
            if (item.getMedia() == null) {
                return;
            }
            Playable playable = new RemoteMedia(item);
            if (!NetworkUtils.isStreamingAllowed()) {
                new StreamingConfirmationDialog(getContext(), playable).show();
                return;
            }

            new PlaybackServiceStarter(getContext(), playable)
                    .callEvenIfRunning(true)
                    .start();

            if (playable.getMediaType() == MediaType.VIDEO) {
                getContext().startActivity(PlaybackService.getPlayerActivityIntent(getContext(), playable));
            }
        });
        convertView.setOnClickListener(v -> {
            if (holder.description.getTag() == Boolean.TRUE) {
                holder.description.setMaxLines(MAX_LINES_COLLAPSED);
                holder.preview.setVisibility(View.GONE);
                holder.description.setTag(Boolean.FALSE);
            } else {
                holder.description.setMaxLines(30);
                holder.description.setTag(Boolean.TRUE);

                holder.preview.setVisibility(item.getMedia() != null ? View.VISIBLE : View.GONE);
                holder.preview.setText(R.string.preview_episode);
            }
        });
        return convertView;
    }

    static class Holder {
        TextView title;
        TextView pubDate;
        TextView description;
        Button preview;
    }
}
