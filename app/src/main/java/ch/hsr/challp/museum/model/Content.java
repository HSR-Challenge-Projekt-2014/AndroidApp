package ch.hsr.challp.museum.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Content implements Parcelable {

    private int previewImageResource;
    private String previewTitle;
    private int imageResource;
    private String title;
    private String contentText;
    private String previewLocation;

    private Content(Parcel in) {
        previewImageResource = in.readInt();
        previewTitle = in.readString();
        imageResource = in.readInt();
        title = in.readString();
        contentText = in.readString();
        previewLocation = in.readString();
    }

    public Content(String previewTitle, int previewImageResource, int imageResource, String title, String contentText, String previewLocation) {
        this.previewImageResource = previewImageResource;
        this.previewTitle = previewTitle;
        this.imageResource = imageResource;
        this.title = title;
        this.contentText = contentText;
        this.previewLocation = previewLocation;
    }

    public int getPreviewImageResource() {
        return previewImageResource;
    }

    public String getPreviewTitle() {
        return previewTitle;
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

    public String getPreviewLocation() {
        return previewLocation;
    }

    @Override
    public String toString() {
        return "Content{" +
                "previewImageResource=" + previewImageResource +
                ", previewTitle='" + previewTitle + '\'' +
                ", imageResource=" + imageResource +
                ", title='" + title + '\'' +
                ", contentText='" + contentText + '\'' +
                ", previewLocation='" + previewLocation + '\'' +
                '}';
    }

    // this is used to regenerate the content. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(previewImageResource);
        parcel.writeString(previewTitle);
        parcel.writeInt(imageResource);
        parcel.writeString(title);
        parcel.writeString(contentText);
        parcel.writeString(previewLocation);
    }

}
