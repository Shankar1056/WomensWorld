package apextechies.womensworld.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shankar on 4/3/2018.
 */

public class VideoModel implements Parcelable{
    protected VideoModel(Parcel in) {
        pro_id = in.readString();
        cat_id = in.readString();
        prod_name = in.readString();
        video_url = in.readString();
        video_id = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    private String pro_id,cat_id, prod_name, video_url, video_id;

    public VideoModel(String pro_id, String cat_id, String prod_name, String video_url, String video_id){
        this.pro_id = pro_id;
        this.cat_id = cat_id;
        this.prod_name = prod_name;
        this.video_url = video_url;
        this.video_id = video_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pro_id);
        dest.writeString(cat_id);
        dest.writeString(prod_name);
        dest.writeString(video_url);
        dest.writeString(video_id);
    }
}
