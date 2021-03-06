package fr.bouyguestelecom.bboxapi.bboxapi.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Epg implements Parcelable, Comparable<Epg> {
    private static final String TAG = Epg.class.getSimpleName();
    private List<Media> medias = new ArrayList<>();
    private List<AudioInfo> audioInfos = new ArrayList<>();
    private List<Reschedule> reschedules = new ArrayList<>();
    private ProgramInfo programInfo;
    private SeriesInfo seriesInfo;
    private String _id;
    private String productId;
    private String title;
    private String parentalGuidance;
    private String lastUpdateTime;
    private String eventId;
    private String externalId;
    private String genre;
    private String startTime;
    private String endTime;
    private String thumb;
    private String channelName;
    private String channelLogo;
    private int epgChannelNumber;
    private int positionId;

    public Epg(JsonReader reader) {
        try {
            if (reader.peek() == JsonToken.BEGIN_OBJECT)
                reader.beginObject();

            else
                reader.skipValue();

            while (reader.hasNext()) {
                String name;

                if (reader.peek() == JsonToken.NAME) {
                    name = reader.nextName();

                    switch (name) {
                        case "_id":
                            if (reader.peek() == JsonToken.STRING)
                                _id = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "thumb":
                            if (reader.peek() == JsonToken.STRING)
                                thumb = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "channelName":
                            if (reader.peek() == JsonToken.STRING)
                                channelName = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "channelLogo":
                            if (reader.peek() == JsonToken.STRING)
                                channelLogo = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "productId":
                            if (reader.peek() == JsonToken.STRING)
                                productId = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "title":
                            if (reader.peek() == JsonToken.STRING)
                                title = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "parentalGuidance":
                            if (reader.peek() == JsonToken.STRING)
                                parentalGuidance = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "lastUpdateTime":
                            if (reader.peek() == JsonToken.STRING)
                                lastUpdateTime = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "genre":
                            if (reader.peek() == JsonToken.STRING)
                                genre = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "eventId":
                            if (reader.peek() == JsonToken.STRING)
                                eventId = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "externalId":
                            if (reader.peek() == JsonToken.STRING)
                                externalId = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "startTime":
                            if (reader.peek() == JsonToken.STRING)
                                startTime = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "endTime":
                            if (reader.peek() == JsonToken.STRING)
                                endTime = reader.nextString();
                            else
                                reader.skipValue();
                            break;

                        case "epgChannelNumber":
                            if (reader.peek() != JsonToken.NULL)
                                epgChannelNumber = reader.nextInt();
                            break;

                        case "positionId":
                            if (reader.peek() != JsonToken.NULL)
                                positionId = reader.nextInt();
                            break;

                        case "programInfo":
                            programInfo = new ProgramInfo(reader);
                            break;

                        case "seriesInfo":
                            seriesInfo = new SeriesInfo(reader);
                            break;

                        case "media":
                            if (reader.peek() != JsonToken.NULL) {
                                medias = getMedias(reader);
                            } else
                                reader.skipValue();
                            break;

                        case "audioInfo":
                            if (reader.peek() != JsonToken.NULL) {
                                audioInfos = getAudioInfos(reader);
                            } else
                                reader.skipValue();
                            break;

                        case "reschedule":
                            if (reader.peek() != JsonToken.NULL) {
                                reschedules = getReschedules(reader);
                            } else
                                reader.skipValue();
                            break;

                        default:
                            reader.skipValue();
                            break;
                    }
                } else if (reader.peek() != JsonToken.END_DOCUMENT)
                    reader.skipValue();

                else
                    break;
            }

            if (reader.peek() == JsonToken.END_OBJECT) {
                reader.endObject();
            } else if (reader.peek() != JsonToken.END_DOCUMENT)
                reader.skipValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Epg(Parcel in) {
        _id = in.readString();
        productId = in.readString();
        title = in.readString();
        parentalGuidance = in.readString();
        lastUpdateTime = in.readString();
        genre = in.readString();
        eventId = in.readString();
        externalId = in.readString();
        thumb = in.readString();
        channelName = in.readString();
        channelLogo = in.readString();
        epgChannelNumber = in.readInt();
        positionId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(productId);
        dest.writeString(title);
        dest.writeString(parentalGuidance);
        dest.writeString(lastUpdateTime);
        dest.writeString(genre);
        dest.writeString(eventId);
        dest.writeString(externalId);
        dest.writeString(thumb);
        dest.writeString(channelName);
        dest.writeString(channelLogo);
        dest.writeInt(epgChannelNumber);
        dest.writeInt(positionId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Epg> CREATOR = new Creator<Epg>() {
        @Override
        public Epg createFromParcel(Parcel in) {
            return new Epg(in);
        }

        @Override
        public Epg[] newArray(int size) {
            return new Epg[size];
        }
    };

    private List<Media> getMedias(JsonReader reader) {
        List<Media> medias = new ArrayList<>();

        try {
            reader.beginArray();

            String url = null;
            int width = 0;
            int height = 0;
            int mediaTypeCode = 0;
            double aspectRatio = 0;

            while (reader.hasNext()) {
                if (reader.peek() == JsonToken.BEGIN_OBJECT)
                    reader.beginObject();

                String name = reader.nextName();

                switch (name) {
                    case Media.TAG_URL:
                        url = reader.nextString();
                        break;

                    case Media.TAG_WIDTH:
                        width = reader.nextInt();
                        break;

                    case Media.TAG_HEIGHT:
                        height = reader.nextInt();
                        break;

                    case Media.TAG_ASPECT_RATIO:
                        aspectRatio = reader.nextDouble();
                        break;

                    case Media.TAG_MEDIA_TYPE_CODE:
                        mediaTypeCode = reader.nextInt();
                        break;

                    default:
                        reader.skipValue();
                        break;
                }

                if (reader.peek() == JsonToken.END_OBJECT) {
                    reader.endObject();
                    medias.add(new Media(url, width, height, mediaTypeCode, aspectRatio));
                }
            }

            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return medias;
    }

    private List<AudioInfo> getAudioInfos(JsonReader reader) {
        List<AudioInfo> audioInfo = new ArrayList<>();

        try {
            reader.beginArray();

            String soundMode = null;
            String language = null;

            while (reader.hasNext()) {
                if (reader.peek() == JsonToken.BEGIN_OBJECT)
                    reader.beginObject();

                String name = reader.nextName();

                switch (name) {
                    case AudioInfo.TAG_SOUND_MODE:
                        soundMode = reader.nextString();
                        break;

                    case AudioInfo.TAG_LANGUAGE:
                        language = reader.nextString();
                        break;

                    default:
                        reader.skipValue();
                        break;
                }

                if (reader.peek() == JsonToken.END_OBJECT) {
                    reader.endObject();
                    audioInfo.add(new AudioInfo(soundMode, language));
                }
            }

            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return audioInfo;
    }

    private List<Reschedule> getReschedules(JsonReader reader) {
        List<Reschedule> reschedules = new ArrayList<>();

        try {
            reader.beginArray();

            String startTime = null;
            String endTime = null;
            String language = null;
            String eventId = null;
            int epgChannelNumber = 0;

            while (reader.hasNext()) {
                if (reader.peek() == JsonToken.BEGIN_OBJECT)
                    reader.beginObject();

                String name = reader.nextName();

                switch (name) {
                    case Reschedule.TAG_START_TIME:
                        if (reader.peek() == JsonToken.STRING)
                            startTime = reader.nextString();
                        else
                            reader.skipValue();
                        break;

                    case Reschedule.TAG_END_TIME:
                        if (reader.peek() == JsonToken.STRING)
                            endTime = reader.nextString();
                        else
                            reader.skipValue();
                        break;

                    case Reschedule.TAG_LANGUAGE:
                        if (reader.peek() == JsonToken.STRING)
                            language = reader.nextString();
                        else
                            reader.skipValue();
                        break;

                    case Reschedule.TAG_EVENT_ID:
                        if (reader.peek() == JsonToken.STRING)
                            eventId = reader.nextString();
                        else
                            reader.skipValue();
                        break;

                    case Reschedule.TAG_EPG_CHANNEL_NUMBER:
                        if (reader.peek() != JsonToken.NULL)
                            epgChannelNumber = reader.nextInt();
                        else
                            reader.skipValue();
                        break;

                    default:
                        reader.skipValue();
                        break;
                }

                if (reader.peek() == JsonToken.END_OBJECT) {
                    reader.endObject();
                    reschedules.add(new Reschedule(startTime, endTime, language, eventId, epgChannelNumber));
                }
            }

            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reschedules;
    }

/*    private List<Reschedule> getReschedules(JSONArray jsonArray) throws JSONException {
        List<Reschedule> reschedules = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            reschedules.add(new Reschedule(jsonObject));
        }

        return reschedules;
    }*/


    public List<Media> getMedias() {
        return medias;
    }

    public List<AudioInfo> getAudioInfos() {
        return audioInfos;
    }

    public List<Reschedule> getReschedules() {
        return reschedules;
    }

    public ProgramInfo getProgramInfo() {
        return programInfo;
    }

    public SeriesInfo getSeriesInfo() {
        return seriesInfo;
    }

    public String getProductId() {
        return productId;
    }

    public String getParentalGuidance() {
        return parentalGuidance;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public String getEventId() {
        return eventId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getEpgChannelNumber() {
        return epgChannelNumber;
    }

    public String getId() {
        return _id;
    }

    public int getPositionId() {
        return positionId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Epg{" +
                "medias=" + medias +
                ", audioInfos=" + audioInfos +
                ", reschedules=" + reschedules +
                ", programInfo=" + programInfo +
                ", seriesInfo=" + seriesInfo +
                ", _id='" + _id + '\'' +
                ", productId='" + productId + '\'' +
                ", title='" + title + '\'' +
                ", parentalGuidance='" + parentalGuidance + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", eventId='" + eventId + '\'' +
                ", externalId='" + externalId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", genre='" + genre + '\'' +
                ", endTime='" + endTime + '\'' +
                ", thumb='" + thumb + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelLogo='" + channelLogo + '\'' +
                ", epgChannelNumber=" + epgChannelNumber +
                ", positionId=" + positionId +
                '}';
    }

    @Override
    public int compareTo(Epg another) {
        if (another == null)
            return 1;

        else if (another.getPositionId() > positionId)
            return -1;

        else if (another.getPositionId() < positionId)
            return 1;

        return 0;
    }
}
