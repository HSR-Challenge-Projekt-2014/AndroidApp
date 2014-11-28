package ch.hsr.challp.museum.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Content implements Parcelable {

    // this is used to regenerate the content. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
    private final int previewImageResource;
    private final int imageResource;
    private final String title;
    private final String contentText;
    private Topic topic;
    private String youTubeId;
    private Room room = null;
    private static List<Content> savedContents = new ArrayList<>();

    private Content(Parcel in) {
        previewImageResource = in.readInt();
        topic = Topic.findById(in.readInt());
        imageResource = in.readInt();
        title = in.readString();
        contentText = in.readString();
        room = Room.findById(in.readInt());
        youTubeId = in.readString();
    }

    public Content(Topic topic, int previewImageResource, int imageResource, String title, String contentText) {
        this(topic, previewImageResource, imageResource, title, contentText, null);
    }

    public Content(Topic topic, int previewImageResource, int imageResource, String title, String contentText, String youTubeId) {
        this.topic = topic;
        this.previewImageResource = previewImageResource;
        this.imageResource = imageResource;
        this.title = title;
        this.contentText = contentText;
        this.youTubeId = youTubeId;
    }

    public static List<Content> getSavedContents() {
        return Collections.unmodifiableList(savedContents);
    }

    public static void saveContent(Content content) {
        savedContents.add(content);
    }

    public int getPreviewImageResource() {
        return previewImageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getContentText() {
        return contentText;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getYouTubeId() {
        return youTubeId;
    }

    public boolean hasYouTubeVideo() {
        return youTubeId != null;
    }

    @Override
    public String toString() {
        return "Content{" +
                "previewImageResource=" + previewImageResource +
                ", topic='" + topic + '\'' +
                ", imageResource=" + imageResource +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", room='" + room + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(previewImageResource);
        parcel.writeInt(topic.getId());
        parcel.writeInt(imageResource);
        parcel.writeString(title);
        parcel.writeString(contentText);
        parcel.writeInt(room.getId());
        parcel.writeString(youTubeId);
    }

    public static void unsaveContent(Content content) {
        savedContents.remove(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        if (imageResource != content.imageResource) return false;
        if (previewImageResource != content.previewImageResource) return false;
        if (contentText != null ? !contentText.equals(content.contentText) : content.contentText != null)
            return false;
        if (room != null ? !room.equals(content.room) : content.room != null) return false;
        if (title != null ? !title.equals(content.title) : content.title != null) return false;
        if (topic != null ? !topic.equals(content.topic) : content.topic != null) return false;
        if (youTubeId != null ? !youTubeId.equals(content.youTubeId) : content.youTubeId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = previewImageResource;
        result = 31 * result + imageResource;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (contentText != null ? contentText.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (youTubeId != null ? youTubeId.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }
}
