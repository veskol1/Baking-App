package com.example.vesko.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable{
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };



    public Steps(Parcel in){
        this.id=in.readString();
        this.shortDescription=in.readString();
        this.description=in.readString();
        this.videoURL=in.readString();
        this.thumbnailURL=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    public Steps(){ }

    public Steps(String id,String shortDescription,String description,String videoURL, String thumbnailURL) {
        this.id=id;
        this.shortDescription=shortDescription;
        this.description=description;
        this.videoURL=videoURL;
        this.thumbnailURL=thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoURL() { return videoURL; }

    public String getThumbnailURL() { return thumbnailURL; }
}

